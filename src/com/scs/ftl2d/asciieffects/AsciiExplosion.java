package com.scs.ftl2d.asciieffects;

import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;

public class AsciiExplosion extends AbstractAsciiEffect {

	public AsciiExplosion(Main main) {
		super(main);
	}

	@Override
	public boolean process() {
		return true;

	}

	@Override
	public void drawChars(IGameView view) {
		
	}

}
