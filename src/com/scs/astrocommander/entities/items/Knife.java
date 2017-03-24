package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entities.mobs.AbstractMob;
import com.scs.astrocommander.entityinterfaces.IMeleeWeapon;

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
