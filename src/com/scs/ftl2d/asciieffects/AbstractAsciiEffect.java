package com.scs.ftl2d.asciieffects;

import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;

public abstract class AbstractAsciiEffect {

	protected Main main;
	public AbstractAsciiEffect nextEffect;

	public AbstractAsciiEffect(Main _main) {
		main = _main;
	}


	public abstract void drawChars(IGameView view);


	public abstract boolean process();

}
