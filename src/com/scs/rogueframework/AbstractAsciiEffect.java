package com.scs.rogueframework;

import com.scs.astrocommander.Main;

public abstract class AbstractAsciiEffect {

	protected Main main;

	public AbstractAsciiEffect(Main _main) {
		super();
		
		main = _main;
	}


	public abstract void drawChars(IGameView view); // todo - rename to drawEffect


	public abstract boolean process(); // return false if ended

}
