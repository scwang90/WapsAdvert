package com.wapsadvert.kernel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.andadvert.AdvertAdapter;
import com.andadvert.PointStatistics;
import com.andadvert.listener.PointsNotifier;
import com.andadvert.model.AdCustom;
import com.andadvert.model.OnlineDeploy;
import com.andadvert.util.DS;
import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfPrivateCaches;
import com.appoffer.AdInfo;
import com.appoffer.AppConnect;
import com.appoffer.UpdatePointsNotifier;
import com.wapsadvert.kernel.activity.AdvMainActivity;

/**
 * 万普广告适配器
 * 
 * @author SCWANG
 * 
 */
public class WapsAdapter extends AdvertAdapter {

	private static final String APP_ID = "b6a563e2e4451f98b47370b05827cd9a";

	public static final String KEY_INITUNINSTALLAD = "10505902520282114102";

	public static final String KEY_ISWAPSWORKS = "05143911204192114102";

	protected static boolean IS_WAPSWORKS = true;
	
	/**
	 * 躲避广告结束日期
	 */
//	protected static Date ENDDATE = new Date(0);
	 private String mChannel = "poetry";
//	 private String mChannel = "google";
//	 private String mChannel = "appchina";
	// private String mChannel = "liqu";
//	 private String mChannel = "goapk";
//	 private String mChannel = "wandoujia";

//	 private String mChannel = "91";
//	 private String mChannel = "hiapk";


//	 private String mChannel = "xiaomi";
//	 private String mChannel = "360";
//	 private String mChannel = "qq";
//	 private String mChannel = "baidu";

//	 private String mChannel = "gfan";
//	  private String mChannel = "eoe";
//	  private String mChannel = "mumayi";
//	  private String mChannel = "lenovo";
//	  private String mChannel = "nduo";

//	 private String mChannel = "update";
//	 private String mChannel = "huali";
//	 private String mChannel = "163";
//	 private String mChannel = "waps";
//	 private String mChannel = "hide";

	 private String mDefChannel = mChannel;

	 //	protected static String UNIT_POINT = "daa48666f050fbd0";//"积分";
	protected static String UNIT_POINT = "092c0e35ab4a3811";//"墨点";
	
	static{
		if (UNIT_POINT != null && UNIT_POINT.length() > 4) {
//			String key = Application.getApp().getDesKey();
//			UNIT_POINT = new AfDesHelper(key).decryptNoException(UNIT_POINT);
			UNIT_POINT = DS.d(UNIT_POINT );
		}
	}
	
	public static void initialize(AfApplication application,String defchannel){
		application.setSingleton(AdvertAdapter.KEY_ADVERT, new WapsAdapter(defchannel));
	}
	
	private WapsAdapter(String defchannel) {
		// TODO Auto-generated constructor stub
		mDefChannel = defchannel;
//		String mchanel = AfApplication.getApp().getMetaData("chanel");
//		if (AfStringUtil.isNotEmpty(mchanel)) {
//			mChannel = mchanel;
//		}
	}
	
	@Override
	public String getDefChannel() {
		// TODO Auto-generated method stub
		return mDefChannel;
	}

	@Override
	public String getChannel() {
		// TODO Auto-generated method stub
		String channel = super.getChannel();
		if (DEFAULT_CHANNEL.equals(channel)) {
			return mChannel;
		}
		return channel;
	}

	@Override
	public boolean isSupportPoint() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isSupportCustom() {
		// TODO Auto-generated method stub
		return true;
	}

	protected void onCheckOnlineHideFail(Throwable ex){
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(2014, 8-1, 29);
//		Date ENDDATE = calendar.getTime();
//		IS_HIDE = !new Date().after(ENDDATE);
	}
	
