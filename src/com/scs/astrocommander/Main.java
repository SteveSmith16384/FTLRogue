/*
 * This code is licenced under GPLv3.  Please see licence.txt for more details.
 * 
 * Written by Stephen Carlyle-Smith (stephen.carlylesmith@googlemail.com)
 */

package com.scs.astrocommander;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.asciieffects.AbstractAsciiEffect;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.map.CSVMap;
import com.scs.astrocommander.modules.AbstractModule;
import com.scs.astrocommander.modules.PlayersShipModule;
import com.scs.astrocommander.views.DefaultView;


public class Main {

	public static final Random RND = new Random();

	private IGameView view;
	public GameData gameData;
	private boolean stopNow = false;
	private KeyStroke lastChar;
	private AbstractModule currentModule;
	public int gameStage = 0;
	private List<AbstractAsciiEffect> asciiEffects = new ArrayList<>();
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
		if (Settings.DEBUG) {
			this.addMsg(1, "## DEBUG MODE ON! ###");
		}
	}


	private void mainGameLoop() throws InterruptedException {
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


	public void addMsgIfSeen(AbstractMapSquare sq, int pri, String s) {
		if (sq.visible == AbstractMapSquare.VisType.Visible) {
			addMsg(pri, s);
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


