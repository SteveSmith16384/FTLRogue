package com.scs.ftl2d.entities;

import com.scs.ftl2d.Main;

public class Alien extends AbstractMob {

	public Alien(Main main, int _x, int _y, int _z, char c, String _name, int _side) {
		super(main, _x, _y, DrawableEntity.Z_UNIT, 'A', "Alien", AbstractMob.SIDE_ALIEN);
	}

	@Override
	public char getChar() {
		return this.theChar;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
