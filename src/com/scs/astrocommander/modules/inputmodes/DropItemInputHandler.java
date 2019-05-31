package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.input.AbstractSelectNumberInputHandler;
import com.scs.rogueframework.input.IInputHander;

public class DropItemInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

	public DropItemInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	
	@Override
	protected void numberSelected(int i) {
		ICarryable de = main.gameData.current_unit.equipment.get(i-1);
		main.gameData.current_unit.dropOrThrow(de, main.gameData.current_unit.getSq());
		main.addMsg("You drop the " + de.getName());
		
	}

}
