package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public abstract class AbstractItem extends DrawableEntity {

	public AbstractItem(Main main, int _x, int _y, int _z) {
		super(main, _x, _y, _z);
	}
	
	
	public int getMeleeValue() {
		return 0;
	}
	
	
	public int getShotValue() {
		return 0;
	}
	
	
	public boolean canShoot() {
		return false;
	}

	
	@Override
	public boolean canBePickedUp() {
		return true;
	}



}
