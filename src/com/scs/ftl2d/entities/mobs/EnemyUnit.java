package com.scs.ftl2d.entities.mobs;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.items.Gun;
import com.scs.ftl2d.map.AbstractMapSquare;

public class EnemyUnit extends AbstractMob {

	public EnemyUnit(Main main, int _x, int _y) throws IOException {
		super(main, _x, _y, DrawableEntity.Z_UNIT, 'E', AbstractMob.GetRandomName(), SIDE_ENEMY_SHIP, 10, 4, 4, true);

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
			//List<AbstractMapSquare> squares = main.gameData.getAdjacentSquares(this.x,  this.y)
			// todo - Check if can sabotage
			this.moveAStar();
		}
	}


	@Override
	public Point getAStarDest() {
		List<AbstractMapSquare> squares = new ArrayList<>();
		for (int y=0 ; y<main.gameData.getHeight() ; y++) {
			for (int x=0 ; x<main.gameData.getWidth() ; x++) {
				AbstractMapSquare sq = main.gameData.map[x][y];
				if (sq.damage_pcent < 100) {
					if (isSabotagable(sq)) {
						squares.add(sq);
					}
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
		return (sq.type == AbstractMapSquare.MAP_BATTERY ||
				sq.type == AbstractMapSquare.MAP_ENGINES ||
				sq.type == AbstractMapSquare.MAP_OXYGEN_GEN);

	}

}

