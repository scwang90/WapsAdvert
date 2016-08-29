package com.attract.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andadvert.AdvertAdapter;
import com.andadvert.model.AdCustom;
import com.andadvert.util.DS;
import com.andframe.activity.AfActivity;
import com.andframe.activity.framework.AfView;
import com.andframe.adapter.AfListAdapter;
import com.andframe.application.AfApplication;
import com.andframe.constant.AfNetworkEnum;
import com.andframe.feature.AfIntent;
import com.andframe.layoutbind.AfFrameSelector;
import com.andframe.layoutbind.AfListItem;
import com.andframe.layoutbind.AfModuleProgress;
import com.andframe.layoutbind.AfModuleProgressImpl;
import com.andframe.layoutbind.AfModuleTitlebar;
import com.andframe.layoutbind.AfModuleTitlebarImpl;
import com.andframe.thread.AfHandlerTask;
import com.attract.application.AdvAttractAdapter;
import com.attract.kernel.AttractStatistics;
import com.wpadvert.R;
import com.wpadvert.kernel.Apache;
import com.wpadvert.kernel.PointKernelAttract;
import com.wpadvert.kernel.event.WpEvent;

import java.util.List;

public class AdvAttractActivity extends AfActivity implements OnClickListener {
	
	private ListView mListView = null;
	private AdvAdapter mAdapter = null;
	private AfModuleTitlebar mTitlebar = null;
	private AfModuleProgress mProgress = null;
	private AfFrameSelector mSelector = null;
	
	private long backTime = 0;

	@Override
	protected void onCreate(Bundle bundle, AfIntent intent) throws Exception {
		AdvertAdapter.getInstance().initInstance(this);
		
		super.onCreate(bundle, intent);
		setContentView(R.layout.wa_layout_advattract);
		mListView = findViewByID(R.id.advattract_list);

		String currency = AdvAttractAdapter.getInstance().getCurrency();
		mTitlebar = new AfModuleTitlebarImpl(this);
		mTitlebar.setTitle("免费"+currency);
		mTitlebar.setOnGoBackListener(this);
		mProgress = new AfModuleProgressImpl(this);
		mProgress.setDescription("正在加载");
		mSelector = new AfFrameSelector(this, R.id.advattract_frame);
		mSelector.selectFrame(mProgress);

//		mTitlebar.setOnGoBackListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				onBackKeyPressed();
//			}
//		});
		
		postTask(new LoadingTask());
	}
	
	@Override
	public void onClick(View v) {
		if (v != null && v.getId() == mTitlebar.getBtGoBackId()) {
			if (!onBackKeyPressed()) {
				this.finish();
			}
		}
	}
	
	@Override
	protected boolean onBackKeyPressed() {
		backTime = System.currentTimeMillis();
		int point = PointKernelAttract.getComPoint();
		String currency = AdvAttractAdapter.getInstance().getCurrency();
		if (point < 100 && PointKernelAttract.isDownloaded() && mAdapter != null && mAdapter.getCount() > 1) {
//			doShowDialog("温馨提示", String.format("    激活软件需要100%s，您目前只下载了一个软件不足以激活软件！", currency),"继续下载",
//			doShowDialog("温馨提示", String.format(DS.d("0c8bf7b5d9ad1cad8be1a337f94f0e9bc9cd226fff431410dc81663" +
//					"6a6119c874d621995e51d609c75bfed5b14723e13720af967b44cae5c4f92a0d4142619986ccd2b2b451282e" +
//					"b402dc5f6e2bac482fd11a07fbcf59580"), currency),"继续下载",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int index) {
//							AdCustom ad = PointKernelAttract.getNewAdCustom(mAdapter.getList());
//							if (ad != null) {
//								AfPrivateCaches.getInstance().put(AttractFragmentBase.KEY_IMPORTUNE, false);
//								AdvAttractActivity.this.downloadAd(ad);
//							}
//							AdvAttractActivity.this.finish();
//						}
//					});
//			return true;
		}
		return super.onBackKeyPressed();
	}

	protected void downloadAd(AdCustom ad) {
		//触发统计下载事件
		AfApplication.getApp().onEvent(WpEvent.WP_POINT_ATTRACT_DONWLOAD);
		Apache connect = Apache.getInstance(getActivity());
		connect.downloadAd(getActivity(), ad.Id);
		AttractStatistics.doStaticsPoint();
		PointKernelAttract.doStatisticsAdInfo(ad);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		long now = System.currentTimeMillis();
		if(now-backTime > 1000){
			AfApplication.getApp().exitForeground(this);
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
			/**
			 * 异常名称:
				class java.lang.IllegalStateException
			       异常信息:
				Cannot execute task: the task is already running.
			 */
//			ExceptionHandler.handle(e, "");
		}

		@Override
		protected boolean onHandle(/*Message msg*/) {
			if (mAdapter != null) {
				mListView.setAdapter(mAdapter);
			}else {
				makeToastLong("数据加载失败！");
			}
			mSelector.selectFrame(mListView);
			return false;
		}
	}

