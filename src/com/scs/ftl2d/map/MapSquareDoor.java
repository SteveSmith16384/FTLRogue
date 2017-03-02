package com.scs.ftl2d.map;

import com.scs.ftl2d.Main;

public class MapSquareDoor extends AbstractMapSquare {

	private static final int CLOSE_DURATION = 4;

	private boolean isOpen = false;
	private int closeTimer;

	public MapSquareDoor(Main main, int code) {
		super(main, code);
	}


	public boolean setOpen(boolean b) {
		boolean res = false;
		if (main.gameData.totalPower > 0) {
			main.gameData.powerUsedPerTurn += 1f;
			this.isOpen = b;
			res = true;
		}
		if (isOpen) {
			closeTimer = CLOSE_DURATION;
		}
		return res;
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
	public void process(int pass) {
		processItems(pass);

		if (pass == 2) {
			if (this.isOpen) {
				if (this.entities.size() == 0) {
					this.closeTimer--;
					if (closeTimer <= 0) {
						this.setOpen(false);
					}
				}
			} else {
				/*if (main.gameData.totalPower <= 0) {
					this.isOpen = true; // Auto-open, don't check power
				}*/
			}
		}
	}

}
