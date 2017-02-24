package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareWall extends AbstractMapSquare {

	public MapSquareWall(Main main, int code) {
		super(main, code);
	}

	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public char getFloorChar() {
		return 'X';
	}

	@Override
	public void process() {
		processItems();

	}

}
