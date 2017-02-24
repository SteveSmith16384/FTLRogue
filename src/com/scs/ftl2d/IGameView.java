package com.scs.ftl2d;

import java.io.IOException;

import com.scs.ftl2d.entities.Unit;

public interface IGameView {

	void init() throws IOException;

	void drawScreen(GameData gameData, Unit currentUnit) throws IOException;
	
	char getInput() throws IOException;

	void close() throws IOException;

}
