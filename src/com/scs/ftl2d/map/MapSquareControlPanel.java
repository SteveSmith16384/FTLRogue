package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareControlPanel extends AbstractMapSquare {

	public MapSquareControlPanel(Main main, int code) {
		super(main, code);
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
	public void process(int pass) {
		processItems(pass);

	}


	@Override
	public String getName() {
		return "Control Panel";
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.gray;
	}

}
