package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareShipLaser extends AbstractMapSquare {

	public MapSquareShipLaser(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}

	@Override
	protected boolean isTraversable() {
		return false;
	}

	@Override
	protected boolean isTransparent() {
		return false;
	}

	@Override
	protected char getFloorChar() {
		return 'W';
	}

	@Override
	protected Color getBackgroundColour() {
		return Color.red;
	}

	@Override
	public String getName() {
		return "Ship Laser";
	}

}
