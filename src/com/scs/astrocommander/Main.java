/*
 * This code is licenced under GPLv3.  Please see licence.txt for more details.
 * 
 * Written by Stephen Carlyle-Smith (stephen.carlylesmith@googlemail.com)
 */

package com.scs.astrocommander;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.CSVMap;
import com.scs.astrocommander.modules.AbstractModule;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.rogueframework.AbstractAsciiEffect;
import com.scs.rogueframework.AbstractGameData;
import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.LogMessage;
import com.scs.rogueframework.views.LanternaView;

import ssmith.audio.SoundCacheThread;


public final class Main extends AbstractRoguelike {
	
	public GameData gameData;

	public boolean checkOxygenFlag = false;
	
	private Main() throws IOException, InterruptedException {
		super("media/sfx/");
		
		createGameData();		
		currentModule = new PlayersShipModule(this, null);

		this.currentModule.updateGame();
		this.gameData.map_data.checkOxygen(); // Do once at start

		mainGameLoop();
	}


	private void createGameData() throws IOException {
		gameData = new GameData(this);
		gameData.init(new CSVMap("map1.csv"));
		
		this.addMsg(1, "Welcome to " + Settings.TITLE);
		if (Settings.DEBUG) {
			this.addMsg(1, "## DEBUG MODE ON! ###");
		}
	}


	@Override
	protected AbstractGameData getGameData() {
		return gameData;
	}

	//--------------------------------------------

	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}


