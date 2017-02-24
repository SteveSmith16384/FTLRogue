package com.scs.ftl2d.map;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.Entity;

public abstract class AbstractMapSquare extends Entity {

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

	public int type = MAP_NOTHING;
	public boolean onFire = false;
	public float damage_pcent = 0;
	public List<DrawableEntity> items = new ArrayList<>(); // todo - sort

	public static AbstractMapSquare Factory(Main main, int code) {
		switch (code) {
		case MAP_NOTHING:
			return new MapSquareNothing(main, code);

		case MAP_FLOOR:
			return new MapSquareFloor(main, code);

		case MAP_WALL:
			return new MapSquareWall(main, code);

		case MAP_TELEPORTER:
			return new MapSquareTeleporter(main, code);

		case MAP_MEDIBAY:
			return new MapSquareMediBay(main, code);

		case MAP_OXYGEN_GEN:
			return new MapSquareOxyGen(main, code);

		case MAP_ENGINES:
			return new MapSquareEngine(main, code);

		case MAP_DOOR:
			return new MapSquareDoor(main, code);

		case MAP_REPLICATOR:
			return new MapSquareReplicator(main, code);

		default:
			throw new RuntimeException("Unknown type: " + code);
		}
	}
	
	
	public AbstractMapSquare(Main _main, int _code) {
		super(_main);
		type = _code;
	}
	
	public abstract boolean isTraversable();
	
	protected abstract char getFloorChar();
	
	protected void processItems() {
		for (DrawableEntity de : this.items) {
			de.process();
		}
	}
	
	public char getChar() {
		if (items.size() == 0) {
			return this.getFloorChar();
		} else {
			// todo
			return items.get(0).getChar();
		}
	}
	
	public float getHealth() {
		return 100 - this.damage_pcent;
	}
}
