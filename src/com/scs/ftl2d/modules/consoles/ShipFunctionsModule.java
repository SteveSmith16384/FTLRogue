package com.scs.ftl2d.modules.consoles;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.Main;

public class ShipFunctionsModule extends AbstractConsoleModule {

	public ShipFunctionsModule(Main main) {
		super(main);
		
		this.addLine("L - Launch Ship");
		//this.addLine("M - View Current Missions");
		this.addLine("X - Return");
	}

	@Override
	public boolean processInput(KeyStroke c) {
		if (c.getCharacter() != null) {
			char ch = c.getCharacter();
			switch (ch) {
			case 'l':
				break;

			case 'x':
				main.setModule(new InitialConsoleModule(main));
				break;

			default:
				addLine("Unknown command");
			}
		}
		return true;
	}

}
