package com.scs.ftl2d.modules;

import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;

public abstract class AbstractModule {

	protected Main main;
	
	public AbstractModule(Main _main) {
		main = _main;
	}

	
	public abstract void init();
	
	public abstract void updateGame();

	public abstract void drawScreen(IGameView view) throws IOException;
	
	public abstract boolean processInput(KeyStroke c);
}
