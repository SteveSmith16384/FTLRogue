package com.scs.ftl2d.entities.mobs;

import java.awt.Point;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class Alien extends AbstractMob {

	private Point lastDir = new Point(1, 0);

	public Alien(Main main, int _x, int _y) {
		super(main, _x, _y, DrawableEntity.Z_UNIT, 'A', "Alien", AbstractMob.SIDE_ALIEN, 50, 50, false);
	}


	/*@Override
	public void preProcess() {

	}*/


	@Override
	public void process() {
		AbstractMob mob = super.getClosestVisibleEnemy();
		if (mob != null) {
			this.moveTowards(mob.x, mob.y);
		} else {
			boolean success = this.attemptMove(lastDir.x, lastDir.y);
			if (!success) {
				lastDir = this.rotateDir(lastDir);
			}
		}
	}


	@Override
	public Point getAStarDest() {
		throw new RuntimeException("Do not call this");
	}


}
