package com.scs.astrocommander.modules;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.scs.astrocommander.GameData;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.asciieffects.ShipLaser;
import com.scs.astrocommander.destinations.EnemyShip;
import com.scs.astrocommander.destinations.Planet;
import com.scs.astrocommander.entities.mobs.Unit;
import com.scs.astrocommander.events.MeteorStorm;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.MapSquareControlPanel;
import com.scs.astrocommander.missions.AbstractMission;
import com.scs.astrocommander.modules.inputmodes.ChangeItemInputHandler;
import com.scs.astrocommander.modules.inputmodes.DirectControlInputHandler;
import com.scs.astrocommander.modules.inputmodes.DropItemInputHandler;
import com.scs.astrocommander.modules.inputmodes.PickupItemInputHandler;
import com.scs.rogueframework.AbstractAsciiEffect;
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.LogMessage;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.ecs.components.IHelpIfCarried;
import com.scs.rogueframework.ecs.entities.DrawableEntity;
import com.scs.rogueframework.input.IInputHander;
/*

ASCII CODES
Numbers - Players units
A - OxyGen (air)
B - Battery
C - Control panel
c - corpse
E - Engines
e - Egg (alien)
F - On fire
g - Grenade
j - oxy mask
k - knife
L - Airlock
M - Medibay
m - Medikit
O - Window
p - pistol
R - Replicator
T - Teleporter
. - Floor
+' - Door (open/closed)

COLOURS
Mapsquares:
Background colour of installations shows type
Foreground colour shows damage (white -> grey)


 */
public class PlayersShipModule extends AbstractModule {

