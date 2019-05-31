package com.scs.rogueframework;

import java.util.ArrayList;
import java.util.List;

import com.scs.astrocommander.entities.mobs.PlayersUnit;

public abstract class AbstractGameData {

	public List<PlayersUnit> players_units = new ArrayList<>();
	public List<LogMessage> msgs = new ArrayList<>();

	public AbstractGameData() {

	}

}
