package com.scs.ftl2d.map;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.ILevelData;

public class CSVMap implements ILevelData {

	private int[][] data;
	private List<Point> crewStart = new ArrayList<>();

	public CSVMap(String file) throws IOException {
		Path path = FileSystems.getDefault().getPath("./data/maps", file);
		List<String> lines = Files.readAllLines(path);

		data = new int[lines.get(0).split("\t").length][lines.size()];
		int y=0;
		for (String line :  lines) {
			String tokens[] = line.split("\t");
			int x = 0;
			for (String t : tokens) {
				data[x][y] = Integer.parseInt(t);
				if (data[x][y] < 0) { // Crew
					crewStart.add(new Point(x, y));
					data[x][y] = 1;
				}
				x++;
			}
			y++;
		}

		// Add walls
		for (y=0 ; y<getHeight() ; y++) {
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


	@Override
	public int getWidth() {
		return data.length;
	}


	@Override
	public int getHeight() {
		return data[0].length;
	}


	@Override
	public int getCodeForSquare(int x, int y) {
		return data[x][y];
	}


	@Override
	public int getNumUnits() {
		return 4;
	}


	@Override
	public Point getUnitStart(int i) {
		return this.crewStart.get(i);
	}

}
