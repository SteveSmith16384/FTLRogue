package com.scs.ftl2d.entities;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareControlPanel;
import com.scs.ftl2d.map.MapSquareDoor;
import com.scs.ftl2d.map.MapSquareReplicator;


public class Unit extends DrawableEntity {

	public char theChar;
	public float health = 100f;
	public float food = 100f;
	public String name;
	public int side;
	public List<Entity> equipment = new ArrayList<>();

	public Unit(Main main, char c, int x, int y, String _name, int _side) {
		super(main, x, y, DrawableEntity.Z_UNIT);

		theChar = c;
		name = _name;
		side = _side;
	}


	@Override
	public char getChar() {
		return theChar;
	}


	@Override
	public void process() {
		main.gameData.incOxygenLevel(-0.1f);
		if (main.gameData.oxygenLevel <= 0) {
			main.addMsg(this.getName() + " is suffocating!");
		}

		// Are we next to a replicator?
		if (this.food < 100) {
			MapSquareReplicator sqr = (MapSquareReplicator)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_REPLICATOR);
			if (sqr != null) {
				this.incFood(10);
				this.main.addMsg(this.getName() + " eats some food");
			}
		}

		this.incFood(-0.25f);
		if (this.food <= 0) {
			main.addMsg(this.getName() + " is starving!");
			this.incHealth(-5, "starvation");
		} else if (this.food < 20) {
			main.addMsg(this.getName() + " is hungry");
		}

		// Are we on a medibay?
		AbstractMapSquare sq = this.getSq();
		if (sq.type == AbstractMapSquare.MAP_MEDIBAY) {
			this.incHealth(1, "");
		}

	}


	private void incHealth(float f, String reason) {
		this.health += f;
		if (this.health > 100f) {
			this.health = 100f;
		} else if (health <= 0) {
			died(reason);
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


	private void died(String reason) {
		main.addMsg(this.getName() + " has died of " + reason);
	}



	public boolean attemptMove(int offx, int offy) {
		AbstractMapSquare newsq = main.gameData.map[x+offx][y+offy];
		if (newsq.isTraversable()) {
			Unit other = main.gameData.getUnitAt(x+offx, y+offy);
			if (other == null) {
				AbstractMapSquare oldsq = main.gameData.map[x][y];
				oldsq.items.remove(this);

				x += offx;
				y += offy;
				newsq.items.add(this);
				return true;
			} else {
				if (this.side == other.side) {
					main.addMsg(other.getName() + " is in the way");
				} else {
					// todo - fight
				}
			}
		}
		return false;
	}


	public void openDoor() {
		MapSquareDoor sq = (MapSquareDoor)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_DOOR);
		if (sq != null) {
			if (!sq.isOpen()) {
				sq.setOpen(true);
				main.addMsg("Door opened");
			} else {
				main.addMsg("Door already open");
			}
		} else {
			main.addMsg("No door found");
		}
	}


	public void closeDoor() {
		MapSquareDoor sq = (MapSquareDoor)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_DOOR);
		if (sq != null) {
			if (sq.isOpen()) {
				sq.setOpen(false);
				main.addMsg("Door closed");
			} else {
				main.addMsg("Door already closed");
			}
		} else {
			main.addMsg("No door found");
		}
	}


	public void useConsole() {
		MapSquareControlPanel sq = (MapSquareControlPanel)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_CONTROL_PANEL);
		if (sq != null) {
		}		
	}


	public String getName() {
		return name;
	}

}
