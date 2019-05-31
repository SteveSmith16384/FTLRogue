package com.scs.astrocommander;

import java.util.ArrayList;
import java.util.List;

import com.scs.astrocommander.entities.mobs.PlayersUnit;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.ILevelData;
import com.scs.astrocommander.map.MapSquareNothing;

import ssmith.astar.IAStarMapInterface;

public class MapData implements IAStarMapInterface {

	public AbstractMapSquare[][] map;

	public MapData(Main main, ILevelData mapdata) {
		map = new AbstractMapSquare[mapdata.getWidth()][mapdata.getHeight()];
		for (int y=0 ; y<mapdata.getHeight() ; y++) {
			for (int x=0 ; x<mapdata.getWidth() ; x++) {
				int code = mapdata.getCodeForSquare(x, y);
				map[x][y] = AbstractMapSquare.Factory(main, code, x, y);
			}			
		}

	}


	public void recalcVisibleSquares(List<PlayersUnit> players_units) {
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
				for (PlayersUnit unit : players_units) {
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
		for (int y=0 ; y<getHeight() ; y++) {
			for (int x=0 ; x<getWidth() ; x++) {
				AbstractMapSquare sq = map[x][y];
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


	// For A* ==========================
	
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
		return map[x][z].isTraversable();
	}


	@Override
	public float getMapSquareDifficulty(int x, int z) {
		return 1;
	}

}
