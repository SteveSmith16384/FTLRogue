package com.scs.ftl2d.entities.mobs;

import java.awt.Point;
import java.io.IOException;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.items.Gun;

public class EnemyUnit extends AbstractMob {

	public EnemyUnit(Main main, int _x, int _y) throws IOException {
		super(main, _x, _y, DrawableEntity.Z_UNIT, 'E', AbstractMob.GetRandomName(), SIDE_ENEMY_SHIP, 10, 4, 4);

		Gun gun = new Gun(main);
		this.equipment.add(gun);

	}


	@Override
	public void preProcess() {
		
	}


	@Override
	public void process() {
			boolean shooting = false;
			if (this.isUsingGun()) {
				shooting = this.checkForShooting();
			}
			if (!shooting) {
				this.moveAStar();
			}
	}

	
	@Override
	public Point getAStarDest() {
		// todo - find something to sabotage
		return null;
	}


}
