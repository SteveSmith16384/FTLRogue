package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.ShipLaser;
import com.scs.ftl2d.map.AbstractMapSquare;

public class AnotherShip extends AbstractSpaceLocation {

	public AnotherShip(Main main) {
		super(main, "Enemy Ship");

		main.addMsg("An enemy ship has appeared!");
	}


	@Override
	public void process() {
		timer++;

		if (this.attacked == false) {
			if (timer == 2) { // Hail
				main.addMsg("The ship enemy ship hails you...");
			} if (timer == 3) { // Hail
				main.addMsg("\"Please surrender or we will attack.\"");
			} if (timer == 10) { // Hail
				main.addMsg("\"We do not want to destroy you...\"");
			} if (timer == 11) { // Hail
				main.addMsg("\"But will if we must!\"");
			} else if (timer > 20) { // Attack
				this.attackPlayer();
			}
		} else {
			this.attackPlayer();
		}
	}

	private void attackPlayer() {
		// Todo - If player damaged, teleport aboard
		
		// Shoot laser
		int x = Main.RND.nextInt(main.gameData.getMapWidth());
		int y = 0;
		while (y < main.gameData.getMapHeight()) {
			AbstractMapSquare sq = main.gameData.map[x][y];
			sq.incDamage(-10);
			if (sq.isSquareTraversable() == false) {
				break;
			}
			y++;
		}
		this.main.asciiEffects.add(new ShipLaser(main, x, 0, 0, 1, x, y)); // todo - add explosion at end
	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		stats.add("Damage: " + (int)damage + "%");
		return stats;
	}


	@Override
	public void shot() {
		this.attacked = true;
		
		int dam = Main.RND.nextInt(15)+1;
		this.damage += dam;
		main.addMsg("You inflict " + dam + " on the enemy ship.");
		
	}

}
