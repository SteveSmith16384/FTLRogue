package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.entityinterfaces.IRangedWeapon;

public class Gun extends AbstractItem implements ICarryable, IRangedWeapon {// AbstractRangedWeapon {

	public Gun(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	}
	

	@Override
	public char getChar() {
		return 'g';
	}

	
	@Override
	public int getShotValue() {
		return 10;
	}
	

	@Override
	public String getName() {
		return "Gun";
	}


	@Override
	public void process() {
		
	}


	@Override
	public int getRange() {
		return 20;
	}

}
