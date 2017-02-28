package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareMediBay extends AbstractMapSquare {

	public MapSquareMediBay(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Medi-Bay";
	}


	@Override
	public boolean isTransparent() {
		return false;
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
