package com.scs.ftl2d;

import java.awt.Point;
import java.io.IOException;
import java.util.Random;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.entities.mobs.Unit;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.ArrayMap;
import com.scs.ftl2d.modules.AbstractModule;
import com.scs.ftl2d.modules.PlayersShipModule;
import com.scs.ftl2d.views.DefaultView;

/*

FTL
Keep it simple at first!

STORY
Start
Choose mission, buy sell etc..
Choose ships destination
Fly there, maybe get intercepted on the way or rnd event happens
Repeat


QUESTIONS



SHIP AREAS
DONE medi-bay: heal player
Torpedo bay: launch weapon
Scanners: see what's going on in flying mode
DONE Oxygen generators
Teleporter
Bridge?
Engineering?
Cryo chambers?


UNIT ROLES
Medic: heal others
mechanic: repair stuff
Weapons: take control of exterior weapons
Saboteur?


SHIP STATS
Oxygen level
Damage (by area) TODO
Shield Level TODO
Energy TODO
Fuel TODO - Determines ships range
Hull damage TODO
Engine temp?


GAMEPLAY
Show unit status - eating, moving, repairing, nothing
Overlays - damage, oxygen, unit routes
Background/foreground colours
All units can do all actions, but specialists are better
DONE Oxygen level - slowly goes down
Fire might spread
Need to stock up on missiles or something?
Board enemy ships
Engines - get ship ready for warp
Random events - enemy ship turns up, meteor storm
Stock up on fuel at spacestation
Have background stories for units
DONE Units need to eat
Hover mouse over square to identify
Units move randomly when oxygen low
Fly ship around space map
Trade with space station
Encounter other ships when moving around in space
Randomly generate maps
Scroll map
Man weapon console to shoot
Units get tired
Missions
Load/save
Choose where to fly to
LOS
Buy crewmembers


EVENTS
Enemy ship off starboard bow
Boarded by enemies
Meteor storm
Aliens invade ship
Installations break down
Solar flare
Encounter empty hulk


MISSIONS
Deliver alien egg


LATER
Draw starfield

 */
public class Main {

	public static final Random RND = new Random();
	
	private IGameView view;
	public GameData gameData;
	private boolean stopNow = false;
	private KeyStroke lastChar;
	private AbstractModule currentModule;
	
	public Main() throws IOException {
		createGameData();

		currentModule = new PlayersShipModule(this);

		view = new DefaultView();

		mainGameLoop();
	}


	private void createGameData() {
		gameData = new GameData();

		// Create map data
		ILevelData mapdata = new ArrayMap();
		AbstractMapSquare[][] map = new AbstractMapSquare[mapdata.getWidth()][mapdata.getDepth()];
		for (int y=0 ; y<mapdata.getDepth() ; y++) {
			for (int x=0 ; x<mapdata.getWidth() ; x++) {
				int code = mapdata.getCodeForSquare(x, y);
				map[x][y] = AbstractMapSquare.Factory(this, code);
			}			
		}
		gameData.map = map;

		// Create player's units
		for (int i=0 ; i<mapdata.getNumUnits() ; i++) {
			Point p = mapdata.getUnitStart(i);
			Unit unit = new Unit(this, ((i+1)+"").charAt(0), p.x, p.y, "Unit " + (i+1), 0);
			this.gameData.units.add(unit);
			gameData.map[p.x][p.y].entities.add(unit);
		}

		gameData.msgs.add("Welcome to " + Settings.TITLE);
	}


	private void mainGameLoop() {
		while (!stopNow) {
			try {
				this.currentModule.drawScreen(view);
				lastChar = view.getInput();
				//System.out.println(c + " pressed");
				this.addMsg("Key '" + lastChar + "' pressed");
				boolean progress = this.currentModule.processInput(lastChar);
				if (progress) {
					updateGame();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private void updateGame() {
		this.currentModule.updateGame();
	}
	
	
	public void addMsg(String s) {
		this.gameData.msgs.add(s);
		while (this.gameData.msgs.size() > Settings.MAX_MSGS) {
			this.gameData.msgs.remove(0);
		}
	}

	
	public void setModule(AbstractModule mod) {
		this.currentModule = mod;
	}
	
	
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


