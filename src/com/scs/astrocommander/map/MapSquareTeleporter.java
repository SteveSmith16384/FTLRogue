package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareTeleporter extends AbstractMapSquare {

	public MapSquareTeleporter(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Teleporter";
	}


	@Override
	public boolean isTransparent_Sub() {
		return true;
	}
	

	@Override
	public boolean isTraversable_Sub() {
		return true;
	}

	@Override
	public char getFloorChar() {
		return 'T';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.magenta;
	}


	@Override
	public String getHelp() {
		return null;
	}

}
