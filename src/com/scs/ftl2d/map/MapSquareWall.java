package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareWall extends AbstractMapSquare {

	public MapSquareWall(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Wall";
	}


	@Override
	public boolean isTraversable() {
		return false;
	}


	@Override
	public boolean isTransparent() {
		return false;
	}
	

	@Override
	public char getFloorChar() {
		return '#';
	}

	@Override
	public void process(int pass) {
		processItems(pass);

	}

}
