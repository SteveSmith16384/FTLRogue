package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareEngine extends AbstractMapSquare {

	public MapSquareEngine(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Engine";
	}


	@Override
	public boolean isTraversable_Sub() {
		return false;
	}


	@Override
	public boolean isTransparent_Sub() {
		return false;
	}


	@Override
	public char getFloorChar() {
		return 'E';
	}


	@Override
	public void process() {
		super.process();

		if (main.gameData.currentLocation == null) {
			float speed = (10f * this.getHealth()) / 100f;
			speed = (speed * main.gameData.enginePowerPcent) / 100f;
			main.gameData.shipSpeed += speed;

		}
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.orange;
	}


	@Override
	public String getHelp() {
		return null;
	}

}
