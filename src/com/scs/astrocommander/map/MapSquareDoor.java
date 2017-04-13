package com.scs.astrocommander.map;

import java.awt.Color;

import com.scs.astrocommander.Main;

public class MapSquareDoor extends AbstractMapSquare {

	private static final int CLOSE_DURATION = 8;

	private boolean isOpen = false;
	private int closeTimer;

	public MapSquareDoor(Main main, int code, int x, int y) {
		super(main, code, x, y);
	}


	public boolean setOpen(boolean b) {
		main.sfx.playSound("door.mp3");

		boolean res = false;
		/*if (main.gameData.totalPower > 0) {
			main.gameData.powerUsedPerTurn += 1f;*/
			this.isOpen = b;
		/*	res = true;
		}*/
		if (isOpen) {
			closeTimer = CLOSE_DURATION;
		}
		this.calcChar(); // To update the char
		main.checkOxygenFlag = true;
		return res;
	}


	@Override
	public String getName() {
		return "Door (" + (this.isOpen?"open":"closed") + ")";
	}


	public boolean isOpen() {
		return this.isOpen;
	}



	@Override
	public boolean isTransparent() {
		return isOpen;
	}


	@Override
	public boolean isTraversable() {
		return true; //isOpen;
	}


	@Override
	public boolean isAirtight() {
		return !isOpen;
	}


	@Override
	public char getFloorChar() {
		return isOpen ? '\'' : '+';
	}


	@Override
	public void process() {
		super.process();


		if (this.isOpen) {
			if (this.getEntities().isEmpty()) {
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


	@Override
	protected Color getBackgroundColour() {
		return Color.blue;
	}


	@Override
	public String getHelp() {
		if (this.isOpen) {
			return "You can press 'c' to close the door.";
		}
		return null;
		
	}


}
