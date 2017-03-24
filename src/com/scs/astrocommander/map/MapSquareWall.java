package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareWall extends AbstractMapSquare {

	public MapSquareWall(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Wall";
	}


	@Override
	public boolean isTraversable() {
		return false;
	}


	@Override
	public boolean isTransparent() {
		return false;
	}
	

	@Override
	public char getFloorChar() {
		return this.getDamagePcent() > 0 ? '#' : ' ';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.blue;
	}


	@Override
	public String getHelp() {
		return null;
	}

}
