package com.scs.ftl2d.views;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

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
import com.scs.ftl2d.entities.Unit;
import com.scs.ftl2d.map.AbstractMapSquare;

public class DefaultView implements IGameView, MouseListener, MouseMotionListener {

	private Terminal terminal;
	private Screen screen;

	public DefaultView() {

	}

	
	@Override
	public void init() throws IOException {
		terminal = new DefaultTerminalFactory().createTerminal();
		SwingTerminalFrame stf = (SwingTerminalFrame)terminal;
		stf.addMouseListener(this);
		stf.addMouseMotionListener(this);
		screen = new TerminalScreen(terminal);
		
	}


	@Override
	public void drawScreen(GameData gameData, Unit currentUnit) throws IOException {
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
		
		// Draw entities
		/*for (Entity e : gameData.entities) {
			if (e instanceof DrawableEntity) {
				DrawableEntity de = (DrawableEntity)e;
				screen.setCharacter(de.x, de.y, new TextCharacter(de.getChar()));
			}
		}*/

		// Draw stats
		tGraphics.putString(gameData.getWidth()+2, 0, "Turn " + gameData.turnNo);
		tGraphics.putString(gameData.getWidth()+2, 1, "Shields: " + (int)gameData.shieldLevel + "%");
		tGraphics.putString(gameData.getWidth()+2, 2, "Engine Temp: " + (int)gameData.engineTemp + "o");
		tGraphics.putString(gameData.getWidth()+2, 3, "Oxygen: " + (int)gameData.oxygenLevel + "%");
		tGraphics.putString(gameData.getWidth()+2, 4, "Fuel: " + gameData.fuel);
		
		// Draw current unit
		tGraphics.putString(gameData.getWidth()+2, 6, "Unit: " + currentUnit.getName());
		tGraphics.putString(gameData.getWidth()+2, 7, "Health: " + (int)currentUnit.health + "%");
		tGraphics.putString(gameData.getWidth()+2, 8, "Food: " + (int)currentUnit.food);

		// Messages
		int y = gameData.getHeight()+2;
		for (String s : gameData.msgs) {
			tGraphics.putString(0, y, s);
			y++;
		}
		
		screen.refresh();

	}


	@Override
	public char getInput() throws IOException {
		KeyStroke ks = screen.readInput();
		Character c = ks.getCharacter();
		if (c != null) {
			return c.charValue();
		}
		else {
			return '?';
		}
	}
	

	@Override
	public void close() throws IOException {
		screen.close();
		terminal.close();
		
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		Main.p("Mouse Moved");

		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		Main.p("Mouse Clicked");
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		Main.p("Mouse Entered");
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		Main.p("Mouse Pressed");
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
