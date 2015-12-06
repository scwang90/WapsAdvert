package com.wpadvert.kernel;

import com.andframe.application.AfApplication;
import com.andframe.helper.android.AfDeviceInfo;

import java.util.HashMap;

/**
 * 信息流广告
 * Created by Administrator on 2015/11/30 0030.
 */
public class FlowAd extends HashMap{

    static AfDeviceInfo deviceInfo;

    private void ensureDeviceInfo() {
        if (deviceInfo == null) {
            deviceInfo = new AfDeviceInfo(AfApplication.getAppContext());
        }
    }

    public String getLaunchArea() {
        return "" + get("launch_area");
    }

    public String getClickUrl() {
        ensureDeviceInfo();
        return "" + get("click_url") + "&udid=" + deviceInfo.getDeviceId();
    }

    public String getAdimg() {
        return "" + get("adimg");
    }

    public String getAdimg16_9() {
        ensureDeviceInfo();
        return "" + get("adimg") + "&res_type=16_9&udid=" + deviceInfo.getDeviceId();
    }

    public String getAdimg4_3() {
        ensureDeviceInfo();
        return "" + get("adimg") + "&res_type=4_3&udid=" + deviceInfo.getDeviceId();
    }

    public float getPrice() {
        return Float.valueOf("" + get("price"));
    }

    public String getAdContent() {
        return "" + get("adcontent");
    }

    public String getDeviceType() {
        return "" + get("device_type");
    }

    public String getAdIcon() {
        return "" + get("adicon");
    }

    public String getAdTitle() {
        return "" + get("adtitle");
    }

    public String getAdType() {
        return "" + get("ad_type");
    }

    public String getType() {
        return "" + get("type");
    }

    public String getOsVersion() {
        return "" + get("os_version");
    }

}
