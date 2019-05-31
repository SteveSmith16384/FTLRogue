package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareNothing extends AbstractMapSquare {

	public MapSquareNothing(Main main, int code, int x, int y) {
		super(main, code, x, y);
		
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
	public boolean isTraversable_Sub() {
		return true;
	}


	@Override
	public boolean isTransparent_Sub() {
		return true;
	}
	

	@Override
	public char getFloorChar() {
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
