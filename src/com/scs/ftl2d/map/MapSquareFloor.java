package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareFloor extends AbstractMapSquare {

	public MapSquareFloor(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Floor";
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
		if (this.hasOxygen) {
			return '.';
		} else {
			return ' ';
		}
	}

	@Override
	public void process(int pass) {
		processItems(pass);
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.darkGray;
	}




}
