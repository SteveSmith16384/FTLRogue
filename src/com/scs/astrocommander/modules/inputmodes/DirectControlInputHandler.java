package com.scs.astrocommander.modules.inputmodes;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.astrocommander.modules.consoles.HelpConsole;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.ecs.components.IExamineable;
import com.scs.rogueframework.ecs.components.IRangedWeapon;
import com.scs.rogueframework.ecs.components.IUseable;
import com.scs.rogueframework.ecs.components.IWearable;
import com.scs.rogueframework.input.IInputHander;

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
			main.gameData.current_unit.attemptMove(0, -1);
			return true;
		} else if (ks.getKeyType() == KeyType.ArrowDown) {
			main.gameData.current_unit.attemptMove(0, 1);
			return true;
		} else if (ks.getKeyType() == KeyType.ArrowLeft) {
			main.gameData.current_unit.attemptMove(-1, 0);
			return true;
		} else if (ks.getKeyType() == KeyType.ArrowRight) {
			main.gameData.current_unit.attemptMove(1, 0);
			return true;
		} else {
			if (ks != null && ks.getCharacter() != null) {
				char c = ks.getCharacter();
				switch (c) {
				case '?':
					this.main.setModule(new HelpConsole(main, shipModule));
					return false;

				case ' ':
					if (main.gameData.currentLocation == null) {
						main.addMsg("The ship continues flying...");
						return true;
					} else {
						main.addMsg("You have an encounter!");
						return false;
					}

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
					main.gameData.current_unit.closeDoor();
					return true;

				case 'd': // Drop
					shipModule.dropMenu();
					return false;

				case 'e': // examine
				{
					ICarryable obj = main.gameData.current_unit.currentItem;
					if (obj != null) {
						if (obj instanceof IExamineable) {
							IExamineable ex = (IExamineable)obj;
							main.addMsg(1, obj.getName() + ": " + ex.getExamineText());
						}
					}

					// Adjacent squares
					for (AbstractMapSquare sq : main.gameData.map_data.getAdjacentSquares(main.gameData.current_unit.x, main.gameData.current_unit.y)) {
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
					main.gameData.current_unit.useConsole();
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
					main.gameData.current_unit.openDoor();
					return true;
				}

				case 'p': // Pickup
				{
					shipModule.pickupMenu();
					return false;
				}

				case 's':
				{
					ICarryable obj = main.gameData.current_unit.currentItem;
					if (obj != null) {
						if (obj instanceof IRangedWeapon) {
							this.shipModule.inputHandler = new SelectShotInputHandler(main, this.shipModule, (IRangedWeapon)obj);
						}
					}
					return false;
				}

				case 't':
				{
					ICarryable obj = main.gameData.current_unit.currentItem;
					if (obj != null) {
						this.shipModule.inputHandler = new SelectThrowInputHandler(main, this.shipModule, obj);
					} else {
						main.addMsg("Unit not using anything");
					}
					return false;
				}

				case 'u':
				{
					ICarryable obj = main.gameData.current_unit.currentItem;
					if (obj != null) {
						if (obj instanceof IUseable) {
							IUseable ex = (IUseable)obj;
							ex.use(main.gameData.current_unit);
							//this.main.gameData.currentUnit.wearing = ex;
							//main.addMsg("Unit is now wearing the " + obj.getName());
						} else {
							main.addMsg("Unit cannot use " + obj.getName());
						}
					} else {
						main.addMsg("Unit not using anything");
					}
				}
				return true;

				case 'w':
				{
					ICarryable obj = main.gameData.current_unit.currentItem;
					if (obj != null) {
						if (obj instanceof IWearable) {
							IWearable ex = (IWearable)obj;
							this.main.gameData.current_unit.wearing = ex;
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
