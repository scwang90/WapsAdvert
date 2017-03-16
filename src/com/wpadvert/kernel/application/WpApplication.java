package com.wpadvert.kernel.application;

public class WpApplication /*extends AfApplication*/{

//	@Override
//	public boolean isDebug() {
//		return true;//*/BuildConfig.DEBUG;
//	}
//
//	@Override
//	public Class<? extends AfMainActivity> getForegroundClass() {
//		return WpMainActivity.class;
//	}
//
////	@Override
////	protected AdvertAdapter newAdvertAdapter() {
////		return new WapsAdapter();
////	}
//
//	@Override
//	public synchronized void setCurActivity(Object power, AfActivity activity) {
//		super.setCurActivity(power, activity);
//		AttractApplication.getApp().setCurActivity(power, activity);
//	}
//
////	@Override
////	public void notifyBusinessModelStart(OnlineDeploy deploy) {
////		super.notifyBusinessModelStart(deploy);
////		if (!AdvertAdapter.getInstance().getChannel().equals("waps")
////				&& !AppAboutActivity.isAwardpoint()) {
////			WebConfig wconfig = new WebConfig();
////			if (deploy != null) {
////				wconfig.Name = deploy.Name;
////				wconfig.Urls = deploy.Urls;
////			}
////			AttractApplication.getApp().notifyBusinessModelStart(wconfig);
////		}
////	}
//
//	@Override
//	public synchronized void notifyForegroundClosed(AfActivity activity) {
//		super.notifyForegroundClosed(activity);
//		AttractApplication.getApp().notifyForegroundClosed();
//}
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//		try {
//			AttractApplication.initialize(new WpAttractApp());
//		} catch (Throwable e) {
//		}
//	}
//
////	@Override
////	public AfExceptionHandler getExceptionHandler() {
////		return new WapsExceptionHandler();
////	}
}
