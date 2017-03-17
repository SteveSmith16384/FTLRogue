package com.scs.ftl2d.modules.inputmodes;

import java.awt.Point;

import ssmith.astar.AStar;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.PlayersShipModule;

public class SelectDestinationInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	public SelectDestinationInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);

		main.addMsg("Select destination with arrows and enter.");
	}

	
	@Override
	protected void routeChanged() {
		AStar astar = new AStar(this.main.gameData);
		astar.findPath(this.main.gameData.currentUnit.x, this.main.gameData.currentUnit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y, false);
		// todo - check it only goes through visible mapsquares
		if (astar.wasSuccessful()) {
			shipModule.route = astar.getRoute();
		}
	}
	

	@Override
	protected void routeSelected() {
		this.main.gameData.currentUnit.astarRoute = shipModule.route;
		shipModule.restoreDirectControlIH();
		shipModule.route = null;
		
	}


}
