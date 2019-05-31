package com.scs.astrocommander.entities.mobs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.items.Pistol;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.rogueframework.ecs.entities.DrawableEntity;

public class EnemyUnit extends AbstractMob {

	public EnemyUnit(Main main) {
		super(main, -1, -1, DrawableEntity.Z_UNIT, 'E', AbstractMob.GetRandomName(), SIDE_ENEMY_SHIP, 10, 4, true);

		Pistol gun = new Pistol(main);
		this.equipment.add(gun);

	}


	@Override
	public void process() {
		super.process();
		boolean shooting = false;
		if (this.isUsingGun()) {
			shooting = this.checkForShooting();
		}
		if (!shooting) {
			Main m = (Main)this.main;
			List<AbstractMapSquare> squares = m.gameData.map_data.getAdjacentSquares(this.x,  this.y);
			for (AbstractMapSquare sq : squares) {
				if (this.isSabotagable(sq)) {
					main.addMsgIfSeen(sq, 2, "You seen the enemy sabotaging the ship!");
					sq.incDamage(Main.RND.nextInt(5));
				}
			}
			this.moveAStar();
		}
	}


	@Override
	public Point getAStarDest() {
		Main m = (Main)this.main;
		List<AbstractMapSquare> squares = new ArrayList<>();
		for (int y=0 ; y<m.gameData.map_data.getHeight() ; y++) {
			for (int x=0 ; x<m.gameData.map_data.getWidth() ; x++) {
				AbstractMapSquare sq = m.gameData.map_data.map[x][y];
				if (isSabotagable(sq)) {
					squares.add(sq);
				}
			}
		}

		if (squares.isEmpty()) {
			return null;
		} else {
			AbstractMapSquare sq2 = squares.get(Main.RND.nextInt(squares.size()));
			return new Point(sq2.x, sq2.y);
		}
	}


	private boolean isSabotagable(AbstractMapSquare sq) {
		if (sq.getHealth() > 0) {
			return (sq.type == AbstractMapSquare.MAP_BATTERY ||
					sq.type == AbstractMapSquare.MAP_ENGINES ||
					sq.type == AbstractMapSquare.MAP_OXYGEN_GEN);
		}
		return false;
	}

}

