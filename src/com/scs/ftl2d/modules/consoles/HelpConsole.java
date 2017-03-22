package com.scs.ftl2d.modules.consoles;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.AbstractModule;

public class HelpConsole extends AbstractConsoleModule {

	public HelpConsole(Main main, AbstractModule prev) {
		super(main, prev);

		this.addLine("HELP");
		this.addLine("");
		this.addLine("CONTROLS:");
		this.addLine("Numbers - select unit");
		this.addLine("Arrows - move unit");
		this.addLine("? - Help");
		this.addLine("c - close door");
		this.addLine("d - drop");
		this.addLine("e - examine (current/ TODO adjacent square)");
		this.addLine("f - fire ships weapons");
		this.addLine("h - Change current item");
		this.addLine("i - inventory");
		this.addLine("l - login to console");
		this.addLine("m - TEST move to");
		this.addLine("n - Nothing");
		this.addLine("o - open door");
		this.addLine("p - Pick up");
		this.addLine("s - shoot");
		this.addLine("t - throw");
		this.addLine("u - Use, e.g. prime grenade");
		this.addLine("w - wear");
		this.addLine("");
		this.addLine("Enter anything to return");


	}

	@Override
	protected void processCommand(String cmd) {
		this.main.setModule(this.prevModule);


	}


}
