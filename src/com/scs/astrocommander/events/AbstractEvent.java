package com.scs.astrocommander.events;

import com.scs.astrocommander.Main;
import com.scs.rogueframework.ecs.components.IProcessable;

public abstract class AbstractEvent implements IProcessable {

	protected Main main;
	
	public AbstractEvent(Main _main) {
		main = _main;
	}

	
	public void remove() {
		this.main.gameData.currentEvent = null;//s.remove(this);
	}


}
