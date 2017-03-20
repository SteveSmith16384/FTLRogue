package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.modules.PlayersShipModule;

public class PrimeGrenadeInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

	public PrimeGrenadeInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	@Override
	protected void numberSelected(int i) {
		// todo
	}

}
