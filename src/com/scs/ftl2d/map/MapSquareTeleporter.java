package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.scs.ftl2d.Main;

public class MapSquareTeleporter extends AbstractMapSquare {

	public MapSquareTeleporter(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Teleporter";
	}


	@Override
	public boolean isTransparent() {
		return true;
	}
	

	@Override
	public boolean isTraversable() {
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
