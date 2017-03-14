package com.scs.ftl2d.asciieffects;

import java.awt.Point;

import com.scs.ftl2d.Main;

public class ShipLaser extends AbstractAsciiEffect {

	private Point start, dir;
	
	public ShipLaser(Main main, int sx, int sy, int dx, int dy) {
		super(main);
		
		start = new Point(sx, sy);
		dir = new Point(dx, dy);
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

}
