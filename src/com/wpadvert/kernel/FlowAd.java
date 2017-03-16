package com.wpadvert.kernel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.andframe.application.AfApp;

import java.util.HashMap;

/**
 * 信息流广告
 * Created by Administrator on 2015/11/30 0030.
 */
@SuppressWarnings("unused")
public class FlowAd extends HashMap {

    static String mDeviceId;

    @SuppressLint("HardwareIds")
    private void ensureDeviceInfo() {
        if (mDeviceId == null) {
            String tmserver = Context.TELEPHONY_SERVICE;
            AfApp app = AfApp.get();
            TelephonyManager manager = (TelephonyManager)app.getSystemService(tmserver);

            mDeviceId = manager.getDeviceId().trim();
        }
    }

    public String getLaunchArea() {
        return "" + get("launch_area");
    }

    public String getClickUrl() {
        ensureDeviceInfo();
        return "" + get("click_url") + "&udid=" + mDeviceId;
    }

    public String getAdimg() {
        return "" + get("adimg");
    }

    public String getAdimg16_9() {
        ensureDeviceInfo();
        return "" + get("adimg") + "&res_type=16_9&udid=" + mDeviceId;
    }

    public String getAdimg4_3() {
        ensureDeviceInfo();
        return "" + get("adimg") + "&res_type=4_3&udid=" + mDeviceId;
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
