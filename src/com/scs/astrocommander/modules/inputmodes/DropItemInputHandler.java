package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.modules.PlayersShipModule;

public class DropItemInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

	public DropItemInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	
	@Override
	protected void numberSelected(int i) {
		ICarryable de = main.gameData.currentUnit.equipment.get(i-1);
		main.gameData.currentUnit.dropOrThrow(de, main.gameData.currentUnit.getSq());
		main.addMsg("You drop the " + de.getName());
		
	}

}
