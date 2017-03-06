package com.scs.ftl2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.CSVMap;
import com.scs.ftl2d.missions.TransportEggMission;
import com.scs.ftl2d.modules.AbstractModule;
import com.scs.ftl2d.modules.PlayersShipModule;
import com.scs.ftl2d.views.DefaultView;


public class Main {

	public static final Random RND = new Random();

	private IGameView view;
	public GameData gameData;
	private boolean stopNow = false;
	private KeyStroke lastChar;
	private AbstractModule currentModule;

	public Main() throws IOException {
		createGameData();

		currentModule = new PlayersShipModule(this, null);

		view = new DefaultView();

		mainGameLoop();
	}


	private void createGameData() throws IOException {
		gameData = new GameData(this, new CSVMap("map1.csv"));//ArrayMap());
		gameData.recalcVisibleSquares();
		gameData.msgs.add("Welcome to " + Settings.TITLE);

		// Add egg
		TransportEggMission tem = new TransportEggMission(this);
		tem.accepted();
		this.gameData.currentMissions.add(tem);

	}


	private void mainGameLoop() {
		this.currentModule.updateGame();
		this.checkOxygen();
		while (!stopNow) {
			try {
				this.currentModule.drawScreen(view);
				lastChar = view.getInput();
				//this.addMsg("Key '" + lastChar + "' pressed");
				boolean progress = this.currentModule.processInput(lastChar);
				if (progress) {
					this.currentModule.updateGame();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void addMsg(String s) {
		this.gameData.msgs.add(s);
		while (this.gameData.msgs.size() > Settings.MAX_MSGS) {
			this.gameData.msgs.remove(0);
		}
	}


	public void checkOxygen() {
		// Set all as oxygen
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				sq.hasOxygen = true;
			}
		}
		
		List<AbstractMapSquare> waiting = new ArrayList<>();
		List<AbstractMapSquare> processed = new ArrayList<>();

		waiting.add(gameData.map[0][0]);

		while (!waiting.isEmpty()) {
			AbstractMapSquare sq = waiting.remove(0);
			processed.add(sq);
			sq.hasOxygen = false;

			List<AbstractMapSquare> adj = this.gameData.getAdjacentSquares(sq.x, sq.y);
			for (AbstractMapSquare asq : adj) {
				if (asq.isTraversable()) {
					if (!processed.contains(asq) && !waiting.contains(asq)) {
						waiting.add(asq);
					}
				}
			}
		}
	}


	public AbstractModule getCurrentModule() {
		return this.currentModule;
	}


	public void setModule(AbstractModule mod) {
		this.currentModule = mod;
	}
	
	
	public void fireShipsWeapons() {
		this.gameData.weaponTemp += 5;
		
	}

	//--------------------------------------------

	public static void p(String s) {
		System.out.println(s);
	}

	//--------------------------------------------

	public static void main(String[] args) {
		try {
			new Main();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


