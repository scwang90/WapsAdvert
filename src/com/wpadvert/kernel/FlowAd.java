package com.wpadvert.kernel;

import java.util.HashMap;

/**
 * 信息流广告
 * Created by Administrator on 2015/11/30 0030.
 */
public class FlowAd extends HashMap{

    public String getLaunchArea() {
        return "" + get("launch_area");
    }

    public String getClickUrl() {
        return "" + get("click_url");
    }

    public String getAdimg() {
        return "" + get("adimg");
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
