package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class MapSquareDoor extends AbstractMapSquare {

	private static final int CLOSE_DURATION = 4;

	private boolean isOpen = false;
	private int closeTimer;

	public MapSquareDoor(Main main, int code) {
		super(main, code);
	}


	public void setOpen(boolean b) {
		this.isOpen = b;
		if (b) {
			closeTimer = CLOSE_DURATION;
		}
	}


	@Override
	public String getName() {
		return "Door";
	}


	public boolean isOpen() {
		return this.isOpen;
	}

	

	@Override
	public boolean isTransparent() {
		return false;
	}
	

	@Override
	public boolean isTraversable() {
		return isOpen;
	}

	
	@Override
	public char getFloorChar() {
		return isOpen ? '\'' : '+';
	}

	
	@Override
	public void process() {
		processItems();

		if (this.isOpen) {
			if (this.entities.size() == 0) {
				this.closeTimer--;
				if (closeTimer <= 0) {
					this.isOpen = false;
				}
			}
		}
	}

}
