package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Line;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.modules.PlayersShipModule;

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
