package com.scs.ftl2d.modules.consoles;

import com.googlecode.lanterna.input.KeyStroke;
import com.scs.ftl2d.Main;
import com.scs.ftl2d.modules.AbstractModule;

public class HelpConsole extends AbstractConsoleModule {

	public HelpConsole(Main main, AbstractModule prev) {
		super(main, prev);

		this.addLine("HELP");
		this.addLine("Todo");
	}

	
	@Override
	public boolean processInput(KeyStroke ks) {
		// TODO Auto-generated method stub
		return false;
	}
}
