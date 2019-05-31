package com.scs.astrocommander.modules.inputmodes;

import ssmith.astar.AStar;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.input.AbstractSelectTargetInputHandler;
import com.scs.rogueframework.input.IInputHander;

public class SelectDestinationInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	private boolean validRoute = false;

	public SelectDestinationInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);

		main.addMsg("Select destination with arrows and enter to .");
	}


	@Override
	protected void routeChanged() {
		validRoute = false;
		if (main.gameData.map[shipModule.selectedpoint.x][shipModule.selectedpoint.y].isTraversable()) {
			AStar astar = new AStar(this.main.gameData);
			astar.findPath(this.main.gameData.current_unit.x, this.main.gameData.current_unit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y, false);
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
			this.main.gameData.current_unit.astarRoute = shipModule.route;
			shipModule.restoreDirectControlIH();
			shipModule.route = null;
			main.addMsg("Destination selected");
		} else {
			main.addMsg("No valid route selected");
		}
	}


}
