package com.wpadvert.kernel;

public class PointKernelAttract{
//
////	//用户的积分
////	private static int mPoints = 0;
////	//等待下载安装的 app
////	private static List<AdInfo> mltAdInfo = new ArrayList<AdInfo>();
////	//已经下载安装的 app
////	private static List<AdInfo> mltInstalled = new ArrayList<AdInfo>();
////
//	private static final String KEY_DOWNLOAD = "95976004134032214102";
//	private static final String KEY_CACHE = "52883484512041114102";
//
//	public static final int DEFAULE_POINT = PointKernel.DEFAULE_POINT;
//	//	private static final String KEY_POINT = "75282482634041114102";
////	private static final String KEY_LIST_ADINFO = "00678600734041114102";
////	private static final String KEY_LIST_INSTALL = "96534570734041114102";
////	//定时器周期10秒钟
////	private static final long KEY_PERIOD = 60000;
////
//	//缓存器
//	private static Cacher mCache = $.cache(KEY_CACHE);
//
//	private static PointKernel kernel = new PointKernel(KEY_CACHE){
//		@Override
//		protected void doNotifyPointIncrease(int accretion, int points) {
//			super.doNotifyPointIncrease(accretion, points);
//			AttractStatistics.doStaticsPoint(points);
//			$.event().post(new WpEvent(WpEvent.WP_POINT_ATTRACT, "" + points));
//		}
//
//		@Override
//		protected void doNotifyPointCheat(int point, int cheat) {
//			super.doNotifyPointCheat(point, cheat);
//			$.event().post(new WpEvent(WpEvent.WP_CHEAT_ATTRACT, "" + points));
//		}
//	};
//
////
////	//点数获取定时器
////	private static Timer mTimer = null;
////	private static Date mTime = new Date();
////	private static AfTimeSpan mSpan = AfTimeSpan.FromHours(1);
////
////	static{
////		doReadCache();
////		if (mltAdInfo.size() > 0) {
////			doStartTimer();
////		}
////	}
////
////	public static void doStatisticsAdInfo(AdCustom info) {
////		for (AdInfo element : mltInstalled) {
////			if (element.equals(info)) {
////				return ;
////			}
////		}
////		for (AdInfo element : mltAdInfo) {
////			if (element.equals(info)) {
////				element.resetTime();
////				return ;
////			}
////		}
////		mCache.put(KEY_DOWNLOAD, true);
////		mltAdInfo.add(new AdInfo(info));
////		doUpdateCache();//更新缓存
////		doStartTimer();
////	}
////
////	public static void doStartTimer() {
////		if (mTimer == null) {
////			mTime = new Date();
////			mTimer = new Timer();
////			mTimer.schedule(new AfTimerTask() {
////				@Override
////				protected void onTimer() {
////					doCheckPoint();
////					if (AfTimeSpan.FromDate(mTime, new Date()).GreaterThan(mSpan)) {
////						doStopTimer();
////					}
////				}
////			}, KEY_PERIOD, KEY_PERIOD);
////		}
////	}
////
////	protected static void doCheckPoint() {
////		String service = Context.ACTIVITY_SERVICE;
////		AfApplication app = AfApp.get();
////		ActivityManager am = (ActivityManager) app.getSystemService(service);
////		List<RunningAppProcessInfo> proces = am.getRunningAppProcesses();
////		List<AdInfo> ltInstalled = new ArrayList<PointKernelAttract.AdInfo>();
////		for (AdInfo adinfo : mltAdInfo) {
////			for (RunningAppProcessInfo proce : proces) {
////				if (proce.processName.equals(adinfo.mAdInfo.Package)) {
////					if(AfApplication.getNetworkStatus() == AfNetwork.TYPE_NONE){
////						String currency = AdvAttractAdapter.getInstance().getCurrency();
////						String msg = "请先联网再打开下载软件才能送"+currency;
////						Toast.makeText(AfApp.get(), msg, Toast.LENGTH_LONG).show();
////						AfExceptionHandler.handleAttach(new Exception(msg), msg);
////						return;
////					}
////					doDivertInstalled(adinfo);
////					ltInstalled.add(adinfo);
////					break;
////				}
////			}
////		}
////		for (AdInfo info : ltInstalled) {
////			mltAdInfo.remove(info);
////		}
////		doUpdateCache();//更新缓存
////	}
////
////	protected static void doUpdateCache() {
////		mCache.put(KEY_POINT, mPoints);
////		//mCache.putList(KEY_LIST_ADINFO, mltAdInfo, AdInfo.class);
////		mCache.putList(KEY_LIST_INSTALL, mltInstalled, AdInfo.class);
////	}
////
////	protected static void doReadCache() {
////		mPoints = mCache.getInt(KEY_POINT, mPoints);
////		mltAdInfo = mCache.getList(KEY_LIST_ADINFO, AdInfo.class);
////		mltInstalled = mCache.getList(KEY_LIST_INSTALL, AdInfo.class);
////	}
////
////	protected static void doDivertInstalled(AdInfo adinfo) {
////		for (AdInfo element : mltInstalled) {
////			if (element.equals(adinfo)) {
////				return ;
////			}
////		}
////		adinfo.mDate = new Date();
////		mltInstalled.add(adinfo);
////		mPoints = mPoints += adinfo.mAdInfo.Points;
////		doNotifyAttractPoint(adinfo.mAdInfo.Points,mPoints);
////	}
////
////	private static void doNotifyAttractPoint(int accretion, int points) {
////
////	}
////
////	public static void doStopTimer() {
////		if (mTimer != null) {
////			mTimer.cancel();
////			mTimer = null;
////		}
////	}
////
////	public static int getPoint() {
////		return mPoints;
////	}
////
////	public static int getComPoint() {
////		int point = mPoints;
////		for (AdInfo ad : mltAdInfo) {
////			point += ad.mAdInfo.Points;
////		}
////		return point;
////	}
////
////
////	public static int spendPoints(int spend) {
////		mPoints = mPoints - spend;
////		mCache.put(KEY_POINT, mPoints);
////		return mPoints;
////	}
////
////	public static int awardPoints(int award) {
////		mPoints = mPoints + award;
////		mCache.put(KEY_POINT, mPoints);
////		return mPoints;
////	}
//
//	public static void doStatisticsAdInfo(AdCustom info) {
//		boolean result = kernel.doStatisticsAdInfo(info);
//		if (result) {
//			mCache.put(KEY_DOWNLOAD, true);
//		}
//	}
//
//	public static int getPoint() {
//		return kernel.getPoint();
//	}
//
//	public static int spendPoints(int spend) {
//		return kernel.spendPoints(spend);
//	}
//
//	public static int awardPoints(int award) {
//		return kernel.awardPoints(award);
//	}
//	/**
//	 * 获取预计点数（已经有的点数+正在监听的点数）
//	 * @return
//	 */
//	public static int getComPoint() {
//		return kernel.getComPoint();
//	}
//	/**
//	 * @return 是否已经下载监听过
//	 */
//	public static boolean isDownloaded() {
//		return mCache.getBoolean(KEY_DOWNLOAD, false);
//	}
//	/**
//	 * 在list获取一个新（没有监听和安装）的 AdCustom
//	 * @param list
//	 * @return AdCustom or null
//	 */
//	public static AdCustom getNewAdCustom(List<AdCustom> list) {
//		list = new ArrayList<AdCustom>(list);
//		for (int i = 0; i < list.size(); i++) {
//			AdCustom info = list.get(i);
//			if (kernel.hasMonitored(info)
//				|| kernel.hasInstalled(info)) {
//				list.remove(i--);
//			}
//		}
//		if (list.size() > 0) {
//			return list.get(0);
//		}
//		return null;
//	}

}
