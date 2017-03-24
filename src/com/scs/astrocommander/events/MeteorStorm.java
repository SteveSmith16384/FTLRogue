package com.scs.astrocommander.events;

import java.awt.Point;

import com.scs.astrocommander.Main;
import com.scs.astrocommander.asciieffects.Meteor;

public class MeteorStorm extends AbstractEvent {
	
	//private static final int TOTAL
	private float size = 100;
	private Point dir = new Point();

	public MeteorStorm(Main main) {
		super(main);
		
		while (dir.x == 0 || dir.y == 0) { // Always diagonal
			dir = new Point(Main.RND.nextInt(3)-1,Main.RND.nextInt(3)-1); 
		}
		
		main.addMsg("Alert!  Meteor Storm!");
	}
	

	@Override
	public void process() {
		size -= main.gameData.shipSpeed;
		
		if (size <= 0) {
			super.remove();
			main.addMsg("You have successfully made it through the meteor storm");
		} else {
			int i = Main.RND.nextInt(10);
			if (i == 0) {
				new Meteor(main, dir);
			}
		}
		
	}

}
