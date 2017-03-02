package com.scs.ftl2d.modules.consoles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.scs.ftl2d.IGameView;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.AbstractModule;

public abstract class AbstractConsoleModule extends AbstractModule {

	private static final int MAX_LINES = 10;
	
	private List<String> lines = new ArrayList<>();
	
	public AbstractConsoleModule(Main main) {
		super(main);
	}


	@Override
	public void updateGame() {
		
	}

	
	@Override
	public void drawScreen(IGameView view) throws IOException {
		view.drawConsoleScreen(lines);
	}
	
	
	public void addLine(String s) {
		this.lines.add(s);
		while (lines.size() > MAX_LINES) {
			lines.remove(0);
		}
	}

	
	public void clearLines() {
		this.lines.clear();
	}
	


}
