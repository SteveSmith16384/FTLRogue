package com.scs.ftl2d.modules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.entities.Unit;
import com.scs.ftl2d.events.AbstractEvent;
import com.scs.ftl2d.map.AbstractMapSquare;
/*
 * 

CONTROLS:
Numbers - select unit
Arrows - move unit
c - close door
e - Use equipment TODO
k - show Key TODO
l - Launch ship TODO
n - Nothing
o - open door
s - shoot TODO
t - teleport TODO
u - Use console TODO

ASCII CODES
Numbers - Players units
A - OxyGen (air)
C - Control panel
E - Engines
F - On fire
M - Medibay
m - Medikit
R - Replicator
T - Teleporter
. - Floor
+' - Door (open/closed)

COLOURS
Background colour of installations shows damage


 */
public class PlayersShipModule extends AbstractModule {

	private Unit currentUnit;

	public PlayersShipModule(Main main) {
		super(main);
	}


	@Override
	public void init() {
		if (this.currentUnit == null) {
			this.selectUnit(0);
		}
	}


	@Override
	public void updateGame() {
		// Go through map
		for (int y=0 ; y<main.gameData.getHeight() ; y++) {
			for (int x=0 ; x<main.gameData.getWidth() ; x++) {
				AbstractMapSquare sq = main.gameData.map[x][y];
				sq.process();
			}			
		}
		
		for(AbstractEvent ev : this.currentEvents) {
			ev.process();
		}
	}
	
	
	private void selectUnit(int i) {
		currentUnit = main.gameData.units.get(i);
		main.addMsg("You are in control of " + this.currentUnit.getName());

	}


	@Override
	public void drawScreen(IGameView view) throws IOException {
		view.drawPlayersShipScreen(main.gameData, this.currentUnit);

	}


	/**
	 * Returns whether the game should progress a turn
	 */
	public boolean processInput(KeyStroke ks) {
		if (ks.getKeyType() == KeyType.ArrowUp) {
			this.currentUnit.attemptMove(0, -1);
		} else if (ks.getKeyType() == KeyType.ArrowDown) {
			this.currentUnit.attemptMove(0, 1);
		} else if (ks.getKeyType() == KeyType.ArrowLeft) {
			this.currentUnit.attemptMove(-1, 0);
		} else if (ks.getKeyType() == KeyType.ArrowRight) {
			this.currentUnit.attemptMove(1, 0);
		} else {
			char c = ks.getCharacter();
			switch (c) {
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				int i = Integer.parseInt(c+"");
				this.selectUnit(i-1);
				break;

			case 'n':
			{
				/*int pos = this.gameData.units.indexOf(this.currentUnit);
			if (pos >= this.gameData.units.size()) {
				this.currentUnit = this.gameData.units.get(0);
			} else {
				this.currentUnit = this.gameData.units.get(pos+1);
			}
			return false;*/
				main.addMsg("You do nothing");
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
				return true;

			case 'a':
				//this.currentUnit.attemptMove(-1, 0);
				return true;

			case 's':
				//this.currentUnit.attemptMove(0, 1);
				return true;

			case 'd':
				//this.currentUnit.attemptMove(1, 0);
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
				main.addMsg("Unknown key " + c);
			}
		}
		return false;
	}



}
