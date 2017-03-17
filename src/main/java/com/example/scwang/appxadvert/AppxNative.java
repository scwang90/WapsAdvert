package com.example.scwang.appxadvert;

import com.andadvert.model.AdCustom;
import com.andframe.application.AfApp;
import com.baidu.appx.BDNativeAd;

/**
 * AppxNative
 * Created by SCWANG on 2016/8/29.
 */
@SuppressWarnings("unused")
public class AppxNative extends AdCustom {

    private final BDNativeAd.AdInfo info;

    public AppxNative(BDNativeAd.AdInfo info, int unitPrice) {
        this.info = info;
        this.Points = unitPrice;
        this.IconUrl = info.getIconUrl();
        this.Name = info.getTitle();
        this.Description = info.getDescription();
        this.Action = "安装";
        this.Filesize = "null".equals(info.getFileSize())?"4.12MB":info.getFileSize();
        this.Id = info.getClickUrl();
        this.Package = AfApp.get().getPackageName();
        this.ImageUrls = new String[]{
            info.getImageUrl()
        };
    }

    public void didShow() {
        info.didShow();
    }

    public void didClick() {
        info.didClick();
    }
}
