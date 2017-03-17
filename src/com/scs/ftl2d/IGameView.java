package com.scs.ftl2d;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.asciieffects.AbstractAsciiEffect;
import com.scs.ftl2d.entities.mobs.Unit;

public interface IGameView {

	void drawPlayersShipScreen(GameData gameData, Map<String, TextCharacter> seenSquares, List<AbstractAsciiEffect> effects, List<String> helpText) throws IOException;
	
	void drawConsoleScreen(List<String> lines, String cmd) throws IOException;
	
	void drawCharacter(int x, int y, TextCharacter ch);
	
	KeyStroke getInput() throws IOException;

	void close() throws IOException;

}
