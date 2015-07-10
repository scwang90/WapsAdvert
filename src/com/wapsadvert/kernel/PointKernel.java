package com.wapsadvert.kernel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.widget.Toast;

import com.andadvert.AdvertAdapter;
import com.andadvert.model.AdCustom;
import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.helper.java.AfTimeSpan;
import com.andframe.thread.AfTimerTask;
import com.andframe.util.android.AfNetwork;

public class PointKernel {
	
	public static class AdInfo{
		public Date mDate;
		public AdCustom info;
		public AdInfo(AdCustom ad) {
			this.info = ad;
			this.mDate = new Date();
		}
		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			if (o instanceof AdInfo) {
				AdInfo info = AdInfo.class.cast(o);
				return this.info.Package.equals(info.info.Package);
			}else if (o instanceof AdCustom) {
				AdCustom info = AdCustom.class.cast(o);
				return this.info.Package.equals(info.Package);
			}
			return super.equals(o);
		}
	}
	public static final int DEFAULE_POINT = -1;
	//用户的积分
	protected int mPoints = DEFAULE_POINT;
	//等待下载安装的 app
	protected List<AdInfo> mltMonitored = new ArrayList<AdInfo>();
	//已经下载安装的 app
	protected List<AdInfo> mltInstalled = new ArrayList<AdInfo>();
	
//	protected String KEY_CACHE = "93089433021020214102";
	protected final String KEY_POINT = "91259234021020214102";
	protected final String KEY_LIST_ADINFO = "15859874021020214102";
	protected final String KEY_LIST_INSTALL = "27893935021020214102";
	//定时器周期10秒钟
	protected final long KEY_PERIOD = 60000;
	
	//缓存器
	protected AfPrivateCaches mCache;// = AfPrivateCaches.getInstance(KEY_CACHE);

	//点数获取定时器
	protected Timer mTimer = null;
	//开始监听的时间
	protected Date mTime = new Date();
	//监听时间的长度（一个小时关闭监听）
	protected AfTimeSpan mSpan = AfTimeSpan.FromHours(1);
	
	public PointKernel(String key) {
		// TODO Auto-generated constructor stub
		mCache = AfPrivateCaches.getInstance(key);
		doReadCache();
		if (mltMonitored.size() > 0) {
			doStartTimer();
		}
	}
	/**
	 * 开始监听统计info
	 * @param info
	 * @return 成功监听（不在已经监听列表里面）
	 */
	public boolean doStatisticsAdInfo(AdCustom info) {
		// TODO Auto-generated method stub
		for (AdInfo element : mltInstalled) {
			if (element.equals(info)) {
				return false;
			}
		}
		for (AdInfo element : mltMonitored) {
			if (element.equals(info)) {
				element.mDate = new Date();
				mTime = new Date();
				return false;
			}
		}
		mltMonitored.add(new AdInfo(info));
		doUpdateCache();//更新缓存
		doStartTimer();
		return true;
	}
	
	public void doStartTimer() {
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
	
	protected void doCheckAttractPoint() {
		// TODO Auto-generated method stub
		String service = Context.ACTIVITY_SERVICE;
		AfApplication app = AfApplication.getApp();
		ActivityManager am = (ActivityManager) app.getSystemService(service);
		List<RunningAppProcessInfo> proces = am.getRunningAppProcesses();
		List<AdInfo> ltInstalled = new ArrayList<PointKernel.AdInfo>();
		for (AdInfo adinfo : mltMonitored) {
			for (RunningAppProcessInfo proce : proces) {
				if (proce.processName.equals(adinfo.info.Package)) {
					if(AfApplication.getNetworkStatus() == AfNetwork.TYPE_NONE){
						String currency = AdvertAdapter.getInstance().getCurrency();
						String msg = "请确保连接到互联网再运行安"+" ".trim()+"装软件才可获得"+currency;
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
			mltMonitored.remove(info);
		}
		doUpdateCache();//更新缓存
	}

	protected void doUpdateCache() {
		// TODO Auto-generated method stub
		mCache.put(KEY_POINT, mPoints);
		mCache.putList(KEY_LIST_ADINFO, mltMonitored, AdInfo.class);
		mCache.putList(KEY_LIST_INSTALL, mltInstalled, AdInfo.class);
	}

	protected void doReadCache() {
		// TODO Auto-generated method stub
		mPoints = mCache.getInt(KEY_POINT, mPoints);
		mltMonitored = mCache.getList(KEY_LIST_ADINFO, AdInfo.class);
		mltInstalled = mCache.getList(KEY_LIST_INSTALL, AdInfo.class);
	}
	
	protected void doDivertInstalled(AdInfo adinfo) {
		// TODO Auto-generated method stub
		for (AdInfo element : mltInstalled) {
			if (element.equals(adinfo)) {
				return ;
			}
		}
		adinfo.mDate = new Date();
		mltInstalled.add(adinfo);
		mPoints = mPoints += adinfo.info.Points;
		doNotifyAttractPoint(adinfo.info.Points,mPoints);
	}

	protected void doNotifyAttractPoint(int accretion, int points) {
		// TODO Auto-generated method stub
		
	}

	public void doStopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	public int getPoint() {
		// TODO Auto-generated method stub
		return mPoints;
	}

	public int spendPoints(int spend) {
		// TODO Auto-generated method stub
		mPoints = mPoints - spend;
		mCache.put(KEY_POINT, mPoints);
		return mPoints;
	}

	public int awardPoints(int award) {
		// TODO Auto-generated method stub
		mPoints = mPoints + award;
		mCache.put(KEY_POINT, mPoints);
		return mPoints;
	}
	
	public boolean hasMonitored(AdCustom custom) {
		// TODO Auto-generated method stub
		for (AdInfo info : mltMonitored) {
			if (info.equals(custom)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasInstalled(AdCustom custom) {
		// TODO Auto-generated method stub
		for (AdInfo info : mltInstalled) {
			if (info.equals(custom)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取预计点数（已经有的点数+正在监听的点数）
	 * @return
	 */
	public int getComPoint() {
		// TODO Auto-generated method stub
		int point = mPoints;
		for (AdInfo ad : mltMonitored) {
			point += ad.info.Points;
		}
		return point;
	}
}