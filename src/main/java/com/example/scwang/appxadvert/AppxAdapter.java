package com.example.scwang.appxadvert;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.andadvert.OnlineKey;
import com.andadvert.model.AdCustom;
import com.baidu.appx.BDAppWallAd;
import com.baidu.appx.BDNativeAd;
import com.wpadvert.kernel.WpAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 百度广告适配器
 * @author 树朾
 */
public class AppxAdapter extends WpAdapter {

    private static String APPX_APPWALL_KEY = "wVs28LdYEBjDO3mfq9mZOfBBbsguGAKq";
    private static String APPX_APP_KEY = "wVs28LdYEBjDO3mfq9mZOfBBbsguGAKq";
    private static String APPX_NATIVE_KEY = "wVs28LdYEBjDO3mfq9mZOfBBbsguGAKq";

    private BDNativeAd nativeAd = null;
    private Activity nativeActivity = null;
    private BDAppWallAd appwallAd = null;
    private Activity appwallActivity = null;

    /**
     * 获取全局 广告适配器
     */
    public static AppxAdapter getAppxInstance(){
        return (AppxAdapter)getInstance();
    }

    public AppxAdapter(String defchannel) {
        super(defchannel);
    }

    public static void initialize(String APPX_APP_KEY, String APPX_NATIVE_KEY, String channel, String currency, boolean debug) {
        DEBUG = debug;
        AppxAdapter.APPX_APP_KEY = APPX_APP_KEY;
        AppxAdapter.APPX_NATIVE_KEY = APPX_NATIVE_KEY;
        UNIT_POINT = TextUtils.isEmpty(currency) ? UNIT_POINT : currency;
        mInstance = new AppxAdapter(channel);
    }

    @Override
    public void initInstance(Context context) {
        super.initInstance(context);
        if (IS_WAPSWORKS && context instanceof Activity) {
            if (nativeAd == null || nativeActivity != context) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeActivity = (Activity) context;
                nativeAd = new BDNativeAd(nativeActivity, APPX_APP_KEY, APPX_NATIVE_KEY);
                nativeAd.loadAd();
            }
            if (appwallAd == null || appwallActivity != context) {
                if (appwallAd != null) {
                    appwallAd.destroy();
                }
                //创建开屏广告
                appwallActivity = (Activity) context;
                appwallAd = new BDAppWallAd(appwallActivity, APPX_APP_KEY, APPX_APPWALL_KEY);
                appwallAd.loadAd();
//                if (appwallAd.isLoaded()) {
//                    appwallAd.doShowAppWall();
//                }
            }
        }
    }

    @Override
    public AdCustom getAdCustom(Context context) {
        if (IS_WAPSWORKS) {
            return super.getAdCustom(context);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AdCustom> getAdCustomList(Context context) {
        List<AdCustom> list = new ArrayList<>();
        if (IS_WAPSWORKS) {
            UNIT_PRICE = OnlineKey.getInteger(context, OnlineKey.KEY_UNITPRICE, UNIT_PRICE, "get unitprice");
            ArrayList<BDNativeAd.AdInfo> infos = nativeAd.getAdInfos();
            for (BDNativeAd.AdInfo info : infos) {
                list.add(new AppxNative(info, UNIT_PRICE));
            }
        }
        return list;
    }

    @Override
    public void showDetailAd(Context context, AdCustom adinfo) {
        if (IS_WAPSWORKS) {
            if (adinfo instanceof AppxNative) {
                ((AppxNative) adinfo).didShow();
            } else {
                super.showDetailAd(context, adinfo);
            }
        }
    }

    @Override
    public void downloadAd(Context context, AdCustom adinfo) {
        if (IS_WAPSWORKS) {
            if (adinfo instanceof AppxNative) {
                ((AppxNative) adinfo).didClick();
            } else {
                super.downloadAd(context, adinfo);
            }
        }
    }

    @Override
    public void uninstallAd(Context context) {
        super.uninstallAd(context);
        if (IS_WAPSWORKS) {
            if (nativeAd != null) {
                nativeAd.destroy();
                nativeAd = null;
            }
        }
    }

    @Override
    public void showOffers(Context context) {
        if (isHide()) {
            if (appwallAd == null && context instanceof Activity) {
                //创建开屏广告
                appwallActivity = (Activity) context;
                appwallAd = new BDAppWallAd(appwallActivity, APPX_APP_KEY, APPX_APPWALL_KEY);
                appwallAd.loadAd();
            }
            if (appwallAd != null && appwallAd.isLoaded()) {
                appwallAd.doShowAppWall();
            } else {
                Toast.makeText(context, "正在加载...", Toast.LENGTH_LONG).show();
            }
        } else {
            super.showOffers(context);
        }
    }

}
