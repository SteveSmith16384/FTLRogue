package com.scs.ftl2d.modules.inputmodes;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.modules.PlayersShipModule;

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
