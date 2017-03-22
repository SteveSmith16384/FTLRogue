package com.scs.ftl2d.destinations;

import java.util.List;

import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.consoles.AbstractConsoleModule;

public class EmptyHulk extends AbstractAnotherShip {

	public EmptyHulk(Main main) {
		super(main, "Space hulk");

	}

	@Override
	public void process() {
		
	}

	@Override
	public void shotByPlayer() {
		
	}

	@Override
	public List<String> getStats() {
		return null;
	}

	
	@Override
	public void getHailResponse(AbstractConsoleModule console) {
		console.addLine("[There is no response]");
	}

	@Override
	public void processCommand(String cmd, AbstractConsoleModule console) {
	}

}
