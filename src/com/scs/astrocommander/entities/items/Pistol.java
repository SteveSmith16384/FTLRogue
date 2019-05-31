package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.entityinterfaces.IRangedWeapon;

public class Pistol extends AbstractItem implements ICarryable, IRangedWeapon {// AbstractRangedWeapon {

	public Pistol(Main main) {
		super(main, -1, -1);
	}
	

	@Override
	public char getChar() {
		return 'p';
	}

	
	@Override
	public int getShotValue() {
		return 10;
	}
	

	@Override
	public String getName() {
		return "Pistol";
	}


	@Override
	public void process() {
		
	}


	@Override
	public int getRange() {
		return 20;
	}

}
