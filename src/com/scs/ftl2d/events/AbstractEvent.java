package com.scs.ftl2d.events;

import com.scs.ftl2d.IProcessable;
import com.scs.ftl2d.Main;

public abstract class AbstractEvent implements IProcessable {

	protected Main main;
	
	public AbstractEvent(Main _main) {
		main = _main;
	}

	
	/*@Override
	public void preProcess() {
		
	}*/
	


}
