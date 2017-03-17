package com.scs.ftl2d.modules;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.GameData;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.ShipLaser;
import com.scs.ftl2d.destinations.EnemyShip;
import com.scs.ftl2d.destinations.Planet;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.entityinterfaces.IHelpIfCarried;
import com.scs.ftl2d.events.AbstractEvent;
import com.scs.ftl2d.events.MeteorStorm;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareControlPanel;
import com.scs.ftl2d.missions.AbstractMission;
import com.scs.ftl2d.modules.inputmodes.ChangeItemInputHandler;
import com.scs.ftl2d.modules.inputmodes.DirectControlInputHandler;
import com.scs.ftl2d.modules.inputmodes.DropItemInputHandler;
import com.scs.ftl2d.modules.inputmodes.IInputHander;
import com.scs.ftl2d.modules.inputmodes.PickupItemInputHandler;
/*

CONTROLS:
Numbers - select unit
Arrows - move unit
? - Help
c - close door
d - drop
e - TODO examine (current/adjacent square)
f - fire ships weapons
g - goto LATER
h - Change current item
i - inventory
l - login to console
m - TEST move to
n - Nothing
o - open door
p - Pick up
s - shoot/select (use current item) TODO
t - throw TODO
u - Use, e.g. prime grenade
w - wear LATER

ASCII CODES
Numbers - Players units
A - OxyGen (air)
B - Battery
C - Control panel
c - corpse
E - Engines
e - Egg (alien)
F - On fire
g - Gun
k - knife
L - Airlock
M - Medibay
m - Medikit
O - Window
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

	//public enum InputMode {Normal, SelectDestination, Pickup, Drop, ChangeCurrentItem, SelectShotTarget, SelectThrowTarget}

	public IInputHander inputHandler;
	private DirectControlInputHandler directControlIH;
	//private InputMode inputMode = InputMode.Normal;

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

		for(AbstractEvent ev : gameData.currentEvents) {
			ev.process();
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
		}

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
		} else if (gameData.totalPower > 100) {
			gameData.totalPower = 100;
		}


		// Flying towards destination?
		if (gameData.currentLocation == null) {
			// Adjust ship speed by engine power
			if (gameData.totalPower > 0) {
				gameData.shipSpeed *= gameData.enginePowerPcent;
				gameData.distanceToDest -= gameData.shipSpeed;
				if (gameData.distanceToDest <= 0) {
					gameData.distanceToDest = 0;
					gameData.currentLocation = new Planet(main, "Rigel 7");
					this.main.addMsg("You have reached your destination!");
					// todo - end?
				}
			}

			// Any encounters?
			int i = Main.RND.nextInt(20);
			switch (i) {
			case 0:
				gameData.currentLocation = new EnemyShip(main);
				break;
			case 1:
				gameData.currentEvents.add(new MeteorStorm(main));
				break;
			}
		} else {
			gameData.shipSpeed = 0;
		}

	}


	public boolean selectUnit(int i) {
		if (i < main.gameData.units.size()) {
			main.gameData.currentUnit = main.gameData.units.get(i);
			main.addMsg("You are controlling " + main.gameData.currentUnit.getName());
			return true;
		}
		return false;

	}


	@Override
	public void drawScreen(IGameView view) throws IOException {
		view.drawPlayersShipScreen(main.gameData, seenSquares, main.asciiEffects, contextSensitiveHelpText, this.route);

	}


	/**
	 * Returns whether the game should progress a turn
	 */
	public boolean processInput(KeyStroke ks) {
		try {
			this.inputHandler.processInput(ks);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.restoreDirectControlIH();
		}
		return true;
	}


	public void pickupMenu() {
		main.addMsg("What to pick up?");
		int i=1;
		for (DrawableEntity de : main.gameData.currentUnit.getSq().getEntities()) {
			if (de instanceof ICarryable) {
				main.addMsg(i + ":" + de.getName());
			}
			i++;
		}
		this.inputHandler = new PickupItemInputHandler(main, this);
	}


	public void dropMenu() {
		main.addMsg("What to drop?");
		int i=1;
		for (ICarryable de : main.gameData.currentUnit.equipment) {
			main.addMsg(i + ":" + de.getName());
		}
		i++;
		this.inputHandler = new DropItemInputHandler(main, this);
	}


	public void changeCurrentItem() {
		main.addMsg("What to use?");
		int i=1;
		for (ICarryable de : main.gameData.currentUnit.equipment) {
			main.addMsg(i + ":" + de.getName());
			i++;
		}
		this.inputHandler = new ChangeItemInputHandler(main, this);
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
					main.addMsg("You have fired at " + main.gameData.currentLocation.name);
					this.main.gameData.weaponTemp += 5;
					this.main.gameData.currentLocation.shot();

					// create bullets
					for (Point p : main.gameData.weaponPoints) {
						this.main.asciiEffects.add(new ShipLaser(main, p.x, p.y, 0, -1, p.x, 0));
					}
				} else {
					main.addMsg("No console here.");
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
