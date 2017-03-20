package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Line;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entityinterfaces.IRangedWeapon;
import com.scs.ftl2d.modules.PlayersShipModule;

public class SelectShotInputHandler extends AbstractSelectTargetInputHandler implements IInputHander {

	private IRangedWeapon gun;
	
	public SelectShotInputHandler(Main _main, PlayersShipModule psm, IRangedWeapon _gun) {
		super(_main, psm);
		
		gun = _gun;

		main.addMsg("Select target with arrows and enter.");
	}

	
	@Override
	protected void routeChanged() {
		shipModule.route = new Line(this.main.gameData.currentUnit.x, this.main.gameData.currentUnit.y, shipModule.selectedpoint.x, shipModule.selectedpoint.y);
	}
	

	@Override
	protected void routeSelected() {
		this.main.gameData.currentUnit.shoot(shipModule.route, gun);
		shipModule.route = null;
		shipModule.restoreDirectControlIH();
		
	}


}
