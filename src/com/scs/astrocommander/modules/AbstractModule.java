package com.scs.astrocommander.modules;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.Main;
import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.IModule;
import com.scs.rogueframework.input.IInputHander;

public abstract class AbstractModule implements IModule {

	protected Main main;
	protected IModule prevModule;
	public IInputHander inputHandler;

	public AbstractModule(Main _main, IModule _prevModule) {
		super();
		
		main = _main;
		this.prevModule = _prevModule;
	}
	
	public abstract void updateGame();

	public abstract void drawScreen(IGameView view);
	
	public abstract boolean processInput(KeyStroke ks);
}
