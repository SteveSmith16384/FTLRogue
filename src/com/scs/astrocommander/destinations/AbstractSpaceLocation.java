package com.scs.astrocommander.destinations;

import java.util.List;

import com.scs.astrocommander.IProcessable;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.asciieffects.ShipLaser;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.modules.consoles.AbstractConsoleModule;

public abstract class AbstractSpaceLocation implements IProcessable {

	protected String name;
	protected Main main;

	protected float damagePcent = 0;
	//protected Point position;


	public AbstractSpaceLocation(Main _main, String _name) {
		super();

		main = _main;
		name = _name;

		/*position = new Point();
		int i = Main.RND.nextInt(4);
		switch (i) {
		case 0: // bottom-right
			position.x = main.gameData.getMapWidth();
			position.y = main.gameData.getMapHeight();
			break;
		case 1: // bottom-left
			position.x = 0;
			position.y = main.gameData.getMapHeight();
			break;
		case 2: // top-right
			position.x = main.gameData.getMapWidth();
			position.y = 0;
			break;
		case 3: // top-left
			position.x = 0;
			position.y = 0;
			break;
		default:
			throw new RuntimeException("Unknown dir: " + position);
		}*/
	}


	public abstract void getHailResponse(AbstractConsoleModule console);

	public abstract void processCommand(String cmd, AbstractConsoleModule console);

	public abstract void shotByPlayer();

	public abstract List<String> getStats();


	protected void shootPlayer(float dam) {
		int x = Main.RND.nextInt(main.gameData.getMapWidth());
		int y = 0;
		while (y < main.gameData.getMapHeight()) {
			AbstractMapSquare sq = main.gameData.map[x][y];
			if (sq.isSquareTraversable() == false) {
				// Take into account shields
				dam = (dam * main.gameData.shieldPowerPcent) / 100;
				main.addMsg(this.name + " fires and damages you " + dam);
				sq.incDamage(dam);
				float powerCost = main.gameData.shieldPowerPcent;
				main.gameData.totalPower -= powerCost;
				main.addMsg("Shields use " + (int)powerCost);
				break;
			}
			y++;
		}
		this.main.addAsciiEffect(new ShipLaser(main, x, 0, 0, 1, x, y));
	}
	
	
	protected void destroyed() {
		main.gameData.currentLocation = null;
	}
	
	
	public String getName() {
		return name;
	}

}
