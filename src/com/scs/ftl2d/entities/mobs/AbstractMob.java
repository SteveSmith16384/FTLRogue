package com.scs.ftl2d.entities.mobs;

import java.util.ArrayList;
import java.util.List;

import ssmith.astar.WayPoints;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.Entity;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareDoor;

public abstract class AbstractMob extends DrawableEntity {

	public enum Status {Moving, Waiting, Eating, Repairing}

	// Sides
	public static final int SIDE_PLAYER = 0;
	public static final int SIDE_ALIEN = 1;

	public char theChar;
	public float health = 100f;
	public String name;
	public int side;
	public List<DrawableEntity> equipment = new ArrayList<>();
	public String manualRoute = "";
	public WayPoints astarRoute;
	public Status status = Status.Waiting;
	public Entity currentItem;

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


	public boolean moveTowards(int x, int y) {
		return attemptMove(Integer.signum(x-this.x), Integer.signum(y-this.y));
	}
	
	
	public boolean attemptMove(int offx, int offy) {
		if (offx == 0 && offy == 0) {
			return false;
		}
		
		AbstractMapSquare newsq = main.gameData.map[x+offx][y+offy];
		if (newsq.isTraversable()) {
			Unit other = main.gameData.getUnitAt(x+offx, y+offy);
			if (other == null) {
				AbstractMapSquare oldsq = main.gameData.map[x][y];
				oldsq.entities.remove(this);

				x += offx;
				y += offy;
				newsq.entities.add(this);
				return true;
			} else {
				if (this.side == other.side) {
					main.addMsg(other.getName() + " is in the way");
				} else {
					// todo - fight
				}
			}
		} else {
			main.addMsg(newsq.getName() + " is in the way");
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
		for(DrawableEntity eq : this.equipment) {
			super.getSq().entities.add(eq); // Drop the equipment
		}
		main.gameData.units.remove(this); // Remove from list if ours
		super.remove();
	}


	public AbstractMob getClosestVisibleEnemy() {
		AbstractMob closest = null;
		float closestDistance = 9999;

		for (int y=0 ; y<main.gameData.getHeight() ; y++) {
			for (int x=0 ; x<main.gameData.getWidth() ; x++) {
				AbstractMapSquare sq = main.gameData.map[x][y];
				for (DrawableEntity e : sq.entities) {
					if (e instanceof AbstractMob) {
						AbstractMob m = (AbstractMob)e;
						if (m.side != this.side) {
							float dist = this.distanceTo(m); 
							if (dist < closestDistance) {
								if (this.canSee(m)) {
									closest = m;
									closestDistance = dist;
								}
							}
						}
					}
				}
			}			
		}

		return closest;

	}


}
