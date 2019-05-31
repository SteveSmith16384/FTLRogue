package com.scs.rogueframework.asciieffects;

import java.awt.Point;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.Line;

public class BulletShot extends AbstractAsciiEffect {

	private Point current;
	private Line line;
	private TextCharacter ch;

	public BulletShot(AbstractRoguelike main, int sx, int sy, int ex, int ey) {
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
		boolean seen = main.getSq(current.x, current.y).isVisible() == AbstractMapSquare.VisType.Visible;
		if (seen || Settings.DEBUG) {
			view.drawCharacter(current.x, current.y, ch);
		}
	}

}
