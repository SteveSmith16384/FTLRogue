package com.scs.ftl2d.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.scs.ftl2d.GameData;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.items.AbstractItem;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.map.AbstractMapSquare;

public class DefaultView implements IGameView {

	private Terminal terminal;
	private Screen screen;

	private Map<TextCharacter, String> seenSquares = new HashMap<>();

	public DefaultView() throws IOException {
		DefaultTerminalFactory fac = new DefaultTerminalFactory();
		fac.setInitialTerminalSize(new TerminalSize(70, 50));
		terminal = fac.createTerminal();
		screen = new TerminalScreen(terminal);
	}


	@Override
	public void drawPlayersShipScreen(GameData gameData, Unit currentUnit) throws IOException {
		screen.startScreen();
		screen.clear();

		TextGraphics tGraphics = screen.newTextGraphics();

		// Draw map
		seenSquares.clear();
		gameData.recalcVisibleSquares(); // todo - only call this if map or units changed
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				//screen.setCharacter(x, y, new TextCharacter(sq.getChar()));
				TextCharacter tc = sq.getChar();
				screen.setCharacter(x, y, tc);
				if (!seenSquares.containsKey(tc)) {
					seenSquares.put(tc, sq.getName());
				}
			}			
		}

		// Draw stats
		int y=0;
		tGraphics.putString(gameData.getWidth()+2, y++, "Turn " + gameData.turnNo);
		tGraphics.putString(gameData.getWidth()+2, y++, "Oxygen: " + (int)gameData.oxygenLevel + "%");
		tGraphics.putString(gameData.getWidth()+2, y++, "Shields: " + (int)gameData.shieldPowerLevelPcent + "%");
		tGraphics.putString(gameData.getWidth()+2, y++, "Hull Dmg: " + (int)gameData.hullDamage + "%");

		y++;
		tGraphics.putString(gameData.getWidth()+2, y++, "POWER");
		tGraphics.putString(gameData.getWidth()+2, y++, "Total Power: " + (int)gameData.totalPower);
		tGraphics.putString(gameData.getWidth()+2, y++, "Power Gain: " + (int)gameData.powerGainedPerTurn);
		tGraphics.putString(gameData.getWidth()+2, y++, "Power Used: " + (int)gameData.powerUsedPerTurn);

		// Draw mapsquares key
		y++;
		for (TextCharacter tc : this.seenSquares.keySet()) {
			tGraphics.setCharacter(gameData.getWidth()+2, y, tc);
			tGraphics.putString(gameData.getWidth()+4, y, this.seenSquares.get(tc));
			y++;
		}

		// Draw units
		y++;
		tGraphics.putString(gameData.getWidth()+2, y++, "CREW");
		for (Unit unit : gameData.units) {
			StringBuilder str = new StringBuilder();
			if (unit == currentUnit) {
				str.append("*");
			}
			str.append(unit.getName()).append(" ");
			str.append("H:" + (int)unit.health + "%").append(" ");
			str.append("F:" + (int)unit.food);
			tGraphics.putString(gameData.getWidth()+2, y++, str.toString());
		}

		// Say what items the unit is near 
		StringBuffer itemlist = new StringBuffer();
		for (DrawableEntity de : gameData.currentUnit.getSq().getEntities()){
			if (de instanceof AbstractItem) {
				itemlist.append(de.getName() + "; ");
			}
		}
		if (itemlist.length() > 0) {
			tGraphics.putString(gameData.getWidth()+2, y++, "Unit can see " + itemlist.toString());
		}


		// Messages
		y = Math.max(y, gameData.getHeight()+2);
		for (String s : gameData.msgs) {
			tGraphics.putString(0, y, s);
			y++;
		}

		screen.refresh();

	}


	@Override
	public KeyStroke getInput() throws IOException {
		KeyStroke ks = screen.readInput();
		return ks;
	}


	@Override
	public void close() throws IOException {
		screen.close();
		terminal.close();

	}


	@Override
	public void drawConsoleScreen(List<String> lines, String cmd) throws IOException {
		screen.startScreen();
		screen.clear();
		
		TextGraphics tGraphics = screen.newTextGraphics();
		tGraphics.setForegroundColor(TextColor.ANSI.GREEN);
		int y = 0;
		for (String s : lines) {
			tGraphics.putString(0, y, s);
			y++;
		}
		y++;
		tGraphics.putString(0, y, "> " + cmd + "_");
		
		screen.refresh();

	}



}
