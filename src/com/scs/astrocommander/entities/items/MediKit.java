package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.mobs.AbstractMob;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.entityinterfaces.IUseable;

public class MediKit extends AbstractItem implements ICarryable, IUseable {

	public MediKit(Main main) {
		super(main, -1, -1);
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
	public void use(AbstractMob user) {
		// todo - remove from user
		// todo - msg if players unit
		this.carrier.incHealth(100, "medikit");
		if (user.currentItem == this) {
			user.currentItem = null;
		}
		user.equipment.remove(this);
		
	}

}
