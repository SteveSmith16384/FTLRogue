package com.scs.ftl2d.modules.inputmodes;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.PlayersShipModule;

public abstract class AbstractSelectItemInputHandler implements IInputHander {

	protected Main main;
	protected PlayersShipModule shipModule;

	public AbstractSelectItemInputHandler(Main _main, PlayersShipModule psm) {
		super();
		
		main = _main;
		shipModule = psm;
	}


	@Override
	public boolean processInput(KeyStroke ks) {
		char c = ks.getCharacter();
		switch (c) {
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			int i = Integer.parseInt(c+"");
			this.numberSelected(i);
			break;
		case 'x':
			break;
		}
		this.shipModule.restoreDirectControlIH();
		return true;
	}
	
	
	protected abstract void numberSelected(int i);

}