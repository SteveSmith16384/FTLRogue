package com.scs.ftl2d.destinations;

import java.util.List;

import com.scs.ftl2d.IProcessable;
import com.scs.ftl2d.Main;

public abstract class AbstractSpaceLocation implements IProcessable {

	public String name;
	protected Main main;

	protected int timer = 0;
	protected float damage = 0;
	protected boolean attacked = false;


	public AbstractSpaceLocation(Main _main, String _name) {
		super();
		
		main = _main;
		name = _name;
	}
	
	
	public abstract void shot();
	
	public abstract List<String> getStats();

}
