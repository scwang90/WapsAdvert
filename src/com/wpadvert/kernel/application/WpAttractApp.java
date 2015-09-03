package com.wpadvert.kernel.application;

import com.attract.application.AdvAttractAdapter;
import com.attract.application.AttractApplication;

public class WpAttractApp extends AttractApplication{

	@Override
	public void sendNotiftyMail(String title,String content) {
		//new NotiftyMail(title, content).sendTask();
	}
	@Override
	public void sendNotiftyMail(boolean sendonce, String title,
			String content) {
		//new NotiftyMail(sendonce, title, content).sendTask();
	}
	
	@Override
	protected AdvAttractAdapter newAdvAttractAdapter() {
		return new WpAttractAdapter();
	}
	
}
