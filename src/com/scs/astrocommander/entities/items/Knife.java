package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.rogueframework.ecs.components.IMeleeWeapon;
import com.scs.rogueframework.ecs.entities.AbstractItem;

public class Knife extends AbstractItem implements IMeleeWeapon {

	public Knife(Main main) {
		super(main, -1, -1);
	}
	

	@Override
	public char getChar() {
		return 'k';
	}

	
	/*@Override
	public void preProcess() {
		
	}*/


	@Override
	public void process() {
		// Do nothing
		
	}


	@Override
	public int getMeleeValue() {
		return 10;
	}
	

	@Override
	public String getName() {
		return "Knife";
	}


}
