package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.scs.ftl2d.Main;

public class MapSquareWall extends AbstractMapSquare {

	public MapSquareWall(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Wall";
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
		return this.getDamagePcent() > 0 ? '#' : ' ';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.blue;
	}


}
