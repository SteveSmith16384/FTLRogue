package com.scs.astrocommander.missions;

import com.scs.astrocommander.Main;
import com.scs.rogueframework.ecs.components.IProcessable;

public abstract class AbstractMission implements IProcessable {

	protected Main main;
	
	public AbstractMission(Main _main) {
		main = _main;
	}

	
	/*@Override
	public void preProcess() {
		
	}*/
	
	public abstract String getDescription();
	
	public abstract int getReward();
	
	public abstract void accepted();
	


}
