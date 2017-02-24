package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareTeleporter extends AbstractMapSquare {

	public MapSquareTeleporter(Main main, int code) {
		super(main, code);
	}


	@Override
	public boolean isTraversable() {
		return true;
	}

	@Override
	public char getFloorChar() {
		return 'T';
	}


	@Override
	public void process() {
		processItems();

	}

}
