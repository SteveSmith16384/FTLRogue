package com.scs.astrocommander.entities.mobs;

import java.awt.Point;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.items.Pistol;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.rogueframework.ecs.components.IIllegal;
import com.scs.rogueframework.ecs.components.IRangedWeapon;
import com.scs.rogueframework.ecs.entities.DrawableEntity;

public class PoliceMob extends AbstractMob {

	private int searchTurnsLeft = 40;
	private static boolean foundAnythingIllegal = false;

	public PoliceMob(Main main) {
		super(main, 0, 0, DrawableEntity.Z_UNIT, 'P', "Police", AbstractMob.SIDE_POLICE, 20, 5, true);

		Pistol gun = new Pistol(main);
		this.equipment.add(gun);
	}


	@Override
	public void process() {
		super.process();

		searchTurnsLeft--;
		
		Main m = (Main)main;

		if (this.searchTurnsLeft > 0) {
			if (m.gameData.wantedLevel > 0) {
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

		// Check for anything illegal
		if (!foundAnythingIllegal) {
			DrawableEntity de = super.getClosestEntity(IIllegal.class);
			if (de != null) {
				foundAnythingIllegal = true;
				m.gameData.wantedLevel += 1;
				main.addMsg(3, "The police find an illegal item!");
			}
		}

		if (this.searchTurnsLeft <= 0) {
			// Leave the ship
			if (this.getSq().getType() == AbstractMapSquare.MAP_TELEPORTER) {
				this.remove();
				main.addMsg("A policeman has left the ship.");
			}
		}
	}



	protected void attackedBy(AbstractMob attacker) {
		if (attacker.side == AbstractMob.SIDE_PLAYER) {
			Main m = (Main)main;
			m.gameData.wantedLevel += 1f;
		}
	}


	@Override
	public Point getAStarDest() {
		Main m = (Main)main;
		if (this.searchTurnsLeft > 0) {
			AbstractMapSquare sq2 =  m.gameData.map_data.getRandomMapSquare(AbstractMapSquare.MAP_FLOOR);
			return new Point(sq2.x, sq2.y);
		} else {
			// Time to leave
			AbstractMapSquare sq2 = m.gameData.map_data.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
			return new Point(sq2.x, sq2.y);
		}
	}



}
