package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.items.Grenade;
import com.scs.ftl2d.modules.PlayersShipModule;

public class PrimeGrenadeInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {
	
	private Grenade grenade;

	public PrimeGrenadeInputHandler(Main _main, PlayersShipModule psm, Grenade _grenade) {
		super(_main, psm);
		
		grenade = _grenade;
	}

	@Override
	protected void numberSelected(int i) {
		grenade.prime(i);
		
		this.playersShipModule.restoreDirectControlIH();
	}

}
