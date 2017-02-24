package com.scs.ftl2d;

import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.Unit;
import com.scs.ftl2d.events.AbstractEvent;
import com.scs.ftl2d.map.AbstractMapSquare;

public class GameData {

	public AbstractMapSquare[][] map;
	public List<Unit> units = new ArrayList<>();
	public List<String> msgs = new ArrayList<>();
	private List<AbstractEvent> currentEvents = new ArrayList<>();

	public int turnNo = 0;
	public float oxygenLevel = 100f;
	public float engineTemp = 0f;
	public float fuel = 50;
	public float shieldLevel = 100f;
	public float weaponLevel = 100f;
	
	
	public int getWidth() {
		return map.length;
	}


	public int getHeight() {
		return map[0].length;
	}
	
	
	public AbstractMapSquare findAdjacentMapSquare(int x, int y, int _type) {
		for (int y2=y-1 ; y2<=y+1 ; y2++) {
			for (int x2=x-1 ; x<=x+1 ; x2++) {
				if (map[x2][y2].type == _type) {
					return map[x2][y2];
				}
			}
		}
		return null;
	}
	
	
	public Unit getUnitAt(int x, int y) {
		List<DrawableEntity> list = map[x][y].items;
		for (DrawableEntity de : list) {
			if (de instanceof Unit) {
				Unit unit = (Unit)  de;
				return unit;
			}
		}
		return null;
	}
	
	
	public void incOxygenLevel(float f) {
		this.oxygenLevel += f;
		if (this.oxygenLevel > 100) {
			this.oxygenLevel = 100;
		}
	}

}
