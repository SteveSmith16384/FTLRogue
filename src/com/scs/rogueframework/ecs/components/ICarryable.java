package com.scs.rogueframework.ecs.components;

import com.scs.rogueframework.ecs.entities.AbstractMob;

public interface ICarryable {

	String getName();
	
	void setCarriedBy(AbstractMob mob);
	
	AbstractMob getCarrier();
	
	void setNotCarried();
}
