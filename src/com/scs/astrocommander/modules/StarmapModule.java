package com.scs.astrocommander.modules;

import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.Main;
import com.scs.rogueframework.IGameView;

public class StarmapModule extends AbstractModule { // Currently unused

	public StarmapModule(Main main, AbstractModule prev) {
		super(main, prev);
	}


	@Override
	public void updateGame() {
		
	}

	@Override
	public void drawScreen(IGameView view) throws IOException {
	}

	@Override
	public boolean processInput(KeyStroke c) {
		return false;
	}
}
