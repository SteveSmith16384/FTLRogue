package com.scs.astrocommander.modules.inputmodes;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entityinterfaces.ICarryable;
import com.scs.astrocommander.modules.PlayersShipModule;

public class ChangeItemInputHandler extends AbstractSelectNumberInputHandler implements IInputHander {

	public ChangeItemInputHandler(Main _main, PlayersShipModule psm) {
		super(_main, psm);
	}

	
	@Override
	protected void numberSelected(int i) {
		ICarryable de = main.gameData.currentUnit.equipment.get(i-1);
		main.gameData.currentUnit.currentItem = de;
		main.addMsg("You use the " + ((DrawableEntity)de).getName());
	}

}
