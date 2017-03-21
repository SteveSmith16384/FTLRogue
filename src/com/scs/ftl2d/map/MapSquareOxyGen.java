package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareOxyGen extends AbstractMapSquare {

	public MapSquareOxyGen(Main main, int code, int x, int y) {
		super(main, code, x, y);
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
		super.process();

		main.gameData.incOxygenLevel((2f * this.getHealth()) / 100);
		if (this.getDamagePcent() > 0) {
			main.gameData.powerUsedPerTurn += 1f;
		}
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.cyan; // todo - change
	}


	@Override
	public String getHelp() {
		return null;
	}


}
