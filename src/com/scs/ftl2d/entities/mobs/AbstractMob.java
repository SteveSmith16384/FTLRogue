package com.scs.ftl2d.entities.mobs;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ssmith.astar.AStar;
import ssmith.astar.WayPoints;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.Settings;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.items.AbstractItem;
import com.scs.ftl2d.entities.items.AbstractRangedWeapon;
import com.scs.ftl2d.entities.items.Corpse;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.AbstractMapSquare.VisType;
import com.scs.ftl2d.map.MapSquareDoor;

public abstract class AbstractMob extends DrawableEntity {

	public enum Status {Waiting, Moving, Eating, Repairing, Healing}

	public static List<AbstractMob> AllMobs = new ArrayList<>();


	// Sides
	public static final int SIDE_PLAYER = 0;
	public static final int SIDE_ALIEN = 1;
	public static final int SIDE_ENEMY_SHIP = 2;
	public static final int SIDE_POLICE = 3;

	public char theChar;
	public String name;
	public int side;
	public List<AbstractItem> equipment = new ArrayList<>();
	public String manualRoute = "";
	public AbstractItem currentItem;
	protected boolean autoOpenDoors;

	private WayPoints astarRoute;
	//private Point aStarDest;


	// Stats
	public float health = 100f;
	public int meleeSkill;

	public AbstractMob(Main main, int _x, int _y, int _z, char c, String _name, int _side, float hlth, int _combat, boolean _autoOpenDoors) {
		super(main, _x, _y, _z);

		theChar = c;
		name = _name;
		side = _side;

		health = hlth;
		meleeSkill = _combat;

		autoOpenDoors = _autoOpenDoors;

		AllMobs.add(this);
	}


	@Override
	public void preProcess() {

	}


	@Override
	public char getChar() {
		return theChar;
	}


	public static String GetRandomName() {
		try {
			Path path = FileSystems.getDefault().getPath("./data/", "names.txt");
			List<String> lines = Files.readAllLines(path);
			int i = Main.RND.nextInt(lines.size());
			return "Captain " + lines.get(i);
		} catch (IOException ex) {
			ex.printStackTrace();
			return "Mr Bum.";
		}
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
		if (f > 0) {
			main.addMsg(this.getName() + " is healed by " + (int)f);
		} else {
			main.addMsg(this.getName() + " is wounded by " + (int)-f);
		}
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
		if (newsq.isSquareTraversable()) {
			AbstractMob other = main.gameData.getUnitAt(x+offx, y+offy);
			if (other == null) {
				if (newsq instanceof MapSquareDoor) {
					if (this.autoOpenDoors) {
						MapSquareDoor door = (MapSquareDoor)newsq;
						door.setOpen(true);
					} else {
						return false;
					}
				}
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


	public boolean checkForShooting() {
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
					float dist = this.distanceTo(enemy);
					if (dist <= gun.getRange()) {
						enemy.shotBy(this, gun);
						return true;
					}
				}
			}
		}
		return false;
	}


	protected void shotBy(AbstractMob shooter, AbstractRangedWeapon gun) {
		this.genericAttack(shooter, gun.getShotValue(), "shot by");
	}


	public void meleeCombat(AbstractMob attacker) {
		float att = attacker.meleeSkill;
		if (attacker.currentItem != null) {
			att += attacker.currentItem.getMeleeValue();
		}
		this.genericAttack(attacker, att, "hit by");
	}


	protected void genericAttack(AbstractMob attacker, float attackVal, String verb) {
		int tot = Math.max(1, attacker.meleeSkill - this.meleeSkill);
		int dam = Main.RND.nextInt(tot)+1;
		this.incHealth(-dam, this.getName());
		if (Settings.DEBUG || this.getSq().visible == VisType.Visible) {
			main.addMsg(this.name + " has been " + verb + " " + attacker.getName());
		}
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
		super.getSq().addEntity(new Corpse(main, this.getName()));
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
								if (this.canSee(m)) { // This takes into account view distances
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


	protected boolean moveAStar() {
		if (this.astarRoute == null || this.astarRoute.isEmpty()) {
			Point dest = this.getAStarDest();
			AStar astar = new AStar(this.main.gameData);
			astar.findPath(x, y, dest.x, dest.y, false);
			if (astar.wasSuccessful()) {
				this.astarRoute = astar.getRoute();
			}
		}
		return true;
	}


	public abstract Point getAStarDest();


	public void pickup(DrawableEntity de) {
		main.gameData.currentUnit.getSq().removeEntity(de);
		main.gameData.currentUnit.equipment.add((AbstractItem)de);
		de.carriedBy = this;

	}


	public void drop(DrawableEntity de) {
		main.gameData.currentUnit.equipment.remove(de);
		main.gameData.currentUnit.getSq().addEntity(de);
		de.carriedBy = null;

	}


	protected Point rotateDir(Point dir) {
		if (dir.x == 1 && dir.y == 0) {
			return new Point(0, 1);
		} else if (dir.x == 0 && dir.y == 1) {
			return new Point(-1, 0);
		} else if (dir.x == -1 && dir.y == 0) {
			return new Point(0, -1);
		} else if (dir.x == 0 && dir.y == -1) {
			return new Point(1, 0);
		} else {
			throw new RuntimeException("Unknown dir: " + dir);
		}
	}


	protected boolean isUsingGun() {
		return this.currentItem != null && this.currentItem instanceof AbstractRangedWeapon;
	}


}

