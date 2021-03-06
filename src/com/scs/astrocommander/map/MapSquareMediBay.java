package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareMediBay extends AbstractMapSquare {

	public MapSquareMediBay(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Medi-Bay";
	}


	@Override
	public boolean isTransparent_Sub() {
		return false;
	}
	

	@Override
	public boolean isTraversable_Sub() {
		return false;
	}

	@Override
	public char getFloorChar() {
		return 'M';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.red;
	}


	@Override
	public String getHelp() {
		return "Units are automatically healed when stood next to a medibay.";
	}



}
