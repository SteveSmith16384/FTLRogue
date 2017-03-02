package com.scs.ftl2d.map;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.scs.ftl2d.ILevelData;

public class CSVMap implements ILevelData {

	private int[][] data;
	
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
				x++;
			}
			y++;
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
		return null; // todo
	}

}
