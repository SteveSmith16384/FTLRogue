package com.scs.ftl2d.map;

import com.scs.ftl2d.ILevelData;

public abstract class AbstractMap implements ILevelData {

	protected int[][] data;

	public AbstractMap() {

	}


	// Add walls
	protected void addWalls() {
		for (int y=0 ; y<getHeight() ; y++) {
			for (int x=0 ; x<getWidth() ; x++) {
				//Main.p(x + "," + y);
				if (data[x][y] == 0) {
					boolean found_floor = false;
					for (int y2=y-1 ; y2<=y+1 ; y2++) {
						for (int x2=x-1 ; x2<=x+1 ; x2++) {
							try {
								if (data[x2][y2] != AbstractMapSquare.MAP_NOTHING && data[x2][y2] != AbstractMapSquare.MAP_WALL) {
									found_floor = true;
									break;
								}
							} catch (ArrayIndexOutOfBoundsException ex) {
								// Do nothing
							}
						}
					}
					if (found_floor) {
						data[x][y] = AbstractMapSquare.MAP_WALL;
					}
				}
			}			
		}
	}

}