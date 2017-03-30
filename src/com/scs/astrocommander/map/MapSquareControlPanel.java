package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareControlPanel extends AbstractMapSquare {

	public MapSquareControlPanel(Main main, int code, int x, int y) {
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
		return 'C';
	}


	@Override
	public String getName() {
		return "Control Panel";
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.gray;
	}


	@Override
	public String getHelp() {
		StringBuffer str = new StringBuffer();
		str.append("Press 'l' to log-in");
		if (main.gameData.currentLocation == null) {
			str.append(", or 'f' to fire ship's weapons.\nPress space to keep flying");
		}
		return str.toString();
	}


}
