package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

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
