package com.scs.ftl2d;

import java.awt.Point;
import java.io.IOException;

import com.scs.ftl2d.entities.Unit;
import com.scs.ftl2d.map.AbstractMapSquare;
import com.scs.ftl2d.map.ArrayMap;
import com.scs.ftl2d.views.DefaultView;

/*

FTL
Keep it simple at first!

CONTROLS:
Numbers - select unit
a - left
c - close door
d - right
h - heal TODO
k - show Key TODO
l - Launch ship TODO
n - Nothing
o - open door
s - down
t - teleport TODO
u - Use console TODO
w - up

ASCII CODES
Numbers - Players units
A - OxyGen (air)
C - Control panel
F - On fire
M - Medibay
R - Replicator
T - Teleporter
. - Floor
+' - Door (open/closed)


SHIP AREAS
DONE medi-bay: heal player
Torpedo bay: launch weapon
Scanners: see what's going on
DONE Oxygen generators
Gravity generators
Teleporter
Bridge?
Engineering?
Cryo chambers?


UNIT ROLES
Medic: heal others
mechanic: repair stuff
Weapons: take control of exterior weapons
Saboteur


SHIP STATS
DONE Oxygen level
Engine temp
Damage (by area)
Shield Level
Energy
Fuel


GAMEPLAY
All units can do all actions, but specialists are better
DONE Oxygen level - slowly goes down
Fire might spread
Need to stock up on missiles or something?
Board enemy ships
Engines - get ship ready for warp
Engines can overheat
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


UNIT COMMANDS
Fight fire
Add resource to unit
Get healed - auto
Man station
Patrol
Go to place


EVENTS
Enemy ship off starboard bow
Boarded by enemies
Meteor storm
Aliens invade ship
Installations break down


LATER
Draw starfield

 */
public class Main {

	private IGameView view;
	public GameData gameData;
	private boolean stopNow = false;
	private Unit currentUnit;
	private char lastChar;

	public Main(IGameView _view) throws IOException {
		view = _view;
		view.init();

		createGameData();
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
			Unit unit = new Unit(this, (i+"").charAt(0), p.x, p.y, "Unit " + (i+1), 0);
			this.gameData.units.add(unit);
			gameData.map[p.x][p.y].items.add(unit);
			if (this.currentUnit == null) {
				currentUnit = unit;
			}
		}

		gameData.msgs.add("Welcome to " + Settings.TITLE);
		gameData.msgs.add("You are in control of " + this.currentUnit.getName());
	}


	private void mainGameLoop() {
		while (!stopNow) {
			try {
				view.drawScreen(gameData, this.currentUnit);
				lastChar = view.getInput();
				//System.out.println(c + " pressed");
				this.addMsg("Key '" + lastChar + "' pressed");
				boolean progress = processInput(lastChar);
				if (progress) {
					updateGame();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Returns whether the game should progress a turn
	 */
	private boolean processInput(char c) {
		switch (c) {
		case 'n':
		{
			/*int pos = this.gameData.units.indexOf(this.currentUnit);
			if (pos >= this.gameData.units.size()) {
				this.currentUnit = this.gameData.units.get(0);
			} else {
				this.currentUnit = this.gameData.units.get(pos+1);
			}
			return false;*/
			this.addMsg("You do nothing");
			return true;
		}
		
		case 'p':
		{
			/*int pos = this.gameData.units.indexOf(this.currentUnit);
			if (pos <= 0) {
				this.currentUnit = this.gameData.units.get(this.gameData.units.size()-1);
			} else {
				this.currentUnit = this.gameData.units.get(pos-1);
			}*/
			return false;
		}
		
		case 'w':
			this.currentUnit.attemptMove(0, -1);
			return true;
			
		case 'a':
			this.currentUnit.attemptMove(-1, 0);
			return true;
			
		case 's':
			this.currentUnit.attemptMove(0, 1);
			return true;
			
		case 'd':
			this.currentUnit.attemptMove(1, 0);
			return true;
			
		case 'o':
			this.currentUnit.openDoor();
			return true;
			
		case 'c':
			this.currentUnit.closeDoor();
			return true;
			
		case 'u':
			this.currentUnit.useConsole();
			return true;
			
		default:
			this.addMsg("Unknown key " + c);
		}
		
		return false;
	}


	private void updateGame() {
		gameData.turnNo++;
		
		// Go through map
		for (int y=0 ; y<gameData.getHeight() ; y++) {
			for (int x=0 ; x<gameData.getWidth() ; x++) {
				AbstractMapSquare sq = gameData.map[x][y];
				sq.process();
			}			
		}
		
	}
	
	
	public void addMsg(String s) {
		this.gameData.msgs.add(s);
		while (this.gameData.msgs.size() > Settings.MAX_MSGS) {
			this.gameData.msgs.remove(0);
		}
	}

	
	public static void p(String s) {
		System.out.println(s);
	}

	//--------------------------------------------

	public static void main(String[] args) {
		try {
			new Main(new DefaultView());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


