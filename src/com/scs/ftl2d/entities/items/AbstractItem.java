package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.mobs.AbstractMob;
import com.scs.ftl2d.entityinterfaces.ICarryable;

public abstract class AbstractItem extends DrawableEntity implements ICarryable {

	protected AbstractMob carrier;
	
	public AbstractItem(Main main, int _x, int _y, int _z) {
		super(main, _x, _y, _z);
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
