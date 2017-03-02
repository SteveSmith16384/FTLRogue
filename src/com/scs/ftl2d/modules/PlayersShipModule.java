package com.scs.ftl2d.modules;

import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.ftl2d.GameData;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.events.AbstractEvent;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.missions.AbstractMission;
/*

CONTROLS:
Numbers - select unit
Arrows - move unit
c - close door
d - drop
e - Use equipment
g - Goto TODO
h - Help TODO
i - inventory
k - show Key TODO
m - move TODO
n - Nothing
o - open door
p - Pick up
s - shoot (at nearest) TODO
t - teleport TODO
u - Use console
w - wear TODO

ASCII CODES
Numbers - Players units
A - OxyGen (air)
B - Battery
C - Control panel
E - Engines
e - Egg (alien)
F - On fire
g - Gun
k - knife
M - Medibay
m - Medikit
O - Window
R - Replicator
T - Teleporter
. - Floor
+' - Door (open/closed)

COLOURS
Mapsquares: TODO
Background colour of installations shows type
Foreground colour shows damage


 */
public class PlayersShipModule extends AbstractModule {

	public enum InputMode {Normal, DirectionalMovement, Pickup, Drop, UseEquipment}

	private InputMode inputMode = InputMode.Normal;

	public PlayersShipModule(Main main) {
		super(main);

		if (main.gameData.currentUnit == null) {
			this.selectUnit(0);
		}
	}


	@Override
	public void updateGame() {
		GameData gameData = this.main.gameData;
		gameData.turnNo++;
		
		// Set values to zero as they will be adjusted by the mapsquares
		gameData.shipSpeed = 0;
		gameData.powerGainedPerTurn= 0;
		gameData.powerUsedPerTurn = 0;

		for (int pass=1 ; pass<=2 ; pass++) {
			// Go through map
			for (int y=0 ; y<gameData.getHeight() ; y++) {
				for (int x=0 ; x<gameData.getWidth() ; x++) {
					AbstractMapSquare sq = gameData.map[x][y];
					sq.process(pass);
				}			
			}

			for(AbstractEvent ev : gameData.currentEvents) {
				ev.process(pass);
			}

			for(AbstractMission m : gameData.currentMissions) {
				m.process(pass);
			}
		}

		// POWER
		gameData.powerUsedPerTurn += gameData.shieldPowerLevel;
		gameData.powerUsedPerTurn += gameData.enginePowerLevel;

		gameData.totalPower += gameData.powerGainedPerTurn;
		gameData.totalPower -= gameData.powerUsedPerTurn;
		if (gameData.totalPower < 0) {
			gameData.totalPower = 0;
		} else if (gameData.totalPower > 100) {
			gameData.totalPower = 100;
		}


		if (gameData.shipFlying) {
			// Adjust ship speed by engine power
			if (gameData.totalPower > 0) {
				gameData.shipSpeed *= gameData.enginePowerLevel;
				gameData.distanceToDest -= gameData.shipSpeed;
				if (gameData.distanceToDest <= 0) {
					gameData.distanceToDest = 0;
					gameData.shipFlying = false;
					this.main.addMsg("You have reached your destination!");
					// todo - end?
				}
			}
		} else {
			gameData.shipSpeed = 0;
		}


		/*if (this.currentEvents.size() == 0) {
			this.currentEvents.add(new EnemyShipEvent(main));
		}*/
	}


	private boolean selectUnit(int i) {
		if (i < main.gameData.units.size()) {
			main.gameData.currentUnit = main.gameData.units.get(i);
			main.addMsg("You are controlling " + main.gameData.currentUnit.getName());
			return true;
		}
		return false;

	}


	@Override
	public void drawScreen(IGameView view) throws IOException {
		view.drawPlayersShipScreen(main.gameData, main.gameData.currentUnit);

	}


