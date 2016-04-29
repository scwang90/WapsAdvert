package com.wpadvert.kernel.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andadvert.AdvertAdapter;
import com.andadvert.model.AdCustom;
import com.andadvert.util.DS;
import com.andcloud.activity.AbActivity;
import com.andframe.activity.AfActivity;
import com.andframe.activity.framework.AfView;
import com.andframe.adapter.AfListAdapter;
import com.andframe.application.AfApplication;
import com.andframe.constant.AfNetworkEnum;
import com.andframe.feature.AfIntent;
import com.andframe.layoutbind.AfFrameSelector;
import com.andframe.layoutbind.AfModuleProgress;
import com.andframe.layoutbind.AfModuleProgressImpl;
import com.andframe.layoutbind.AfModuleTitlebar;
import com.andframe.layoutbind.AfModuleTitlebarImpl;
import com.andframe.thread.AfHandlerTask;
import com.wpadvert.R;
import com.wpadvert.kernel.Apache;
import com.wpadvert.kernel.PointKernelMain;
import com.wpadvert.kernel.application.WpBackService;
import com.wpadvert.kernel.event.WpEvent;

import java.util.List;

public class AdvMainActivity extends AfActivity {
	
	private ListView mListView = null;
	private AdvAdapter mAdapter = null;
	private AfModuleTitlebar mTitlebar = null;
	private AfModuleProgress mProgress = null;
	private AfFrameSelector mSelector = null;

	@Override
	protected void onCreate(Bundle bundle, AfIntent intent) throws Exception {
//		AdvertAdapter.getInstance().initInstance(this);
		
		super.onCreate(bundle, intent);
		setContentView(R.layout.wa_layout_advattract);
		mListView = findViewByID(R.id.advattract_list);
		
		mTitlebar = new AfModuleTitlebarImpl(this);
		StatusBarCompat.compatPadding(mTitlebar.getTarget());
		mTitlebar.setTitle("热门应用推荐");
		mProgress = new AfModuleProgressImpl(this);
		mProgress.setDescription("正在加载");
		mSelector = new AfFrameSelector(this, R.id.advattract_frame);
		mSelector.selectFrame(mProgress);
		
		postTask(new LoadingTask());
		if (WpBackService.SetBackground(this)) {
			mTitlebar.getTarget().setBackgroundColor(0x99000000);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AdvertAdapter.getInstance().uninstallAd(this);
	}
	
	private class LoadingTask extends AfHandlerTask{

		@Override
		protected void onWorking(/*Message msg*/) throws Exception {
			AdvertAdapter adapter = AdvertAdapter.getInstance();
			List<AdCustom> ltdata = adapter.getAdCustomList(getActivity());
			while (ltdata == null || ltdata.size() == 0) {
				Thread.sleep(1000);
				ltdata = adapter.getAdCustomList(getActivity());
			}
			mAdapter = new AdvAdapter(getActivity(), ltdata);
		}
		
		@Override
		protected void onException(Throwable e) {
			super.onException(e);
			//ExceptionHandler.handler(e, "");
		}

		@Override
		protected boolean onHandle(/*Message msg*/) {
			if (mAdapter != null) {
				mListView.setAdapter(mAdapter);
				mSelector.selectFrame(mListView);
			}
			return false;
		}
	}

	private class AdvAdapter extends AfListAdapter<AdCustom> implements OnClickListener{

		public AdvAdapter(Context context, List<AdCustom> ltdata) {
			super(context, ltdata);
		}

		@Override
		protected IAfLayoutItem<AdCustom> getItemLayout(AdCustom data) {
			return new IAfLayoutItem<AdCustom>() {

				private ImageView mIvImage;
				private TextView mTvTip;
				private TextView mTvTitle;
				private TextView mTvFileSize;
				private TextView mTvContent;
				private TextView mTvDownload;
				private TextView mTvGivePoint;
				private RelativeLayout mRlLayout;
				
				@Override
				public void onHandle(AfView view) {
					mTvTip = view.findViewByID(R.id.advpitem_tip);
					mTvTitle = view.findViewByID(R.id.advpitem_title);
					mTvFileSize = view.findViewByID(R.id.advpitem_filesize);
					mTvContent = view.findViewByID(R.id.advpitem_content);
					mTvDownload = view.findViewByID(R.id.advpitem_download);
					mTvGivePoint = view.findViewByID(R.id.advpitem_givepoint);
					mIvImage = view.findViewByID(R.id.advpitem_image);
					mRlLayout = view.findViewByID(R.id.advpitem_layout);
					
					mTvDownload.setOnClickListener(AdvAdapter.this);
					if (AfApplication.getNetworkStatus() == AfNetworkEnum.TYPE_WIFI) {
						mRlLayout.setOnClickListener(AdvAdapter.this);
					}
				}

				@Override
				public void onBinding(AdCustom model,int index) {
					mRlLayout.setTag(model);
					mTvDownload.setTag(model);

					mTvTip.setText(model.Provider);
					mTvTitle.setText(model.Name);
					mTvContent.setText(model.Text);
					mIvImage.setImageBitmap(model.Icon);
					mTvDownload.setText(model.Action);
					mTvFileSize.setText(model.Filesize);
					
					AdvertAdapter adapter = AdvertAdapter.getInstance();
					String currency = adapter.getCurrency();
					String html = "送 <font color=\"#337FE5\">"+model.Points+"</font> "+currency;
					mTvGivePoint.setText(Html.fromHtml(html));
					
					if (adapter.isHide() || adapter.getChannel().equals("waps")) {
						mTvGivePoint.setVisibility(View.GONE);
					}else {
						mTvGivePoint.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public int getLayoutId() {
					return R.layout.wa_listitem_advpoetry;
				}
			};
		}

		@Override
		public void onClick(View v) {
			if (v.getTag() instanceof AdCustom) {
				final AdCustom info = AdCustom.class.cast(v.getTag());
				String title = "温馨提示";
				String question = "确定下载%s吗？";
				AdvertAdapter adapter = AdvertAdapter.getInstance();
				DialogInterface.OnClickListener download;
				DialogInterface.OnClickListener cancel;
				download = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						doDownloadAdv(info);
					}
				};
				cancel = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AfApplication.getApp().onEvent(WpEvent.WP_DONWLOAD_CANCEL_MAIN);
					}
				};
				if (adapter.isHide()) {
					doShowDialog(title, String.format(question,info.Name)
							,"下载",download,"取消",cancel);
//					return;
				} else {
					doShowDialog(title, String.format(question,info.Name)+
							DS.d("148f22dd2efbc20caece145c579fa73a1f0b1f5b4a3a253bc4d14f07" +
									"eefddddaaf54e072b15f08d342895d73bee55f0cf4e83a7fb6456" +
									"b5ed3d0177ffa1409cb1f6980e529583c25d7b9bf35aaecc00373" +
									"3579698944d66a11daa275a6089761ae77896fd89eadce3f029c2" +
									"4c241eb9e5352d4ea3e34a3f890aa0178d1c0f2a5d06572e3d7f5" +
									"c1c7") + adapter.getCurrency()
							,"下载",download,"取消",cancel);
				}
//				doDownloadAdv(info);
			}
		}

