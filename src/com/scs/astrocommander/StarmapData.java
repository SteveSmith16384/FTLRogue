package com.scs.astrocommander;

import com.scs.astrocommander.destinations.AbstractSpaceLocation;
import com.scs.astrocommander.destinations.Planet;


public class StarmapData implements IStarmapData {

	private static final int NUM_PLANETS = 10;
	/*private static final int MAX_WIDTH = 100;
	private static final int MAX_HEIGHT = 100;

	public Point playerCoords = new Point();
	 */

	private Main main;
	public AbstractSpaceLocation[] locations = new AbstractSpaceLocation[NUM_PLANETS];


	public StarmapData(Main _main) {
		main = _main;
	}


	public StarmapData() {
		for (int i=0 ; i<NUM_PLANETS ; i++) {
			locations[i] = new Planet(main, "Planet " + (i+1));
		}
	}


	@Override
	public AbstractSpaceLocation getRandomLocation(AbstractSpaceLocation except) {
		while (true) {
			int i = Main.RND.nextInt(NUM_PLANETS);
			AbstractSpaceLocation asl = this.locations[i];
			if (asl != except) {
				return asl;
			}
		}
	}

}
