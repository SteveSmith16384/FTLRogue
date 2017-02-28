package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class Gun extends DrawableEntity {

	public Gun(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	}
	

	@Override
	public char getChar() {
		return 'g';
	}

	
	@Override
	public void process() {
		// Do nothing
		
	}

}
