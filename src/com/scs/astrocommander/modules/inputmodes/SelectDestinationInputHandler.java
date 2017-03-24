package com.scs.astrocommander.modules.inputmodes;

import ssmith.astar.AStar;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;

public class SelectDestinationInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	private boolean validRoute = false;

	public SelectDestinationInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);

		main.addMsg("Select destination with arrows and enter to .");
	}


	@Override
	protected void routeChanged() {
		validRoute = false;
		if (main.gameData.map[shipModule.selectedpoint.x][shipModule.selectedpoint.y].isSquareTraversable()) {
			AStar astar = new AStar(this.main.gameData);
			astar.findPath(this.main.gameData.currentUnit.x, this.main.gameData.currentUnit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y, false);
			// todo - check it only goes through visible mapsquares
			if (astar.wasSuccessful()) {
				shipModule.route = astar.getRoute();
				validRoute = true;
			}
		}
	}


	@Override
	protected void routeSelected() {
		if (validRoute) {
			this.main.gameData.currentUnit.astarRoute = shipModule.route;
			shipModule.restoreDirectControlIH();
			shipModule.route = null;
			main.addMsg("Destination selected");
		} else {
			main.addMsg("No valid route selected");
		}
	}


}
