package com.scs.ftl2d.map;

import java.awt.Color;

import com.scs.ftl2d.Main;

public class MapSquareReplicator extends AbstractMapSquare {

	public MapSquareReplicator(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	@Override
	public String getName() {
		return "Replicator";
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
		return 'R';
	}


	@Override
	protected Color getBackgroundColour() {
		return Color.green;
	}


	@Override
	public String getHelp() {
		return "Units stood next to a replicator will automatically eat food.";
	}


}