	private static final TextCharacter ROUTE_CHAR = new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
	private static final TextCharacter TARGET_CHAR = new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.RED);
	private static final TextCharacter STAR_CHAR = new TextCharacter('*', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);

	public IInputHander inputHandler;
	private DirectControlInputHandler directControlIH;

	private Map<String, TextCharacter> seenSquares = new HashMap<>();
	private List<String> contextSensitiveHelpText = new ArrayList<>();

	public Point selectedpoint; // For choosing shooting dest etc...  todo - rename with _
	public List<Point> route;

	private List<Point> stars;

	public PlayersShipModule(Main main, AbstractModule prev) {
		super(main, prev);

		if (main.gameData.current_unit == null) {
			this.selectUnit(0);
		}

		directControlIH = new DirectControlInputHandler(main, this);

		restoreDirectControlIH();
	}


	@Override
	public void updateGame() {
		contextSensitiveHelpText.clear();

		GameData gameData = this.main.gameData;
		gameData.turnNo++;

		if (main.checkOxygenFlag) {
			main.checkOxygenFlag = false;
			gameData.checkOxygen();
		}

		main.gameData.wantedLevel = main.gameData.wantedLevel - 0.01f;
		if (main.gameData.wantedLevel < 0) {
			main.gameData.wantedLevel= 0;
		}

		// Set values to zero as they will be adjusted by the mapsquares
		gameData.shipSpeed = 0;
		gameData.powerGainedPerTurn = 0;
		gameData.powerUsedPerTurn = 0;

		// Refresh items in each square - todo - does this need doing?
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				sq.updateItemList();
			}			
		}

		// Go through map
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				sq.process();
			}			
		}

		if (gameData.currentEvent != null) {
			gameData.currentEvent.process();
		}

		for(AbstractMission m : gameData.currentMissions) {
			m.process();
		}

		if (this.main.gameData.currentLocation != null) {
			this.main.gameData.currentLocation.process();
		}

		// Refresh items in each square
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				sq.updateItemList();
			}			
		}

		gameData.recalcVisibleSquares();

		// Create map key
		seenSquares.clear();
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				// Add to key if seen
				if (sq.visible == AbstractMapSquare.VisType.Visible) {
					if (!seenSquares.containsKey(sq.getName())) {
						seenSquares.put(sq.getName(), sq.getChar());
					}
					if (sq.extraInfo.length() > 0) {
						if (!seenSquares.containsKey(sq.extraInfo)) {
							seenSquares.put(sq.extraInfo, sq.getChar());
						}
					}
				}
			}			
		}

		// Help based on current item
		if (this.main.gameData.current_unit.currentItem != null) {
			if (this.main.gameData.current_unit.currentItem instanceof IHelpIfCarried) {
				IHelpIfCarried help = (IHelpIfCarried)this.main.gameData.current_unit.currentItem;
				this.contextSensitiveHelpText.add(help.getHelpIfCarried());
			}
			this.contextSensitiveHelpText.add("Press 'd' to drop the current item.");
		}

		// todo - help based on item in square

		// Help based on adjacent mapsquares
		List<AbstractMapSquare> squares = main.gameData.getAdjacentSquares(this.main.gameData.current_unit.x,  this.main.gameData.current_unit.y);
		for (AbstractMapSquare sq : squares) {
			String help = sq.getHelp();
			if (help != null && help.length() > 0) {
				this.contextSensitiveHelpText.add(help);
			}
		}



		// ALL NON_MAP STUFF HERE

		// Weapons
		gameData.weaponTemp -= 1;
		if (gameData.weaponTemp < 0) {
			gameData.weaponTemp = 0;
		}


		// POWER
		gameData.powerUsedPerTurn += (gameData.shieldPowerPcent / 10);
		if (gameData.currentLocation == null) {
			gameData.powerUsedPerTurn += (gameData.enginePowerPcent / 10);
		}
		gameData.totalPower += gameData.powerGainedPerTurn;
		gameData.totalPower -= gameData.powerUsedPerTurn;
		if (gameData.totalPower < 0) {
			gameData.totalPower = 0;
			gameData.shieldPowerPcent = 0;
			gameData.enginePowerPcent = 0;
			main.addMsg(3, "SHIP OUT OF POWER");
		} else if (gameData.totalPower > 100) {
			gameData.totalPower = 100;
		}


		// Flying towards destination?
		if (gameData.currentLocation == null) {
			// Adjust ship speed by engine power
			if (gameData.totalPower > 0) {
				gameData.shipSpeed *= gameData.enginePowerPcent;
				gameData.distanceToDest -= (gameData.shipSpeed / 100);
				if (gameData.distanceToDest <= 0) {
					gameData.distanceToDest = 0;
					gameData.currentLocation = new Planet(main, "Rigel 7");
					this.main.addMsg("You have reached your destination!");
					main.gameOver();
					return;
				}

			}

			// Any encounters?
			if (main.gameData.currentEvent == null) {
				int i = Main.RND.nextInt(20);
				switch (i) {
				case 0:
					gameData.currentLocation = new EnemyShip(main);
					break;

				case 1:
					gameData.currentEvent = new MeteorStorm(main);
					break;

				case 2:
					//gameData.currentLocation = new PoliceShip(main);
					break;

				case 3:
					//gameData.currentLocation = new EmptyHulk(main);
					break;

				}
			}

			if (gameData.currentLocation != null) {
				gameData.enginePowerPcent = 0; // Stop engines
			}
		} else {
			gameData.shipSpeed = 0;
		}

	}


	public boolean selectUnit(int i) {
		if (i < main.gameData.players_units.size()) {
			AbstractMapSquare sq1 = null;
			if (main.gameData.current_unit != null) {
				sq1 = main.gameData.current_unit.getSq();
			}
			Unit unit = main.gameData.players_units.get(i);
			if (unit.isAlive()) {
				main.gameData.current_unit = unit;//main.gameData.units.get(i);
				AbstractMapSquare sq2 = main.gameData.current_unit.getSq();
				main.addMsg("You are controlling " + main.gameData.current_unit.getName());
				main.addMsg("(Press the unit number to select another)");

				if (sq1 != null) {
					sq1.calcChar();
				}
				sq2.calcChar();
				return true;
			} else {
				main.addMsg("That unit is dead");
			}
		}
		return false;

	}


	@Override
	public void drawScreen(IGameView view) throws IOException {
		GameData gameData = main.gameData;
		
		if (stars == null) {
			createStars(gameData);
		}

		view.startScreen();
		view.clear();

		//TextGraphics tGraphics = screen.newTextGraphics();

		if (gameData.shipSpeed > 0 && gameData.currentLocation == null) {
			this.moveStars(gameData);
		}
		drawStars(view);

		// Draw map
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				TextCharacter tc = sq.getChar();
				if (sq.type != AbstractMapSquare.MAP_NOTHING) {
					view.drawCharacter(x, y, tc);
				}
			}			
		}

		// Draw route
		if (route != null) {
			for (Point p : route) {
				view.drawCharacter(p.x, p.y, ROUTE_CHAR);
			}
		}
		if (selectedpoint != null) {
			view.drawCharacter(selectedpoint.x, selectedpoint.y, TARGET_CHAR);
		}

		// Draw effects
		for (AbstractAsciiEffect effect : main.asciiEffects) {
			effect.drawChars(view);
		}


		// Draw stats
		int x = gameData.getWidth()+2;
		int y = 0;

		if (gameData.currentLocation == null) {
			view.drawString(x, y++, "LOCATION: Deep Space");
		} else {
			view.setTextForegroundColor(TextColor.ANSI.YELLOW);
			view.drawString(x, y++, "LOCATION: " + gameData.currentLocation.getName());
		}

		view.setTextForegroundColor(TextColor.ANSI.WHITE);
		view.drawString(x, y++, "Turn " + gameData.turnNo);
		view.drawString(x, y++, "Oxygen: " + (int)gameData.oxygenLevel + "%");
		view.drawString(x, y++, "Shields: " + (int)gameData.shieldPowerPcent + "%");
		view.drawString(x, y++, "Engines: " + (int)gameData.enginePowerPcent + "%");
		view.drawString(x, y++, "Weapons: " + (int)gameData.weaponPowerPcent + "%");
		view.drawString(x, y++, "Weapon Temp: " + (int)gameData.weaponTemp + "c");
		view.drawString(x, y++, "Wanted Level: " + (int)gameData.wantedLevel);

		if (gameData.currentLocation == null) {
			y++;
			view.drawString(x, y++, "Ship Speed: " + (int)gameData.shipSpeed + " m/s");
			view.drawString(x, y++, "Distance Left: " + (int)gameData.distanceToDest + " ly");
		}

		y++;
		view.drawString(x, y++, "POWER");
		view.drawString(x, y++, "Total Power: " + (int)gameData.totalPower);
		view.drawString(x, y++, "Power Gain/t: " + (int)gameData.powerGainedPerTurn);
		view.drawString(x, y++, "Power Used/t: " + (int)gameData.powerUsedPerTurn);

		// Draw mapsquares key
		y++;
		for (String tc : seenSquares.keySet()) {
			view.drawCharacter(x, y, seenSquares.get(tc));
			view.drawString(x+2, y, tc);
			y++;
		}

		int col2height = y;

		// Draw units
		x = 54;
		y=2;
		view.drawString(x, y++, "CREW");
		int i=1;
		for (Unit unit : gameData.players_units) {
			StringBuilder str = new StringBuilder();
			if (unit == gameData.current_unit) {
				str.append("*");
			}
			str.append((i++) + ") ");
			str.append(unit.getName()).append(" ");
			if (unit.health > 0) {
				str.append("H:" + (int)unit.health + "%").append(" ");
				str.append("F:" + (int)unit.food);
			} else {
				str.append("DEAD");
			}
			view.drawString(x, y++, str.toString());
		}

		if (gameData.current_unit.wearing != null) {
			view.drawString(x, y++, "Unit is wearing " + gameData.current_unit.wearing.hashCode());
		}

		// Say what items the unit is near 
		StringBuffer itemlist = new StringBuffer();
		for (DrawableEntity de : gameData.current_unit.getSq().getEntities()) {
			if (de instanceof Unit == false) {
				itemlist.append(de.getName() + "; ");
			}
		}
		if (itemlist.length() > 0) {
			view.drawString(x, y++, "Unit can see " + itemlist.toString());
		}

		// Location stats:
		if (gameData.currentLocation != null) {
			y++;
			view.drawString(x, y++, gameData.currentLocation.getName() + ":");
			List<String> stats = gameData.currentLocation.getStats();
			for (String s : stats) {
				view.drawString(x, y++, s);
			}
		}

		// Help text
		if (this.contextSensitiveHelpText.size() > 0) {
			y++;
			view.drawString(x, y++, "HELP");
			for (String s : contextSensitiveHelpText) {
				while (s.contains("\n")) {
					String s2 = s.substring(0, s.indexOf("\n"));
					view.drawString(x, y++, s2);
					s = s.substring(s2.length()+1, s.length());
				}
				view.drawString(x, y++, s);
			}
		}


		// Messages/log
		y = Math.max(Math.max(col2height, y), gameData.getHeight()) + 2;
		view.setTextForegroundColor(TextColor.ANSI.YELLOW);
		for (LogMessage msg : gameData.msgs) {
			switch (msg.priority) {
			case 3:
				view.setTextForegroundColor(TextColor.ANSI.RED);
				break;
			case 2:
				view.setTextForegroundColor(TextColor.ANSI.YELLOW);
				break;
			default:
				view.setTextForegroundColor(TextColor.ANSI.WHITE);
				break;
			}
			view.drawString(0, y, msg.msg);
			y++;
		}

		if (Settings.DEBUG) {
			view.setTextForegroundColor(TextColor.ANSI.RED);
			view.drawString(0, 0, "DEBUG MODE");
		}


		view.refresh();
	}


	/**
	 * Returns whether the game should progress a turn
	 */
	public boolean processInput(KeyStroke ks) {
		try {
			return this.inputHandler.processInput(ks);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.restoreDirectControlIH();
		}
		return false;
	}


	public void pickupMenu() {
		if (main.gameData.current_unit.getSq().getEntities().isEmpty() == false) {
			main.addMsg("What to pick up?");
			int i=1;
			for (DrawableEntity de : main.gameData.current_unit.getSq().getEntities()) {
				if (de instanceof ICarryable) {
					main.addMsg(i + ":" + de.getName());
				}
				i++;
			}
			this.inputHandler = new PickupItemInputHandler(main, this);
		} else {
			main.addMsg("There is nothing to pick up");
		}
	}


	public void dropMenu() {
		if (main.gameData.current_unit.equipment.size() > 0) {
			main.addMsg("What to drop?");
			int i=1;
			for (ICarryable de : main.gameData.current_unit.equipment) {
				main.addMsg(i + ":" + de.getName());
			}
			i++;
			this.inputHandler = new DropItemInputHandler(main, this);
		} else {
			main.addMsg("The current unit isn't using aything");
		}
	}


	public void changeCurrentItem() {
		if (main.gameData.current_unit.equipment.size() > 0) {
			main.addMsg("Which item do you want the unit to use?");
			int i=1;
			for (ICarryable de : main.gameData.current_unit.equipment) {
				main.addMsg(i + ":" + de.getName());
				i++;
			}
			this.inputHandler = new ChangeItemInputHandler(main, this);
		} else {
			main.addMsg("The current unit isn't using aything");
		}
	}


	public void showInventory() {
		main.addMsg(main.gameData.current_unit.getName() + " is carrying");
		for (ICarryable de : main.gameData.current_unit.equipment) {
			main.addMsg(de.getName());
		}
	}


	public void fireShipsWeapons() {
		if (this.main.gameData.weaponTemp < 100) {
			if (this.main.gameData.currentLocation != null) {
				// Check next to console
				MapSquareControlPanel sq = (MapSquareControlPanel)main.gameData.findAdjacentMapSquare(this.main.gameData.current_unit.x, this.main.gameData.current_unit.y, AbstractMapSquare.MAP_CONTROL_PANEL);
				if (sq != null) {
					main.sfx.playSound("laser4.mp3");
					main.addMsg("You have fired at " + main.gameData.currentLocation.getName());
					this.main.gameData.weaponTemp += 5;
					this.main.gameData.currentLocation.shotByPlayer();

					// create bullets
					for (Point p : main.gameData.weaponPoints) {
						this.main.addAsciiEffect(new ShipLaser(main, p.x, p.y, 0, -1, p.x, 0));
					}
				} else {
					main.addMsg("You must be next to the command console.");
				}
			} else {
				main.addMsg("There is nothing to shoot at");
			}
		} else {
			main.addMsg("Weapons are too hot!");
		}

	}


	public void restoreDirectControlIH() {
		this.inputHandler = this.directControlIH;
	}


	private void createStars(GameData gameData) {
		stars = new ArrayList<>();
		for (int i=0 ; i<20 ; i++) {
			int x = Main.RND.nextInt(gameData.getMapWidth());
			int y = Main.RND.nextInt(gameData.getMapHeight());
			this.stars.add(new Point(x, y));
		}
	}


	private void moveStars(GameData gameData) {
		for (Point p : stars) {
			p.y++;
			// if drop of bottom, put back to top
			if (p.y > gameData.getHeight()) {
				p.x = Main.RND.nextInt(gameData.getMapWidth());
				p.y = 0;
			}
		}
	}


	private void drawStars(IGameView view) {
		for (Point p : stars) {
			view.drawCharacter(p.x,  p.y, STAR_CHAR);
		}
	}

}
