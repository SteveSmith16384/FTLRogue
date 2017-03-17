package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Line;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.PlayersShipModule;

public class SelectShotInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	public SelectShotInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);

		main.addMsg("Select target with arrows and enter.");
	}

	
	@Override
	protected void routeChanged() {
		shipModule.route = new Line(this.main.gameData.currentUnit.x, this.main.gameData.currentUnit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y);
		// todo - check LOS
	}
	

	@Override
	protected void routeSelected() {
		this.main.gameData.currentUnit.astarRoute = shipModule.route;
		shipModule.restoreDirectControlIH();
		shipModule.route = null;
		
	}


}
