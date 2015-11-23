package com.wpadvert.kernel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.andadvert.AdvertAdapter;
import com.andadvert.OnlineKey;
import com.andadvert.PointStatistics;
import com.andadvert.listener.PointsNotifier;
import com.andadvert.model.AdCustom;
import com.andadvert.util.DS;
import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.util.java.AfStringUtil;
import com.wpadvert.kernel.activity.AdvMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 万普广告适配器
 * @author 树朾
 */
public class WpAdapter extends AdvertAdapter {

    private static String APP_ID = "b6a563e2e4451f98b47370b05827cd9a";

    public static final String KEY_INITUNINSTALLAD = "10505902520282114102";

    public static final String KEY_ISWAPSWORKS = "05143911204192114102";

    private static int UNIT_PRICE = 70;

    protected static boolean IS_WAPSWORKS = true;

    /**
     * 躲避广告结束日期
     */
    private String mChannel = "google";
    private String mDefChannel = mChannel;

    protected static String UNIT_POINT = "daa48666f050fbd0";//"积分";

    static {
        if (UNIT_POINT != null && UNIT_POINT.length() > 4) {
            UNIT_POINT = DS.d(UNIT_POINT);
        }
        DynnamicJar.initize(AfApplication.getApp());
    }

    /**
     * 获取全局 广告适配器
     */
    public static AdvertAdapter getWpInstance(){
        return AfApplication.getApp().getSingleton(AdvertAdapter.KEY_ADVERT);
    }

    public static void initialize(AfApplication application, String channel, String currency, String appId) {
        APP_ID = appId;
        UNIT_POINT = AfStringUtil.isNotEmpty(currency)?currency:UNIT_POINT;
        application.setSingleton(AdvertAdapter.KEY_ADVERT, new WpAdapter(channel));
    }

    private WpAdapter(String defchannel) {
        mDefChannel = defchannel;
        mChannel = getChannel();
    }

    @Override
    public String getDefChannel() {
        return mDefChannel;
    }

    @Override
    public String getChannel() {
        String channel = super.getChannel();
        if (DEFAULT_CHANNEL.equals(channel)) {
            return mChannel;
        }
        return channel;
    }

    @Override
    public boolean isSupportPoint() {
        return true;
    }

    @Override
    public boolean isSupportCustom() {
        return true;
    }

