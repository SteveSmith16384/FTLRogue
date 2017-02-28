package com.scs.ftl2d.entities.mobs;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class Alien extends AbstractMob {

	public Alien(Main main, int _x, int _y) {
		super(main, _x, _y, DrawableEntity.Z_UNIT, 'A', "Alien", AbstractMob.SIDE_ALIEN);
	}

	@Override
	public char getChar() {
		return this.theChar;
	}

	@Override
	public void process() {
		AbstractMob mob = super.getClosestVisibleEnemy();
		if (mob != null) {
			this.moveTowards(mob.x, mob.y);
		}
	}

}
