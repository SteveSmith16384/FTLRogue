package com.scs.astrocommander.map;

public abstract class AbstractMap implements ILevelData {

	protected int[][] data;

	public AbstractMap() {

	}


	// Add walls
	protected void addWalls() {
		for (int y=0 ; y<getHeight() ; y++) {
			for (int x=0 ; x<getWidth() ; x++) {
				//Main.p(x + "," + y);
				if (data[x][y] == 0) { // Unset
					boolean found_floor = false;
					for (int y2=y-1 ; y2<=y+1 ; y2++) {
						for (int x2=x-1 ; x2<=x+1 ; x2++) {
							if (x2 != x || y2 != y) {
								try {
									if (data[x2][y2] == AbstractMapSquare.MAP_BATTERY ||
											data[x2][y2] == AbstractMapSquare.MAP_CONTROL_PANEL ||
											data[x2][y2] == AbstractMapSquare.MAP_DOOR ||
											data[x2][y2] == AbstractMapSquare.MAP_ENGINES ||
											data[x2][y2] == AbstractMapSquare.MAP_FLOOR ||
											data[x2][y2] == AbstractMapSquare.MAP_MEDIBAY ||
											data[x2][y2] == AbstractMapSquare.MAP_OXYGEN_GEN ||
											data[x2][y2] == AbstractMapSquare.MAP_REPLICATOR ||
											data[x2][y2] == AbstractMapSquare.MAP_TELEPORTER
											) {
										found_floor = true;
										break;
									}
								} catch (ArrayIndexOutOfBoundsException ex) {
									// Do nothing
								}
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