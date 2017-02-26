package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

/**
 * This allows weapons to be fired etc...
 *
 */
public class MapSquareControlPanel extends AbstractMapSquare {

	public MapSquareControlPanel(Main main, int code) {
		super(main, code);
	}


	@Override
	public boolean isTraversable() {
		return false;
	}
	

	@Override
	public char getFloorChar() {
		return 'C';
	}


	@Override
	public void process() {
		processItems();

	}


	@Override
	public String getName() {
		return "Control Panel";
	}

}
