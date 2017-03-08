package com.scs.ftl2d.missions;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.Entity;

public abstract class AbstractMission extends Entity {

	public AbstractMission(Main _main) {
		super(_main);// = _main;
	}

	
	public abstract String getDescription();
	
	public abstract int getReward();
	
	public abstract void accepted();
	
	@Override
	public void preProcess() {
		
	}



}
