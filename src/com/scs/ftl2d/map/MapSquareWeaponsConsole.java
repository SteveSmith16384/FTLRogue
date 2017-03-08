package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareWeaponsConsole extends AbstractMapSquare {

	public MapSquareWeaponsConsole(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public boolean isTraversable() {
		return false;
	}
	

	@Override
	public boolean isTransparent() {
		return true;
	}
	

	@Override
	public char getFloorChar() {
		return 'W';
	}


	@Override
	public String getName() {
		return "Weapons Control";
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.gray;
	}

}
