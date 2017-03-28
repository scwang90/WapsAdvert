package com.wpadvert.kernel.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.andadvert.AdvertAdapter;
import com.andadvert.model.AdCustom;
import com.andadvert.util.AfNetwork;
import com.andadvert.util.DS;
import com.andframe.$;
import com.andframe.activity.AfItemsActivity;
import com.andframe.adapter.itemviewer.AfItemViewer;
import com.andframe.api.Paging;
import com.andframe.api.adapter.ItemViewer;
import com.andframe.api.task.TaskWithPaging;
import com.wpadvert.R;
import com.wpadvert.kernel.PointKernelMain;
import com.wpadvert.kernel.application.WpBackService;
import com.wpadvert.kernel.event.WpEvent;

import java.util.List;

public class AdvMainActivity extends AfItemsActivity<AdCustom> {

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.wa_layout_advattract);

		$(R.id.titlebar_title).text("热门应用推荐");
//		AfStatusBarCompat.compatPadding(mTitlebar.getView());
		if (WpBackService.SetBackground(this)) {
			$(Toolbar.class).backgroundColor(0x99000000);
		}
	}

	@Nullable
	@Override
	public List<AdCustom> onTaskLoadList(@Nullable Paging paging) throws Exception {
		AdvertAdapter adapter = AdvertAdapter.getInstance();
		List<AdCustom> ltdata = adapter.getAdCustomList(getActivity());
		int recount = 0;
		while (recount++ < 5 && (ltdata == null || ltdata.size() == 0)) {
			Thread.sleep(1000);
			ltdata = adapter.getAdCustomList(getActivity());
		}
		return ltdata;
	}

	@Override
	public boolean setMoreShow(@NonNull TaskWithPaging task, @Nullable List<AdCustom> list) {
		return false;
	}

	@NonNull
	@Override
	public ItemViewer<AdCustom> newItemViewer(int viewType) {
		return new AfItemViewer<AdCustom>(R.layout.wa_listitem_advpoetry) {
			@Override
			public void onViewCreated(View view) {
				super.onViewCreated(view);
				$(R.id.advitem_download).clicked((OnClickListener) v -> onItemClick(mModel, -1));
			}
			@Override
			public void onBinding(AdCustom model, int index) {
				$(R.id.advpitem_tip).text(model.Provider);
				$(R.id.advpitem_title).text(model.Name);
				$(R.id.advpitem_content).text(model.Text);
				$(R.id.advpitem_download).text(model.Action);
				$(R.id.advpitem_filesize).text(model.Filesize);
				if (model.Icon != null) {
					$(R.id.advpitem_image).image(model.Icon);
				} else if (!TextUtils.isEmpty(model.IconUrl)) {
					$(R.id.advpitem_image).image(model.IconUrl);
//				} else {
//						$(R.id.advpitem_image).image(AfApp.get().getLogoId());
				}

				AdvertAdapter adapter = AdvertAdapter.getInstance();
				String currency = adapter.getCurrency();
				$(R.id.advpitem_givepoint).html("送 <font color='#337FE5'>%d</font> %s", model.Points, currency)
					.visible(!adapter.isHide() && !adapter.getChannel().equals("waps"));
			}
		};
	}

	@Override
	public void onItemClick(final AdCustom info, int index) {
		if (index == -1 || AfNetwork.getNetworkState(getContext()) == AfNetwork.TYPE_WIFI) {
			String title = "温馨提示";
			String question = "确定下载%s吗？";
			AdvertAdapter adapter = AdvertAdapter.getInstance();
			DialogInterface.OnClickListener download;
			DialogInterface.OnClickListener cancel;
			download = (dialog, which) -> doDownloadAdv(info);
			cancel = (dialog, which) -> $.event().post(new WpEvent(WpEvent.WP_DONWLOAD_CANCEL_MAIN));
			if (adapter.isHide()) {
				$.dialog(getContext()).showDialog(title, String.format(question,info.Name)
						,"下载",download,"取消",cancel);
			} else {
				$.dialog(getContext()).showDialog(title, String.format(question,info.Name)+
								DS.d("148f22dd2efbc20caece145c579fa73a1f0b1f5b4a3a253bc4d14f07" +
										"eefddddaaf54e072b15f08d342895d73bee55f0cf4e83a7fb6456" +
										"b5ed3d0177ffa1409cb1f6980e529583c25d7b9bf35aaecc00373" +
										"3579698944d66a11daa275a6089761ae77896fd89eadce3f029c2" +
										"4c241eb9e5352d4ea3e34a3f890aa0178d1c0f2a5d06572e3d7f5" +
										"c1c7") + adapter.getCurrency()
						,"下载",download,"取消",cancel);
			}
		}
	}
	protected void doDownloadAdv(AdCustom info) {
		AdvertAdapter adapter = AdvertAdapter.getInstance();
//			Apache connect = Apache.getInstance(getActivity());
//			AbActivity.event(getActivity(), "downloadAd.poetry", info.Name);
		adapter.downloadAd(getActivity(), info);
//			connect.downloadAd(getActivity(), info.Id);
//			AttractStatistics.doStaticsPoint();
//			AttractPointKernel.doStatisticsAdInfo(info);
		PointKernelMain.doStatisticsAdInfo(info);
		//触发统计下载事件
		$.event().post(new WpEvent(WpEvent.WP_POINT_MAIN_DONWLOAD));
//			if (!adapter.isHide()) {
		//makeToastLong("软件正在下载中，请在下载完成之后半小时之内安装并打开30秒以上（并确保网络连接），才能获得"+adapter.getCurrency());
//				makeToastLong(DS.d("7a02ad1ae8010ef5f8" +
//						"7f5123be1adff8d75eee3c3a4f676d792671c10dc" +
//						"038f396ea164b84c6ab496edbae6dfcfed87181d5" +
//						"2cdb8209ca41d8ab507a8d9a89b3797e70ddfde7" +
//						"387a2116da783575eefad6d2c13645d1e0d3227a" +
//						"270b3e8d7e904bff3278f78cc963277f0d301c9463" +
//						"c2f661f52f6c1e47bf86e4b8d2d6a38b9fa21286ae" +
//						"3c752efc") + adapter.getCurrency());
//			}
	}

}
