package com.scs.ftl2d.missions;

import com.scs.ftl2d.IProcessable;
import com.scs.ftl2d.Main;

public abstract class AbstractMission implements IProcessable {

	protected Main main;
	
	public AbstractMission(Main _main) {
		main = _main;
	}

	
	public abstract String getDescription();
	
	public abstract int getReward();
	
	public abstract void accepted();
	


}
