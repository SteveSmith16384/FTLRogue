package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.scs.ftl2d.Main;

public class MapSquareNothing extends AbstractMapSquare {

	public MapSquareNothing(Main main, int code, int x, int y) {
		super(main, code, x, y);
		
		this.hasOxygen = false;
	}


	@Override
	public String getName() {
		return "Space";
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


	@Override
	protected Color getBackgroundColour() {
		return Color.black;
	}


}
