package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareEngine extends AbstractMapSquare {

	public MapSquareEngine(Main main, int code) {
		super(main, code);
	}


	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public char getFloorChar() {
		return 'E';
	}


	@Override
	public void process() {
		processItems();

	}

}
