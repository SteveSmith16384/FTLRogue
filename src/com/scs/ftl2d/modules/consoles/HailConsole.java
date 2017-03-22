package com.scs.ftl2d.modules.consoles;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.destinations.AbstractSpaceLocation;
import com.scs.ftl2d.modules.AbstractModule;

public class HailConsole extends AbstractConsoleModule {

	private AbstractSpaceLocation loc;

	public HailConsole(Main main, AbstractModule prev) {
		super(main, prev);
		
		loc = main.gameData.currentLocation;

		super.addLine("Hailing " + loc.name + "...");
		super.addLine("");
		super.addLine("(Enter 'exit' to return)");
		super.addLine("");
		//super.addLine(loc.getHailResponse());
		loc.getHailResponse(this);
	}
	

	@Override
	protected void processCommand(String cmd) {
		try {
			switch (cmd) {
			case "exit":
			case "quit":
			case "logout":
			case "x":
				this.main.setModule(this.prevModule);
				return;
			default:
				loc.processCommand(cmd, this);
			}
		} catch (Exception ex) {
			super.addLine("Unable to understand: " + ex.getMessage());
			ex.printStackTrace();
		}
		super.addLine("");
		//super.addLine(loc.getHailResponse());
		loc.getHailResponse(this);
	}


}
