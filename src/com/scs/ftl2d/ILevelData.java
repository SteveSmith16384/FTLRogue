package com.scs.ftl2d;

import java.awt.Point;

public interface ILevelData {

	int getWidth();
	
	int getHeight();
	
	int getCodeForSquare(int x, int y);
	
	int getNumUnits();
	
	Point getUnitStart(int i);
	
}
