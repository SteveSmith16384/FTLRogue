package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.DrawableEntity;

public class Corpse extends AbstractItem {// AbstractItem {

	private String name;
	
	public Corpse(Main main, String _name) {
		super(main, -1, -1);
		
		name = "Corpse of " + _name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public char getChar() {
		return 'c';
	}

	@Override
	public void process() {
		
	}

}
