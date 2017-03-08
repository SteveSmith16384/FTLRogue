package com.scs.ftl2d.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ssmith.util.SortedArrayList;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.Entity;
import com.scs.ftl2d.entities.mobs.AbstractMob;

public abstract class AbstractMapSquare extends Entity implements Comparator<DrawableEntity> {

	public enum VisType {Hidden, Seen, Visible};

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
	public static final int MAP_CONTROL_PANEL = 9;
	public static final int MAP_BATTERY = 10;
	public static final int MAP_WEAPON_CONSOLE = 11;
	public static final int MAP_AIRLOCK = 12;
	public static final int MAP_WEAPON_POINT = 13;

	public int type = MAP_NOTHING;
	public VisType visible = VisType.Hidden;
	public boolean onFire = false;
	public boolean hasSmoke = false;
	public boolean hasOxygen = true;
	public float damage_pcent = 0;
	public int x, y;

	private static TextCharacter hiddenChar = new TextCharacter(' ', TextColor.ANSI.BLACK, TextColor.ANSI.BLACK); 
	private TextCharacter seenChar; 
	private TextCharacter visibleChar; 

	private SortedArrayList<DrawableEntity> entities = new SortedArrayList<DrawableEntity>();//10, this);
	private List<DrawableEntity> entitiesToAdd = new ArrayList<DrawableEntity>();
	private List<DrawableEntity> entitiesToRemove = new ArrayList<DrawableEntity>();

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

		case MAP_WEAPON_CONSOLE:
			return new MapSquareWeaponsConsole(main, code, x, y);

		case MAP_AIRLOCK:
			return new MapSquareAirlock(main, code, x, y);

		case MAP_WEAPON_POINT:
			// todo
			return new MapSquareNothing(main, code, x, y);

		default:
			throw new RuntimeException("Unknown type: " + code);
		}
	}


	public AbstractMapSquare(Main _main, int _code, int _x, int _y) {
		super(_main);

		type = _code;
		x = _x;
		y = _y;

		this.calcChars();
	}

	public abstract boolean isTraversable();

	public abstract boolean isTransparent();

	protected abstract char getFloorChar();

	protected abstract Color getBackgroundColour();

	public abstract String getName();

	@Override
	public void preProcess() {
		
	}


	@Override
	public void process() {
		this.processItems();
	}


	protected void processItems() {
		if (!this.hasOxygen) {
			this.onFire = false;
			// Kill any units
			for (DrawableEntity de : this.entities) {
				if (de instanceof AbstractMob) {
					AbstractMob am = (AbstractMob)de;
					am.died("asphyxiation");
				}
			}
			this.entities.clear();
		}

		for (DrawableEntity de : this.entities) {
			de.process();
		}

	}

	
	public void updateItemList() {
		boolean calcChar = this.entitiesToAdd.size() > 0 || this.entitiesToRemove.size() > 0;
		this.entities.removeAll(this.entitiesToRemove);
		entitiesToRemove.clear();
		this.entities.addAll(this.entitiesToAdd);
		entitiesToAdd.clear();
		
		if (calcChar) {
			this.calcChars();
		}
	}

	public TextCharacter getChar() {
		if (this.visible == VisType.Hidden) {
			return hiddenChar;//' ';
		} else if (this.visible == VisType.Seen) { 
			return this.seenChar;
		} else {
			return this.visibleChar;
		}
	}


	protected void calcChars() {
		Color foregroundCol = Color.white;
		if (this.damage_pcent > 0) {
			int num = (int)(this.damage_pcent / 10)+1; 
			for (int i=0 ; i<num ; i++) {
				foregroundCol = foregroundCol.darker();
			}
		}
		TextColor foregroundTC = new TextColor.RGB(foregroundCol.getRed(), foregroundCol.getGreen(), foregroundCol.getBlue());

		Color backgroundColSeen = this.getBackgroundColour().darker().darker();
		TextColor backgroundTC2 = new TextColor.RGB(backgroundColSeen.getRed(), backgroundColSeen.getGreen(), backgroundColSeen.getBlue());
		seenChar = new TextCharacter(this.getFloorChar(), foregroundTC, backgroundTC2);

		// Visible char
		char c = this.getFloorChar();
		boolean isSelectedUnit = false;
		if (this.entities.size() > 0) {
			c = entities.get(0).getChar();
			if (entities.get(0) == main.gameData.currentUnit) {
				isSelectedUnit = true;
			}
		}
		Color backgroundCol = this.getBackgroundColour();
		if (isSelectedUnit) {
			backgroundCol = Color.red;
		}
		TextColor backgroundTC = new TextColor.RGB(backgroundCol.getRed(), backgroundCol.getGreen(), backgroundCol.getBlue());
		visibleChar = new TextCharacter(c, foregroundTC, backgroundTC);
	}


	public float getHealth() {
		return 100 - this.damage_pcent;
	}


	@Override
	public int compare(DrawableEntity de1, DrawableEntity de2) {
		return de2.z - de1.z;
	}


	public void addEntity(DrawableEntity de) {
		de.x = x;
		de.y = y;
		this.entitiesToAdd.add(de);
		//this.calcChars();
	}


	public void removeEntity(DrawableEntity de) {
		this.entitiesToRemove.add(de);
		//this.calcChars();
	}


	public DrawableEntity getEntity(int i) {
		return this.entities.get(i);
	}


	public List<DrawableEntity> getEntities() {
		return this.entities;
	}


	public Iterator<DrawableEntity> getIterator() {
		return this.entities.iterator();
	}

}