		protected void doDownloadAdv(AdCustom info) {
			AdvertAdapter adapter = AdvertAdapter.getInstance();

			Apache connect = Apache.getInstance(getActivity());
			AbActivity.event(getActivity(), "downloadAd.poetry", info.Name);
			connect.downloadAd(getActivity(), info.Id);
//			AttractStatistics.doStaticsPoint();
//			AttractPointKernel.doStatisticsAdInfo(info);
			PointKernelMain.doStatisticsAdInfo(info);
			//触发统计下载事件
			AfApplication.getApp().onEvent(WpEvent.WP_POINT_MAIN_DONWLOAD);
			if (!adapter.isHide()) {
				//makeToastLong("软件正在下载中，请在下载完成之后半小时之内安装并打开30秒以上（并确保网络连接），才能获得"+adapter.getCurrency());
//				makeToastLong(DS.d("7a02ad1ae8010ef5f8" +
//						"7f5123be1adff8d75eee3c3a4f676d792671c10dc" +
//						"038f396ea164b84c6ab496edbae6dfcfed87181d5" +
//						"2cdb8209ca41d8ab507a8d9a89b3797e70ddfde7" +
//						"387a2116da783575eefad6d2c13645d1e0d3227a" +
//						"270b3e8d7e904bff3278f78cc963277f0d301c9463" +
//						"c2f661f52f6c1e47bf86e4b8d2d6a38b9fa21286ae" +
//						"3c752efc") + adapter.getCurrency());
			}
		}
		
	}
}
