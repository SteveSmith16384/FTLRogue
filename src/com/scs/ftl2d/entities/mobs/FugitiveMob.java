package com.scs.ftl2d.entities.mobs;

import java.awt.Point;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.IIllegal;

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
