package com.scs.ftl2d.views;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
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
import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.AbstractAsciiEffect;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.map.AbstractMapSquare;

public class DefaultView implements IGameView {

	private static final TextCharacter ROUTE_CHAR = new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
	private static final TextCharacter TARGET_CHAR = new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.RED);
	private static final TextCharacter STAR_CHAR = new TextCharacter('*', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);

	private Terminal terminal;
	private Screen screen;

	private List<Point> stars;// = new ArrayList<>();

	public DefaultView() throws IOException {
		DefaultTerminalFactory fac = new DefaultTerminalFactory();
		fac.setInitialTerminalSize(new TerminalSize(70, 50));
		terminal = fac.createTerminal();
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
		int y=0;
		tGraphics.putString(gameData.getWidth()+2, y++, "Turn " + gameData.turnNo);
		tGraphics.putString(gameData.getWidth()+2, y++, "Oxygen: " + (int)gameData.oxygenLevel + "%");
		tGraphics.putString(gameData.getWidth()+2, y++, "Shields: " + (int)gameData.shieldPowerPcent + "%");
		tGraphics.putString(gameData.getWidth()+2, y++, "Weapon Temp: " + (int)gameData.weaponTemp + "c");

		if (gameData.currentLocation == null) {
			y++;
			tGraphics.putString(gameData.getWidth()+2, y++, "Ship Speed: " + (int)gameData.shipSpeed + " m/s");
			tGraphics.putString(gameData.getWidth()+2, y++, "Distance Left: " + (int)gameData.distanceToDest + " ly");
		}

		y++;
		tGraphics.putString(gameData.getWidth()+2, y++, "POWER");
		tGraphics.putString(gameData.getWidth()+2, y++, "Total Power: " + (int)gameData.totalPower);
		tGraphics.putString(gameData.getWidth()+2, y++, "Power Gain/t: " + (int)gameData.powerGainedPerTurn);
		tGraphics.putString(gameData.getWidth()+2, y++, "Power Used/t: " + (int)gameData.powerUsedPerTurn);

		// Draw mapsquares key
		y++;
		for (String tc : seenSquares.keySet()) {
			tGraphics.setCharacter(gameData.getWidth()+2, y, seenSquares.get(tc));
			tGraphics.putString(gameData.getWidth()+4, y, tc);
			y++;
		}

		// Draw units
		y++;
		tGraphics.putString(gameData.getWidth()+2, y++, "CREW");
		for (Unit unit : gameData.units) {
			StringBuilder str = new StringBuilder();
			if (unit == gameData.currentUnit) {
				str.append("*");
			}
			str.append(unit.getName()).append(" ");
			str.append("H:" + (int)unit.health + "%").append(" ");
			str.append("F:" + (int)unit.food);
			tGraphics.putString(gameData.getWidth()+2, y++, str.toString());
		}
		
		if (gameData.currentUnit.wearing != null) {
			tGraphics.putString(gameData.getWidth()+2, y++, "Unit is wearing " + gameData.currentUnit.wearing.hashCode());
		}

		// Say what items the unit is near 
		StringBuffer itemlist = new StringBuffer();
		for (DrawableEntity de : gameData.currentUnit.getSq().getEntities()) {
			if (de instanceof Unit == false) {
				itemlist.append(de.getName() + "; ");
			}
		}
		if (itemlist.length() > 0) {
			tGraphics.putString(gameData.getWidth()+2, y++, "Unit can see " + itemlist.toString());
		}

		// Location stats:
		if (gameData.currentLocation != null) {
			y++;
			tGraphics.putString(gameData.getWidth()+2, y++, "LOCATION: " + gameData.currentLocation.name);
			List<String> stats = gameData.currentLocation.getStats();
			for (String s : stats) {
				tGraphics.putString(gameData.getWidth()+2, y++, s);
			}
		}

		// Help text
		if (helpText.size() > 0) {
			y++;
			tGraphics.putString(gameData.getWidth()+2, y++, "HELP");
			for (String s : helpText) {
				tGraphics.putString(gameData.getWidth()+2, y++, s);
			}
		}


		// Messages
		y = Math.max(y, gameData.getHeight()+2);
		tGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
		for (String s : gameData.msgs) {
			tGraphics.putString(0, y, s);
			y++;
		}

		screen.refresh();

	}


	/*private int drawCurrentUnit(int x, int y) {
		
	}*/
	
	
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
			// todo - if drop of bottom, put back to top
		}
	}


	private void drawStars(Screen screen) {
		for (Point p : stars) {
			screen.setCharacter(p.x,  p.y, STAR_CHAR);
		}
	}
}
