package com.scs.rogueframework;

public class LogMessage {

	public int priority;
	public String msg;
	
	public LogMessage(int _pri, String _msg) {
		super();
		
		this.priority = _pri;
		this.msg = _msg;
	}

}
