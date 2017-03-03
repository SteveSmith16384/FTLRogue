package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
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
	public void process(int pass) {
		processItems(pass);

		if (pass == 1) {
			main.gameData.incOxygenLevel((2f * this.getHealth()) / 100);
			if (this.damage_pcent > 0) {
				main.gameData.powerUsedPerTurn += 1f;
			}
		}
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.cyan;
	}



}
