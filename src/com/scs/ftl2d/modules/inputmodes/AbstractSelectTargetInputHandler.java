package com.scs.ftl2d.modules.inputmodes;

import java.awt.Point;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.PlayersShipModule;

public abstract class AbstractSelectTargetInputHandler implements IInputHander {

	protected Main main;
	protected PlayersShipModule shipModule;

	public AbstractSelectTargetInputHandler(Main _main, PlayersShipModule psm) {
		super();

		main = _main;
		shipModule = psm;

		psm.selectedpoint = new Point(main.gameData.currentUnit.x, main.gameData.currentUnit.y);
}


	@Override
	public boolean processInput(KeyStroke ks) {
		if (ks.getKeyType() == KeyType.ArrowUp) {
			shipModule.selectedpoint.y--;
			routeChanged();
		} else if (ks.getKeyType() == KeyType.ArrowDown) {
			shipModule.selectedpoint.y++;
			routeChanged();
		} else if (ks.getKeyType() == KeyType.ArrowLeft) {
			shipModule.selectedpoint.x--;
			routeChanged();
		} else if (ks.getKeyType() == KeyType.ArrowRight) {
			shipModule.selectedpoint.x++;
			routeChanged();
		} else if (ks.getKeyType() == KeyType.Enter) {
			routeSelected();
		} else if (ks.getKeyType() == KeyType.Escape) {
			shipModule.route = null;
		}
		return false;
	}


	protected abstract void routeChanged();

	protected abstract void routeSelected();

}
