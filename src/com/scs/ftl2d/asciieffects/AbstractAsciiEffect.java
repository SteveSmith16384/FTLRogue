package com.scs.ftl2d.asciieffects;

import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.PlayersShipModule;

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
