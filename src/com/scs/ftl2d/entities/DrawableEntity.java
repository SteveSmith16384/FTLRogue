package com.scs.ftl2d.entities;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;


public abstract class DrawableEntity extends Entity {
	
	// Z levels
	public static final int Z_UNIT = 10;
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
}
