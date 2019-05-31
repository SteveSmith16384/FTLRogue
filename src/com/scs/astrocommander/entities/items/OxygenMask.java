package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.ecs.components.IExamineable;
import com.scs.rogueframework.ecs.components.IWearable;
import com.scs.rogueframework.ecs.entities.AbstractItem;

public class OxygenMask extends AbstractItem implements ICarryable, IWearable, IExamineable {

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


	@Override
	public String getExamineText() {
		return "It is a standard oxygen mask, allowing for breathing where there is no oxygen.";
	}

}
