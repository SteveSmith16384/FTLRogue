package com.scs.astrocommander.modules;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.GameData;
import com.scs.astrocommander.IGameView;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.asciieffects.ShipLaser;
import com.scs.astrocommander.destinations.EnemyShip;
import com.scs.astrocommander.destinations.Planet;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entities.mobs.Unit;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.entityinterfaces.IHelpIfCarried;
import com.scs.astrocommander.events.MeteorStorm;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.MapSquareControlPanel;
import com.scs.astrocommander.missions.AbstractMission;
import com.scs.astrocommander.modules.inputmodes.ChangeItemInputHandler;
import com.scs.astrocommander.modules.inputmodes.DirectControlInputHandler;
import com.scs.astrocommander.modules.inputmodes.DropItemInputHandler;
import com.scs.astrocommander.modules.inputmodes.IInputHander;
import com.scs.astrocommander.modules.inputmodes.PickupItemInputHandler;
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

	public IInputHander inputHandler;
	private DirectControlInputHandler directControlIH;

	private Map<String, TextCharacter> seenSquares = new HashMap<>();
	private List<String> contextSensitiveHelpText = new ArrayList<>();

	public Point selectedpoint; // For choosing shooting dest etc...
	public List<Point> route;

	public PlayersShipModule(Main main, AbstractModule prev) {
		super(main, prev);

		if (main.gameData.currentUnit == null) {
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
		gameData.powerGainedPerTurn= 0;
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
		if (this.main.gameData.currentUnit.currentItem != null) {
			if (this.main.gameData.currentUnit.currentItem instanceof IHelpIfCarried) {
				IHelpIfCarried help = (IHelpIfCarried)this.main.gameData.currentUnit.currentItem;
				this.contextSensitiveHelpText.add(help.getHelpIfCarried());
			}
			this.contextSensitiveHelpText.add("Press 'd' to drop the current item.");
		}

		// todo - help based on item in square

		// Help based on adjacent mapsquares
		List<AbstractMapSquare> squares = main.gameData.getAdjacentSquares(this.main.gameData.currentUnit.x,  this.main.gameData.currentUnit.y);
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
		if (i < main.gameData.units.size()) {
			AbstractMapSquare sq1 = null;
			if (main.gameData.currentUnit != null) {
				sq1 = main.gameData.currentUnit.getSq();
			}
			Unit unit = main.gameData.units.get(i);
			if (unit.isAlive()) {
				main.gameData.currentUnit = unit;//main.gameData.units.get(i);
				AbstractMapSquare sq2 = main.gameData.currentUnit.getSq();
				main.addMsg("You are controlling " + main.gameData.currentUnit.getName());
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
		view.drawPlayersShipScreen(main.gameData, seenSquares, main.getAsciiEffects(), contextSensitiveHelpText, this.route, selectedpoint);

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
		if (main.gameData.currentUnit.getSq().getEntities().isEmpty() == false) {
			main.addMsg("What to pick up?");
			int i=1;
			for (DrawableEntity de : main.gameData.currentUnit.getSq().getEntities()) {
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
		if (main.gameData.currentUnit.equipment.size() > 0) {
			main.addMsg("What to drop?");
			int i=1;
			for (ICarryable de : main.gameData.currentUnit.equipment) {
				main.addMsg(i + ":" + de.getName());
			}
			i++;
			this.inputHandler = new DropItemInputHandler(main, this);
		} else {
			main.addMsg("The current unit isn't using aything");
		}
	}


	public void changeCurrentItem() {
		if (main.gameData.currentUnit.equipment.size() > 0) {
			main.addMsg("Which item do you want the unit to use?");
			int i=1;
			for (ICarryable de : main.gameData.currentUnit.equipment) {
				main.addMsg(i + ":" + de.getName());
				i++;
			}
			this.inputHandler = new ChangeItemInputHandler(main, this);
		} else {
			main.addMsg("The current unit isn't using aything");
		}
	}


	public void showInventory() {
		main.addMsg(main.gameData.currentUnit.getName() + " is carrying");
		for (ICarryable de : main.gameData.currentUnit.equipment) {
			main.addMsg(de.getName());
		}
	}


	public void fireShipsWeapons() {
		if (this.main.gameData.weaponTemp < 100) {
			if (this.main.gameData.currentLocation != null) {
				// Check next to console
				MapSquareControlPanel sq = (MapSquareControlPanel)main.gameData.findAdjacentMapSquare(this.main.gameData.currentUnit.x, this.main.gameData.currentUnit.y, AbstractMapSquare.MAP_CONTROL_PANEL);
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

}
