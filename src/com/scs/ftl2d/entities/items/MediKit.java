package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.entityinterfaces.IUseable;

public class MediKit extends AbstractItem implements ICarryable, IUseable {

	public MediKit(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	}
	

	@Override
	public char getChar() {
		return 'm';
	}

	
	@Override
	public String getName() {
		return "MediKit";
	}


	@Override
	public void use() {
		this.carrier.incHealth(100, "medikit");
		
	}

}
