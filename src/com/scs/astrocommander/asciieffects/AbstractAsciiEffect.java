package com.scs.astrocommander.asciieffects;

import com.scs.astrocommander.IGameView;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;

public abstract class AbstractAsciiEffect {

	protected Main main;
	//protected PlayersShipModule module;
	//public AbstractAsciiEffect nextEffect;

	public AbstractAsciiEffect(Main _main) {//, PlayersShipModule _module) {
		super();
		
		main = _main;
		//module = _module;
	}


	public abstract void drawChars(IGameView view);


	public abstract boolean process(); // return false if ended

}
