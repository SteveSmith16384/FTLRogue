package com.scs.astrocommander.entities.items;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.Settings;
import com.scs.astrocommander.entities.mobs.Alien;
import com.scs.rogueframework.ecs.components.ICarryable;
import com.scs.rogueframework.ecs.components.IExamineable;
import com.scs.rogueframework.ecs.components.IIllegal;
import com.scs.rogueframework.ecs.entities.AbstractItem;

public class AlienEgg extends AbstractItem implements ICarryable, IExamineable, IIllegal {

	private static final int PCENT_CHANCE_HATCH = 10;

	private int minHatchTurn;

	public AlienEgg(Main main) {
		super(main, -1, -1);

		minHatchTurn = main.gameData.turnNo + 10;
	}


	@Override
	public char getChar() {
		return 'e';
	}


	@Override
	public void process() {
		if (minHatchTurn <  main.gameData.turnNo) {
			if (Main.RND.nextInt(100) <= PCENT_CHANCE_HATCH) {
				Alien a = new Alien(main, this.x, this.y);
				this.getSq().addEntity(a);
				this.remove();

				if (Settings.DEBUG || this.seenByPlayer()) {
					main.addMsg(3, "THE EGG HAS HATCHED!");
					main.sfx.playSound("horrorambience.mp3");

				}
			}
		}
	}


	@Override
	public String getName() {
		return "Egg";
	}


	@Override
	public String getExamineText() {
		return "It is large and green.";
	}


	@Override
	public int getIllegality() {
		return 1;
	}

}
