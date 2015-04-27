package com.wapsadvert.kernel.application;

import com.andframe.activity.AfActivity;
import com.andframe.activity.AfMainActivity;
import com.andframe.application.AfApplication;
import com.attract.application.AttractApplication;
import com.wapsadvert.kernel.activity.WapsMainActivity;

public class WapsApplication extends AfApplication{

	@Override
	public boolean isDebug() {
		// TODO Auto-generated method stub
		return true;//*/BuildConfig.DEBUG;
	}
	
	@Override
	public Class<? extends AfMainActivity> getForegroundClass() {
		// TODO Auto-generated method stub
		return WapsMainActivity.class;
	}
	
//	@Override
//	protected AdvertAdapter newAdvertAdapter() {
//		// TODO Auto-generated method stub
//		return new WapsAdapter();
//	}
	
	@Override
	public synchronized void setCurActivity(Object power, AfActivity activity) {
		// TODO Auto-generated method stub
		super.setCurActivity(power, activity);
		AttractApplication.getApp().setCurActivity(power, activity);
	}
	
//	@Override
//	public void notifyBusinessModelStart(OnlineDeploy deploy) {
//		// TODO Auto-generated method stub
//		super.notifyBusinessModelStart(deploy);
//		if (!AdvertAdapter.getInstance().getChannel().equals("waps")
//				&& !AppAboutActivity.isAwardpoint()) {
//			WebConfig wconfig = new WebConfig();
//			if (deploy != null) {
//				wconfig.Name = deploy.Name;
//				wconfig.Urls = deploy.Urls;
//			}
//			AttractApplication.getApp().notifyBusinessModelStart(wconfig);
//		}
//	}
	
	@Override
	public synchronized void notifyForegroundClosed(AfMainActivity activity) {
		// TODO Auto-generated method stub
		super.notifyForegroundClosed(activity);
		AttractApplication.getApp().notifyForegroundClosed();
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			AttractApplication.initialize(new WapsAttractCongfig());
		} catch (Throwable e) {
			// TODO: handle exception
		}
	}
	
//	@Override
//	public AfExceptionHandler getExceptionHandler() {
//		// TODO Auto-generated method stub
//		return new WapsExceptionHandler();
//	}
}
