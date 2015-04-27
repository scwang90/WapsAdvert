package com.wapsadvert.kernel.application;

import android.content.Context;

import com.andadvert.AdvertAdapter;
import com.andadvert.PointStatistics;
import com.andmail.NotiftyMail;
import com.attract.application.AdvAttractAdapter;
import com.attract.application.AttractApplication;

public class WapsAttractCongfig extends AttractApplication{

	@Override
	public void onDestroyAdvert(Context context) {
		// TODO Auto-generated method stub
		super.onDestroyAdvert(context);
		AdvertAdapter.getInstance().uninstallAd(context);
	}
	
	@Override
	public void onInitializeAdvert(Context context) {
		// TODO Auto-generated method stub
		super.onInitializeAdvert(context);
		PointStatistics.stop();
		AdvertAdapter.getInstance().initInstance(context);
	}
	@Override
	public void sendNotiftyMail(String title,String content) {
		// TODO Auto-generated method stub
		new NotiftyMail(title, content).sendTask();
	}
	@Override
	public void sendNotiftyMail(boolean sendonce, String title,
			String content) {
		// TODO Auto-generated method stub
		new NotiftyMail(sendonce, title, content).sendTask();
	}
	
	@Override
	protected AdvAttractAdapter newAdvAttractAdapter() {
		// TODO Auto-generated method stub
		return new WapsAttractAdapter();
	}
	
}
