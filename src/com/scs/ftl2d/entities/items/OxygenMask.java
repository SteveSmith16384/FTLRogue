package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.entityinterfaces.IWearable;

public class OxygenMask extends AbstractItem implements ICarryable, IWearable {

	public OxygenMask(Main main) {
		super(main, -1, -1);
	}
	

	@Override
	public void process() {
		
	}
	

	@Override
	public String getName() {
		return "Oxygen Mask";
	}

	
	@Override
	public char getChar() {
		return 'j';
	}

	
	@Override
	public boolean giveOxygen() {
		return true;
	}

}
