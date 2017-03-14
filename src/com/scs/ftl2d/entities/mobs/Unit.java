package com.scs.ftl2d.entities.mobs;

import java.awt.Point;
import java.io.IOException;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareControlPanel;
import com.scs.ftl2d.map.MapSquareReplicator;
import com.scs.ftl2d.modules.consoles.CommandConsole;

public class Unit extends AbstractMob {

	public float food = 100f;
	public float tiredness = 0;
	public Status status = Status.Waiting;

	public Unit(Main main, char c, int x, int y) throws IOException {
		super(main, x, y, DrawableEntity.Z_UNIT, c, AbstractMob.GetRandomName(), SIDE_PLAYER, 20, 5, true);
	}


	@Override
	public void preProcess() {

	}


	@Override
	public void process() {
		status = Status.Waiting;

		// Can we see anything to shoot at?
		if (this != this.main.gameData.currentUnit) {
			checkForShooting();
		}


		this.incFood(-0.25f);
		// Are we next to a replicator?
		if (status == Status.Waiting) {
			if (this.food < 99f) {
				MapSquareReplicator sqr = (MapSquareReplicator)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_REPLICATOR);
				if (sqr != null) {
					if (main.gameData.totalPower > 0) {
						main.gameData.powerUsedPerTurn += 1f;
						this.incFood(10);
						this.main.addMsg(this.getName() + " eats some food");
						status = Status.Eating;
					}
				}
			}
		}

		if (this.food <= 0) {
			main.addMsg(this.getName() + " is starving!");
			this.incHealth(-5, "starvation");
		} else if (this.food < 20) {
			main.addMsg(this.getName() + " is hungry");
		}

		// Are we on a medibay?
		if (status == Status.Waiting) {
			AbstractMapSquare sq = this.getSq();
			if (sq.type == AbstractMapSquare.MAP_MEDIBAY) {
				if (main.gameData.totalPower > 0) {
					main.gameData.powerUsedPerTurn += 1f;
					this.incHealth(1, "");
					main.addMsg(this.getName() + " is healing");
					status = Status.Healing;
				}
			}
		}

		// Repair something
		if (status == Status.Waiting) {
			AbstractMapSquare sqr = main.gameData.findAdjacentRepairableMapSquare(x, y);
			if (sqr != null) {
				sqr.incDamage(-(Main.RND.nextInt(5) + 1));
				this.main.addMsg(this.getName() + " repairs the " + sqr.getName());
				status = Status.Repairing;
			}
		}

		// Moving
		if (status == Status.Waiting && this != this.main.gameData.currentUnit) {
			if (manualRoute.length() > 0) {
				processManualRoute(manualRoute.substring(0, 1));
				manualRoute = manualRoute.substring(1);
			}				
		}


		if (status == Status.Waiting) {
			this.incTiredness(-1f);
		}

		//} else if (pass == 2) {
		main.gameData.incOxygenLevel(-0.1f);
		if (main.gameData.oxygenLevel <= 0) {
			main.addMsg(this.getName() + " is suffocating!");
		}

		//}
	}


	private void processManualRoute(String dir) {
		switch (dir.toLowerCase()) {
		case "u":
		case "n":
			this.moveTowards(0, -1);
			break;
		case "d":
		case "s":
			this.moveTowards(0, 1);
			break;
		case "l":
		case "w":
			this.moveTowards(-1, 0);
			break;
		case "r":
		case "e":
			this.moveTowards(1, 0);
			break;
		default:
			main.addMsg("Unknown movement char: " + dir);
		}
	}


	private void incFood(float f) {
		this.food += f;
		if (this.food > 100f) {
			this.food = 100f;
		} else if (this.food <= 0) {
			this.food = 0;
		}
	}


	public void useConsole() {
		MapSquareControlPanel sq = (MapSquareControlPanel)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_CONTROL_PANEL);
		if (sq != null) {
			main.setModule(new CommandConsole(main, main.getCurrentModule()));
		} else {
			/*if (main.gameData.currentLocation != null) {
				MapSquareWeaponsConsole wsq = (MapSquareWeaponsConsole)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_WEAPON_CONSOLE);
				if (wsq != null) {
					if (main.gameData.weaponTemp <= 0) {
						main.fireShipsWeapons();
					}
				}
			} else {
				main.addMsg("There is nothing to shoot at.");
			}*/
			main.addMsg("No console here.");
		}
	}


	public void incTiredness(float f) {
		this.tiredness += f;
		if (this.tiredness > 100f) {
			this.tiredness = 100f;
		} else if (this.tiredness <= 0) {
			this.tiredness = 0;
		}
	}


	@Override
	public Point getAStarDest() {
		return null;
	}



}
