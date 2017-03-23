package com.scs.ftl2d.entities.mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.items.Pistol;
import com.scs.ftl2d.entityinterfaces.IRangedWeapon;
import com.scs.ftl2d.map.AbstractMapSquare;

public class PoliceMob extends AbstractMob {

	private int searchTurnsLeft = 40;

	public PoliceMob(Main main) {
		super(main, 0, 0, DrawableEntity.Z_UNIT, 'P', "Police", AbstractMob.SIDE_POLICE, 20, 5, true);

		Pistol gun = new Pistol(main);
		this.equipment.add(gun);
	}


	@Override
	public void process() {
		super.process();

		searchTurnsLeft--;

		// todo - check for anythingh illegal
		if (this.searchTurnsLeft > 0) {
			if (main.gameData.wantedLevel > 1) {
				AbstractMob mob = super.getClosestVisibleEnemy();
				if (mob != null) {
					searchTurnsLeft = Math.max(searchTurnsLeft, 20);
					IRangedWeapon gun = this.getGun();
					if (gun != null && this.distanceTo(mob) <= gun.getRange()) {
						this.shoot(mob, gun);
					} else {
						this.moveTowards(mob.x, mob.y);
					}
					return;
				}
			}
		}

		this.moveAStar();

		if (this.searchTurnsLeft <= 0) {
			if (this.getSq().type == AbstractMapSquare.MAP_TELEPORTER) {
				this.remove();
				main.addMsg("A policeman has left the ship.");
			}
		}
	}



	protected void attackedBy(AbstractMob attacker) {
		if (attacker.side == AbstractMob.SIDE_PLAYER) {
			main.gameData.wantedLevel += 1f;
		}
	}


	@Override
	public Point getAStarDest() {
		if (this.searchTurnsLeft > 0) {
			AbstractMapSquare sq2 =  main.gameData.getRandomMapSquare(AbstractMapSquare.MAP_FLOOR);
			return new Point(sq2.x, sq2.y);
		} else {
			// Time to leave
			AbstractMapSquare sq2 = main.gameData.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
			return new Point(sq2.x, sq2.y);
		}
	}



}
