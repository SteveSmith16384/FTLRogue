package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareReplicator extends AbstractMapSquare {

	public MapSquareReplicator(Main main, int code) {
		super(main, code);
	}


	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public char getFloorChar() {
		return 'R';
	}


	@Override
	public void process() {
		processItems();

	}

}
