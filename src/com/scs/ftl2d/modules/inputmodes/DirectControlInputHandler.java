package com.scs.ftl2d.modules.inputmodes;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entityinterfaces.ICarryable;
import com.scs.ftl2d.entityinterfaces.IExamineable;
import com.scs.ftl2d.entityinterfaces.IRangedWeapon;
import com.scs.ftl2d.entityinterfaces.IWearable;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.modules.PlayersShipModule;
import com.scs.ftl2d.modules.consoles.HelpConsole;

public class DirectControlInputHandler implements IInputHander {

	private Main main;
	private PlayersShipModule shipModule;

	public DirectControlInputHandler(Main _main, PlayersShipModule psm) {
		super();

		main = _main;
		shipModule = psm;
	}


	@Override
	public boolean processInput(KeyStroke ks) {
		if (ks.getKeyType() == KeyType.ArrowUp) {
			main.gameData.currentUnit.attemptMove(0, -1);
			return true;
		} else if (ks.getKeyType() == KeyType.ArrowDown) {
			main.gameData.currentUnit.attemptMove(0, 1);
			return true;
		} else if (ks.getKeyType() == KeyType.ArrowLeft) {
			main.gameData.currentUnit.attemptMove(-1, 0);
			return true;
		} else if (ks.getKeyType() == KeyType.ArrowRight) {
			main.gameData.currentUnit.attemptMove(1, 0);
			return true;
		} else {
			if (ks != null && ks.getCharacter() != null) {
				char c = ks.getCharacter();
				switch (c) {
				case '?':
					this.main.setModule(new HelpConsole(main, shipModule));
					return false;

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
					shipModule.selectUnit(i-1);
					return false;

				case 'c':
					main.gameData.currentUnit.closeDoor();
					return true;

				case 'd': // Drop
					shipModule.dropMenu();
					return false;

				case 'e': // examine
				{
					ICarryable obj = main.gameData.currentUnit.currentItem;
					if (obj != null) {
						if (obj instanceof IExamineable) {
							IExamineable ex = (IExamineable)obj;
							main.addMsg(1, obj.getName() + ": " + ex.getExamineText());
						}
					}
					
					// Adjacent squares
					for (AbstractMapSquare sq : main.gameData.getAdjacentSquares(main.gameData.currentUnit.x, main.gameData.currentUnit.y)) {
						String s = sq.getExamineText();
						if (s.length() > 0) {
							main.addMsg(1, obj.getName() + ": " + sq.getExamineText());
						}
					}
				}
				return false;

				case 'f': // Fire ships weapons
					shipModule.fireShipsWeapons();
					return true;

				case 'h': // Change current item
					shipModule.changeCurrentItem();
					return false;

				case 'i': // Inventory
					shipModule.showInventory();
					return false;

				case 'l':
					main.gameData.currentUnit.useConsole();
					return true;

				case 'm':
					this.shipModule.inputHandler = new SelectDestinationInputHandler(main, this.shipModule);
					return false;

				case 'n':
				{
					main.addMsg("You do nothing");
					return true;
				}

				case 'o':
				{
					main.gameData.currentUnit.openDoor();
					return true;
				}

				case 'p': // Pickup
				{
					shipModule.pickupMenu();
					return false;
				}

				case 's':
				{
					ICarryable obj = main.gameData.currentUnit.currentItem;
					if (obj != null) {
						if (obj instanceof IRangedWeapon) {
							this.shipModule.inputHandler = new SelectShotInputHandler(main, this.shipModule, (IRangedWeapon)obj);
						}
					}
					return false;
				}

				case 't':
				{
					ICarryable obj = main.gameData.currentUnit.currentItem;
					if (obj != null) {
						this.shipModule.inputHandler = new SelectThrowInputHandler(main, this.shipModule, obj);
					} else {
						main.addMsg("Unit not using anything");
					}
					return false;
				}

				case 'w':
				{
					ICarryable obj = main.gameData.currentUnit.currentItem;
					if (obj != null) {
						if (obj instanceof IWearable) {
							IWearable ex = (IWearable)obj;
							this.main.gameData.currentUnit.wearing = ex;
							main.addMsg("Unit is now wearing the " + obj.getName());
						} else {
							main.addMsg("Unit cannot wear " + obj.getName());
						}
					} else {
						main.addMsg("Unit not using anything");
					}
				}
				return true;

				default:
					main.addMsg("Unknown key " + c);
				}
			}
			return false;
		}
	}
}
