package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.consoles.AbstractConsoleModule;

/*
 * AI:
 * If not wanted, ship is searched
 * If wanted, either teleport across or shoot (if high)
 * If teleport, ...
 */
public class PoliceShip extends AbstractAnotherShip {

	private int stage = 0;
	private boolean playerBoarded = false;

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
		main.addMsg("You inflict " + dam + " on the enemy ship.");
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


	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
		// Do nothing
	}

}
