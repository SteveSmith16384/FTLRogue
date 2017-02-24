package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareMediBay extends AbstractMapSquare {

	public MapSquareMediBay(Main main, int code) {
		super(main, code);
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
	public void process() {
		processItems();

	}

}
