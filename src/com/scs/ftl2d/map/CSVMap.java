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
		Path path = FileSystems.getDefault().getPath("./data/maps", "file");
		List<String> lines = Files.readAllLines(path);
			
		data = new int[lines.get(0).split("\t").length][lines.size()];
		for (String line :  lines) {
			
		}
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCodeForSquare(int x, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Point getUnitStart(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
