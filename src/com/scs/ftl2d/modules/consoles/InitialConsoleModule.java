package com.scs.ftl2d.modules.consoles;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.AbstractModule;
import com.scs.ftl2d.modules.PlayersShipModule;

public class InitialConsoleModule extends AbstractConsoleModule {

	public InitialConsoleModule(Main main, AbstractModule prev) {
		super(main, prev);

		if (main.gameData.currentLocation != null) {
			addLine("Currently docked at " + main.gameData.currentLocation);
			addLine("");
		}
		addLine("1 - Ship Functions");
		//addLine("2 - Available Missions");
		addLine("X - Exit");
	}


	@Override
	public boolean processInput(KeyStroke c) {
		char ch = c.getCharacter();
		switch (ch) {
		case '1':
			main.setModule(new ShipFunctionsModule(main, this));
			break;

		case '2':
			main.setModule(new MissionConsoleModule(main, this));
			break;

		case 'X':
			main.setModule(new PlayersShipModule(main, this));
			break;

		default:
			addLine("Unknown command");
		}
		return true;
	}

}
