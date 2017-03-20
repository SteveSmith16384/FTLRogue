package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;

public class Planet extends AbstractSpaceLocation {

	public Planet(Main main, String name) {
		super(main, name);
	}

	@Override
	public void process() {
		
	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		return stats;
	}

	@Override
	public void shotByPlayer() {
		main.addMsg("You shoot the planet.  Your laser dissipates in the atmosphere.");
		
	}

	
	@Override
	public String getHailResponse() {
		return "Welcome to " + this.name + ".  You are cleared for launch.";
	}

	@Override
	public String processCommand(String cmd) {
		return null;
	}

}
