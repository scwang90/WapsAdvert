package com.wpadvert.kernel;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.Toast;

import com.andadvert.AdvertAdapter;
import com.andadvert.model.AdCustom;
import com.andadvert.util.AfNetwork;
import com.andframe.$;
import com.andframe.api.Cacher;
import com.andframe.application.AfApp;
import com.andframe.exception.AfExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PointKernel implements Runnable {
	
	public static class AdInfo{
		public Date mDate;
		public AdCustom info;
		public AdInfo(AdCustom ad) {
			this.info = ad;
			this.mDate = new Date();
		}
		@Override
		public boolean equals(Object o) {
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
	protected List<AdInfo> mltMonitored = new ArrayList<>();
	//已经下载安装的 app
	protected List<AdInfo> mltInstalled = new ArrayList<>();

	//	protected String KEY_CACHE = "93089433021020214102";
	protected final String KEY_POINT = "91259234021020214102";
	protected final String KEY_LIST_ADINFO = "15859874021020214102";
	protected final String KEY_LIST_INSTALL = "27893935021020214102";
	//缓存器
	protected Cacher mCache;// = $.cache(KEY_CACHE);

	public PointKernel(String key) {
		mCache = $.cache(key);
		doReadCache();
		if (mltMonitored.size() > 0) {
			PointTimer.doStartTimer(this);
		}
	}
	/**
	 * 开始监听统计info
	 * @return 成功监听（不在已经监听列表里面）
	 */
	public boolean doStatisticsAdInfo(AdCustom info) {
		for (AdInfo element : mltInstalled) {
			if (element.equals(info)) {
				return false;
			}
		}
		for (AdInfo element : mltMonitored) {
			if (element.equals(info)) {
				element.mDate = new Date();
				PointTimer.resetTime();
				return false;
			}
		}
		mltMonitored.add(new AdInfo(info));
		doUpdateCache();//更新缓存
		PointTimer.doStartTimer(this);
		return true;
	}

	@Override
	public void run() {
		doCheckPoint();
	}

	protected void doCheckPoint() {
		String service = Context.ACTIVITY_SERVICE;
		AfApp app = AfApp.get();
		ActivityManager am = (ActivityManager) app.getSystemService(service);
		List<ActivityManager.RunningAppProcessInfo> proces = am.getRunningAppProcesses();
		List<AdInfo> ltInstalled = new ArrayList<>();
		for (AdInfo adinfo : mltMonitored) {
			for (ActivityManager.RunningAppProcessInfo proce : proces) {
				if (proce.processName.equals(adinfo.info.Package)) {
					if(AfNetwork.getNetworkState(app) == AfNetwork.TYPE_NONE){
						String currency = AdvertAdapter.getInstance().getCurrency();
						String msg = "请确保连接到互联网再运行安"+" ".trim()+"装软件才可获得"+currency;
						Toast.makeText(app, msg, Toast.LENGTH_LONG).show();
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
		int point = mCache.getInt(KEY_POINT,mPoints);
		if (mPoints - point > 500){
			doNotifyPointCheat(point, mPoints);
			mPoints = point;
		}
		mCache.put(KEY_POINT, mPoints);
		mCache.putList(KEY_LIST_ADINFO, mltMonitored);
		mCache.putList(KEY_LIST_INSTALL, mltInstalled);
	}

	protected void doReadCache() {
		mPoints = mCache.getInt(KEY_POINT, mPoints);
		mltMonitored = mCache.getList(KEY_LIST_ADINFO, AdInfo.class);
		mltInstalled = mCache.getList(KEY_LIST_INSTALL, AdInfo.class);
	}

	protected void doDivertInstalled(AdInfo adinfo) {
		for (AdInfo element : mltInstalled) {
			if (element.equals(adinfo)) {
				return ;
			}
		}
		adinfo.mDate = new Date();
		mltInstalled.add(adinfo);
		mPoints = mPoints + adinfo.info.Points;
		doNotifyPointIncrease(adinfo.info.Points, mPoints);
	}

	/**
	 * 子类监听点数增加
	 * @param accretion 增量
	 * @param points 结果
	 */
	protected void doNotifyPointIncrease(int accretion, int points) {

	}

	/**
	 * 子类监听点数作弊
	 * @param point 正常点数
	 * @param cheat 作弊结果点数
	 */
	protected void doNotifyPointCheat(int point, int cheat) {

	}

	public int getPoint() {
		return mPoints;
	}

	public int spendPoints(int spend) {
		mPoints = mPoints - spend;
		mCache.put(KEY_POINT, mPoints);
		return mPoints;
	}

	public int awardPoints(int award) {
		mPoints = mPoints + award;
		mCache.put(KEY_POINT, mPoints);
		return mPoints;
	}

	public boolean hasMonitored(AdCustom custom) {
		for (AdInfo info : mltMonitored) {
			if (info.equals(custom)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasInstalled(AdCustom custom) {
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
		int point = mPoints;
		for (AdInfo ad : mltMonitored) {
			point += ad.info.Points;
		}
		return point;
	}
}
