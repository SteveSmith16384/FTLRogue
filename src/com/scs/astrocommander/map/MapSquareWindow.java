package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareWindow extends AbstractMapSquare {

	public MapSquareWindow(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Window";
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
		return 'O';
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
