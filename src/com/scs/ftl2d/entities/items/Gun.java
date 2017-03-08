package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class Gun extends AbstractRangedWeapon {

	public Gun(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM, 20);
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

}