	private class AdvAdapter extends AfListAdapter<AdCustom> implements OnClickListener{

		public AdvAdapter(Context context, List<AdCustom> ltdata) {
			super(context, ltdata);
		}


		@Override
		protected IListItem<AdCustom> getListItem(AdCustom data) {
			return new AfListItem<AdCustom>() {

				private ImageView mIvImage;
				private TextView mTvTip;
				private TextView mTvTitle;
				private TextView mTvDowntry;
				private TextView mTvContent;
				private TextView mTvDownload;
				private RelativeLayout mRlLayout;
				
				@Override
				public void onHandle(AfView view) {
					mTvTip = view.findViewByID(R.id.advitem_tip);
					mTvTitle = view.findViewByID(R.id.advitem_title);
					mTvDowntry = view.findViewByID(R.id.adbar_downtry);
					mTvContent = view.findViewByID(R.id.advitem_content);
					mTvDownload = view.findViewByID(R.id.advitem_download);
					mIvImage = view.findViewByID(R.id.advitem_image);
					mRlLayout = view.findViewByID(R.id.advitem_layout);
					
					mTvDownload.setOnClickListener(AdvAdapter.this);
					if (AfApplication.getNetworkStatus() == AfNetworkEnum.TYPE_WIFI) {
						mRlLayout.setOnClickListener(AdvAdapter.this);
					}
				}

				@Override
				public void onBinding(AdCustom model,int index) {
					mRlLayout.setTag(model);
					mTvDownload.setTag(model);

					String currency = AdvAttractAdapter.getInstance().getCurrency();
					mTvTip.setText(model.Filesize);
					mTvTitle.setText(model.Name);
					mTvContent.setText(model.Text);
					mTvDownload.setText("送"+model.Points+currency);
					mTvDowntry.setText("下载"+model.Action);

					if (model.Icon != null) {
						$(mIvImage).image(model.Icon);
					} else if (!TextUtils.isEmpty(model.IconUrl)) {
						$(mIvImage).image(model.IconUrl);
					} else {
//						$(mIvImage).image(AfApplication.getApp().getLogoId());
					}
				}

				@Override
				public int getLayoutId() {
					return R.layout.wa_listitem_advattract;
				}
			};
		}

		@Override
		public void onClick(final View v) {
			if (v.getTag() instanceof AdCustom) {
				final AdCustom custom = AdCustom.class.cast(v.getTag());
				doShowDialog("温馨提示", "确定下载【"+custom.Name+"】吗？"/*+
						DS.d("e4df9701b39819305900c7b52451fea31f6980e529583c25d7b9bf35aaecc" +
								"0032ed01e1effcf3edc54e8ecc3d3ad203683cd8bea0ecc7171")+
						AdvAttractAdapter.getInstance().getCurrency()*/
						,"下载",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						downloadAd(custom);
						makeToastLong(DS.d("e4df9701b39819305900c7b52451fea31f6980e52" +
								"9583c25d7b9bf35aaecc0032ed01e1effcf3edc54e8ecc3d3ad2" +
								"03683cd8bea0ecc7171")+
								AdvAttractAdapter.getInstance().getCurrency());
					}
				},"取消",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AfApplication.getApp().onEvent(WpEvent.WP_DONWLOAD_CANCEL_ATTRACT);
					}
				});
//				doShowDialog("温馨提示", "    软件正在下载中，请" +
//						"在下载完成之后半小时之内安装并打开30秒以上" +
//						"（并确保网络连接），才能获得"+
//						AdvAttractAdapter.getInstance().getCurrency());
//				doShowDialog("温馨提示", DS.d("7a02ad1ae8010ef5f8" +
//						"7f5123be1adff8d75eee3c3a4f676d792671c10dc" +
//						"038f396ea164b84c6ab496edbae6dfcfed87181d5" +
//						"2cdb8209ca41d8ab507a8d9a89b3797e70ddfde7" +
//						"387a2116da783575eefad6d2c13645d1e0d3227a" +
//						"270b3e8d7e904bff3278f78cc963277f0d301c9463" +
//						"c2f661f52f6c1e47bf86e4b8d2d6a38b9fa21286ae" +
//						"3c752efc")+
//						AdvAttractAdapter.getInstance().getCurrency());
			}
		}
		
	}
}
