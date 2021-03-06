package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

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
	public boolean isTransparent_Sub() {
		return false;
	}


	@Override
	public boolean isTraversable_Sub() {
		return isOpen;
	}


	@Override
	public boolean isAirtight() {
		return !isOpen;
	}
	
	
	@Override
	public char getFloorChar() {
		return isOpen ? '\'' : '+';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.lightGray;
	}


	@Override
	public String getHelp() {
		return "Airlocks can only be opened using the command console.";
	}


}