	/**
	 * Returns whether the game should progress a turn
	 */
	public boolean processInput(KeyStroke ks) {
		try {
			if (inputMode == InputMode.Normal) {
				if (ks.getKeyType() == KeyType.ArrowUp) {
					main.gameData.currentUnit.attemptMove(0, -1);
					return true;
				} else if (ks.getKeyType() == KeyType.ArrowDown) {
					main.gameData.currentUnit.attemptMove(0, 1);
					return true;
				} else if (ks.getKeyType() == KeyType.ArrowLeft) {
					main.gameData.currentUnit.attemptMove(-1, 0);
					return true;
				} else if (ks.getKeyType() == KeyType.ArrowRight) {
					main.gameData.currentUnit.attemptMove(1, 0);
					return true;
				} else {
					if (ks != null) {
						char c = ks.getCharacter();
						switch (c) {
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							int i = Integer.parseInt(c+"");
							this.selectUnit(i-1);
							return false;

						case 'c':
							main.gameData.currentUnit.closeDoor();
							return true;

						case 'd': // Drop
							this.dropMenu();
							return false;

						case 'e': // Use equipment
							this.useEquipmentMenu();
							return false;

						case 'i': // Inventory
							showInventory();
							return false;

						case 'n':
						{
							main.addMsg("You do nothing");
							return true;
						}

						case 'o':
							main.gameData.currentUnit.openDoor();
							return true;

						case 'p': // Pickup
						{
							pickupMenu();
							return false;
						}

						case 's':
							this.main.gameData.currentUnit.checkForShooting();
							return true;

						case 'u':
							main.gameData.currentUnit.useConsole();
							return true;

						case 'w':
							return true;

						default:
							main.addMsg("Unknown key " + c);
						}
					}
				}
			} else if (inputMode == InputMode.DirectionalMovement) {
				char c = ks.getCharacter();
				main.gameData.currentUnit.manualRoute = main.gameData.currentUnit.manualRoute + c;
			} else if (inputMode == InputMode.Pickup) {
				char c = ks.getCharacter();
				switch (c) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					int i = Integer.parseInt(c+"");
					DrawableEntity de = main.gameData.currentUnit.getSq().entities.get(i);
					if (de.canBePickedUp()) {
						main.gameData.currentUnit.getSq().entities.remove(de);
						main.gameData.currentUnit.equipment.add(de);
						main.addMsg("You pick up the " + de.getName());
					} else {
						main.addMsg("You can't pick up the " + de.getName());
					}
				}
				inputMode = InputMode.Normal;
			} else if (inputMode == InputMode.Drop) {
				char c = ks.getCharacter();
				switch (c) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					int i = Integer.parseInt(c+"");
					DrawableEntity de = main.gameData.currentUnit.equipment.get(i);
					main.gameData.currentUnit.equipment.remove(de);
					main.gameData.currentUnit.getSq().entities.add(de);
					main.addMsg("You drop the " + de.getName());
				}
				inputMode = InputMode.Normal;
			} else if (inputMode == InputMode.UseEquipment) {
				char c = ks.getCharacter();
				switch (c) {
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					int i = Integer.parseInt(c+"");
					DrawableEntity de = main.gameData.currentUnit.equipment.get(i);
					main.gameData.currentUnit.currentItem = de;
					main.addMsg("You use the " + de.getName());
				}
				inputMode = InputMode.Normal;
			} else {
				throw new RuntimeException("Unknown input mode: " + inputMode);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			inputMode = InputMode.Normal;
		}
		return false;
	}


	private void pickupMenu() {
		main.addMsg("What to pick up?");
		int i=0;
		for (DrawableEntity de : main.gameData.currentUnit.getSq().entities) {
			if (de.canBePickedUp()) {
				main.addMsg(i + ":" + de.getName());
			}
			i++;
		}
		this.inputMode = InputMode.Pickup;
	}


	private void dropMenu() {
		main.addMsg("What to drop?");
		int i=0;
		for (DrawableEntity de : main.gameData.currentUnit.equipment) {
			main.addMsg(i + ":" + de.getName());
			i++;
		}
		this.inputMode = InputMode.Drop;
	}


	private void useEquipmentMenu() {
		main.addMsg("What to use?");
		int i=0;
		for (DrawableEntity de : main.gameData.currentUnit.equipment) {
			main.addMsg(i + ":" + de.getName());
			i++;
		}
		this.inputMode = InputMode.UseEquipment;
	}


	private void showInventory() {
		main.addMsg(main.gameData.currentUnit.getName() + " is carrying");
		for (DrawableEntity de : main.gameData.currentUnit.equipment) {
			main.addMsg(de.getName());
		}
	}
	
	
}
