package com.scs.astrocommander.modules;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.IGameView;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.asciieffects.AbstractAsciiEffect;

public abstract class AbstractModule {

	protected Main main;
	protected AbstractModule prevModule;
	
	public AbstractModule(Main _main, AbstractModule _prevModule) {
		super();
		
		main = _main;
		this.prevModule = _prevModule;
	}
	
	public abstract void updateGame();

	public abstract void drawScreen(IGameView view) throws IOException;
	
	public abstract boolean processInput(KeyStroke ks);
}
