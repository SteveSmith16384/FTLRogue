package com.scs.ftl2d.entities.mobs;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ssmith.astar.WayPoints;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.items.AbstractItem;
import com.scs.ftl2d.entities.items.AbstractRangedWeapon;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareDoor;

public abstract class AbstractMob extends DrawableEntity {

	public enum Status {Waiting, Moving, Eating, Repairing, Healing}

	public static List<AbstractMob> AllMobs = new ArrayList<>();


	// Sides
	public static final int SIDE_PLAYER = 0;
	public static final int SIDE_ALIEN = 1;
	public static final int SIDE_ENEMY_SHIP = 2;

	public char theChar;
	public String name;
	public int side;
	public List<AbstractItem> equipment = new ArrayList<>();
	public String manualRoute = "";
	public WayPoints astarRoute;
	public AbstractItem currentItem;

	// Stats
	public float health = 100f;
	public int att;
	public int def;

	public AbstractMob(Main main, int _x, int _y, int _z, char c, String _name, int _side, float hlth, int _att, int _def) {
		super(main, _x, _y, _z);

		theChar = c;
		name = _name;
		side = _side;
		
		health = hlth;
		att = _att;
		def = _def;

		AllMobs.add(this);
	}

	
	@Override
	public char getChar() {
		return theChar;
	}


	public static String GetRandomName() throws IOException {
		Path path = FileSystems.getDefault().getPath("./data/", "names.txt");
		List<String> lines = Files.readAllLines(path);
		int i = Main.RND.nextInt(lines.size());
		return lines.get(i);
	}
	
	
	public void remove() {
		AllMobs.remove(this);
		super.remove();
	}

	@Override
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
				oldsq.removeEntity(this);

				x += offx;
				y += offy;
				newsq.addEntity(this);
				return true;
			} else {
				if (this.side == other.side) {
					addMsgIfOurs(other.getName() + " is in the way");
				} else {
					this.meleeCombat(other);
				}
			}
		} else {
			addMsgIfOurs(newsq.getName() + " is in the way");
		}
		return false;
	}

	
	public void checkForShooting() {
		if (this.currentItem != null) {
			AbstractItem i = (AbstractItem)this.currentItem;
			if (i instanceof AbstractRangedWeapon) {
				/*for (AbstractMob mob : AbstractMob.AllMobs) {
					if (mob.side != this.side) {
						if (this.canSee(mob)) {
						}
					}
				}*/
				AbstractRangedWeapon gun = (AbstractRangedWeapon)i;
				AbstractMob enemy = getClosestVisibleEnemy();
				if (enemy != null) {
				// todo - check range
					float dist = this.distanceTo(enemy);
					if (dist <= gun.getRange()) {
						
					}
				}
			}
		}
	}


	public void meleeCombat(AbstractMob other) {
		int tot = Math.max(0, this.att - other.def);
		int dam = Main.RND.nextInt(tot)+1;
		other.incHealth(-dam, this.getName());
		
	}
	
	
	protected void addMsgIfOurs(String s) {
		if (this.side == SIDE_PLAYER) {
			main.addMsg(s);
		}
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


	public void died(String reason) {
		main.addMsg(this.getName() + " has died of " + reason);
		for(DrawableEntity eq : this.equipment) {
			super.getSq().addEntity(eq); // Drop the equipment
		}
		main.gameData.units.remove(this); // Remove from list if ours
		if (main.gameData.units.isEmpty()) {
			main.addMsg("GAME OVER!");
			main.gameStage = 1;
		}
		remove();
	}


	protected AbstractMob getClosestVisibleEnemy() {
		AbstractMob closest = null;
		float closestDistance = 9999;
		
		for (int y=0 ; y<main.gameData.getHeight() ; y++) {
			for (int x=0 ; x<main.gameData.getWidth() ; x++) {
				AbstractMapSquare sq = main.gameData.map[x][y];
				for (DrawableEntity e : sq.getEntities()) {
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
