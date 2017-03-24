package com.scs.astrocommander;

import com.scs.astrocommander.destinations.AbstractSpaceLocation;

public interface IStarmapData {

	public AbstractSpaceLocation getRandomLocation(AbstractSpaceLocation except); 
}
