package com.scs.astrocommander;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.scs.astrocommander.destinations.AbstractSpaceLocation;
import com.scs.astrocommander.destinations.StartingOutpost;
import com.scs.astrocommander.entities.DrawableEntity;
import com.scs.astrocommander.entities.items.Knife;
import com.scs.astrocommander.entities.items.MediKit;
import com.scs.astrocommander.entities.items.OxygenMask;
import com.scs.astrocommander.entities.items.Pistol;
import com.scs.astrocommander.entities.mobs.Unit;
import com.scs.astrocommander.events.AbstractEvent;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.MapSquareNothing;
import com.scs.astrocommander.missions.AbstractMission;

import ssmith.astar.IAStarMapInterface;

public class GameData implements IAStarMapInterface {

	public Main main;
	private ILevelData mapdata;
	
	public AbstractMapSquare[][] map;
	public List<Unit> units = new ArrayList<>();
	public List<LogMessage> msgs = new ArrayList<>();
	public List<Point> weaponPoints = new ArrayList<>();

	public Unit currentUnit;

	public AbstractEvent currentEvent;// = new CopyOnWriteArrayList<>();
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

	public GameData(Main _main, ILevelData _mapdata) throws IOException {
		super();
		
		main = _main;
		mapdata = _mapdata;
	}

	
	public void init() throws IOException { // Can't put this in the constructor since it uses a reference to gamedata
		map = new AbstractMapSquare[mapdata.getWidth()][mapdata.getHeight()];
		for (int y=0 ; y<mapdata.getHeight() ; y++) {
			for (int x=0 ; x<mapdata.getWidth() ; x++) {
				int code = mapdata.getCodeForSquare(x, y);
				map[x][y] = AbstractMapSquare.Factory(main, code, x, y);
			}			
		}

		// Create player's units
		for (int i=0 ; i<mapdata.getNumUnits() ; i++) {
			Point p = mapdata.getUnitStart(i);
			Unit unit = new Unit(main, ((i+1)+"").charAt(0), p.x, p.y);
			units.add(unit);
			map[p.x][p.y].addEntity(unit);
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
			this.getRandomMapSquare(AbstractMapSquare.MAP_FLOOR).addEntity(item);
		}
		
		this.currentLocation = new StartingOutpost(main, "Station Zybex");
	}


	public void recalcVisibleSquares() {
		for (int y=0 ; y<getHeight() ; y++) {
			for (int x=0 ; x<getWidth() ; x++) {
				// Space is always seen
				if (map[x][y] instanceof MapSquareNothing) {
					map[x][y].visible = AbstractMapSquare.VisType.Visible;
					continue;
				}
				if (map[x][y].visible != AbstractMapSquare.VisType.Hidden) {
					this.map[x][y].visible = AbstractMapSquare.VisType.Seen;
				}
				for (Unit unit : this.units) {
					if (unit.canSee(x, y)) {
						this.map[x][y].visible = AbstractMapSquare.VisType.Visible;
						break;
					}					
				}
			}			
		}

	}


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


	public AbstractMapSquare findAdjacentRepairableMapSquare(int x, int y) {
		for (int y2=y-1 ; y2<=y+1 ; y2++) {
			for (int x2=x-1 ; x2<=x+1 ; x2++) {
				try {
					if (map[x2][y2].getDamagePcent() > 0 && map[x2][y2].getDamagePcent() < 100) {
						return map[x2][y2];
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// Do nothing
				}
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


	public AbstractMapSquare getFirstMapSquare(int _type) {
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


	public AbstractMapSquare getRandomMapSquare(int _type) {
		List<AbstractMapSquare> squares = new ArrayList<>();
		for (int y=0 ; y<main.gameData.getHeight() ; y++) {
			for (int x=0 ; x<main.gameData.getWidth() ; x++) {
				AbstractMapSquare sq = main.gameData.map[x][y];
				if (sq.type == _type) {
					squares.add(sq);
				}
			}
		}

		if (squares.isEmpty()) {
			return null;
		} else {
			return squares.get(Main.RND.nextInt(squares.size()));
		}
	}


	public List<AbstractMapSquare> getAdjacentSquares(int x, int y) {
		List<AbstractMapSquare> list = new ArrayList<>();

		for (int y2=y-1 ; y2<=y+1 ; y2++) {
			for (int x2=x-1 ; x2<=x+1 ; x2++) {
				try {
					if (x2 != x || y2 != y) {
						list.add(map[x2][y2]);
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// Do nothing
				}
			}
		}		

		return list;
	}


	public void checkOxygen() {
		// Set all as oxygen
		for (int y=0 ; y<getHeight() ; y++) {
			for (int x=0 ; x<getWidth() ; x++) {
				AbstractMapSquare sq = map[x][y];
				sq.hasOxygen = true;
			}
		}

		List<AbstractMapSquare> waiting = new ArrayList<>();
		List<AbstractMapSquare> processed = new ArrayList<>();

		waiting.add(map[0][0]); // 0,0 is always space

		while (!waiting.isEmpty()) {
			AbstractMapSquare sq = waiting.remove(0);
			processed.add(sq);
			sq.hasOxygen = false;
			/*if (Settings.DEBUG) {
				Main.p("No oxygen in " + sq.x + "," + sq.y);
			}*/

			List<AbstractMapSquare> adj = getAdjacentSquares(sq.x, sq.y);
			for (AbstractMapSquare asq : adj) {
				if (!asq.isAirtight()) {
					if (!processed.contains(asq) && !waiting.contains(asq)) {
						waiting.add(asq);
					}
				}
			}
		}
	}



	@Override
	public int getMapWidth() {
		return this.getWidth();
	}


	@Override
	public int getMapHeight() {
		return this.getHeight();
	}


	@Override
	public boolean isMapSquareTraversable(int x, int z) {
		return map[x][z].isSquareTraversable();
	}


	@Override
	public float getMapSquareDifficulty(int x, int z) {
		return 1;
	}
}
