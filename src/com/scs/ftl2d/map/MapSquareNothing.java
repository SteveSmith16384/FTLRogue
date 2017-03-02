package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareNothing extends AbstractMapSquare {

	public MapSquareNothing(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Nothing";
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
		return ' ';
	}


	@Override
	public void process(int pass) {
		processItems(pass);

	}

}
