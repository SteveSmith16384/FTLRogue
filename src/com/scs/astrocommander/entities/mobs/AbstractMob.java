package com.scs.astrocommander.entities.mobs;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import ssmith.astar.AStar;

import com.scs.astrocommander.Line;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.asciieffects.BulletShot;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entities.items.Corpse;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.entityinterfaces.IMeleeWeapon;
import com.scs.astrocommander.entityinterfaces.IRangedWeapon;
import com.scs.astrocommander.entityinterfaces.IWearable;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.CSVMap;
import com.scs.astrocommander.map.MapSquareDoor;
import com.scs.astrocommander.map.AbstractMapSquare.VisType;

public abstract class AbstractMob extends DrawableEntity {

	public enum Status {Waiting, Moving, Eating, Repairing, Healing}

	public static List<AbstractMob> AllMobs = new ArrayList<>();


	// Sides
	public static final int SIDE_PLAYER = 0;
	public static final int SIDE_ALIEN = 1;
	public static final int SIDE_ENEMY_SHIP = 2;
	public static final int SIDE_POLICE = 3;

	public char theChar;
	protected String name;
	public int side;
	public List<ICarryable> equipment = new ArrayList<>();
	public ICarryable currentItem;
	protected boolean autoOpenDoors;
	public IWearable wearing;
	public int unconciouseTimer = 0;

	public List<Point> astarRoute;

	// Stats
	public float health, maxHealth;
	public int meleeSkill;

	public AbstractMob(Main main, int _x, int _y, int _z, char c, String _name, int _side, float hlth, int _combat, boolean _autoOpenDoors) {
		super(main, _x, _y, _z);

		theChar = c;
		name = _name;
		side = _side;

		health = hlth;
		maxHealth = health;
		meleeSkill = _combat;

		autoOpenDoors = _autoOpenDoors;

		AllMobs.add(this);
	}


	@Override
	public void process() {
		if (this.unconciouseTimer > 0) {
			this.unconciouseTimer--;
		}
	}


	@Override
	public char getChar() {
		return theChar;
	}


	public static String GetRandomName() {
		//try {
		//Path path = FileSystems.getDefault().getPath("./data/", "names.txt");
		//List<String> lines = Files.readAllLines(path);
		String text = new Scanner(CSVMap.class.getResourceAsStream("/data/names.txt"), "UTF-8").useDelimiter("\\A").next();
		List<String> lines = Arrays.asList(text.split("\n"));
		int i = Main.RND.nextInt(lines.size());
		return "Captain " + lines.get(i).trim();
		/*} catch (IOException ex) {
			ex.printStackTrace();
			return "Mr Bum.";
		}*/
	}


	public void remove() {
		AllMobs.remove(this);
		super.remove();
	}


	@Override
	public String getName() {
		return name;
	}


