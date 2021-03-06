package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareFloor extends AbstractMapSquare {

	public MapSquareFloor(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Floor";
	}


	@Override
	public boolean isTransparent_Sub() {
		return true;
	}


	@Override
	public boolean isAirtight() {
		return false;
	}
	
	
	@Override
	public boolean isTraversable_Sub() {
		return true;
	}

	@Override
	public char getFloorChar() {
		if (this.hasOxygen) {
			return '.';
		} else {
			return ' ';
		}
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.darkGray;
	}


	@Override
	public String getHelp() {
		return null;
	}



}
