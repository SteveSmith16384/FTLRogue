package com.scs.ftl2d;

import java.awt.Point;

public interface ILevelData {

	int getWidth();
	
	int getDepth();
	
	int getCodeForSquare(int x, int z);
	
	int getNumUnits();
	
	Point getUnitStart(int i);
	
}
