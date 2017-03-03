package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.scs.ftl2d.Main;

public class MapSquareWindow extends AbstractMapSquare {

	public MapSquareWindow(Main main, int code) {
		super(main, code);
	}


	@Override
	public String getName() {
		return "Window";
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
		return 'O';
	}

	@Override
	public void process(int pass) {
		processItems(pass);

	}


	@Override
	protected Color getBackgroundColour() {
		return Color.blue;
	}




}
