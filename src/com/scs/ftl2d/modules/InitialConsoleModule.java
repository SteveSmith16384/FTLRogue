package com.scs.ftl2d.modules;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.Main;

public class InitialConsoleModule extends AbstractConsoleModule {

	public InitialConsoleModule(Main main) {
		super(main);

		addLine("1 - Available Missions");
		addLine("X - Exit");
	}


	@Override
	public boolean processInput(KeyStroke c) {
		char ch = c.getCharacter();
		switch (ch) {
		case '1':
			main.setModule(new MissionConsoleModule(main));
			break;

		case 'X':
			main.setModule(new PlayersShipModule(main));
			break;

		default:
			addLine("Unknown command");
		}
		return false;
	}

}
