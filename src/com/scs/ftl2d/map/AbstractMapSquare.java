package com.scs.ftl2d.map;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.Entity;

public abstract class AbstractMapSquare extends Entity implements Comparator<DrawableEntity> {

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
	public Queue<DrawableEntity> entities = new PriorityQueue<DrawableEntity>(10, this);

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

		case MAP_CONTROL_PANEL:
			return new MapSquareControlPanel(main, code);

		default:
			throw new RuntimeException("Unknown type: " + code);
		}
	}
	
	
	public AbstractMapSquare(Main _main, int _code) {
		super(_main);
		type = _code;
	}
	
	public abstract boolean isTraversable();
	
	public abstract boolean isTransparent();
	
	protected abstract char getFloorChar();
	
	public abstract String getName();
	
	protected void processItems() {
		for (DrawableEntity de : this.entities) {
			de.process();
		}
	}
	
	public char getChar() {
		if (entities.size() == 0) {
			return this.getFloorChar();
		} else {
			return entities.peek().getChar();
		}
	}
	
	public float getHealth() {
		return 100 - this.damage_pcent;
	}

	
	@Override
	public int compare(DrawableEntity arg0, DrawableEntity arg1) {
		return arg1.z - arg0.z;
	}


}
