package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;

public class Knife extends AbstractItem {

	public Knife(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);
	}
	

	@Override
	public char getChar() {
		return 'k';
	}

	
	@Override
	public void preProcess() {
		
	}


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
