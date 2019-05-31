package com.scs.rogueframework;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.map.AbstractMapSquare;
import com.scs.astrocommander.modules.AbstractModule;
import com.scs.rogueframework.views.LanternaView;

import ssmith.audio.SoundCacheThread;

public abstract class AbstractRoguelike {

	public static final Random RND = new Random();
	protected IGameView view;
	protected AbstractModule currentModule;
	public List<AbstractAsciiEffect> asciiEffects = new CopyOnWriteArrayList<>();
	private boolean stopNow = false;
	public SoundCacheThread sfx;

	protected AbstractRoguelike(String sfx_path) {
		sfx = new SoundCacheThread(sfx_path);//);
		view = new LanternaView();

	}


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


	public AbstractModule getCurrentModule() {
		return this.currentModule;
	}


	public void setModule(AbstractModule mod) {
		this.currentModule = mod;
	}

	
	public void addAsciiEffect(AbstractAsciiEffect aae) {
		this.asciiEffects.add(aae);
	}
	
	
	public List<AbstractAsciiEffect> getAsciiEffects() {
		return this.asciiEffects;
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
		getGameData().msgs.add(new LogMessage(pri, s));
		while (getGameData().msgs.size() > Settings.MAX_MSGS) {
			getGameData().msgs.remove(0);
		}
	}

	
	abstract protected AbstractGameData getGameData();

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
