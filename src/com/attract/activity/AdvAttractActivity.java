package com.attract.activity;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
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
import com.andframe.caches.AfPrivateCaches;
import com.andframe.constant.AfNetworkEnum;
import com.andframe.feature.AfIntent;
import com.andframe.layoutbind.AfFrameSelector;
import com.andframe.layoutbind.AfModuleProgress;
import com.andframe.layoutbind.AfModuleProgressImpl;
import com.andframe.layoutbind.AfModuleTitlebar;
import com.andframe.thread.AfHandlerTask;
import com.appoffer.AppConnect;
import com.attract.application.AdvAttractAdapter;
import com.attract.fragment.AttractFragmentBase;
import com.attract.kernel.AttractStatistics;
import com.wapsadvert.R;
import com.wapsadvert.kernel.PointKernelAttract;

public class AdvAttractActivity extends AfActivity implements OnClickListener {
	
	private ListView mListView = null;
	private AdvAdapter mAdapter = null;
	private AfModuleTitlebar mTitlebar = null;
	private AfModuleProgress mProgress = null;
	private AfFrameSelector mSelector = null;

	@Override
	protected void onCreate(Bundle bundle, AfIntent intent) throws Exception {
		// TODO Auto-generated method stub
		AdvertAdapter.getInstance().initInstance(this);
		
		super.onCreate(bundle, intent);
		setContentView(R.layout.wa_layout_advattract);
		mListView = findViewByID(R.id.advattract_list);

		String currency = AdvAttractAdapter.getInstance().getCurrency();
		mTitlebar = new AfModuleTitlebar(this);
		mTitlebar.setTitle("免费"+currency);
		mTitlebar.setOnGoBackListener(this);
		mProgress = new AfModuleProgressImpl(this);
		mProgress.setDescription("正在加载");
		mSelector = new AfFrameSelector(this, R.id.advattract_frame);
		mSelector.SelectFrame(mProgress);

//		mTitlebar.setOnGoBackListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				onBackKeyPressed();
//			}
//		});
		
		postTask(new LoadingTask());
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v != null && v.getId() == AfModuleTitlebar.ID_GOBACK) {
			if (!onBackKeyPressed()) {
				this.finish();
			}
		}
	}
	
	@Override
	protected boolean onBackKeyPressed() {
		// TODO Auto-generated method stub
		int point = PointKernelAttract.getComPoint();
		String currency = AdvAttractAdapter.getInstance().getCurrency();
		if (point < 100 && PointKernelAttract.isDownloaded() && mAdapter != null && mAdapter.getCount() > 1) {
//			doShowDialog("温馨提示", String.format("    激活软件需要100%s，您目前只下载了一个软件不足以激活软件！", currency),"继续下载",
			doShowDialog("温馨提示", String.format(DS.d("0c8bf7b5d9ad1cad8be1a337f94f0e9bc9cd226fff431410dc81663" +
					"6a6119c874d621995e51d609c75bfed5b14723e13720af967b44cae5c4f92a0d4142619986ccd2b2b451282e" +
					"b402dc5f6e2bac482fd11a07fbcf59580"), currency),"继续下载",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int index) {
							// TODO Auto-generated method stub
							AdCustom ad = PointKernelAttract.getNewAdCustom(mAdapter.getList());
							if (ad != null) {
								AfPrivateCaches.getInstance().put(AttractFragmentBase.KEY_IMPORTUNE, false);
								AdvAttractActivity.this.downloadAd(ad);
							}
							AdvAttractActivity.this.finish();
						}
					});
			return true;
		}
		return super.onBackKeyPressed();
	}
	

	protected void downloadAd(AdCustom ad) {
		// TODO Auto-generated method stub
		AbActivity.event(this, "downloadAd.attract", ad.Name);
		AppConnect connect = AppConnect.getInstance(getActivity());
		connect.downloadAd(getActivity(), ad.Id);
		AttractStatistics.doStaticsPoint();
		PointKernelAttract.doStatisticsAdInfo(ad);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		AfApplication.getApp().exitForeground(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AdvertAdapter.getInstance().uninstallAd(this);
	}
	
	private class LoadingTask extends AfHandlerTask{

		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			super.onException(e);
			/**
			 * 异常名称:
				class java.lang.IllegalStateException
			       异常信息:
				Cannot execute task: the task is already running.
			 */
//			ExceptionHandler.handler(e, "");
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if (mAdapter != null) {
				mListView.setAdapter(mAdapter);
			}else {
				makeToastLong("数据加载失败！");
			}
			mSelector.SelectFrame(mListView);
			return false;
		}
	}

	private class AdvAdapter extends AfListAdapter<AdCustom> implements OnClickListener{

		public AdvAdapter(Context context, List<AdCustom> ltdata) {
			super(context, ltdata);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected IAfLayoutItem<AdCustom> getItemLayout(AdCustom data) {
			// TODO Auto-generated method stub
			return new IAfLayoutItem<AdCustom>() {

				private ImageView mIvImage;
				private TextView mTvTip;
				private TextView mTvTitle;
				private TextView mTvDowntry;
				private TextView mTvContent;
				private TextView mTvDownload;
				private RelativeLayout mRlLayout;
				
				@Override
				public void onHandle(AfView view) {
					// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub
					mRlLayout.setTag(model);
					mTvDownload.setTag(model);

					String currency = AdvAttractAdapter.getInstance().getCurrency();
					mTvTip.setText(model.Filesize);
					mTvTitle.setText(model.Name);
					mTvContent.setText(model.Text);
					mIvImage.setImageBitmap(model.Icon);
					mTvDownload.setText("送"+model.Points+currency);
					mTvDowntry.setText("下载"+model.Action);
				}

				@Override
				public int getLayoutId() {
					// TODO Auto-generated method stub
					return R.layout.wa_listitem_advattract;
				}
			};
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getTag() instanceof AdCustom) {
				downloadAd(AdCustom.class.cast(v.getTag()));
//				doShowDialog("温馨提示", "    软件正在下载中，请" +
//						"在下载完成之后半小时之内安装并打开30秒以上" +
//						"（并确保网络连接），才能获得"+
//						AdvAttractAdapter.getInstance().getCurrency());
				doShowDialog("温馨提示", DS.d("7a02ad1ae8010ef5f8" +
						"7f5123be1adff8d75eee3c3a4f676d792671c10dc" +
						"038f396ea164b84c6ab496edbae6dfcfed87181d5" +
						"2cdb8209ca41d8ab507a8d9a89b3797e70ddfde7" +
						"387a2116da783575eefad6d2c13645d1e0d3227a" +
						"270b3e8d7e904bff3278f78cc963277f0d301c9463" +
						"c2f661f52f6c1e47bf86e4b8d2d6a38b9fa21286ae" +
						"3c752efc")+
						AdvAttractAdapter.getInstance().getCurrency());
			}
		}
		
	}
}
