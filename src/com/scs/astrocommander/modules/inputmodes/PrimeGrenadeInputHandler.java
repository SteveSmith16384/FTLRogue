package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.items.Grenade;
import com.scs.astrocommander.modules.PlayersShipModule;

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
