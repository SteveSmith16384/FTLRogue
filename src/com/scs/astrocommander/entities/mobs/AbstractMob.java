package com.scs.astrocommander.entities.mobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.map.CSVMap;
import com.scs.rogueframework.AbstractRoguelike;
import com.scs.rogueframework.ecs.components.IWearable;
import com.scs.rogueframework.ecs.entities.FrameworkMob;

public abstract class AbstractMob extends FrameworkMob { // todo - rename to AbstractFTLMob

	public enum Status {Waiting, Moving, Eating, Repairing, Healing}

	// Sides
	public static final int SIDE_PLAYER = 0;
	public static final int SIDE_ALIEN = 1;
	public static final int SIDE_ENEMY_SHIP = 2;
	public static final int SIDE_POLICE = 3;

	public IWearable wearing;
	public int unconciouseTimer = 0;

	public AbstractMob(AbstractRoguelike main, int _x, int _y, int _z, char c, String _name, int _side, float hlth, int _combat, boolean _autoOpenDoors) {
		super(main, _x, _y, _z, c, _name, _side, hlth, _combat, _autoOpenDoors);

		AllMobs.add(this);
	}


	@Override
	public void process() {
		if (this.unconciouseTimer > 0) {
			this.unconciouseTimer--;
		}
	}


	@Override
	public char getChar() {
		return theChar;
	}


	public static String GetRandomName() {
		//try {
		//Path path = FileSystems.getDefault().getPath("./data/", "names.txt");
		//List<String> lines = Files.readAllLines(path);
		Scanner scanner = new Scanner(CSVMap.class.getResourceAsStream("/data/names.txt"), "UTF-8");
		scanner.useDelimiter("\\A");
		String text = scanner.next();
		scanner.close();
		
		List<String> lines = Arrays.asList(text.split("\n"));
		int i = Main.RND.nextInt(lines.size());
		return "Captain " + lines.get(i).trim();
		/*} catch (IOException ex) {
			ex.printStackTrace();
			return "Mr Bum.";
		}*/
	}



}

