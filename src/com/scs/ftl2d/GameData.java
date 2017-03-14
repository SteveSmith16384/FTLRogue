package com.scs.ftl2d;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ssmith.astar.IAStarMapInterface;

import com.scs.ftl2d.destinations.AbstractSpaceLocation;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.mobs.AbstractMob;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.events.AbstractEvent;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.MapSquareNothing;
import com.scs.ftl2d.missions.AbstractMission;

public class GameData implements IAStarMapInterface {

	public Main main;

	public AbstractMapSquare[][] map;
	public List<Unit> units = new ArrayList<>();
	public List<String> msgs = new ArrayList<>();
	public AbstractSpaceLocation currentLocation = null;

	public Unit currentUnit;

	public List<AbstractEvent> currentEvents = new ArrayList<>();
	public List<AbstractMission> currentMissions = new ArrayList<>();

	public int turnNo = 0;
	public int creds = 500;
	public float oxygenLevel = 100f;
	public float hullDamage = 0f;

	public int shieldPowerPcent = 33;
	public int weaponPowerPcent = 33;
	public int enginePowerPcent = 0;

	public float totalPower = 100;
	public float powerGainedPerTurn = 0;
	public float powerUsedPerTurn = 0;

	public boolean shipLightsOn = true;

	public boolean shipFlying = false;
	public float shipSpeed;
	public float distanceToDest;

	public float weaponTemp = 0;

	public GameData(Main _main, ILevelData mapdata) throws IOException {
		main = _main;

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

		//recalcVisibleSquares();

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


	public AbstractMob getUnitAt(int x, int y) {
		//List<DrawableEntity> list = map[x][y].items;
		for (DrawableEntity de : map[x][y].getEntities()) {
			if (de instanceof AbstractMob) {
				AbstractMob unit = (AbstractMob) de;
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


	public List<AbstractMapSquare> getAdjacentSquares(int x, int y) {
		List<AbstractMapSquare> list = new ArrayList<>();

		for (int y2=y-1 ; y2<=y+1 ; y2++) {
			for (int x2=x-1 ; x2<=x+1 ; x2++) {
				try {
					if (x2 != x && y2 != y) {
						list.add(map[x2][y2]);
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// Do nothing
				}
			}
		}		

		return list;
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
