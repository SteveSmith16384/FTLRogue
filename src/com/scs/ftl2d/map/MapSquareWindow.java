package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareWindow extends AbstractMapSquare {

	public MapSquareWindow(Main main, int code) {
		super(main, code);
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
	public void process() {
		processItems();

	}

}
