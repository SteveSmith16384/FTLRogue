package com.scs.astrocommander.entities.items;

import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.ecs.entities.AbstractItem;

public class Corpse extends AbstractItem {

	private String name;
	
	public Corpse(AbstractRoguelike main, String _name) {
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
