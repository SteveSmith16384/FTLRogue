package com.scs.ftl2d.entities;

import com.scs.ftl2d.Main;

public abstract class Entity {

	public Main main;
	
	public Entity(Main _main) {
		main = _main;
	}
	
	
	public abstract void process(int pass);


}
