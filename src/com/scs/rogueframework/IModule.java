package com.scs.rogueframework;

import com.googlecode.lanterna.input.KeyStroke;

public interface IModule {

	void drawScreen(IGameView view);
	
	void updateGame();
	
	boolean processInput(KeyStroke lastChar);
}
