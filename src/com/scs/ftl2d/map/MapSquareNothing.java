package com.scs.ftl2d.map;

import java.awt.Color;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.scs.ftl2d.Main;

public class MapSquareNothing extends AbstractMapSquare {

	private char theChar = ' '; // Default
	
	public MapSquareNothing(Main main, int code, int x, int y) {
		super(main, code, x, y);
		
		int i = Main.RND.nextInt(10);
		switch (i) {
		case 0: 
			theChar = '.';
			break;
		case 1: 
			theChar = '*';
			break;
		default: 
			theChar = ' ';
			break;
		}
		calcChars();
		
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
		if ((int)theChar == 0) { // Not set yet
			return ' ';
		}
		return theChar;
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
