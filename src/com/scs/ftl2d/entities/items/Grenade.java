package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;

public class Grenade extends AbstractItem implements ICarryable {

	public Grenade(Main main) {
		super(main, -1, -1);
	}

	
	@Override
	public String getName() {
		return "Grenade";
	}
	

	@Override
	public char getChar() {
		return 'g';
	}

}
