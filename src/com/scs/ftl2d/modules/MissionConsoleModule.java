package com.scs.ftl2d.modules;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.missions.AbstractMission;
import com.scs.ftl2d.missions.TransportEggMission;

public class MissionConsoleModule extends AbstractConsoleModule {

	private AbstractMission mission;

	public MissionConsoleModule(Main main) {
		super(main);

	}


	@Override
	public void updateGame() {
		if (this.mission != null) {
			addLine(mission.getDescription());
			addLine("Do you accept? (y/n)");
		} else {
			// todo - get missions from current docked location
			addLine("1 - Transport Egg");
			addLine("X - Exit");
			
		}

	}


	@Override
	public boolean processInput(KeyStroke c) {
		char ch = c.getCharacter();
		switch (ch) {
		case '1':
			mission = new TransportEggMission(main);
			this.clearLines();
			break;

		case 'y':
			if (mission != null) {
				mission.accepted();
				main.gameData.currentMissions.add(mission);
				main.setModule(new InitialConsoleModule(main));
				mission = null;
			}

		case 'X':
			main.setModule(new PlayersShipModule(main));
			break;

		default:
			addLine("Unknown command");
		}
		return false;
	}
}
