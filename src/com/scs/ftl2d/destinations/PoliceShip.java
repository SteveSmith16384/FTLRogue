package com.scs.ftl2d.destinations;

import java.util.List;

import com.scs.ftl2d.Main;

public class PoliceShip extends AbstractAnotherShip {

	private int stage = 0;
	
	public PoliceShip(Main main) {
		super(main, "Police Ship");

	}

	
	@Override
	public void process() {
		stage++;
	}
	

	@Override
	public void shotByPlayer() {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public List<String> getStats() {
		return null;
	}


	@Override
	public String getHailResponse() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void processCommand(String cmd) {
	}

}
