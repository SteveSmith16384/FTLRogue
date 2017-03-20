package com.scs.ftl2d.asciieffects;

import java.awt.Point;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.map.AbstractMapSquare;

public class Meteor extends AbstractAsciiEffect {
	
	private static final TextCharacter CHAR = new TextCharacter('O', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);

	private Point p, dir;

	public Meteor(Main main, Point dir) {
		super(main);
		
		p = new Point();
		int w = Main.RND.nextInt(main.gameData.getMapWidth());
		int h = Main.RND.nextInt(main.gameData.getMapHeight());
		
		if (dir.x < 0 && dir.y < 0) { // bottom-right
			p.x = w;
			p.y = main.gameData.getMapHeight();
		} else if (dir.x > 0 && dir.y < 0) { // bottom-left
			p.x = 0;
			p.y = h;
		} else if (dir.x < 0 && dir.y > 0) { // top-right
			p.x = main.gameData.getMapWidth();
			p.y = h;
		} else if (dir.x > 0 && dir.y > 0) { // top-left
			p.x = w;
			p.y = 0;
		} else {
			throw new RuntimeException("Unknown dir: " + p);
		}
	}

	
	@Override
	public void drawChars(IGameView view) {
		view.drawCharacter(p.x, p.y, CHAR);
		
	}

	@Override
	public boolean process() {
		p.x += dir.x;
		p.y += dir.y;
		
		if (this.p.x < 0 || this.p.x > main.gameData.getWidth() || this.p.y < 0 || this.p.y > main.gameData.getHeight()) {
			return false;
		}
		
		AbstractMapSquare sq = main.gameData.map[p.x][p.y]; 
		if (sq.isSquareTraversable() == false) {
			float dam = 10 + (main.gameData.shipSpeed/100);
			sq.incDamage(dam); // Adjust by ship speed
			main.addMsg("Ship damaged " + (int)dam + " by meteor!");
			this.main.asciiEffects.add(new AsciiExplosion(main, p.x, p.y));
			return false;
		}
		return true;
	}

}
