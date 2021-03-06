package com.wpadvert.kernel;

import android.content.Context;

import com.andadvert.AdvertAdapter;
import com.andadvert.PointStatistics;
import com.andadvert.model.AdCustom;
import com.andframe.$;
import com.andframe.application.AfApp;
import com.wpadvert.kernel.event.WpEvent;

public class PointKernelMain {
	
	static PointKernel kernel = new PointKernel("93089433021020214102"){
		@Override
		protected void doNotifyPointIncrease(int accretion, int points) {
			super.doNotifyPointIncrease(accretion, points);
			Context context = AfApp.get();
			String currency = AdvertAdapter.getInstance().getCurrency();
			final int point = PointStatistics.getPoint(context);
			PointStatistics.doStaticsPoint(context, points,currency);
			$.event().post(new WpEvent(WpEvent.WP_POINT_MAIN, point + "+" + (points - point) + "=" + points));
		}

		@Override
		protected void doNotifyPointCheat(int point, int cheat) {
			super.doNotifyPointCheat(point, cheat);
			$.event().post(new WpEvent(WpEvent.WP_CHEAT_MAIN, "" + point));
		}
	};

	public static final int DEFAULE_POINT = PointKernel.DEFAULE_POINT;

	public static boolean doStatisticsAdInfo(AdCustom info) {
		return kernel.doStatisticsAdInfo(info);
	}

	public static int getPoint() {
		return kernel.getPoint();
	}

	public static int spendPoints(int spend) {
		return kernel.spendPoints(spend);
	}

	public static int awardPoints(int award) {
		return kernel.awardPoints(award);
	}
	
//	public static class AdInfo{
//		public Date mDate;
//		public AdCustom mAdInfo;
//		public AdInfo(AdCustom ad) {
//			this.mAdInfo = ad;
//			this.resetTime();
//		}
//		public void resetTime() {
//			this.mDate = mTime = new Date();
//		}
//		@Override
//		public boolean equals(Object o) {
//			if (o instanceof AdInfo) {
//				AdInfo info = AdInfo.class.cast(o);
//				return mAdInfo.Package.equals(info.mAdInfo.Package);
//			}else if (o instanceof AdCustom) {
//				AdCustom info = AdCustom.class.cast(o);
//				return mAdInfo.Package.equals(info.Package);
//			}
//			return super.equals(o);
//		}
//	}
//	public static final int DEFAULE_POINT = -1;
//	//用户的积分
//	private static int mPoints = DEFAULE_POINT;
//	//等待下载安装的 app
//	private static List<AdInfo> mltAdInfo = new ArrayList<AdInfo>();
//	//已经下载安装的 app
//	private static List<AdInfo> mltInstalled = new ArrayList<AdInfo>();
//	
//	private static final String KEY_CACHE = "93089433021020214102";
//	private static final String KEY_POINT = "91259234021020214102";
//	private static final String KEY_LIST_ADINFO = "15859874021020214102";
//	private static final String KEY_LIST_INSTALL = "27893935021020214102";
//	//定时器周期10秒钟
//	private static final long KEY_PERIOD = 60000;
//	
//	//缓存器
//	private static Cacher mCache = $.cache(KEY_CACHE);
//
//	//点数获取定时器
//	private static Timer mTimer = null;
//	private static Date mTime = new Date();
//	private static AfTimeSpan mSpan = AfTimeSpan.FromHours(1);
//	
//	static{
//		doReadCache();
//		if (mltAdInfo.size() > 0) {
//			doStartTimer();
//		}
//	}
//	
//	public static void doStatisticsAdInfo(AdCustom info) {
//		for (AdInfo element : mltInstalled) {
//			if (element.equals(info)) {
//				return ;
//			}
//		}
//		for (AdInfo element : mltAdInfo) {
//			if (element.equals(info)) {
//				element.resetTime();
//				return ;
//			}
//		}
//		mltAdInfo.add(new AdInfo(info));
//		doUpdateCache();//更新缓存
//		doStartTimer();
//	}
//	
//	public static void doStartTimer() {
//		if (mTimer == null) {
//			mTime = new Date();
//			mTimer = new Timer();
//			mTimer.schedule(new AfTimerTask() {
//				@Override
//				protected void onTimer() {
//					doCheckPoint();
//					if (AfTimeSpan.FromDate(mTime, new Date()).GreaterThan(mSpan)) {
//						doStopTimer();
//					}
//				}
//			}, KEY_PERIOD, KEY_PERIOD);
//		}
//	}
//	
//	protected static void doCheckPoint() {
//		String service = Context.ACTIVITY_SERVICE;
//		AfApplication app = AfApp.get();
//		ActivityManager am = (ActivityManager) app.getSystemService(service);
//		List<RunningAppProcessInfo> proces = am.getRunningAppProcesses();
//		List<AdInfo> ltInstalled = new ArrayList<PointKernelMain.AdInfo>();
//		for (AdInfo adinfo : mltAdInfo) {
//			for (RunningAppProcessInfo proce : proces) {
//				if (proce.processName.equals(adinfo.mAdInfo.Package)) {
//					if(AfApplication.getNetworkStatus() == AfNetwork.TYPE_NONE){
//						String currency = AdvertAdapter.getInstance().getCurrency();
//						String msg = "请确保连接到互联网再运行安装软件才可获得"+currency;
//						Toast.makeText(AfApp.get(), msg, Toast.LENGTH_LONG).show();
//						AfExceptionHandler.handleAttach(new Exception(msg), msg);
//						return;
//					}
//					doDivertInstalled(adinfo);
//					ltInstalled.add(adinfo);
//					break;
//				}
//			}
//		}
//		for (AdInfo info : ltInstalled) {
//			mltAdInfo.remove(info);
//		}
//		doUpdateCache();//更新缓存
//	}
//
//	protected static void doUpdateCache() {
//		mCache.put(KEY_POINT, mPoints);
//		mCache.putList(KEY_LIST_ADINFO, mltAdInfo, AdInfo.class);
//		mCache.putList(KEY_LIST_INSTALL, mltInstalled, AdInfo.class);
//	}
//
//	protected static void doReadCache() {
//		mPoints = mCache.getInt(KEY_POINT, mPoints);
//		mltAdInfo = mCache.getList(KEY_LIST_ADINFO, AdInfo.class);
//		mltInstalled = mCache.getList(KEY_LIST_INSTALL, AdInfo.class);
//	}
//	
//	protected static void doDivertInstalled(AdInfo adinfo) {
//		for (AdInfo element : mltInstalled) {
//			if (element.equals(adinfo)) {
//				return ;
//			}
//		}
//		adinfo.mDate = new Date();
//		mltInstalled.add(adinfo);
//		mPoints = mPoints += adinfo.mAdInfo.Points;
//		doNotifyAttractPoint(adinfo.mAdInfo.Points,mPoints);
//	}
//
//	private static void doNotifyAttractPoint(int accretion, int points) {
//		
//	}
//
//	public static void doStopTimer() {
//		if (mTimer != null) {
//			mTimer.cancel();
//			mTimer = null;
//		}
//	}
//
//	public static int getPoint() {
//		return mPoints;
//	}
//
//	public static int spendPoints(int spend) {
//		mPoints = mPoints - spend;
//		mCache.put(KEY_POINT, mPoints);
//		return mPoints;
//	}
//
//	public static int awardPoints(int award) {
//		mPoints = mPoints + award;
//		mCache.put(KEY_POINT, mPoints);
//		return mPoints;
//	}
}
