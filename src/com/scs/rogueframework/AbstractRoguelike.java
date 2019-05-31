package com.scs.rogueframework;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.rogueframework.asciieffects.AbstractAsciiEffect;
import com.scs.rogueframework.ecs.entities.FrameworkMob;
import com.scs.rogueframework.map.FrameworkMapSquare;
import com.scs.rogueframework.map.IMapSquare;
import com.scs.rogueframework.views.LanternaView;
import com.sun.scenario.Settings;

import ssmith.audio.SoundCacheThread;

public abstract class AbstractRoguelike { // todo - add Main to the name

	public static final Random RND = new Random();
	protected IGameView view;
	protected IModule currentModule;
	public List<AbstractAsciiEffect> asciiEffects = new CopyOnWriteArrayList<>();
	private boolean stopNow = false;
	public SoundCacheThread sfx;

	protected AbstractRoguelike(String sfx_path) {
		sfx = new SoundCacheThread(sfx_path);//);
		view = new LanternaView();

	}


	public abstract IMapSquare getSq(int x, int y);
	
	public abstract IMapSquare findAdjacentMapSquare(int x, int y, int type);
	
	public abstract FrameworkMob getCurrentUnit();
	
	public abstract int getPlayersSide();
	
	protected void mainGameLoop() throws InterruptedException {
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
					for (int i=asciiEffects.size()-1 ; i>=0 ; i--) {
						AbstractAsciiEffect effect = asciiEffects.get(i);
						if (!effect.process()) {
							asciiEffects.remove(effect);
						}
					}
					Thread.sleep(100);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public IModule getCurrentModule() {
		return this.currentModule;
	}


	public void setModule(IModule mod) {
		this.currentModule = mod;
	}

	
	public void addAsciiEffect(AbstractAsciiEffect aae) {
		this.asciiEffects.add(aae);
	}
	
	
	public List<AbstractAsciiEffect> getAsciiEffects() {
		return this.asciiEffects;
	}
	
	
	public void addMsgIfSeen(IMapSquare sq, int pri, String s) {
		if (sq.isVisible() == FrameworkMapSquare.VisType.Visible) {
			addMsg(pri, s);
		}
	}

	public void addMsg(String s) {
		addMsg(1, s);
	}


	public void addMsg(int pri, String s) {
		getGameData().msgs.add(new LogMessage(pri, s));
		while (getGameData().msgs.size() > getMaxMsgs()) {
			getGameData().msgs.remove(0);
		}
	}

	
	protected abstract int getMaxMsgs();
	
	protected abstract AbstractGameData getGameData();
	
	public abstract int getViewDist();

	public void gameOver() {
		addMsg(3, "GAME OVER!");
		//gameStage = GameStage.Finished;
		// todo - end?

	}


	//--------------------------------------------

	public static void p(String s) {
		System.out.println(s);
	}

}
