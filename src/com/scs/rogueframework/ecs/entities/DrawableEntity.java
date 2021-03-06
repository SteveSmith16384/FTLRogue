package com.scs.rogueframework.ecs.entities;

import java.awt.Point;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.rogueframework.Line;
import com.scs.rogueframework.ecs.components.ICarryable;


public abstract class DrawableEntity implements Comparable<Object> {

	// Z levels
	public static final int Z_UNIT = 10;
	public static final int Z_ITEM = 1;
	public static final int Z_FLOOR = 0;

	public int x, y, z;
	public Main main;

	public DrawableEntity(Main _main, int _x, int _y, int _z) {
		super();

		main = _main;
		x = _x;
		y = _y;
		z = _z;
	}

	public abstract void process();

	public abstract String getName();

	public abstract char getChar();


	public AbstractMapSquare getSq() {
		if (this instanceof ICarryable) {
			ICarryable ic = (ICarryable) this;
			if (ic.getCarrier() != null) {
				return main.gameData.map_data.map[ic.getCarrier().x][ic.getCarrier().y];
			}
		}
		return main.gameData.map_data.map[x][y];
	}


	public boolean canSee(DrawableEntity de) {
		return this.canSee(de.x, de.y);
	}


	public boolean canSee(int mx, int my) {
		int viewRange = Settings.VIEW_RANGE_NORMAL;
		if (main.gameData.totalPower <= 0 || main.gameData.shipLightsOn == false) {
			viewRange = Settings.VIEW_RANGE_DARK;
		}

		Line l = new Line(this.x, this.y, mx, my);
		if (l.size() > viewRange) {
			return false;
		}
		for (Point p : l) {
			if (p.x != x || p.y != y) {
				if (p.x != mx || p.y != my) {
					if (!main.gameData.map_data.map[p.x][p.y].isTransparent()) {
						return false;
					}
				}
			}
		}
		return true;
	}


	public boolean seenByPlayer() {
		return this.getSq().visible == AbstractMapSquare.VisType.Visible;
	}


	public float distanceTo(DrawableEntity de) {
		return (float) Math.sqrt((this.x-de.x)*(this.x-de.x) + this.y-de.y)*(this.y-de.y);
	}


	public void remove() {
		this.getSq().removeEntity(this); // Remove us
	}


	@Override
	public int compareTo(Object o) {
		if (o instanceof DrawableEntity) {
			DrawableEntity de = (DrawableEntity)o;
			return de.z - this.z;
		}
		return 0;
	}

}
