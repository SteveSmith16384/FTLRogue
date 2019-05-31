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
import com.scs.rogueframework.IGameView;
import com.scs.rogueframework.LogMessage;
import com.scs.rogueframework.views.LanternaView;

import ssmith.audio.SoundCacheThread;


public class Main {
	
	public enum GameStage {Started, Finished};

	public static final Random RND = new Random();

	private IGameView view;
	public GameData gameData;
	private boolean stopNow = false;
	private AbstractModule currentModule;
	private GameStage gameStage = GameStage.Started;
	public List<AbstractAsciiEffect> asciiEffects = new CopyOnWriteArrayList<>();
	public SoundCacheThread sfx;

	public boolean checkOxygenFlag = false;
	
	public Main() throws IOException, InterruptedException {
		createGameData();

		sfx = new SoundCacheThread("media/sfx/");
		
		currentModule = new PlayersShipModule(this, null);

		view = new LanternaView();

		mainGameLoop();
	}


	private void createGameData() throws IOException {
		gameData = new GameData(this);
		gameData.init(new CSVMap("map1.csv"));
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
					KeyStroke lastChar = view.getInput();
					boolean progress = this.currentModule.processInput(lastChar);
					if (progress) {
						this.currentModule.updateGame();
					}

				} else {
					//Iterator<AbstractAsciiEffect> it = asciiEffects.iterator();
					//while (it.hasNext()) {
					//for (AbstractAsciiEffect effect : asciiEffects) {
					for (int i=0 ; i<asciiEffects.size() ; i++) {
						AbstractAsciiEffect effect = asciiEffects.get(i);
						if (!effect.process()) {
							asciiEffects.remove(effect);
							i--;
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

	
	public void gameOver() {
		addMsg(3, "GAME OVER!");
		gameStage = GameStage.Finished;
		// todo - end?

	}


	public void addAsciiEffect(AbstractAsciiEffect aae) {
		this.asciiEffects.add(aae);
	}
	
	
	public List<AbstractAsciiEffect> getAsciiEffects() {
		return this.asciiEffects;
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


