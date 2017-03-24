package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Line;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.modules.PlayersShipModule;

public class SelectThrowInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	private ICarryable item;
	
	public SelectThrowInputHandler(Main _main, PlayersShipModule psm, ICarryable _item) {
		super(_main, psm);
		
		item = _item;

		main.addMsg("Select target with arrows and enter.");
	}

	
	@Override
	protected void routeChanged() {
		shipModule.route = new Line(this.main.gameData.currentUnit.x, this.main.gameData.currentUnit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y);
	}
	

	@Override
	protected void routeSelected() {
		this.main.gameData.currentUnit.throwItem(shipModule.route, item);
		shipModule.route = null;
		shipModule.restoreDirectControlIH();
		
	}


}
