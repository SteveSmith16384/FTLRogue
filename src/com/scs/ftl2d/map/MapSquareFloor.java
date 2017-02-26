package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareFloor extends AbstractMapSquare {

	public MapSquareFloor(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Floor";
	}


	@Override
	public boolean isTraversable() {
		return true;
	}

	@Override
	public char getFloorChar() {
		return '.';
	}

	@Override
	public void process() {
		processItems();

	}

}
