package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareNothing extends AbstractMapSquare {

	//private char theChar = ' '; // Default
	
	public MapSquareNothing(Main main, int code, int x, int y) {
		super(main, code, x, y);
		
		/*int i = Main.RND.nextInt(40);
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
		}*/
		//theChar = ' ';
		calcChar();
		
		this.hasOxygen = false;
	}


	@Override
	public String getName() {
		return "Space";
	}


	@Override
	public boolean isAirtight() {
		return false;
	}
	
	
	@Override
	public boolean isTraversable() {
		return true;
	}


	@Override
	public boolean isTransparent() {
		return true;
	}
	

	@Override
	public char getFloorChar() {
		/*if ((int)theChar == 0) { // Not set yet
			return ' ';
		}
		return theChar;*/
		return ' ';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.black;
	}


	@Override
	public String getHelp() {
		return null;
	}


}
