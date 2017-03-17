package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.modules.PlayersShipModule;

public class PickupItemInputHandler extends AbstractSelectItemInputHandler implements IInputHander {

	public PickupItemInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	@Override
	protected void numberSelected(int i) {
		DrawableEntity de = main.gameData.currentUnit.getSq().getEntity(i-1);
		if (de instanceof ICarryable) {
			main.gameData.currentUnit.pickup((ICarryable)de);
			main.addMsg("You pick up the " + de.getName());
		} else {
			main.addMsg("You can't pick up the " + de.getName());
		}
		
	}

}