	public void incHealth(float f, String reason) {
		this.health += f;
		if (f > 0) {
			main.addMsg(2, this.getName() + " is healed by " + (int)f);
		} else {
			main.addMsg(2, this.getName() + " is wounded by " + (int)-f);
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
			AbstractMob other = newsq.getUnit();// main.gameData.getUnitAt(x+offx, y+offy);
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
					addMsgIfOurs(1, other.getName() + " is in the way");
				} else {
					this.meleeCombat(other);
				}
			}
		} else {
			addMsgIfOurs(1, newsq.getName() + " is in the way");
		}
		return false;
	}


	public boolean checkForShooting() {
		if (this.currentItem != null) {
			if (currentItem instanceof IRangedWeapon) {
				IRangedWeapon gun = (IRangedWeapon)currentItem;
				AbstractMob enemy = getClosestVisibleEnemy();
				if (enemy != null) {
					float dist = this.distanceTo(enemy);
					if (dist <= gun.getRange()) {
						shoot(new Line(x, y, enemy.x, enemy.y), gun);
						return true;
					}
				}
			}
		}
		return false;
	}


	public void shoot(AbstractMob target, IRangedWeapon gun) {
		this.shoot(new Line(this.x, this.y, target.x, target.y), gun);
	}


	public void shoot(List<Point> line, IRangedWeapon gun) {
		Iterator<Point> it = line.iterator();
		while (it.hasNext()) {
			Point p =it.next();
			AbstractMob mob = (AbstractMob) main.gameData.map[p.x][p.y].getEntityOfType(AbstractMob.class);
			if (mob != null) {
				new BulletShot(main, this.x, this.y, p.x, p.y);
				mob.shotBy(this, gun);
			}
		}
	}


	public void throwItem(List<Point> line, ICarryable gun) {
		Iterator<Point> it = line.iterator();
		AbstractMapSquare prevSq = null;
		while (it.hasNext()) {
			Point p =it.next();
			AbstractMapSquare sq = main.gameData.map[p.x][p.y];
			if (sq.isSquareTraversable() == false) {
				new BulletShot(main, this.x, this.y, prevSq.x, prevSq.y);
				this.dropOrThrow(gun, prevSq) ;
			}
			prevSq = sq;
		}
	}


	protected void shotBy(AbstractMob shooter, IRangedWeapon gun) {
		this.genericAttack(shooter, gun.getShotValue(), "shot by");
	}


	public void meleeCombat(AbstractMob defender) {
		float att = this.meleeSkill;
		if (this.currentItem != null && this.currentItem instanceof IMeleeWeapon) {
			IMeleeWeapon weapon = (IMeleeWeapon)this.currentItem;
			att += weapon.getMeleeValue();
		}
		this.genericAttack(defender, att, "hit by");
	}


	protected void genericAttack(AbstractMob defender, float attackVal, String verb) {
		int tot = Math.max(1, this.meleeSkill - defender.meleeSkill);
		int dam = Main.RND.nextInt(tot)+1;
		if (Settings.DEBUG || this.getSq().visible == VisType.Visible) {
			main.addMsg(this.name + " has " + verb + " " + defender.name + " for " + dam);
		}
		defender.incHealth(-dam, this.getName());

		defender.attackedBy(this);
	}


	protected void attackedBy(AbstractMob attacker) {
		// Override if req
	}


	protected void addMsgIfOurs(int pri, String s) {
		if (this.side == SIDE_PLAYER) {
			main.addMsg(pri, s);
		}
	}


	public void openDoor() {
		MapSquareDoor sq = (MapSquareDoor)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_DOOR);
		if (sq != null) {
			if (!sq.isOpen()) {
				sq.setOpen(true);
				main.addMsg(1, "Door opened");
			} else {
				main.addMsg(1, "Door already open");
			}
		} else {
			main.addMsg(1, "No door found");
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
		main.addMsg(3, this.getName() + " has died of " + reason);
		for(ICarryable eq : this.equipment) {
			super.getSq().addEntity((DrawableEntity)eq); // Drop the equipment
		}
		super.getSq().addEntity(new Corpse(main, this.getName()));
		main.gameData.units.remove(this); // Remove from list if ours
		if (main.gameData.units.isEmpty()) {
			main.gameOver();
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


	protected DrawableEntity getClosestEntity(Class clazz) {
		DrawableEntity closest = null;
		float closestDistance = 9999;

		for (int y=0 ; y<main.gameData.getHeight() ; y++) {
			for (int x=0 ; x<main.gameData.getWidth() ; x++) {
				AbstractMapSquare sq = main.gameData.map[x][y];
				for (DrawableEntity e : sq.getEntities()) {
					if (clazz.isInstance(e)) {
						float dist = this.distanceTo(e); 
						if (dist < closestDistance) {
							if (this.canSee(e)) { // This takes into account view distances
								closest = e;
								closestDistance = dist;
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
			if (dest != null) {
				AStar astar = new AStar(this.main.gameData);
				astar.findPath(x, y, dest.x, dest.y, false);
				if (astar.wasSuccessful()) {
					this.astarRoute = astar.getRoute();
				}
			}
		} else {
			Point p = this.astarRoute.get(0);
			boolean res = this.moveTowards(p.x, p.y);
			if (res) {
				this.astarRoute.remove(0);
			}
		}
		return true;
	}


	public abstract Point getAStarDest();


	public void pickup(ICarryable ic) {
		DrawableEntity de = (DrawableEntity)ic;
		main.gameData.currentUnit.getSq().removeEntity(de);
		main.gameData.currentUnit.equipment.add(ic);
		ic.setCarriedBy(this);

	}


	public void dropOrThrow(ICarryable ic, AbstractMapSquare sq) {
		DrawableEntity de = (DrawableEntity)ic;
		main.gameData.currentUnit.equipment.remove(de);
		sq.addEntity(de);//main.gameData.currentUnit.getSq().addEntity(de);
		ic.setNotCarried();
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
		return this.currentItem != null && this.currentItem instanceof IRangedWeapon;
	}


	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":" + name;
	}


	public boolean isAlive() {
		return this.health > 0;
	}


	public IRangedWeapon getGun() {
		if (this.currentItem instanceof IRangedWeapon) {
			return (IRangedWeapon) currentItem;
		}
		return null;
	}


	/*public DrawableEntity getCarriedItemOfType(Class clazz) {

	}*/


}