	@Override
	public void initInstance(Context context) {
		// TODO Auto-generated method stub
		try {
			if (AfPrivateCaches.getInstance().getBoolean(KEY_ISWAPSWORKS, true)) {
				AppConnect.getInstance(APP_ID, mChannel, context);
			}else {
				IS_WAPSWORKS = false;
			}
		} catch (Throwable e) {
			// TODO: handle exception
			IS_WAPSWORKS = false;
			AfExceptionHandler.handler(e, "waps initInstance error");
		}
		
//		IS_WAPSWORKS = date1.after(date2);
		if (IS_WAPSWORKS) {
			if("poetry".equals(mChannel)){
				IS_HIDE = false;
				OnlineDeploy deploy = new OnlineDeploy();
				deploy.HideAd = false;
				deploy.Name = "poetry";
				deploy.Remark = "dpoetry";
				deploy.Urls = "http://attract";
				this.notifyBusinessModelStart(deploy);
			}else if ("update".equals(mChannel) ||"waps".equals(mChannel) ||"huali".equals(mChannel)) {
				IS_HIDE = false;
				this.notifyBusinessModelStart(null);
			}else if(!"hide".equals(mChannel)){
				onCheckOnlineHideFail(null);
				doCheckOnlineHide(context);
			}
			 //预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
			 AppConnect.getInstance(context).initAdInfo();
			// 预加载功能广告内容（仅在使用到功能广告的情况，才需要添加）
			// AppConnect.getInstance(context).initFunAd(context);
			// 预加载插屏广告内容（仅在使用到插屏广告的情况，才需要添加）
			AppConnect.getInstance(context).initPopAd(context);
			// 禁用错误报告
			// AppConnect.getInstance(context).setCrashReport(false);
			// 初始化卸载广告
			try {
				if (AfPrivateCaches.getInstance().getBoolean(KEY_INITUNINSTALLAD, true)) {
					AppConnect.getInstance(context).initUninstallAd(context);
				}else {
					/**
					 * 经过日志验证以下通知会发生，注释掉
					 */
					//new NotiftyMail(SginType.TITLE, "initUninstall", "false").sendTask();
				}
			} catch (Throwable e) {
				// TODO: handle exception
				AfExceptionHandler.handler(e, "initUninstallAd异常");
			}
		}
	}

	@Override
	public AdCustom getAdCustom(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			return doAdInfoToAdCustom(AppConnect.getInstance(context).getAdInfo());
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AdCustom> getAdCustomList(Context context) {
		// TODO Auto-generated method stub
		List<AdCustom> list = new ArrayList<AdCustom>();
		if (IS_WAPSWORKS) {
			List<AdInfo> ads = AppConnect.getInstance(context).getAdInfoList();
			if (ads != null) {
				for (AdInfo info : ads) {
					list.add(doAdInfoToAdCustom(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public void showDetailAd(Context context, AdCustom adinfo) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			AppConnect.getInstance(context).clickAd(context, adinfo.Id);
		}
	}
	
	@Override
	public void downloadAd(Context context, AdCustom adinfo) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			AppConnect.getInstance(context).downloadAd(context, adinfo.Id);
		}
	}

	private AdCustom doAdInfoToAdCustom(AdInfo info) {
		// TODO Auto-generated method stub
		if (info != null && IS_WAPSWORKS) {
			AdCustom custom = new AdCustom();
			custom.Action = info.getAction();
			custom.Icon = info.getAdIcon();
			custom.Id = info.getAdId();
			custom.Name = info.getAdName();
			custom.Package = info.getAdPackage();
			custom.Points = info.getAdPoints();
			custom.Text = info.getAdText();
			custom.Description = info.getDescription();
			custom.ImageUrls = info.getImageUrls();
			custom.Filesize = info.getFilesize()+"MB";
			custom.Provider = info.getProvider();
			custom.Version = info.getVersion();
			custom.Points = 80;
			return custom;
		}
		return null;
	}

	@Override
	public void uninstallAd(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			AppConnect.getInstance(context).close();
		}
	}

	@Override
	public String getCurrency() {
		return UNIT_POINT;
	}
	
	@Override
	public boolean isHide() {
		// TODO Auto-generated method stub
		if (!IS_WAPSWORKS) {
			return true;
		}
//		if (IS_HIDE && !"hide".equals(mChannel)) {
//			doCheckOnlineHide(AfApplication.getApp());
//		}
		return IS_HIDE;
	}

	@Override
	public String getConfig(Context context, String key, String vdefault) {
		// TODO Auto-generated method stub
		try {
			String value = AppConnect.getInstance(context).getConfig(key, vdefault);
			if ("".equals(value) && !"".equals(vdefault)) {
				return vdefault;
			}
			return value;
		} catch (Throwable e) {
			// TODO: handle exception
			return vdefault;
		}
	}

	@Override
	public void showOffers(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
//			AppConnect.getInstance(context).showOffers(context);
			context.startActivity(new Intent(context, AdvMainActivity.class));
		}
	}

	@Override
	public void showAppOffers(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
//			AppConnect.getInstance(context).showAppOffers(context);
			context.startActivity(new Intent(context, AdvMainActivity.class));
		}
	}
	
