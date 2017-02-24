package com.scs.ftl2d.entities;

import com.scs.ftl2d.Main;

public class MediKit extends DrawableEntity {

	public MediKit(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	}
	

	@Override
	public char getChar() {
		return 'm';
	}

	
	@Override
	public void process() {
		// Do nothing
		
	}

}
