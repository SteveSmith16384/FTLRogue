package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.consoles.AbstractConsoleModule;

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
	public void getHailResponse(AbstractConsoleModule console) {
		console.addLine("Welcome to " + this.name + ".  You are cleared for launch.");
	}

	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
	}

}