    protected void onCheckOnlineHideFail(Throwable ex) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(2014, 8-1, 29);
//		Date ENDDATE = calendar.getTime();
//		IS_HIDE = !new Date().after(ENDDATE);
    }

    @Override
    public void initInstance(Context context) {
        try {
            if (AfPrivateCaches.getInstance().getBoolean(KEY_ISWAPSWORKS, true)) {
                Apache.getInstance(APP_ID, mChannel, context);
            } else {
                IS_WAPSWORKS = false;
            }
            UNIT_PRICE = OnlineKey.getInteger(context, OnlineKey.KEY_UNITPRICE, UNIT_PRICE, "get unitprice");
        } catch (Throwable e) {
            IS_WAPSWORKS = false;
//            AfPrivateCaches.getInstance().put(KEY_ISWAPSWORKS,false);
            AfExceptionHandler.handler(e, "waps initInstance error");
        }

//		IS_WAPSWORKS = date1.after(date2);
        if (IS_WAPSWORKS) {
            /*if("poetry".equals(Channel)){
                IS_HIDE = false;
				OnlineDeploy deploy = new OnlineDeploy();
				deploy.HideAd = false;
				deploy.Name = "poetry";
				deploy.Remark = "dpoetry";
				deploy.Urls = "http://attract";
				this.helper.setValue(deploy);
				this.notifyBusinessModelStart(deploy);
			}else */
            if ("update".equals(mChannel) || "waps".equals(mChannel) || "huali".equals(mChannel)) {
//				IS_HIDE = false;
//				this.notifyBusinessModelStart(null);
                onCheckOnlineHideFail(null);
//                doCheckOnlineHide(context);
            } else if (!"hide".equals(mChannel)) {
                onCheckOnlineHideFail(null);
//                doCheckOnlineHide(context);
            }
            //预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
            Apache.getInstance(context).initAdInfo();
            // 预加载功能广告内容（仅在使用到功能广告的情况，才需要添加）
            // Apache.getInstance(context).initFunAd(context);
            // 预加载插屏广告内容（仅在使用到插屏广告的情况，才需要添加）
            Apache.getInstance(context).initPopAd(context);
            // 禁用错误报告
            Apache.getInstance(context).setCrashReport(false);
            // 初始化卸载广告
            try {
                if (AfPrivateCaches.getInstance().getBoolean(KEY_INITUNINSTALLAD, true)) {
//                    Apache.getInstance(context).initUninstallAd(context);
                } else {
                    /**
                     * 经过日志验证以下通知会发生，注释掉
                     */
                    //new NotiftyMail(SginType.TITLE, "initUninstall", "false").sendTask();
                }
            } catch (Throwable e) {
                AfExceptionHandler.handler(e, "initUninstallAd异常");
            }
        }
    }

    @Override
    public AdCustom getAdCustom(Context context) {
        if (IS_WAPSWORKS) {
            UNIT_PRICE = OnlineKey.getInteger(context, OnlineKey.KEY_UNITPRICE, UNIT_PRICE, "get unitprice");
            return doAdInfoToAdCustom(Apache.getInstance(context).getAdInfo());
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AdCustom> getAdCustomList(Context context) {
        List<AdCustom> list = new ArrayList<AdCustom>();
        if (IS_WAPSWORKS) {
            UNIT_PRICE = OnlineKey.getInteger(context, OnlineKey.KEY_UNITPRICE, UNIT_PRICE, "get unitprice");
            list = Apache.getInstance(context).getAdInfoList();
            for (AdCustom custom : list) {
                if (custom.Points == 0){
                    custom.Points = UNIT_PRICE;
                }
            }
//            List<AdInfo> ads = Apache.getInstance(context).getAdInfoList();
//            if (ads != null) {
//                for (AdInfo info : ads) {
//                    list.add(doAdInfoToAdCustom(info));
//                }
//            }
        }
        return list;
    }

    @Override
    public void showDetailAd(Context context, AdCustom adinfo) {
        if (IS_WAPSWORKS) {
            Apache.getInstance(context).clickAd(context, adinfo.Id);
        }
    }

    @Override
    public void downloadAd(Context context, AdCustom adinfo) {
        if (IS_WAPSWORKS) {
            Apache.getInstance(context).downloadAd(context, adinfo.Id);
        }
    }

    private AdCustom doAdInfoToAdCustom(AdCustom info) {
        if (info != null && info.Points == 0) {
            info.Points = UNIT_PRICE;
        }
        return info;
    }

//    private AdCustom doAdInfoToAdCustom(AdInfo info) {
//        if (info != null && IS_WAPSWORKS) {
//            AdCustom custom = new AdCustom();
//            custom.Action = info.getAction();
//            custom.Icon = info.getAdIcon();
//            custom.Id = info.getAdId();
//            custom.Name = info.getAdName();
//            custom.Package = info.getAdPackage();
//            custom.Points = info.getAdPoints();
//            custom.Text = info.getAdText();
//            custom.Description = info.getDescription();
//            custom.ImageUrls = info.getImageUrls();
//            custom.Filesize = info.getFilesize() + "MB";
//            custom.Provider = info.getProvider();
//            custom.Version = info.getVersion();
//            custom.Points = info.getAdPoints();
//            if (custom.Points == 0){
//                custom.Points = UNIT_PRICE;
//            }
//            return custom;
//        }
//        return null;
//    }

    @Override
    public void uninstallAd(Context context) {
        if (IS_WAPSWORKS) {
            Apache.getInstance(context).close();
        }
    }

    @Override
    public String getCurrency() {
        return UNIT_POINT;
    }

    @Override
    public boolean isHide() {
        if (!IS_WAPSWORKS) {
            return true;
        }
//		if (IS_HIDE && !"hide".equals(Channel)) {
//			doCheckOnlineHide(AfApplication.getApp());
//		}
        return IS_HIDE;
    }

    @Override
    public String getConfig(Context context, String key, String vdefault) {
        try {
            String value = Apache.getInstance(context).getConfig(key, vdefault);
            if (AfStringUtil.isEmpty(value) && value != null) {
                return vdefault;
            }
            return value;
        } catch (Throwable e) {
            return vdefault;
        }
    }

    @Override
    public void showOffers(Context context) {
        if (IS_WAPSWORKS) {
//			Apache.getInstance(context).showOffers(context);
            context.startActivity(new Intent(context, AdvMainActivity.class));
        }
    }

    @Override
    public void showAppOffers(Context context) {
        if (IS_WAPSWORKS) {
//			Apache.getInstance(context).showAppOffers(context);
            context.startActivity(new Intent(context, AdvMainActivity.class));
        }
    }

    @Override
    public void showGameOffers(Context context) {
        if (IS_WAPSWORKS) {
//			Apache.getInstance(context).showGameOffers(context);
            context.startActivity(new Intent(context, AdvMainActivity.class));
        }
    }

    @Override
    public void showBannerAd(Context context, LinearLayout layout) {
        if (IS_WAPSWORKS) {
//			Apache.getInstance(context).showBannerAd(context, layout);
//			Apache.getInstance(context).showPopAd(context);
        }
    }

    @Override
    public void showPopAd(Context context) {
        if (IS_WAPSWORKS) {
            Apache.getInstance(context).showPopAd(context);
        }
    }

    @Override
    public void awardPoints(Context context, int award,
                            final PointsNotifier notifier) {
        if (IS_WAPSWORKS) {
            notifier.getPoints(getCurrency(), PointKernelMain.awardPoints(award));
        } else {
            notifier.getPointsFailed("");
        }
    }

    @Override
    public void spendPoints(Context context, int spend,
                            final PointsNotifier notifier) {
        if (IS_WAPSWORKS) {
            if (PointKernelMain.getPoint() < spend) {
                notifier.getPointsFailed(getCurrency() + "余额不足" + spend);
                return;
            }
            notifier.getPoints(getCurrency(), PointKernelMain.spendPoints(spend));
        } else {
            notifier.getPointsFailed("");
        }
    }

    @Override
    public void getPoints(Context context, final PointsNotifier notifier) {
        if (IS_WAPSWORKS) {
            int point = PointKernelMain.getPoint();
            if (point == PointKernelMain.DEFAULE_POINT) {
                point = 20;
                PointKernelMain.awardPoints(point - PointKernelMain.getPoint());
            }
            String currency = getCurrency();
            notifier.getPoints(currency, point);
            PointStatistics.doStaticsPoint(point, currency);
        } else {
            notifier.getPointsFailed("");
        }
    }

    @Override
    public View getPopAdView(Context context) {
        if (IS_WAPSWORKS) {
            return Apache.getInstance(context).getPopAdView(context);
        } else {
            return super.getPopAdView(context);
        }
    }

    @Override
    public boolean showMore(Context context) {
        if (IS_WAPSWORKS) {
            Apache.getInstance(context).showMore(context);
            return true;
        } else {
            return false;
        }
    }
}
