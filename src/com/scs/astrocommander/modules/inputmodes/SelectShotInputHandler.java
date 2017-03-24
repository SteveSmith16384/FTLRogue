package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Line;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.entityinterfaces.IRangedWeapon;
import com.scs.astrocommander.modules.PlayersShipModule;

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
