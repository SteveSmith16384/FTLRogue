package com.scs.rogueframework.ecs.entities;

import com.scs.astrocommander.Main;
import com.scs.rogueframework.ecs.components.ICarryable;

public abstract class AbstractItem extends DrawableEntity implements ICarryable {

	protected AbstractMob carrier;
	
	public AbstractItem(Main main, int _x, int _y) {
		super(main, _x, _y, DrawableEntity.Z_ITEM);
	}
	
	
	@Override
	public void setNotCarried() {
		carrier = null;
	}
	
	
	@Override
	public AbstractMob getCarrier() {
		return carrier;
	}
	
	
	@Override
	public void process() {
		
	}


	@Override
	public void setCarriedBy(AbstractMob mob) {
		carrier = mob;
		
	}

}
