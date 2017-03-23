package com.scs.ftl2d.missions;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.destinations.AbstractSpaceLocation;
import com.scs.ftl2d.entities.items.AlienEgg;
import com.scs.ftl2d.map.AbstractMapSquare;

public class TransportFugitiveMission extends AbstractMission {
	
	private AbstractSpaceLocation destination;

	public TransportFugitiveMission(Main main) {
		super(main);
		
		//destination = main.gameData.starmap.getRandomLocation(main.gameData.currentLocation);
	}


	@Override
	public String getDescription() {
		return "Transport a fugitive to " + destination.name + " for " + this.getReward() + " creds";
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
