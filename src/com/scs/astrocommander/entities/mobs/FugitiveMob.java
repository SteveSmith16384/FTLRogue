package com.scs.astrocommander.entities.mobs;

import java.awt.Point;

import com.scs.astrocommander.Main;
import com.scs.rogueframework.ecs.components.IIllegal;
import com.scs.rogueframework.ecs.entities.DrawableEntity;

public class FugitiveMob extends AbstractMob implements IIllegal {

	public FugitiveMob(Main main) {
		super(main, 0, 0, DrawableEntity.Z_UNIT, 'F', "Fugitive", AbstractMob.SIDE_PLAYER, 20, 5, true);
	}

	@Override
	public int getIllegality() {
		return 1;
	}

	@Override
	public Point getAStarDest() {
		return null;
	}

}
