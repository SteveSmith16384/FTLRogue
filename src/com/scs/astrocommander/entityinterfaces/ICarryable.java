package com.scs.astrocommander.entityinterfaces;

import com.scs.astrocommander.entities.mobs.AbstractMob;

public interface ICarryable {

	String getName();
	
	void setCarriedBy(AbstractMob mob);
	
	AbstractMob getCarrier();
	
	void setNotCarried();
}
