package com.scs.ftl2d.destinations;

import java.util.List;

import com.scs.ftl2d.Main;

public class EmptyHulk extends AbstractAnotherShip {

	public EmptyHulk(Main main) {
		super(main, "Space hulk");

	}

	@Override
	public void process() {
		
	}

	@Override
	public void shotByPlayer() {
		
	}

	@Override
	public List<String> getStats() {
		return null;
	}

}
