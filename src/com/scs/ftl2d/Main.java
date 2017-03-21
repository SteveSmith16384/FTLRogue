package com.scs.ftl2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.asciieffects.AbstractAsciiEffect;
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
	public int gameStage = 0;
	public List<AbstractAsciiEffect> asciiEffects = new ArrayList<>();
	public boolean checkOxygenFlag = false;

	public Main() throws IOException, InterruptedException {
		createGameData();

		currentModule = new PlayersShipModule(this, null);

		view = new DefaultView();

		mainGameLoop();
	}


	private void createGameData() throws IOException {
		gameData = new GameData(this, new CSVMap("map1.csv"));
		gameData.init();
		gameData.recalcVisibleSquares();
		this.addMsg(1, "Welcome to " + Settings.TITLE);

		// Add egg
		TransportEggMission tem = new TransportEggMission(this);
		tem.accepted();
		this.gameData.currentMissions.add(tem);

	}


	private void mainGameLoop() throws InterruptedException {
		if (Settings.DEBUG) {
			this.addMsg("## DEBUG MODE ##");
		}
		this.currentModule.updateGame();
		this.gameData.checkOxygen(); // Do once at start
		
		while (!stopNow) {
			try {
				this.currentModule.drawScreen(view);
				if (this.asciiEffects.isEmpty()) {
					lastChar = view.getInput();
					boolean progress = this.currentModule.processInput(lastChar);
					if (progress) {
						this.currentModule.updateGame();
					}
					
				} else {
					Iterator<AbstractAsciiEffect> it = asciiEffects.iterator();
					while (it.hasNext()) {
						AbstractAsciiEffect effect = it.next();
						if (!effect.process()) {
							it.remove();
						}
					}
					Thread.sleep(100);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void addMsg(String s) {
		addMsg(1, s);
	}
	
	
	public void addMsg(int pri, String s) {
		this.gameData.msgs.add(new LogMessage(pri, s));
		while (this.gameData.msgs.size() > Settings.MAX_MSGS) {
			this.gameData.msgs.remove(0);
		}
	}


	public AbstractModule getCurrentModule() {
		return this.currentModule;
	}


	public void setModule(AbstractModule mod) {
		this.currentModule = mod;
	}


	//--------------------------------------------

	public static void p(String s) {
		System.out.println(s);
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


