package com.scs.ftl2d.events;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.Entity;

public abstract class AbstractEvent extends Entity {

	public AbstractEvent(Main main) {
		super(main);
	}

	
	public abstract void init();


}
