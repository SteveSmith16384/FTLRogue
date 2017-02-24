package com.scs.ftl2d;

import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.entities.Unit;

public interface IGameView {

	void init() throws IOException;

	void drawPlayersShipScreen(GameData gameData, Unit currentUnit) throws IOException;
	
	KeyStroke getInput() throws IOException;

	void close() throws IOException;

}
