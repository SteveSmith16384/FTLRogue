package com.scs.ftl2d.destinations;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.missions.TransportEggMission;
import com.scs.ftl2d.modules.consoles.AbstractConsoleModule;

public class StartingOutpost extends AbstractSpaceLocation {

	private boolean shot = false;
	private boolean eggTransported = false;


	public StartingOutpost(Main main, String name) {
		super(main, name);
	}


	@Override
	public void process() {
		if (shot) {
		}
	}


	@Override
	public List<String> getStats() {
		List<String> stats = new ArrayList<>();
		return stats;
	}


	@Override
	public void shotByPlayer() {
		if (!shot) {
			main.addMsg("Why are you shooting at us?  Please help us!");
		}
		shot = true;

	}


	@Override
	public void getHailResponse(AbstractConsoleModule console) {
		if (this.eggTransported == false) {
			console.addLine("We need your help.  Will you transport a delicate egg for us to the next starbase?");
		} else {
			console.addLine("Please look after the egg, it is very fragile.");
			
		}
	}


	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
		if (this.eggTransported == false) {
			switch (cmd) {
			case "y":
			case "yes":
				// Add egg
				TransportEggMission tem = new TransportEggMission(main);
				tem.accepted();
				main.gameData.currentMissions.add(tem);
				eggTransported = true;
				console.addLine("Thank you!  The egg has been teleported onto your ship.");
				break;

			case "n":
			case "no":
				console.addLine("Please reconsider; we need your help!");
				break;
			}
		}
	}

}
