package com.wpadvert.kernel;

import java.util.ArrayList;
import java.util.List;

import com.andadvert.model.AdCustom;
import com.andframe.application.AfApplication;
import com.andframe.caches.AfPrivateCaches;
import com.attract.kernel.AttractStatistics;
import com.wpadvert.kernel.event.WapsEvent;

public class PointKernelAttract{

//	//用户的积分
//	private static int mPoints = 0;
//	//等待下载安装的 app
//	private static List<AdInfo> mltAdInfo = new ArrayList<AdInfo>();
//	//已经下载安装的 app
//	private static List<AdInfo> mltInstalled = new ArrayList<AdInfo>();
//
	private static final String KEY_DOWNLOAD = "95976004134032214102";
	private static final String KEY_CACHE = "52883484512041114102";
//	private static final String KEY_POINT = "75282482634041114102";
//	private static final String KEY_LIST_ADINFO = "00678600734041114102";
//	private static final String KEY_LIST_INSTALL = "96534570734041114102";
//	//定时器周期10秒钟
//	private static final long KEY_PERIOD = 60000;
//	
	//缓存器
	private static AfPrivateCaches mCache = AfPrivateCaches.getInstance(KEY_CACHE);

	private static PointKernel kernel = new PointKernel(KEY_CACHE){
		@Override
		protected void doNotifyPointAttract(int accretion, int points) {
			super.doNotifyPointAttract(accretion, points);
			AttractStatistics.doStaticsPoint(points);
			AfApplication.getApp().onEvent(WapsEvent.WAPS_POINT_ATTRACT,""+points);
		}

		@Override
		protected void doNotifyPointCheat(int point, int cheat) {
			super.doNotifyPointCheat(point, cheat);
			AfApplication.getApp().onEvent(WapsEvent.WAPS_CHEAT_ATTRACT,""+point);
		}
	};

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
//		// TODO Auto-generated method stub
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
//		mCache.put(KEY_DOWNLOAD, true);
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
//					// TODO Auto-generated method stub
//					doCheckAttractPoint();
//					if (AfTimeSpan.FromDate(mTime, new Date()).GreaterThan(mSpan)) {
//						doStopTimer();
//					}
//				}
//			}, KEY_PERIOD, KEY_PERIOD);
//		}
//	}
//	
//	protected static void doCheckAttractPoint() {
//		// TODO Auto-generated method stub
//		String service = Context.ACTIVITY_SERVICE;
//		AfApplication app = AfApplication.getApp();
//		ActivityManager am = (ActivityManager) app.getSystemService(service);
//		List<RunningAppProcessInfo> proces = am.getRunningAppProcesses();
//		List<AdInfo> ltInstalled = new ArrayList<PointKernelAttract.AdInfo>();
//		for (AdInfo adinfo : mltAdInfo) {
//			for (RunningAppProcessInfo proce : proces) {
//				if (proce.processName.equals(adinfo.mAdInfo.Package)) {
//					if(AfApplication.getNetworkStatus() == AfNetwork.TYPE_NONE){
//						String currency = AdvAttractAdapter.getInstance().getCurrency();
//						String msg = "请先联网再打开下载软件才能送"+currency;
//						Toast.makeText(AfApplication.getApp(), msg, Toast.LENGTH_LONG).show();
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
//		// TODO Auto-generated method stub
//		mCache.put(KEY_POINT, mPoints);
//		//mCache.putList(KEY_LIST_ADINFO, mltAdInfo, AdInfo.class);
//		mCache.putList(KEY_LIST_INSTALL, mltInstalled, AdInfo.class);
//	}
//
//	protected static void doReadCache() {
//		// TODO Auto-generated method stub
//		mPoints = mCache.getInt(KEY_POINT, mPoints);
//		mltAdInfo = mCache.getList(KEY_LIST_ADINFO, AdInfo.class);
//		mltInstalled = mCache.getList(KEY_LIST_INSTALL, AdInfo.class);
//	}
//	
//	protected static void doDivertInstalled(AdInfo adinfo) {
//		// TODO Auto-generated method stub
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
//		// TODO Auto-generated method stub
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
//		// TODO Auto-generated method stub
//		return mPoints;
//	}
//
//	public static int getComPoint() {
//		// TODO Auto-generated method stub
//		int point = mPoints;
//		for (AdInfo ad : mltAdInfo) {
//			point += ad.mAdInfo.Points;
//		}
//		return point;
//	}
//	
//
//	public static int spendPoints(int spend) {
//		// TODO Auto-generated method stub
//		mPoints = mPoints - spend;
//		mCache.put(KEY_POINT, mPoints);
//		return mPoints;
//	}
//
//	public static int awardPoints(int award) {
//		// TODO Auto-generated method stub
//		mPoints = mPoints + award;
//		mCache.put(KEY_POINT, mPoints);
//		return mPoints;
//	}
	
	public static void doStatisticsAdInfo(AdCustom info) {
		boolean result = kernel.doStatisticsAdInfo(info);
		if (result) {
			mCache.put(KEY_DOWNLOAD, true);
		}
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
	/**
	 * 获取预计点数（已经有的点数+正在监听的点数）
	 * @return
	 */
	public static int getComPoint() {
		// TODO Auto-generated method stub
		return kernel.getComPoint();
	}
	/**
	 * @return 是否已经下载监听过
	 */
	public static boolean isDownloaded() {
		// TODO Auto-generated method stub
		return mCache.getBoolean(KEY_DOWNLOAD, false);
	}
	/**
	 * 在list获取一个新（没有监听和安装）的 AdCustom
	 * @param list
	 * @return AdCustom or null
	 */
	public static AdCustom getNewAdCustom(List<AdCustom> list) {
		// TODO Auto-generated method stub
		list = new ArrayList<AdCustom>(list);
		for (int i = 0; i < list.size(); i++) {
			AdCustom info = list.get(i);
			if (kernel.hasMonitored(info)
				|| kernel.hasInstalled(info)) {
				list.remove(i--);
			}
		}
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
