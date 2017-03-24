package com.scs.astrocommander.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.mobs.AbstractMob;
import com.scs.astrocommander.entities.mobs.EnemyUnit;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.modules.consoles.AbstractConsoleModule;

public class EnemyShip extends AbstractAnotherShip {

	private enum Stage {Hailed, Attacking, PlayerSurrendered };  

	private static final int SHOT_POWER = 10;
	private static final int STAGE_TIMER_INTERVAL = 10;

	private Stage stage = Stage.Hailed;
	protected int totalTimer = 0;
	protected int stageTimer = 0;
	protected List<AbstractMob> unitsTeleported = new ArrayList<>();

	public EnemyShip(Main main) {
		super(main, "Enemy Ship");

		main.addMsg("An enemy ship has appeared!");
	}


	@Override
	public void process() {
		totalTimer++;
		stageTimer++;

		switch (stage) {
		case Hailed:
			if (stageTimer > STAGE_TIMER_INTERVAL) {
				stageTimer = 0;
				stage = Stage.Attacking;
			}
			break;

		case Attacking:
			if (Main.RND.nextInt(8) == 0) {
				this.shootPlayer(SHOT_POWER);
			}
			teleportOntoShip();
			break;

		case PlayerSurrendered:
			teleportOntoShip();
			break;

		default:
			throw new RuntimeException("Unknown stage: " + stage);

		}

	}


	private void teleportOntoShip() {
		if (this.unitsTeleported.size() < 2) {
			if (Main.RND.nextInt(5) == 0) {
				AbstractMapSquare sq = main.gameData.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
				if (sq.getUnit() == null) {
					EnemyUnit unit = new EnemyUnit(main);
					this.unitsTeleported.add(unit);
					sq.addEntity(unit);
				}
			}
		} else {
			// Check if all dead
			for (AbstractMob mob : this.unitsTeleported) {
				if (mob.isAlive()) {
					return;
				}
			}
			// Got this far, all dead!
			main.addMsg(3, "The enemy ship disappears");
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
	public void getHailResponse(AbstractConsoleModule console) {
		switch (stage) {
		case Hailed:
			console.addLine("We will destroy you if you do not surrender.  Do you surrender? (y/n)");
			break;
			
		case Attacking:
			console.addLine("We will destroy you for your insolence!");
			break;

		case PlayerSurrendered:
			console.addLine("It was a wise choice to surrender.");
			break;

		default:
			throw new RuntimeException("Unknown stage: " + stage);

		}
	}


	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
		switch (stage) {
		case Hailed:
			switch (cmd) {
			case "y":
			case "yes":
				this.stage = Stage.PlayerSurrendered;
				console.addLine("Surrendering was you only choice!  Prepare to be boarded.");
				break;

			case "n":
			case "no":
				this.stage = Stage.Attacking;
				console.addLine("You will regret your decision!");
				break;
			}
			break;

		case Attacking:
			console.addLine("We have nothing to say to you except DIE!");
			break;

		case PlayerSurrendered:
			break;

		default:
			throw new RuntimeException("Unknown stage: " + stage);

		}
	}

}
