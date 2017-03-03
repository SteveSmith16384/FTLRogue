package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.scs.ftl2d.Main;

public class MapSquareBattery extends AbstractMapSquare {
	
	//private static TextCharacter FloorChar = new TextCharacter('B', TextColor.);
	
	public MapSquareBattery(Main main, int code) {
		super(main, code);
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
	public void process(int pass) {
		processItems(pass);

		if (pass == 1) {
			main.gameData.powerGainedPerTurn += (2f * this.getHealth() / 100);
		}
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.blue;
	}

}
