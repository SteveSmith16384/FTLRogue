package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareBattery extends AbstractMapSquare {

	public MapSquareBattery(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Battery";
	}


	@Override
	public boolean isTransparent_Sub() {
		return false;
	}


	@Override
	public boolean isTraversable_Sub() {
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
		return Color.yellow;
	}


	@Override
	public String getHelp() {
		return null;
	}

}
