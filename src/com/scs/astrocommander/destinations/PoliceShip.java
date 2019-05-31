package com.scs.astrocommander.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.mobs.PoliceMob;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.modules.consoles.AbstractConsoleModule;

/*
 * AI:
 * If wanted=0, ship is searched
 * If wanted=1, player is arrested
 * If wanted>=2, shoot to kill
 * 
 */
public class PoliceShip extends AbstractAnotherShip {

	private static final int SHOT_POWER = 20;

	private int stage = 0;
	//private boolean playerBoarded = false;
	private int numTeleported = 0;

	public PoliceShip(Main main) {
		super(main, "Police Ship");

		main.addMsg("A police ship has appeared!");
	}


	@Override
	public void process() {
		stage++;

		if (stage > 5) {
			this.teleportOntoShip();
			if (main.gameData.wantedLevel >= 2) {
				if (Main.RND.nextInt(8) == 0) {
					this.shootPlayer(SHOT_POWER);
				}
			}
		}
	}


	@Override
	public void shotByPlayer() {
		main.gameData.wantedLevel++;
		int dam = Main.RND.nextInt(15)+1;
		this.damagePcent += dam;
		main.addMsg("You inflict " + dam + " on the police ship.");
		if (this.damagePcent >= 100) {
			this.destroyed();
			main.gameData.wantedLevel += 3;
		}

	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		stats.add("Damage: " + (int)damagePcent + "%");
		return stats;
	}


	@Override
	public void getHailResponse(AbstractConsoleModule console) {
		if (this.numTeleported <= 0) {
			switch ((int)main.gameData.wantedLevel) {
			case 0:
				console.addLine("Prepare for a routine search.");
				break;

			default:
				console.addLine("Prepare to be boarded.");
			}
		} else {
			switch ((int)main.gameData.wantedLevel) {
			case 0:
				console.addLine("Stand by.");
				break;

			default:
				console.addLine("[There is no response]");
			}

		}
	}


	private void teleportOntoShip() {
		if (numTeleported < 2) {
			if (Main.RND.nextInt(5) == 0) {
				AbstractMapSquare sq = main.gameData.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
				if (sq.getUnit() == null) {
					PoliceMob unit = new PoliceMob(main);
					numTeleported++;
					sq.addEntity(unit);
				}
			}
		}
	}


	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
		// Do nothing
	}

}
