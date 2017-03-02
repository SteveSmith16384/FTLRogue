package com.scs.ftl2d.modules.consoles;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.ftl2d.Main;

public class CommandConsole extends AbstractConsoleModule {

	private String command = "";

	public CommandConsole(Main main) {
		super(main);

		showMenu();		
	}


	private void showMenu() {
		super.addLine("Please enter a command: ");
		super.addLine("");
		super.addLine("'shields <number>' to set shield level (currently " + (int)main.gameData.shieldPowerLevel + ")");
		super.addLine("'engines <number>' to set engine level (currently " + (int)main.gameData.enginePowerLevel + ")");
		super.addLine("'weapons <number>' to set weapon level (currently " + (int)main.gameData.weaponPowerLevel + ")");
		if (!main.gameData.shipFlying) {
			super.addLine("'launch' to launch the ship");
		}
		super.addLine("'airlock open/close' to open or close the main airlock");
		super.addLine("'lights on/off' to turn the ship's lights on or off");
		super.addLine("");
		int powerDiff = (int)((main.gameData.powerGainedPerTurn - main.gameData.powerUsedPerTurn) * 60f);
		super.addLine("Ship power is currently changing by " + powerDiff  + " per minute");
	}


	@Override
	public boolean processInput(KeyStroke ks) {
		if (ks.getKeyType() == KeyType.Enter) {
			processCommand();
			command = "";
		} else {
			command = command + ks.getCharacter();
		}
		return false;
	}


	private void processCommand() {
		try {
			String tokens[] = this.command.split(" ");
			switch (tokens[0].toLowerCase()) {
			case "shields":
				super.clearLines();
				main.gameData.shieldPowerLevel = Integer.parseInt(tokens[0]);
				main.addMsg("Shields now at " + main.gameData.shieldPowerLevel);
				break;

			case "engines":
				super.clearLines();
				main.gameData.enginePowerLevel = Integer.parseInt(tokens[0]);
				main.addMsg("Engines now at " + main.gameData.enginePowerLevel);
				break;

			case "weapons":
				super.clearLines();
				main.gameData.weaponPowerLevel = Integer.parseInt(tokens[0]);
				main.addMsg("Weapons now at " + main.gameData.weaponPowerLevel);
				break;

			case "launch":
				super.clearLines();
				if (!main.gameData.shipFlying) {
					main.gameData.shipFlying = true;
					main.addMsg("The ship has been launched!");
				} else {
					main.addMsg("The ship has already been launched.");
				}
				break;

			case "airlock":
				super.clearLines();
				boolean open = tokens[1].equalsIgnoreCase("open");
				// todo
				break;

			case "lights":
				super.clearLines();
				boolean on = tokens[1].equalsIgnoreCase("on");
				main.gameData.shipLightsOn = on;
				if (on) {
					main.addMsg("The ship lights are now on.");
				} else {
					main.addMsg("The ship lights are now off.");
				}
				break;

			}
			showMenu();
		} catch (Exception ex) {
			super.addLine("Unable to understand: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