	@Override
	public void showGameOffers(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
//			AppConnect.getInstance(context).showGameOffers(context);
			context.startActivity(new Intent(context, AdvMainActivity.class));
		}
	}

	@Override
	public void showBannerAd(Context context, LinearLayout layout) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
//			AppConnect.getInstance(context).showBannerAd(context, layout);
//			AppConnect.getInstance(context).showPopAd(context);
		}
	}

	@Override
	public void showPopAd(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			AppConnect.getInstance(context).showPopAd(context);
		}
	}

	@Override
	public void awardPoints(Context context, int award,
			final PointsNotifier notifier) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			notifier.getPoints(getCurrency(), PointMainKernel.awardPoints(award));
//			AppConnect.getInstance(context).awardPoints(award,
//					new UpdatePointsNotifier() {
//					private int mLast = 0;
//					private int mCount = 0;
//						@Override
//						public void getUpdatePointsFailed(String error) {
//							// TODO Auto-generated method stub
//							notifier.getPointsFailed(error);
//						}
//
//						@Override
//						public void getUpdatePoints(String currency, int point) {
//							// TODO Auto-generated method stub
//							UNIT_POINT = currency;
//							if (++mCount > 1) {
//								if (mLast == point) {
//									return;
//								}
//							}
//							PointStatistics.doStaticsPoint(point, currency);
//							notifier.getPoints(currency, mLast = point);
//						}
//					});
		} else {
			notifier.getPointsFailed("");
		}
	}

	@Override
	public void spendPoints(Context context, int spend,
			final PointsNotifier notifier) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			if (PointMainKernel.getPoint() < spend) {
				notifier.getPointsFailed(getCurrency()+"余额不足"+spend);
				return ;
			}		
			notifier.getPoints(getCurrency(), PointMainKernel.spendPoints(spend));
//			if (PointStatistics.getPoint() < spend) {
//				notifier.getPointsFailed(UNIT_POINT+"余额不足"+spend);
//				return ;
//			}
//			AppConnect.getInstance(context).spendPoints(spend,
//					new UpdatePointsNotifier() {
//						private int mLast = 0;
//						private int mCount = 0;
//						@Override
//						public void getUpdatePointsFailed(String error) {
//							// TODO Auto-generated method stub
//							notifier.getPointsFailed(error);
//						}
//
//						@Override
//						public void getUpdatePoints(String currency, int point) {
//							// TODO Auto-generated method stub
//							UNIT_POINT = currency;
//							if (++mCount > 1) {
//								//new NotiftyMail(SginType.ALL,"多次调用 spend", "count="+mCount+" point="+point+" last="+mLast).sendTask();
//								if (mLast == point) {
//									return;
//								}
//							}
//							PointStatistics.doStaticsPoint(point, currency);
//							notifier.getPoints(currency, mLast = point);
//						}
//					});
		} else {
			notifier.getPointsFailed("");
		}
	}

	@Override
	public void getPoints(Context context, final PointsNotifier notifier) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			int point = PointMainKernel.getPoint();
			if (point != PointMainKernel.DEFAULE_POINT) {
				String currency = getCurrency();
				notifier.getPoints(currency , PointMainKernel.getPoint());
				PointStatistics.doStaticsPoint(point, currency);
				return;
			}
			AppConnect.getInstance(context).getPoints(
					new UpdatePointsNotifier() {
						private int mLast = 0;
						private int mCount = 0;
						@Override
						public void getUpdatePointsFailed(String error) {
							// TODO Auto-generated method stub
							notifier.getPointsFailed(error);
						}

						@Override
						public void getUpdatePoints(String currency, int point) {
							// TODO Auto-generated method stub
//							UNIT_POINT = currency;
							currency = getCurrency();
							if (++mCount > 1) {
								//new NotiftyMail(SginType.ALL,"多次调用 getpoint", "count="+mCount+" point="+point+" last="+mLast).sendTask();
								if (mLast == point) {
									return;
								}
							}
							PointMainKernel.awardPoints(point-PointMainKernel.getPoint());
							PointStatistics.doStaticsPoint(point, currency);
							notifier.getPoints(currency, mLast = point);
						}
					});
		} else {
			notifier.getPointsFailed("");
		}
	}

	@Override
	public View getPopAdView(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			return AppConnect.getInstance(context).getPopAdView(context);
		} else {
			return super.getPopAdView(context);
		}
	}

	@Override
	public boolean showMore(Context context) {
		// TODO Auto-generated method stub
		if (IS_WAPSWORKS) {
			AppConnect.getInstance(context).showMore(context);
			return true;
		} else {
			return false;
		}
	}
}
