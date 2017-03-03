package com.scs.ftl2d;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.entities.mobs.Unit;

public interface IGameView {

	void drawPlayersShipScreen(GameData gameData, Unit currentUnit) throws IOException;
	
	void drawConsoleScreen(List<String> lines, String cmd) throws IOException;
	
	KeyStroke getInput() throws IOException;

	void close() throws IOException;

}
