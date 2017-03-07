package com.scs.ftl2d.entities.mobs;

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
	public void process(int pass) {
		if (pass == 2) {
			/*AbstractMob enemy = super.getClosestVisibleEnemy();
			if (enemy != null) {
				// todo
				float dist = this.distanceTo(enemy);
				this.moveTowards(enemy.x, enemy.y);
			}*/
			this.checkForShooting();
		}
		
	}

}
