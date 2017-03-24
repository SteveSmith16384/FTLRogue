package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.modules.PlayersShipModule;

public class PickupItemInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

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
