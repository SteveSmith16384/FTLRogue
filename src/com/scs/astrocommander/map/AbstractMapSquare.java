package com.scs.astrocommander.map;

import java.awt.Color;
import java.awt.Point;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.astrocommander.Main;
import com.scs.rogueframework.FrameworkMapSquare;
import com.scs.rogueframework.ecs.components.IProcessable;
import com.scs.rogueframework.ecs.entities.AbstractMob;
import com.scs.rogueframework.ecs.entities.DrawableEntity;

public abstract class AbstractMapSquare extends FrameworkMapSquare implements IProcessable {

	// Map codes
	public static final int MAP_NOTHING = 0;
	public static final int MAP_FLOOR = 1;
	public static final int MAP_TELEPORTER = 2;
	public static final int MAP_MEDIBAY = 3;
	public static final int MAP_OXYGEN_GEN = 4;
	public static final int MAP_ENGINES= 5;
	public static final int MAP_DOOR = 6;
	public static final int MAP_WALL = 7;
	public static final int MAP_REPLICATOR = 8;
	//public static final int MAP_WEAPON_CONSOLE = 9;
	public static final int MAP_BATTERY = 10;
	public static final int MAP_CONTROL_PANEL = 11;
	public static final int MAP_AIRLOCK = 12;
	public static final int MAP_WEAPON_POINT = 13;

	public Main main;
	public int type = MAP_NOTHING;
	public boolean onFire = false;
	public boolean hasSmoke = false;
	public boolean hasOxygen = true;
	private float damage_pcent = 0;
	public String extraInfo = "";

	public static AbstractMapSquare Factory(Main main, int code, int x, int y) {
		switch (code) {
		case MAP_NOTHING:
			return new MapSquareNothing(main, code, x, y);

		case MAP_FLOOR:
			return new MapSquareFloor(main, code, x, y);

		case MAP_WALL:
			return new MapSquareWall(main, code, x, y);

		case MAP_TELEPORTER:
			return new MapSquareTeleporter(main, code, x, y);

		case MAP_MEDIBAY:
			return new MapSquareMediBay(main, code, x, y);

		case MAP_OXYGEN_GEN:
			return new MapSquareOxyGen(main, code, x, y);

		case MAP_ENGINES:
			return new MapSquareEngine(main, code, x, y);

		case MAP_DOOR:
			return new MapSquareDoor(main, code, x, y);

		case MAP_REPLICATOR:
			return new MapSquareReplicator(main, code, x, y);

		case MAP_CONTROL_PANEL:
			return new MapSquareControlPanel(main, code, x, y);

		case MAP_BATTERY:
			return new MapSquareBattery(main, code, x, y);

		case MAP_AIRLOCK:
			return new MapSquareAirlock(main, code, x, y);

		case MAP_WEAPON_POINT:
			main.gameData.weaponPoints.add(new Point(x, y));
			return new MapSquareShipLaser(main, code, x, y);

		default:
			throw new RuntimeException("Unknown mapsquare type: " + code);
		}
	}


	public AbstractMapSquare(Main _main, int _code, int x, int y) {
		super(x, y);

		main = _main;
		type = _code;

		this.calcChar();
	}

	
	@Override
	public boolean isTraversable() {
		if (this.damage_pcent >= 100) {
			return true;
		} else {
			return isTraversable_Sub();
		}
	}
	
	
	@Override
	public boolean isTransparent() {
		if (this.hasSmoke) {
			return false;
		} else {
			return isTransparent_Sub();
		}
	}
	
	
	public boolean isAirtight() {
		return true;
	}
	

	@Override
	public void process() {
		if (!this.hasOxygen) {
			this.onFire = false;
			this.hasSmoke = false;
			// Kill any units
			for (DrawableEntity de : this.entities) {
				if (de instanceof AbstractMob) {
					AbstractMob am = (AbstractMob)de;
					// Check for oxygen
					if (am.wearing != null) {
						if (am.wearing.giveOxygen()) {
							continue;
						}
					}
					
					am.died("asphyxiation");
				}
			}
			//this.entities.clear();  Entities move around?
		} else if (this.onFire) {
			// todo - spread?
		}

		this.processItems();
	}


	@Override
	public void calcChar() {
		Color foregroundCol = Color.white;
		if (this.damage_pcent > 0) {
			int num = (int)(this.damage_pcent / 10)+1; 
			for (int i=0 ; i<num ; i++) {
				foregroundCol = foregroundCol.darker();
			}
		}
		TextColor foregroundTC = new TextColor.RGB(foregroundCol.getRed(), foregroundCol.getGreen(), foregroundCol.getBlue());

		// Seen
		Color backgroundColSeen = this.getBackgroundColour().darker().darker();
		if (this.onFire) {
			backgroundColSeen = Color.red;
		} else if (this.hasSmoke) {
			backgroundColSeen = Color.lightGray;
		}
		TextColor backgroundTC2 = new TextColor.RGB(backgroundColSeen.getRed(), backgroundColSeen.getGreen(), backgroundColSeen.getBlue());
		seenChar = new TextCharacter(this.getFloorChar(), foregroundTC, backgroundTC2);

		// Visible char
		char c = this.getFloorChar();
		boolean isSelectedUnit = false;
		extraInfo = "";
		if (this.entities.size() > 0) {
			c = entities.get(0).getChar();
			extraInfo = entities.get(0).getName();
			if (entities.get(0) == main.gameData.current_unit) {
				isSelectedUnit = true;
			}
		}
		Color backgroundColVisible = this.getBackgroundColour();
		if (this.onFire) {
			backgroundColVisible = Color.orange;
		} else if (this.hasSmoke) {
			backgroundColVisible = Color.lightGray;
		} else if (isSelectedUnit) {
			backgroundColVisible = Color.red;
		}
		TextColor backgroundTC = new TextColor.RGB(backgroundColVisible.getRed(), backgroundColVisible.getGreen(), backgroundColVisible.getBlue());
		visibleChar = new TextCharacter(c, foregroundTC, backgroundTC);
	}


	public float getHealth() {
		return 100 - this.damage_pcent;
	}

	
	public float getDamagePcent() {
		return this.damage_pcent;
	}

	
	public void incDamage(float i) {
		this.damage_pcent += i;		
		if (this.damage_pcent > 100) {
			//this.damage_pcent = 100;
			main.checkOxygenFlag = true;
			this.main.gameData.map_data.map[x][y] = new MapSquareFloor(main, MAP_FLOOR, x, y);
		} else {
			// Start a fire?
			int n = Main.RND.nextInt(100);
			if (n < this.damage_pcent) {
				this.onFire = true;
			}
		}
		this.calcChar();
	}

	
	public String getExamineText() {
		if (this.damage_pcent > 0) {
			return "The " + this.getName() + " is " + damage_pcent + "% damaged.";
		}
		return "";
	}

}
