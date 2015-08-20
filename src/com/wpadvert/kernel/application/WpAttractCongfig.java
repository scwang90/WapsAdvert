package com.wpadvert.kernel.application;

import android.content.Context;

import com.andadvert.AdvertAdapter;
import com.andadvert.PointStatistics;
import com.attract.application.AdvAttractAdapter;
import com.attract.application.AttractApplication;

public class WpAttractCongfig extends AttractApplication{

	@Override
	public void onDestroyAdvert(Context context) {
		super.onDestroyAdvert(context);
		AdvertAdapter.getInstance().uninstallAd(context);
	}
	
	@Override
	public void onInitializeAdvert(Context context) {
		super.onInitializeAdvert(context);
		PointStatistics.stop();
		AdvertAdapter.getInstance().initInstance(context);
	}
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
