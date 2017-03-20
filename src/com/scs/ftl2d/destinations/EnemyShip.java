package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.ShipLaser;
import com.scs.ftl2d.map.AbstractMapSquare;

public class EnemyShip extends AbstractAnotherShip {

	private static final int SHOT_POWER = 10;

	public EnemyShip(Main main) {
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
				if (Main.RND.nextInt(8) == 0) {
					// Todo - If player damaged, teleport aboard
					this.shootPlayer(SHOT_POWER);
				}
			}
		} else {
			// todo - don't shot every turn!
			this.shootPlayer(SHOT_POWER);
		}
	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		stats.add("Damage: " + (int)damage + "%");
		return stats;
	}


	@Override
	public void shotByPlayer() {
		this.attacked = true;

		int dam = Main.RND.nextInt(15)+1;
		this.damage += dam;
		main.addMsg("You inflict " + dam + " on the enemy ship.");

	}


	@Override
	public String getHailResponse() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String processCommand(String cmd) {
		// TODO Auto-generated method stub
		return null;
	}

}
