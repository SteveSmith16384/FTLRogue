package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class AlienEgg extends DrawableEntity {

	public AlienEgg(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	
	}

	
	@Override
	public char getChar() {
		return 'e';
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
