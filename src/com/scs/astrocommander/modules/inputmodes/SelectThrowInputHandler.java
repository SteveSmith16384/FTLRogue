package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.Line;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.input.AbstractSelectTargetInputHandler;
import com.scs.rogueframework.input.IInputHander;

public class SelectThrowInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	private ICarryable item;
	
	public SelectThrowInputHandler(Main _main, PlayersShipModule psm, ICarryable _item) {
		super(_main, psm);
		
		item = _item;

		main.addMsg("Select target with arrows and enter.");
	}

	
	@Override
	protected void routeChanged() {
		shipModule.route = new Line(this.main.gameData.current_unit.x, this.main.gameData.current_unit.y, shipModule.selected_point.x, shipModule.selected_point.y);
	}
	

	@Override
	protected void routeSelected() {
		this.main.gameData.current_unit.throwItem(shipModule.route, item);
		shipModule.route = null;
		shipModule.restoreDirectControlIH();
		
	}


}
