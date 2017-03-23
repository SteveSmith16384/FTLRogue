package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.mobs.EnemyUnit;
import com.scs.ftl2d.entities.mobs.PoliceMob;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.modules.consoles.AbstractConsoleModule;

/*
 * AI:
 * If wanted=0, ship is searched
 * If wanted=1, player is arrested
 * If wanted>=2, shoot to kill
 * 
 */
public class PoliceShip extends AbstractAnotherShip {

	private int stage = 0;
	private boolean playerBoarded = false;
	private int numTeleoprted = 0;

	public PoliceShip(Main main) {
		super(main, "Police Ship");

		main.addMsg("A police ship has appeared!");
	}


	@Override
	public void process() {
		stage++;

		//switch ()
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
		if (this.playerBoarded == false) {
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
		if (numTeleoprted < 2) {
			if (Main.RND.nextInt(5) == 0) {
				AbstractMapSquare sq = main.gameData.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
				if (sq.getUnit() == null) {
					PoliceMob unit = new PoliceMob(main);
					numTeleoprted++;
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
