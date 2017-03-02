package com.scs.ftl2d.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.ILevelData;


public class ArrayMap implements ILevelData {

	private int[][] data = new int[][]{ // data[9][0] == 1
			// Player starts looking upwards
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 8, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0},
			{0, 0, 2, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 1, 1, 3, 0},
			{0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 8, 0, 0, 1, 0, 3, 3, 3, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
	};

	private List<Point> startPoints = new ArrayList<>();
	
	public ArrayMap() {
		// Add walls
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
				} else if (data[x][y] == 1) {
					startPoints.add(new Point(x, y));
				}
			}			
		}

	}


	@Override
	public int getWidth() {
		return data[0].length;
	}


	@Override
	public int getHeight() {
		return data.length;
	}


	@Override
	public int getCodeForSquare(int x,  int z) {
		return data[z][x];
	}


	@Override
	public int getNumUnits() {
		return 3;
	}


	@Override
	public Point getUnitStart(int i) {
		return this.startPoints.get(i);
	}


}
