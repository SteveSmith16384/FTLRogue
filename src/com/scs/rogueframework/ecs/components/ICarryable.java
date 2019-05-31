package com.scs.rogueframework.ecs.components;

import com.scs.astrocommander.entities.mobs.AbstractMob;
import com.scs.rogueframework.ecs.entities.FrameworkMob;

public interface ICarryable {

	String getName();
	
	void setCarriedBy(FrameworkMob mob);
	
	FrameworkMob getCarrier();
	
	void setNotCarried();
}
