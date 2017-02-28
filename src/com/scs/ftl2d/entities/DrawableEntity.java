package com.scs.ftl2d.entities;

import java.awt.Point;

import com.scs.ftl2d.Line;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;


public abstract class DrawableEntity extends Entity {
	
	// Z levels
	public static final int Z_UNIT = 10;
	public static final int Z_ITEM = 1;
	public static final int Z_FLOOR = 0;
	
	public int x, y, z;

	public DrawableEntity(Main main, int _x, int _y, int _z) {
		super(main);
		
		x = _x;
		y = _y;
		z = _z;
	}


	public abstract char getChar();
	
	
	public AbstractMapSquare getSq() {
		return main.gameData.map[x][y];
	}
	
	
	public boolean canSee(DrawableEntity de) {
		Line l = new Line(this.x, this.y, de.x, de.y);
		for (Point p : l) {
			if (!main.gameData.map[p.x][p.y].isTransparent()) {
				return false;
			}
		}
		return true;
	}
	
	
	public float distanceTo(DrawableEntity de) {
		return (float) Math.sqrt((this.x-de.x)*(this.x-de.x) + this.y-de.y)*(this.y-de.y);
	}
	
	
	public void remove() {
		this.getSq().entities.remove(this); // Remove us
	}
}
