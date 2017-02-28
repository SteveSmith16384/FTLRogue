package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareOxyGen extends AbstractMapSquare {

	public MapSquareOxyGen(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Oxy-Gen";
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
		return 'A';
	}


	@Override
	public void process() {
		processItems();

		main.gameData.incOxygenLevel(2f * this.getHealth() / 100);
	}

}
