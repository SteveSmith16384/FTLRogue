package com.scs.ftl2d.entities;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareControlPanel;
import com.scs.ftl2d.map.MapSquareReplicator;


public class Unit extends AbstractMob {

	public float food = 100f;

	public Unit(Main main, char c, int x, int y, String _name, int _side) {
		super(main, x, y, DrawableEntity.Z_UNIT, c, _name, _side);

	}


	@Override
	public char getChar() {
		return theChar;
	}


	@Override
	public void process() {
		main.gameData.incOxygenLevel(-0.1f);
		if (main.gameData.oxygenLevel <= 0) {
			main.addMsg(this.getName() + " is suffocating!");
		}

		// Are we next to a replicator?
		if (this.food < 100) {
			MapSquareReplicator sqr = (MapSquareReplicator)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_REPLICATOR);
			if (sqr != null) {
				this.incFood(10);
				this.main.addMsg(this.getName() + " eats some food");
			}
		}

		this.incFood(-0.25f);
		if (this.food <= 0) {
			main.addMsg(this.getName() + " is starving!");
			this.incHealth(-5, "starvation");
		} else if (this.food < 20) {
			main.addMsg(this.getName() + " is hungry");
		}

		// Are we on a medibay?
		AbstractMapSquare sq = this.getSq();
		if (sq.type == AbstractMapSquare.MAP_MEDIBAY) {
			this.incHealth(1, "");
		}

	}


	private void incFood(float f) {
		this.food += f;
		if (this.food > 100f) {
			this.food = 100f;
		} else if (this.food <= 0) {
			this.food = 0;
		}
	}


	public void useConsole() {
		MapSquareControlPanel sq = (MapSquareControlPanel)main.gameData.findAdjacentMapSquare(x, y, AbstractMapSquare.MAP_CONTROL_PANEL);
		if (sq != null) {
			// todo
		}		
	}


}
