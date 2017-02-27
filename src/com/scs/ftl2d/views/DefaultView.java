package com.scs.ftl2d.views;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import com.scs.ftl2d.GameData;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.map.AbstractMapSquare;

public class DefaultView implements IGameView {

	private Terminal terminal;
	private Screen screen;

	public DefaultView() throws IOException {
		DefaultTerminalFactory fac = new DefaultTerminalFactory();
		fac.setInitialTerminalSize(new TerminalSize(70, 50));
		terminal = fac.createTerminal();
		//terminal.
		SwingTerminalFrame stf = (SwingTerminalFrame)terminal;
		screen = new TerminalScreen(terminal);


	}


	@Override
	public void drawPlayersShipScreen(GameData gameData, Unit currentUnit) throws IOException {
		screen.startScreen();
		screen.clear();

		TextGraphics tGraphics = screen.newTextGraphics();

		// Draw map
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				screen.setCharacter(x, y, new TextCharacter(sq.getChar()));
			}			
		}

		// Draw stats
		tGraphics.putString(gameData.getWidth()+2, 0, "Turn " + gameData.turnNo);
		tGraphics.putString(gameData.getWidth()+2, 1, "Shields: " + (int)gameData.shieldLevel + "%");
		tGraphics.putString(gameData.getWidth()+2, 1, "Hull Dmg: " + (int)gameData.hullDamage + "%");
		//tGraphics.putString(gameData.getWidth()+2, 2, "Engine Temp: " + (int)gameData.engineTemp + "o");
		tGraphics.putString(gameData.getWidth()+2, 3, "Oxygen: " + (int)gameData.oxygenLevel + "%");
		tGraphics.putString(gameData.getWidth()+2, 4, "Fuel: " + gameData.fuel);

		// Draw current unit
		tGraphics.putString(gameData.getWidth()+2, 6, "Unit: " + currentUnit.getName());
		tGraphics.putString(gameData.getWidth()+2, 7, "Health: " + (int)currentUnit.health + "%");
		tGraphics.putString(gameData.getWidth()+2, 8, "Food: " + (int)currentUnit.food);

		// Todo - list other units and status

		// Messages
		int y = gameData.getHeight()+2;
		for (String s : gameData.msgs) {
			tGraphics.putString(0, y, s);
			y++;
		}

		screen.refresh();

	}


	@Override
	public KeyStroke getInput() throws IOException {
		KeyStroke ks = screen.readInput();
		/*Character c = ks.getCharacter();
		if (c != null) {
			return c.charValue();
		}
		else {
			return '?';
		}*/
		return ks;
	}


	@Override
	public void close() throws IOException {
		screen.close();
		terminal.close();

	}


	@Override
	public void drawConsoleScreen(List<String> lines) throws IOException {
		screen.startScreen();
		screen.clear();

		TextGraphics tGraphics = screen.newTextGraphics();
		int y = 0;
		for (String s : lines) {
			tGraphics.putString(0, y, lines.get(0));
			y++;
		}

		screen.refresh();

	}



}
