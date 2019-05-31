package com.scs.astrocommander.missions;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.destinations.AbstractSpaceLocation;
import com.scs.astrocommander.entities.items.AlienEgg;
import com.scs.astrocommander.map.AbstractMapSquare;

public class TransportEggMission extends AbstractMission {
	
	private AbstractSpaceLocation destination;

	public TransportEggMission(Main main) {
		super(main);
		
		//destination = main.gameData.starmap.getRandomLocation(main.gameData.currentLocation);
	}


	@Override
	public String getDescription() {
		return "Transport an egg to " + destination.getName() + " for " + this.getReward() + " creds";
	}


	@Override
	public int getReward() {
		return 1000;
	}


	@Override
	public void accepted() {
		//main.addMsg("The egg has been teleported onto your ship.");
		AbstractMapSquare sq = main.gameData.map_data.getFirstMapSquare(AbstractMapSquare.MAP_TELEPORTER);
		sq.addEntity(new AlienEgg(main));
		//main.addMsg("We look forward to receiving it.  Please treat it carefully.  ");
	}


	@Override
	public void process() {
		
	}
	

}
