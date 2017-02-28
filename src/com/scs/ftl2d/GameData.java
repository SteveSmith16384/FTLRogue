package com.scs.ftl2d;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TextCharacter;
import com.scs.ftl2d.destinations.AbstractSpaceLocation;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.events.AbstractEvent;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.missions.AbstractMission;

public class GameData {

	public AbstractMapSquare[][] map;
	public IStarmapData starmap = new StarmapData();
	public List<Unit> units = new ArrayList<>();
	public List<String> msgs = new ArrayList<>();
	public AbstractSpaceLocation currentLocation = null;

	public List<AbstractEvent> currentEvents = new ArrayList<>();
	public List<AbstractMission> currentMissions = new ArrayList<>();

	public int turnNo = 0;
	public int creds = 500;
	public float oxygenLevel = 100f;
	public float fuel = 50;
	public float shieldLevel = 100f;
	public float weaponLevel = 100f;
	public float hullDamage = 0f;


	public int getWidth() {
		return map.length;
	}


	public int getHeight() {
		return map[0].length;
	}


	public AbstractMapSquare findAdjacentMapSquare(int x, int y, int _type) {
		for (int y2=y-1 ; y2<=y+1 ; y2++) {
			for (int x2=x-1 ; x2<=x+1 ; x2++) {
				try {
					if (map[x2][y2].type == _type) {
						return map[x2][y2];
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// Do nothing
				}
			}
		}
		return null;
	}


	public Unit getUnitAt(int x, int y) {
		//List<DrawableEntity> list = map[x][y].items;
		for (DrawableEntity de : map[x][y].entities) {
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


	public AbstractMapSquare getMapSquare(int _type) {
		for (int y=0 ; y<getHeight() ; y++) {
			for (int x=0 ; x<getWidth() ; x++) {
				AbstractMapSquare sq = map[x][y];
				if (sq.type == _type) {
					return sq;
				}
			}			
		}
		return null;
	}
}
