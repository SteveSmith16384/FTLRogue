package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.ShipLaser;
import com.scs.ftl2d.map.AbstractMapSquare;

public class EnemyShip extends AbstractAnotherShip {
	
	private enum Stage {NoContact, Hailed, Attacking, Boarding, WeSurrender }; 

	private static final int SHOT_POWER = 10;
	
	private Stage stage = Stage.NoContact;
	protected int totalTimer = 0;
	protected int stageTimer = 0;

	public EnemyShip(Main main) {
		super(main, "Enemy Ship");

		main.addMsg("An enemy ship has appeared!");
	}


	@Override
	public void process() {
		totalTimer++;
		stageTimer++;
		
		switch (stage) {
		case NoContact:
			break;
		case Hailed:
			break;
		case Attacking:
			break;
		case Boarding:
			break;
		case WeSurrender:
			break;
		default:
			break;
		
		}

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
				if (Main.RND.nextInt(10) == 0) {
					// Todo - If player damaged, teleport aboard
					this.shootPlayer(SHOT_POWER);
				}
			}
		} else {
			if (Main.RND.nextInt(8) == 0) {
				this.shootPlayer(SHOT_POWER);
			}
		}
	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		stats.add("Damage: " + (int)damagePcent + "%");
		return stats;
	}


	@Override
	public void shotByPlayer() {
		int dam = Main.RND.nextInt(15)+1;
		this.damagePcent += dam;
		main.addMsg("You inflict " + dam + " on the enemy ship.");
		if (this.damagePcent >= 100) {
			this.destroyed();
		}

		stage = Stage.Attacking;

	}


	@Override
	public String getHailResponse() {
		return "Prepare to be destroyed!";
	}


	@Override
	public void processCommand(String cmd) {
	}

}
