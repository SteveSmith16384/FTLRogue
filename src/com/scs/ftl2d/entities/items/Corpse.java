package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

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
