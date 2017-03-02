package com.scs.ftl2d.destinations;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.Entity;

public class AbstractSpaceLocation extends Entity {

	public int x, y;
	public String name;
	
	public AbstractSpaceLocation(Main main, String _name) {
		super(main);
		
		name = _name;
	}

	
	@Override
	public void process(int pass) {
		
	}

}
