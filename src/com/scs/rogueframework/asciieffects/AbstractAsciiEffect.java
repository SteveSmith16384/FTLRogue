package com.scs.rogueframework.asciieffects;

import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.IGameView;

public abstract class AbstractAsciiEffect {

	protected AbstractRoguelike main;

	public AbstractAsciiEffect(AbstractRoguelike _main) {
		super();
		
		main = _main;
	}


	public abstract void drawChars(IGameView view); // todo - rename to drawEffect


	public abstract boolean process(); // return false if ended

}
