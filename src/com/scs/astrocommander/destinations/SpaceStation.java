package com.scs.astrocommander.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.mobs.PoliceMob;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.modules.consoles.AbstractConsoleModule;

public class SpaceStation extends AbstractSpaceLocation {

	private boolean shot = false;
	private int turnsUntilPolice = 0;
	private int numPoliceRequired = 0;

	public SpaceStation(Main main, String name) {
		super(main, name);
	}


	@Override
	public void process() {
		if (shot) {
			turnsUntilPolice--;
			if (turnsUntilPolice <= 0) {
				if (this.numPoliceRequired > 0) {
					numPoliceRequired--;
					// Teleport onto ship
					AbstractMapSquare sq = main.gameData.map_data.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
					sq.addEntity(new PoliceMob(main));
					main.addMsg("This is the police!  We have boarded your ship.");
				}
				turnsUntilPolice = Main.RND.nextInt(5)+2;
			}
		}
	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		return stats;
	}


	@Override
	public void shotByPlayer() {
		if (!shot) {
			main.addMsg(this.name + ": Prepare to be boarded!");
			turnsUntilPolice = Main.RND.nextInt(5)+2;
		}
		shot = true;
		numPoliceRequired++;

	}


	@Override
	public void getHailResponse(AbstractConsoleModule console) {
		console.addLine("Welcome to " + this.name + ".  You are cleared for launch.");
	}


	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
	}

}
