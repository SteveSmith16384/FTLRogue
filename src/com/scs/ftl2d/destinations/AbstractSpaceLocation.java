package com.scs.ftl2d.destinations;

import java.util.List;

import com.scs.ftl2d.IProcessable;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.asciieffects.ShipLaser;
import com.scs.ftl2d.map.AbstractMapSquare;

public abstract class AbstractSpaceLocation implements IProcessable {

	public String name;
	protected Main main;

	protected int timer = 0;
	protected float damage = 0;
	protected boolean attacked = false;


	public AbstractSpaceLocation(Main _main, String _name) {
		super();
		
		main = _main;
		name = _name;
	}
	
	
	public abstract String getHailResponse();
	
	public abstract String processCommand(String cmd);
	
	public abstract void shotByPlayer();
	
	public abstract List<String> getStats();


	protected void shootPlayer(float dam) {		
		// Shoot laser - todo - choose two random points
		int x = Main.RND.nextInt(main.gameData.getMapWidth());
		int y = 0;
		while (y < main.gameData.getMapHeight()) {
			AbstractMapSquare sq = main.gameData.map[x][y];
			if (sq.isSquareTraversable() == false) {
				main.addMsg(this.name + " fires and damages you " + dam);
				// todo - take into account shields
				sq.incDamage(dam);
				break;
			}
			y++;
		}
		this.main.asciiEffects.add(new ShipLaser(main, x, 0, 0, 1, x, y));
	}

}
