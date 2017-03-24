package com.scs.astrocommander.modules.consoles;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.AbstractModule;

public class HelpConsole extends AbstractConsoleModule {

	public HelpConsole(Main main, AbstractModule prev) {
		super(main, prev);

		this.addLine("HELP");
		this.addLine("");
		this.helpKeys();
		this.showFooter();
	}


	@Override
	protected void processCommand(String cmd) {
		this.clearLines();
		if (cmd.toLowerCase().contains("keys")) {
			this.helpKeys();
		} else if (cmd.toLowerCase().contains("move")) {
			this.helpMovement();
		} else if (cmd.toLowerCase().contains("ship")) {
			this.helpShip();
		} else {
			this.main.setModule(this.prevModule);
		}
		this.showFooter();

	}


	private void helpShip() {
		this.addLine("SHIP HELP\n");
		this.addLine("* Your ship contains various installations:-");
		this.addLine("* MediBays heal wounded units.\n* Replicators provide food.\n* The control panel allows control of the ship.\n* Engines provide speed.\n* Oxygen Generators provide oxygen.\n");
		this.addLine("* If a section is damaged, e.g. by sabotage or enemy ships lasers, they are reduced in their effectiveness and must be repaired.");
	}
	
	
	private void showFooter() {
		this.addLine("");
		this.addLine("Enter 'movement', 'ship' or 'keys'.");
		this.addLine("Or anything else to return");

	}


	private void helpKeys() {
		this.addLine("CONTROLS:");
		this.addLine("Numbers - select unit");
		this.addLine("Arrows - move unit");
		this.addLine("? - Help");
		this.addLine("c - Close door");
		this.addLine("d - Drop");
		this.addLine("e - Examine (current/adjacent squares)");
		this.addLine("f - Fire ships weapons");
		this.addLine("h - Change current item");
		this.addLine("i - Inventory");
		this.addLine("l - Login to console");
		this.addLine("m - Move to");
		this.addLine("n - Nothing");
		this.addLine("o - Open door");
		this.addLine("p - Pick up");
		this.addLine("s - Shoot");
		this.addLine("t - Throw");
		this.addLine("u - Use, e.g. prime grenade");
		this.addLine("w - Wear");

	}


	private void helpMovement() {
		this.clearLines();
		this.addLine("MOVEMENT\n\n* Use the arrow keys to move the selected unit.\n* You can also give a unit a destination using 'm'.");
		this.addLine("* They will then move towards that point when not under direct control.");
		this.addLine("* Units will automatically perform relevant actions using adjacent squares, e.g. repair a damaged section of the ship.");

	}

}
