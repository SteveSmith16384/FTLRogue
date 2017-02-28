package com.scs.ftl2d.missions;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.destinations.AbstractSpaceLocation;
import com.scs.ftl2d.entities.items.AlienEgg;
import com.scs.ftl2d.map.AbstractMapSquare;

public class TransportEggMission extends AbstractMission {
	
	private AbstractSpaceLocation destination;

	public TransportEggMission(Main main) {
		super(main);
		
		destination = main.gameData.starmap.getRandomLocation(main.gameData.currentLocation);
	}


	@Override
	public String getDescription() {
		return "Transport an egg to " + destination.name + " for " + this.getReward() + " creds";
	}


	@Override
	public int getReward() {
		return 1000;
	}


	@Override
	public void accepted() {
		main.addMsg("The egg has been teleported onto your ship.");
		AbstractMapSquare sq = main.gameData.getMapSquare(AbstractMapSquare.MAP_TELEPORTER);
		sq.entities.add(new AlienEgg(main));
		main.addMsg("We look forward to receiving it.  Please treat it carefully.");
	}


	@Override
	public void process() {
		// todo - mission complete?
		
	}
	

}
