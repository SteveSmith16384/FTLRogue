package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareAirlock extends AbstractMapSquare {

	private boolean isOpen = false;

	public MapSquareAirlock(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	public boolean setOpen(boolean b) {
		boolean res = false;
		if (main.gameData.totalPower > 0) {
			main.gameData.powerUsedPerTurn += 1f;
			this.isOpen = b;
			res = true;
		}
		// main.checkOxygen(); No; do that once all open
		return res;
	}


	@Override
	public String getName() {
		return "Airlock";
	}


	public boolean isOpen() {
		return this.isOpen;
	}



	@Override
	public boolean isTransparent() {
		return false;
	}


	@Override
	public boolean isTraversable() {
		return isOpen;
	}


	@Override
	public char getFloorChar() {
		return isOpen ? '\'' : '+';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.lightGray;
	}


}
