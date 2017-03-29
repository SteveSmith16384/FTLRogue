package com.scs.astrocommander.views;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.scs.astrocommander.GameData;
import com.scs.astrocommander.IGameView;
import com.scs.astrocommander.LogMessage;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.asciieffects.AbstractAsciiEffect;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entities.mobs.Unit;
import com.scs.astrocommander.map.AbstractMapSquare;

/*
 * Map   Stats   Help
 * 
 * Log
 */
public class DefaultView implements IGameView {
	
	private static final int WIDTH = 120;

	private static final TextCharacter ROUTE_CHAR = new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
	private static final TextCharacter TARGET_CHAR = new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.RED);
	private static final TextCharacter STAR_CHAR = new TextCharacter('*', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);

	private Terminal terminal;
	private Screen screen;

	private List<Point> stars;

	public DefaultView() throws IOException {
		DefaultTerminalFactory fac = new DefaultTerminalFactory();
		fac.setInitialTerminalSize(new TerminalSize(WIDTH, 50));
		//fac.setForceTextTerminal(true);
		terminal = fac.createTerminalEmulator(); //.createTerminal();
		screen = new TerminalScreen(terminal);

	}


	@Override
	public void drawPlayersShipScreen(GameData gameData, Map<String, TextCharacter> seenSquares, List<AbstractAsciiEffect> effects, List<String> helpText, List<Point> route, Point selectedTarget) throws IOException {
		if (stars == null) {
			createStars(gameData);
		}

		screen.startScreen();
		screen.clear();

		TextGraphics tGraphics = screen.newTextGraphics();

		if (gameData.shipSpeed > 0) {
			this.moveStars(gameData);
		}
		drawStars(screen);

		// Draw map
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				TextCharacter tc = sq.getChar();
				if (sq.type != AbstractMapSquare.MAP_NOTHING) {
					//if (tc.getCharacter() != ' ') {
					screen.setCharacter(x, y, tc);
				}
			}			
		}

		// Draw route
		if (route != null) {
			for (Point p : route) {
				screen.setCharacter(p.x, p.y, ROUTE_CHAR);
			}
		}
		if (selectedTarget != null) {
			screen.setCharacter(selectedTarget.x, selectedTarget.y, TARGET_CHAR);
		}

		// Draw effects
		for (AbstractAsciiEffect effect : effects) {
			effect.drawChars(this);
		}

		
		// Draw stats
		int x = gameData.getWidth()+2;
		int y = 0;

		if (gameData.currentLocation == null) {
			tGraphics.putString(x, y++, "LOCATION: Deep Space");
		} else {
			tGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
			tGraphics.putString(x, y++, "LOCATION: " + gameData.currentLocation.getName());
		}

		tGraphics.setForegroundColor(TextColor.ANSI.WHITE);
		tGraphics.putString(x, y++, "Turn " + gameData.turnNo);
		tGraphics.putString(x, y++, "Oxygen: " + (int)gameData.oxygenLevel + "%");
		tGraphics.putString(x, y++, "Shields: " + (int)gameData.shieldPowerPcent + "%");
		tGraphics.putString(x, y++, "Engines: " + (int)gameData.enginePowerPcent + "%");
		tGraphics.putString(x, y++, "Weapons: " + (int)gameData.weaponPowerPcent + "%");
		tGraphics.putString(x, y++, "Weapon Temp: " + (int)gameData.weaponTemp + "c");
		tGraphics.putString(x, y++, "Wanted Level: " + (int)gameData.wantedLevel);

		if (gameData.currentLocation == null) {
			y++;
			tGraphics.putString(x, y++, "Ship Speed: " + (int)gameData.shipSpeed + " m/s");
			tGraphics.putString(x, y++, "Distance Left: " + (int)gameData.distanceToDest + " ly");
		}

		y++;
		tGraphics.putString(x, y++, "POWER");
		tGraphics.putString(x, y++, "Total Power: " + (int)gameData.totalPower);
		tGraphics.putString(x, y++, "Power Gain/t: " + (int)gameData.powerGainedPerTurn);
		tGraphics.putString(x, y++, "Power Used/t: " + (int)gameData.powerUsedPerTurn);

		// Draw mapsquares key
		y++;
		for (String tc : seenSquares.keySet()) {
			tGraphics.setCharacter(x, y, seenSquares.get(tc));
			tGraphics.putString(x+2, y, tc);
			y++;
		}

		// Draw units
		x = 54;
		y=2;
		tGraphics.putString(x, y++, "CREW");
		for (Unit unit : gameData.units) {
			StringBuilder str = new StringBuilder();
			if (unit == gameData.currentUnit) {
				str.append("*");
			}
			str.append(unit.getName()).append(" ");
			str.append("H:" + (int)unit.health + "%").append(" ");
			str.append("F:" + (int)unit.food);
			tGraphics.putString(x, y++, str.toString());
		}

		if (gameData.currentUnit.wearing != null) {
			tGraphics.putString(x, y++, "Unit is wearing " + gameData.currentUnit.wearing.hashCode());
		}

		// Say what items the unit is near 
		StringBuffer itemlist = new StringBuffer();
		for (DrawableEntity de : gameData.currentUnit.getSq().getEntities()) {
			if (de instanceof Unit == false) {
				itemlist.append(de.getName() + "; ");
			}
		}
		if (itemlist.length() > 0) {
			tGraphics.putString(x, y++, "Unit can see " + itemlist.toString());
		}

		// Location stats:
		if (gameData.currentLocation != null) {
			y++;
			tGraphics.putString(x, y++, gameData.currentLocation.getName() + ":");
			List<String> stats = gameData.currentLocation.getStats();
			for (String s : stats) {
				tGraphics.putString(x, y++, s);
			}
		}

		// Help text
		if (helpText.size() > 0) {
			y++;
			tGraphics.putString(x, y++, "HELP");
			for (String s : helpText) {
				tGraphics.putString(x, y++, s);
			}
		}


		// Messages
		y = Math.max(y, gameData.getHeight()) + 2;
		tGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
		for (LogMessage msg : gameData.msgs) {
			switch (msg.priority) {
			case 3:
				tGraphics.setForegroundColor(TextColor.ANSI.RED);
				break;
			case 2:
				tGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
				break;
			default:
				tGraphics.setForegroundColor(TextColor.ANSI.WHITE);
				break;
			}
			tGraphics.putString(0, y, msg.msg);
			y++;
		}

		if (Settings.DEBUG) {
			tGraphics.setForegroundColor(TextColor.ANSI.RED);
			tGraphics.putString(0, 0, "DEBUG MODE");
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
			while (s.length() > 70) {
				String s2 = s.substring(0, 70);
				tGraphics.putString(0, y, s2);
				y++;
				s = s.substring(70);
			}
			tGraphics.putString(0, y, s);
			y++;
		}
		y++;
		tGraphics.putString(0, y, "> " + cmd);
		screen.setCursorPosition(new TerminalPosition(cmd.length()+2, y));
		screen.refresh();

	}


	@Override
	public void drawCharacter(int x, int y, TextCharacter ch) {
		screen.setCharacter(x, y, ch);		
	}


	private void createStars(GameData gameData) {
		stars = new ArrayList<>();
		for (int i=0 ; i<20 ; i++) {
			int x = Main.RND.nextInt(gameData.getMapWidth());
			int y = Main.RND.nextInt(gameData.getMapHeight());
			this.stars.add(new Point(x, y));
		}
	}


	private void moveStars(GameData gameData) {
		for (Point p : stars) {
			p.y++;
			// if drop of bottom, put back to top
			if (p.y > gameData.getHeight()) {
				p.x = Main.RND.nextInt(gameData.getMapWidth());
				p.y = 0;
			}
		}
	}


	private void drawStars(Screen screen) {
		for (Point p : stars) {
			screen.setCharacter(p.x,  p.y, STAR_CHAR);
		}
	}
}