package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.ecs.entities.DrawableEntity;
import com.scs.rogueframework.input.AbstractSelectNumberInputHandler;
import com.scs.rogueframework.input.IInputHander;

public class ChangeItemInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

	public ChangeItemInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	
	@Override
	protected void numberSelected(int i) {
		ICarryable de = main.gameData.current_unit.equipment.get(i-1);
		main.gameData.current_unit.currentItem = de;
		main.addMsg("You use the " + ((DrawableEntity)de).getName());
	}

}
