package com.scs.astrocommander;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.scs.astrocommander.destinations.AbstractSpaceLocation;
import com.scs.astrocommander.destinations.StartingOutpost;
import com.scs.astrocommander.entities.items.Knife;
import com.scs.astrocommander.entities.items.MediKit;
import com.scs.astrocommander.entities.items.OxygenMask;
import com.scs.astrocommander.entities.items.Pistol;
import com.scs.astrocommander.entities.mobs.PlayersUnit;
import com.scs.astrocommander.events.AbstractEvent;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.ILevelData;
import com.scs.astrocommander.missions.AbstractMission;
import com.scs.rogueframework.AbstractGameData;
import com.scs.rogueframework.ecs.entities.DrawableEntity;

public class GameData extends AbstractGameData {

	public Main main;
	public MapData map_data;
	
	public PlayersUnit current_unit;
	public List<Point> weaponPoints = new ArrayList<>();

	public AbstractEvent currentEvent;
	public List<AbstractMission> currentMissions = new CopyOnWriteArrayList<>();

	public int turnNo = 0;
	public int creds = 500;
	public float wantedLevel = 0;
	public float oxygenLevel = 100f;

	public int shieldPowerPcent = 33;
	public int weaponPowerPcent = 33;
	public int enginePowerPcent = 0;

	public float totalPower = 100;
	public float powerGainedPerTurn = 0;
	public float powerUsedPerTurn = 0;

	public boolean shipLightsOn = true;

	public AbstractSpaceLocation currentLocation = null;
	public float shipSpeed;
	public float distanceToDest = 1000;

	public float weaponTemp = 0;

	public GameData(Main _main) throws IOException {
		super();
		
		main = _main;
	}

	
	public void init(ILevelData mapdata) {//throws IOException { // Can't put this in the constructor since it uses a reference to gamedata
		map_data = new MapData(main, mapdata);
		// Create player's units
		for (int i=0 ; i<mapdata.getNumUnits() ; i++) {
			Point p = mapdata.getUnitStart(i);
			PlayersUnit unit = new PlayersUnit(main, ((i+1)+"").charAt(0), p.x, p.y);
			players_units.add(unit);
			map_data.map[p.x][p.y].addEntity(unit);
		}
		
		// Add random equipment
		List<DrawableEntity> itemsToAdd = new ArrayList<>();
		itemsToAdd.add(new MediKit(main));
		itemsToAdd.add(new MediKit(main));
		itemsToAdd.add(new Knife(main));
		itemsToAdd.add(new Knife(main));
		itemsToAdd.add(new Pistol(main));
		itemsToAdd.add(new Pistol(main));
		itemsToAdd.add(new OxygenMask(main));
		for (DrawableEntity item : itemsToAdd) {
			map_data.getRandomMapSquare(AbstractMapSquare.MAP_FLOOR).addEntity(item);
		}
		
		this.recalcVisibleSquares();

		this.currentLocation = new StartingOutpost(main, "Station Zythum");
		
	}


	public void incOxygenLevel(float f) {
		this.oxygenLevel += f;
		if (this.oxygenLevel > 100) {
			this.oxygenLevel = 100;
		}
	}


	public void recalcVisibleSquares() {
		this.map_data.recalcVisibleSquares(players_units);
	}



}
