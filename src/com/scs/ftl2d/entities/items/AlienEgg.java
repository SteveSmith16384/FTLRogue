package com.scs.ftl2d.entities.items;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.Settings;
import com.scs.ftl2d.entities.DrawableEntity;
import com.scs.ftl2d.entities.mobs.Alien;

public class AlienEgg extends AbstractItem {

	private static final int PCENT_CHANCE_HATCH = 1;

	public AlienEgg(Main main) {
		super(main, -1, -1, DrawableEntity.Z_ITEM);

	}


	@Override
	public char getChar() {
		return 'e';
	}

	@Override
	public void process(int pass) {
		if (pass == 2) {
			if (Main.RND.nextInt(100) <= PCENT_CHANCE_HATCH) {
				Alien a = new Alien(main, this.x, this.y);
				this.getSq().addEntity(a);
				this.remove();
				
				if (Settings.DEBUG || this.seenByPlayer()) {
					main.addMsg("THE EGG HAS HATCHED!");
				}
			}
		}
	}


	@Override
	public String getName() {
		return "Egg";
	}

}
