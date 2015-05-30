package com.wapsadvert.kernel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.widget.Toast;

import com.andadvert.model.AdCustom;
import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.helper.java.AfTimeSpan;
import com.andframe.thread.AfTimerTask;
import com.andframe.util.android.AfNetwork;
import com.attract.application.AdvAttractAdapter;

public class PointKernelAttract {
	
	public static class AdInfo{
		public Date mDate;
		public AdCustom mAdInfo;
		public AdInfo(AdCustom ad) {
			this.mAdInfo = ad;
			this.resetTime();
		}
		public void resetTime() {
			// TODO Auto-generated method stub
			this.mDate = mTime = new Date();
		}
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			if (o instanceof AdInfo) {
				AdInfo info = AdInfo.class.cast(o);
				return mAdInfo.Package.equals(info.mAdInfo.Package);
			}else if (o instanceof AdCustom) {
				AdCustom info = AdCustom.class.cast(o);
				return mAdInfo.Package.equals(info.Package);
			}
			return super.equals(o);
		}
	}
	//用户的积分
	private static int mPoints = 0;
	//等待下载安装的 app
	private static List<AdInfo> mltAdInfo = new ArrayList<AdInfo>();
	//已经下载安装的 app
	private static List<AdInfo> mltInstalled = new ArrayList<AdInfo>();

	private static final String KEY_DOWNLOAD = "95976004134032214102";
	private static final String KEY_CACHE = "52883484512041114102";
	private static final String KEY_POINT = "75282482634041114102";
	private static final String KEY_LIST_ADINFO = "00678600734041114102";
	private static final String KEY_LIST_INSTALL = "96534570734041114102";
	//定时器周期10秒钟
	private static final long KEY_PERIOD = 60000;
	
	//缓存器
	private static AfPrivateCaches mCache = AfPrivateCaches.getInstance(KEY_CACHE);

	//点数获取定时器
	private static Timer mTimer = null;
	private static Date mTime = new Date();
	private static AfTimeSpan mSpan = AfTimeSpan.FromHours(1);
	
	static{
		doReadCache();
		if (mltAdInfo.size() > 0) {
			doStartTimer();
		}
	}
	
	public static void doStatisticsAdInfo(AdCustom info) {
		// TODO Auto-generated method stub
		for (AdInfo element : mltInstalled) {
			if (element.equals(info)) {
				return ;
			}
		}
		for (AdInfo element : mltAdInfo) {
			if (element.equals(info)) {
				element.resetTime();
				return ;
			}
		}
		mCache.put(KEY_DOWNLOAD, true);
		mltAdInfo.add(new AdInfo(info));
		doUpdateCache();//更新缓存
		doStartTimer();
	}
	
	public static void doStartTimer() {
		if (mTimer == null) {
			mTime = new Date();
			mTimer = new Timer();
			mTimer.schedule(new AfTimerTask() {
				@Override
				protected void onTimer() {
					// TODO Auto-generated method stub
					doCheckAttractPoint();
					if (AfTimeSpan.FromDate(mTime, new Date()).GreaterThan(mSpan)) {
						doStopTimer();
					}
				}
			}, KEY_PERIOD, KEY_PERIOD);
		}
	}
	
	protected static void doCheckAttractPoint() {
		// TODO Auto-generated method stub
		String service = Context.ACTIVITY_SERVICE;
		AfApplication app = AfApplication.getApp();
		ActivityManager am = (ActivityManager) app.getSystemService(service);
		List<RunningAppProcessInfo> proces = am.getRunningAppProcesses();
		List<AdInfo> ltInstalled = new ArrayList<PointKernelAttract.AdInfo>();
		for (AdInfo adinfo : mltAdInfo) {
			for (RunningAppProcessInfo proce : proces) {
				if (proce.processName.equals(adinfo.mAdInfo.Package)) {
					if(AfApplication.getNetworkStatus() == AfNetwork.TYPE_NONE){
						String currency = AdvAttractAdapter.getInstance().getCurrency();
						String msg = "请先联网再打开下载软件才能送"+currency;
						Toast.makeText(AfApplication.getApp(), msg, Toast.LENGTH_LONG).show();
						AfExceptionHandler.handleAttach(new Exception(msg), msg);
						return;
					}
					doDivertInstalled(adinfo);
					ltInstalled.add(adinfo);
					break;
				}
			}
		}
		for (AdInfo info : ltInstalled) {
			mltAdInfo.remove(info);
		}
		doUpdateCache();//更新缓存
	}

	protected static void doUpdateCache() {
		// TODO Auto-generated method stub
		mCache.put(KEY_POINT, mPoints);
		//mCache.putList(KEY_LIST_ADINFO, mltAdInfo, AdInfo.class);
		mCache.putList(KEY_LIST_INSTALL, mltInstalled, AdInfo.class);
	}

	protected static void doReadCache() {
		// TODO Auto-generated method stub
		mPoints = mCache.getInt(KEY_POINT, mPoints);
		mltAdInfo = mCache.getList(KEY_LIST_ADINFO, AdInfo.class);
		mltInstalled = mCache.getList(KEY_LIST_INSTALL, AdInfo.class);
	}
	
	protected static void doDivertInstalled(AdInfo adinfo) {
		// TODO Auto-generated method stub
		for (AdInfo element : mltInstalled) {
			if (element.equals(adinfo)) {
				return ;
			}
		}
		adinfo.mDate = new Date();
		mltInstalled.add(adinfo);
		mPoints = mPoints += adinfo.mAdInfo.Points;
		doNotifyAttractPoint(adinfo.mAdInfo.Points,mPoints);
	}

	private static void doNotifyAttractPoint(int accretion, int points) {
		// TODO Auto-generated method stub
		
	}

	public static void doStopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	public static int getPoint() {
		// TODO Auto-generated method stub
		return mPoints;
	}

	public static int getComPoint() {
		// TODO Auto-generated method stub
		int point = mPoints;
		for (AdInfo ad : mltAdInfo) {
			point += ad.mAdInfo.Points;
		}
		return point;
	}
	

	public static int spendPoints(int spend) {
		// TODO Auto-generated method stub
		mPoints = mPoints - spend;
		mCache.put(KEY_POINT, mPoints);
		return mPoints;
	}

	public static int awardPoints(int award) {
		// TODO Auto-generated method stub
		mPoints = mPoints + award;
		mCache.put(KEY_POINT, mPoints);
		return mPoints;
	}

	public static AdCustom getNewAdCustom(List<AdCustom> list) {
		// TODO Auto-generated method stub
		AdCustom ad = null;
		list = new ArrayList<AdCustom>(list);
		for (AdInfo adInfo : mltAdInfo) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).Id.equals(adInfo.mAdInfo.Id)) {
					list.remove(i);
					break;
				}
			}
		}
		for (AdInfo adInfo : mltInstalled) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).Id.equals(adInfo.mAdInfo.Id)) {
					list.remove(i);
					break;
				}
			}
		}
		if (list.size() > 0) {
			return list.get(0);
		}
		return ad;
	}

	public static boolean isDownloaded() {
		// TODO Auto-generated method stub
		return mCache.getBoolean(KEY_DOWNLOAD, false);
	}
}
