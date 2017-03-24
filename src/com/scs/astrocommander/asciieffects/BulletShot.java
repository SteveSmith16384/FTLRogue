package com.scs.astrocommander.asciieffects;

import java.awt.Point;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.astrocommander.IGameView;
import com.scs.astrocommander.Line;
import com.scs.astrocommander.Main;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.map.AbstractMapSquare;

public class BulletShot extends AbstractAsciiEffect {

	private Point current;
	private Line line;
	private TextCharacter ch;

	public BulletShot(Main main, int sx, int sy, int ex, int ey) {
		super(main);

		current = new Point(sx, sy);

		line = new Line(sx, sy, ex, ey);
		ch = new TextCharacter('*', TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);
	}


	@Override
	public boolean process() {
		if (line.size() > 0) {
			current = line.remove(0);
			return true;
		} else {
			return false;
		}
	}


	@Override
	public void drawChars(IGameView view) {
		boolean seen = main.gameData.map[current.x][current.y].visible == AbstractMapSquare.VisType.Visible;
		if (seen || Settings.DEBUG) {
			view.drawCharacter(current.x, current.y, ch);
		}
	}

}
