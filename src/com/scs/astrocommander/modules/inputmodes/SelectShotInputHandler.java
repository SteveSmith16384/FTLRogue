package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.Line;
import com.scs.rogueframework.ecs.components.IRangedWeapon;
import com.scs.rogueframework.input.AbstractSelectTargetInputHandler;
import com.scs.rogueframework.input.IInputHander;

public class SelectShotInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	private IRangedWeapon gun;
	
	public SelectShotInputHandler(Main _main, PlayersShipModule psm, IRangedWeapon _gun) {
		super(_main, psm);
		
		gun = _gun;

		main.addMsg("Select target with arrows and enter.");
	}

	
	@Override
	protected void routeChanged() {
		shipModule.route = new Line(this.main.gameData.current_unit.x, this.main.gameData.current_unit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y);
	}
	

	@Override
	protected void routeSelected() {
		this.main.gameData.current_unit.shoot(shipModule.route, gun);
		shipModule.route = null;
		shipModule.restoreDirectControlIH();
		
	}


}
