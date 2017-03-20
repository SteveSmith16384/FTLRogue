package com.scs.ftl2d.modules.consoles;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareAirlock;
import com.scs.ftl2d.modules.AbstractModule;

public class CommandConsole extends AbstractConsoleModule {

	public CommandConsole(Main main, AbstractModule prev) {
		super(main, prev);

		this.addLine("SHIP COMPUTER CONSOLE (v0.01 beta)");
		this.addLine("");
		if (main.gameData.currentLocation != null) {
			addLine("Currently docked at " + main.gameData.currentLocation);
			addLine("");
		}

		showMenu();
	}


	private void showMenu() {
		super.addLine("Please enter a command and press enter: ");
		super.addLine("");
		super.addLine("'shields <0-100>' to set shield level (currently " + (int)main.gameData.shieldPowerPcent + "%)");
		super.addLine("'engines <0-100>' to set engine level (currently " + (int)main.gameData.enginePowerPcent + "%)");
		super.addLine("'weapons <0-100>' to set weapon level (currently " + (int)main.gameData.weaponPowerPcent + "%)");
		if (main.gameData.currentLocation != null) {
			super.addLine("'launch' to launch the ship");
			super.addLine("'hail' to hail " + main.gameData.currentLocation.name);
		}
		super.addLine("'airlock open/close' to open or close the main airlock");
		super.addLine("'lights on/off' to turn the ship's lights on or off");
		super.addLine("'exit' to return");
		super.addLine("");
		//int powerDiff = (int)((main.gameData.powerGainedPerTurn - main.gameData.powerUsedPerTurn) * 60f);
		//super.addLine("Ship power is currently changing by " + powerDiff  + " per minute");
	}


	@Override
	protected void processCommand(String cmd) {
		try {
			String tokens[] = cmd.split(" ");
			switch (tokens[0].toLowerCase()) {
			case "shields":
				super.clearLines();
				main.gameData.shieldPowerPcent = Integer.parseInt(tokens[1]);
				super.addLine("Shields now at " + main.gameData.shieldPowerPcent);
				break;

			case "engines":
				super.clearLines();
				main.gameData.enginePowerPcent = Integer.parseInt(tokens[1]);
				super.addLine("Engines now at " + main.gameData.enginePowerPcent);
				if (main.gameData.currentLocation != null) {
					super.addLine("(Although not currently in use)");
				}
				break;

			case "weapons":
				super.clearLines();
				main.gameData.weaponPowerPcent = Integer.parseInt(tokens[1]);
				super.addLine("Weapons now at " + main.gameData.weaponPowerPcent);
				break;

			case "launch":
				super.clearLines();
				if (main.gameData.currentLocation != null) {
					super.addLine("The ship has been launched!");
					main.gameData.currentLocation = null;
					//main.gameData.distanceToDest = 1000;
				} else {
					super.addLine("The ship has already been launched.");
				}
				break;

			case "airlock":
				super.clearLines();
				boolean open = tokens[1].equalsIgnoreCase("open");
				for (int y=0 ; y<main.gameData.getHeight() ; y++) {
					for (int x=0 ; x<main.gameData.getWidth() ; x++) {
						AbstractMapSquare sq = main.gameData.map[x][y];
						if (sq instanceof MapSquareAirlock) {
							MapSquareAirlock al = (MapSquareAirlock)sq;
							al.setOpen(open);
						}
					}			
				}
				super.addLine("The airlock is now " + (open?"open":"closed") + ".");
				main.checkOxygenFlag = true;
				break;

			case "lights":
				super.clearLines();
				boolean on = tokens[1].equalsIgnoreCase("on");
				main.gameData.shipLightsOn = on;
				super.addLine("The ship lights are now " + (on?"on":"off") + ".");
				main.gameData.recalcVisibleSquares();
				break;

			case "exit":
			case "quit":
			case "logout":
			case "x":
				this.main.setModule(this.prevModule);
				return;
			}
		} catch (Exception ex) {
			super.addLine("Unable to understand: " + ex.getMessage());
			ex.printStackTrace();
		}
		super.addLine("");
		showMenu();
	}

}
