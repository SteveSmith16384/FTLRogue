package com.scs.ftl2d.modules;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.AbstractAsciiEffect;

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
