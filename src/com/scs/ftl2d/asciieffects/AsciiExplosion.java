package com.scs.ftl2d.asciieffects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;

public class AsciiExplosion extends AbstractAsciiEffect {
	
	private static final TextCharacter CHAR1 = new TextCharacter('*', TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);
	private static final TextCharacter CHAR2 = new TextCharacter('*', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
	private static final TextCharacter CHAR3 = new TextCharacter('*', TextColor.ANSI.RED, TextColor.ANSI.BLACK);

	private Point p;
	private int showUntil = 10;
	private List<TextCharacter> chars = new ArrayList<>(); 
	
	public AsciiExplosion(Main main, int x, int y) {
		super(main);
		
		p.x = x;
		p.y = y;
		
		chars.add(CHAR1);
		chars.add(CHAR2);
		chars.add(CHAR3);
	}
	

	@Override
	public boolean process() {
		showUntil--;
		if (showUntil <= 0) {
			return false;
		}
		return true;

	}

	@Override
	public void drawChars(IGameView view) {
		for (int y=p.y-1 ; y<=p.y+1 ; y++) {
			for (int x=p.x-1 ; x<=p.x+1 ; x++) {
				view.drawCharacter(x, y, chars.get(Main.RND.nextInt(3)));
			}			
		}
	}

}
