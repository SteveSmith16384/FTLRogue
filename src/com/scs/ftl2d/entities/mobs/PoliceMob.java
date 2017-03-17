package com.scs.ftl2d.entities.mobs;

import java.awt.Point;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class PoliceMob extends AbstractMob {

	private Point lastDir = new Point(1, 0);

	public PoliceMob(Main main) { // todo - make stats random
		super(main, 0, 0, DrawableEntity.Z_UNIT, 'P', "Police", AbstractMob.SIDE_POLICE, 20, 5, true);
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
