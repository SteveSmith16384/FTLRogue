package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareMediBay extends AbstractMapSquare {

	public MapSquareMediBay(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Medi-Bay";
	}


	@Override
	public boolean isTransparent() {
		return false;
	}
	

	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public char getFloorChar() {
		return 'M';
	}


	@Override
	public void process(int pass) {
		processItems(pass);

	}


	@Override
	protected Color getBackgroundColour() {
		return Color.red;
	}



}
