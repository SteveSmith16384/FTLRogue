package com.scs.ftl2d.events;

import com.scs.ftl2d.Main;

public class EnemyShipEvent extends AbstractEvent {

	public EnemyShipEvent(Main main) {
		super(main);

		main.addMsg("An enemy ship has appeared!");
	}


	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

}
