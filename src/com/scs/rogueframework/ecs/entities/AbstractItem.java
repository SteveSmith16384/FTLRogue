package com.scs.rogueframework.ecs.entities;

import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.ecs.components.ICarryable;

public abstract class AbstractItem extends DrawableEntity implements ICarryable {

	protected FrameworkMob carrier;
	
	public AbstractItem(AbstractRoguelike main, int _x, int _y) {
		super(main, _x, _y, DrawableEntity.Z_ITEM);
	}
	
	
	@Override
	public void setNotCarried() {
		carrier = null;
	}
	
	
	@Override
	public FrameworkMob getCarrier() {
		return carrier;
	}
	
	
	@Override
	public void process() {
		
	}


	@Override
	public void setCarriedBy(FrameworkMob mob) {
		carrier = mob;
		
	}

}
