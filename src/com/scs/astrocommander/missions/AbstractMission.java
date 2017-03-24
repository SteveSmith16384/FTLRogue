package com.scs.astrocommander.missions;

import com.scs.astrocommander.IProcessable;
import com.scs.astrocommander.Main;

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
