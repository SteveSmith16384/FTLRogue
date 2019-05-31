package com.scs.rogueframework.guitests;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class TestOverlays {

	public TestOverlays() {
	}

	public static void main(String[] args) throws IOException {
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		
		Screen screen = new TerminalScreen(terminal);
		TextGraphics tGraphics = screen.newTextGraphics();
		screen.startScreen();
		screen.clear();
		tGraphics.drawRectangle(new TerminalPosition(3,3), new TerminalSize(10,10), '*');
		screen.refresh();

		screen.readInput();
		
		Screen screen2 = new TerminalScreen(terminal);
		TextGraphics tGraphics2 = screen2.newTextGraphics();
		screen2.startScreen();
		//screen2.clear();
		tGraphics2.drawRectangle(new TerminalPosition(6,6), new TerminalSize(8,8), '*');
		screen.refresh();
		screen2.refresh();
		
		screen2.readInput();
		
		screen2.stopScreen();
		screen2.close();
		
		screen.readInput();
			
		screen.stopScreen();
		screen.close();
	}


}
