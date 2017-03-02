package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class Gun extends AbstractItem {

	public Gun(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	}
	

	@Override
	public char getChar() {
		return 'g';
	}

	
	@Override
	public void process(int pass) {
		// Do nothing
		
	}


	@Override
	public int getShotValue() {
		return 10;
	}
	
	@Override
	public boolean canShoot() {
		return true;
	}


	@Override
	public String getName() {
		return "Gun";
	}

}
