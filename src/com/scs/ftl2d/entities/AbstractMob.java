package com.scs.ftl2d.entities;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareDoor;

public abstract class AbstractMob extends DrawableEntity {
	
	// Sides
	public static final int SIDE_PLAYER = 0;
	public static final int SIDE_ALIEN = 1;

	public char theChar;
	public float health = 100f;
	public String name;
	public int side;
	public List<Entity> equipment = new ArrayList<>();

	public AbstractMob(Main main, int _x, int _y, int _z, char c, String _name, int _side) {
		super(main, _x, _y, _z);
		
		theChar = c;
		name = _name;
		side = _side;

	}
	

	public String getName() {
		return name;
	}


	protected void incHealth(float f, String reason) {
		this.health += f;
		if (this.health > 100f) {
			this.health = 100f;
		} else if (health <= 0) {
			died(reason);
		}
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


	private void died(String reason) {
		main.addMsg(this.getName() + " has died of " + reason);
		// todo - remove from list if ours
	}




}
