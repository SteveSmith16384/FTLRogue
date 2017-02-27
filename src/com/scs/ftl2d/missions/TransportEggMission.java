package com.scs.ftl2d.missions;

import com.scs.ftl2d.Main;

public class TransportEggMission extends AbstractMission {

	public TransportEggMission(Main main) {
		super(main);
	}


	@Override
	public String getDescription() {
		return "Transport an egg to Planet X for " + this.getReward() + " creds";
	}


	@Override
	public int getReward() {
		return 1000;
	}


	@Override
	public void accepted() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}
	

}
