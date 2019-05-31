package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareShipLaser extends AbstractMapSquare {

	public MapSquareShipLaser(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}

	
	@Override
	protected boolean isTraversable_Sub() {
		return false;
	}

	
	@Override
	protected boolean isTransparent_Sub() {
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


	@Override
	public String getHelp() {
		return null;
	}

}
