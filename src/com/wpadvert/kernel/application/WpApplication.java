package com.wpadvert.kernel.application;

import com.andframe.activity.AfMainActivity;
import com.andframe.activity.framework.AfActivity;
import com.andframe.application.AfApplication;
import com.attract.application.AttractApplication;
import com.wpadvert.kernel.activity.WpMainActivity;

public class WpApplication extends AfApplication{

	@Override
	public boolean isDebug() {
		// TODO Auto-generated method stub
		return true;//*/BuildConfig.DEBUG;
	}
	
	@Override
	public Class<? extends AfMainActivity> getForegroundClass() {
		// TODO Auto-generated method stub
		return WpMainActivity.class;
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
	public synchronized void notifyForegroundClosed(AfActivity activity) {
		// TODO Auto-generated method stub
		super.notifyForegroundClosed(activity);
		AttractApplication.getApp().notifyForegroundClosed();
}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		try {
			AttractApplication.initialize(new WpAttractCongfig());
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
