package com.scs.astrocommander.asciieffects;

import java.awt.Point;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.astrocommander.Main;
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.asciieffects.AbstractAsciiEffect;
import com.scs.rogueframework.asciieffects.AsciiExplosion;

public class ShipLaser extends AbstractAsciiEffect {

	private Point current, dir, end;
	private TextCharacter ch;
	
	public ShipLaser(Main main, int sx, int sy, int dx, int dy, int ex, int ey) {
		super(main);
		
		current = new Point(sx, sy);
		dir = new Point(dx, dy);
		end = new Point(ex, ey);
		
		ch = new TextCharacter(dir.y==0?'=':'"', TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);
	}
	

	@Override
	public boolean process() {
		if (current.x == end.x && current.y == end.y) {
			this.main.addAsciiEffect(new AsciiExplosion(main, current.x, current.y));
			return false;
		}
		
		current.x += dir.x;
		current.y += dir.y;
		
		Main m = (Main)main;
		if (this.current.x < 0 || this.current.x > m.gameData.map_data.getWidth() || this.current.y < 0 || this.current.y > m.gameData.map_data.getHeight()) {
			return false;
		}
		return true;
	}

	
	@Override
	public void drawChars(IGameView view) {
		view.drawCharacter(current.x, current.y, ch);
	}

}
