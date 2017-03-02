package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareEngine extends AbstractMapSquare {

	public MapSquareEngine(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Engine";
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
		return 'E';
	}


	@Override
	public void process(int pass) {
		processItems(pass);
		
		if (pass == 2) {
			if (main.gameData.shipFlying) {
				main.gameData.shipSpeed += (2f * this.getHealth()) / 100;
			}
		}
	}

}
