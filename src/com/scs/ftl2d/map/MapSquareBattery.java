package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareBattery extends AbstractMapSquare {

	public MapSquareBattery(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Battery";
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
		return 'B';
	}


	@Override
	public void process() {
		super.process();

		main.gameData.powerGainedPerTurn += (2f * this.getHealth() / 100);
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.blue;
	}

}
