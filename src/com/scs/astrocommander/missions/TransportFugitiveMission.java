package com.scs.astrocommander.missions;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.destinations.AbstractSpaceLocation;
import com.scs.astrocommander.entities.items.AlienEgg;
import com.scs.astrocommander.map.AbstractMapSquare;

public class TransportFugitiveMission extends AbstractMission {
	
	private AbstractSpaceLocation destination;

	public TransportFugitiveMission(Main main) {
		super(main);
		
		//destination = main.gameData.starmap.getRandomLocation(main.gameData.currentLocation);
	}


	@Override
	public String getDescription() {
		return "Transport a fugitive to " + destination.getName() + " for " + this.getReward() + " creds";
	}


	@Override
	public int getReward() {
		return 1000;
	}


	@Override
	public void accepted() {
		// todo
	}


	@Override
	public void process() {
		
	}
	

}
