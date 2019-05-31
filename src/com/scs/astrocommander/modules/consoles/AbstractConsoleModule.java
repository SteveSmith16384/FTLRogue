package com.scs.astrocommander.modules.consoles;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.AbstractModule;
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.IModule;

public abstract class AbstractConsoleModule extends AbstractModule {

	private static final int MAX_LINES = 20;

	private List<String> lines = new ArrayList<>();
	private String command = "";

	public AbstractConsoleModule(Main main, IModule prev) {
		super(main, prev);
	}


	@Override
	public void updateGame() {

	}


	@Override
	public boolean processInput(KeyStroke ks) {
		main.sfx.playSound("type2.mp3");
		if (ks.getKeyType() == KeyType.Enter) {
			this.addLine(command);
			processCommand(command);
			command = "";
		} else if (ks.getKeyType() == KeyType.Backspace) {
			if (command.length() > 0) {
				command = command.substring(0, command.length()-1);
			}
		} else {
			command = command + ks.getCharacter();
		}
		return false;
	}


	protected abstract void processCommand(String cmd);


	@Override
	public void drawScreen(IGameView view) {
		view.drawConsoleScreen(lines, command);
	}


	public void addLine(String s) {
		String sublines[] = s.split("\n");
		for (String l : sublines) { 
			this.lines.add("> " + l);
		}
		if (MAX_LINES > 0) {
			while (lines.size() > MAX_LINES) {
				lines.remove(0);
			}
		}
	}


	public void clearLines() {
		this.lines.clear();
	}

}
