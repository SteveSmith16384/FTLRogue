package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.ecs.entities.DrawableEntity;
import com.scs.rogueframework.input.AbstractSelectNumberInputHandler;
import com.scs.rogueframework.input.IInputHander;

public class PickupItemInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

	public PickupItemInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	@Override
	protected void numberSelected(int i) {
		DrawableEntity de = main.gameData.current_unit.getSq().getEntity(i-1);
		if (de instanceof ICarryable) {
			main.gameData.current_unit.pickup((ICarryable)de);
			main.addMsg("You pick up the " + de.getName());
		} else {
			main.addMsg("You can't pick up the " + de.getName());
		}
		
	}

}
