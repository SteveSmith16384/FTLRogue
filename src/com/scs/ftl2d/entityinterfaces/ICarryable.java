package com.scs.ftl2d.entityinterfaces;

import com.scs.ftl2d.entities.mobs.AbstractMob;

public interface ICarryable {

	String getName();
	
	void setCarriedBy(AbstractMob mob);
	
	AbstractMob getCarrier();
	
	void setNotCarried();
}
