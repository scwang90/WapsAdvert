package com.attract.activity;

public class AdvAttractActivity /*extends AfItemsActivity<AdCustom>*/ {

//	private long backTime = 0;
//
//	@Override
//	protected void onCreate(Bundle bundle) {
//		super.onCreate(bundle);
//		setContentView(R.layout.wa_layout_advattract);
//		AdvertAdapter.getInstance().initInstance(this);
//		String currency = AdvertAdapter.getInstance().getCurrency();//AdvAttractAdapter.getInstance().getCurrency();
//		$(R.id.titlebar_title).text("免费" + currency);
//	}
//
//	@Override
//	protected boolean onBackKeyPressed() {
//		backTime = System.currentTimeMillis();
//		int point = PointKernelAttract.getComPoint();
////		String currency = AdvAttractAdapter.getInstance().getCurrency();
//		if (point < 100 && PointKernelAttract.isDownloaded() && mAdapter != null && mAdapter.getCount() > 1) {
////			doShowDialog("温馨提示", String.format("    激活软件需要100%s，您目前只下载了一个软件不足以激活软件！", currency),"继续下载",
////			doShowDialog("温馨提示", String.format(DS.d("0c8bf7b5d9ad1cad8be1a337f94f0e9bc9cd226fff431410dc81663" +
////					"6a6119c874d621995e51d609c75bfed5b14723e13720af967b44cae5c4f92a0d4142619986ccd2b2b451282e" +
////					"b402dc5f6e2bac482fd11a07fbcf59580"), currency),"继续下载",
////					new DialogInterface.OnClickListener() {
////						@Override
////						public void onClick(DialogInterface dialog, int index) {
////							AdCustom ad = PointKernelAttract.getNewAdCustom(mAdapter.getList());
////							if (ad != null) {
////								$.cache().put(AttractFragmentBase.KEY_IMPORTUNE, false);
////								AdvAttractActivity.this.downloadAd(ad);
////							}
////							AdvAttractActivity.this.finish();
////						}
////					});
////			return true;
//		}
//		return super.onBackKeyPressed();
//	}
//
//	protected void downloadAd(AdCustom ad) {
//		AdvertAdapter.getInstance().downloadAd(getActivity(), ad);
//		//触发统计下载事件
//		$.event().post(new WpEvent(WpEvent.WP_POINT_ATTRACT_DONWLOAD));
////		AttractStatistics.doStaticsPoint();
//		PointKernelAttract.doStatisticsAdInfo(ad);
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		long now = System.currentTimeMillis();
//		if(now-backTime > 1000){
//			$.pager().finishAllActivity();
//		}
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		AdvertAdapter.getInstance().uninstallAd(this);
//	}
//
//	@Nullable
//	@Override
//	public List<AdCustom> onTaskLoadList(@Nullable Paging paging) throws Exception {
//		AdvertAdapter adapter = AdvertAdapter.getInstance();
//		List<AdCustom> ltdata = adapter.getAdCustomList(getActivity());
//		while (ltdata == null || ltdata.size() == 0) {
//			Thread.sleep(1000);
//			ltdata = adapter.getAdCustomList(getActivity());
//		}
//		return ltdata;
//	}
//
//	@NonNull
//	@Override
//	public ItemViewer<AdCustom> newItemViewer(int viewType) {
//		return new AfItemViewer<AdCustom>(R.layout.wa_listitem_advattract) {
//			@Override
//			public void onViewCreated() {
//				super.onViewCreated();
//				$(R.id.advitem_download).clicked((OnClickListener) v -> onItemClick(mModel, -1));
//			}
//			@Override
//			public void onBinding(AdCustom model, int index) {
//				String currency = AdvertAdapter.getInstance().getCurrency();//AdvAttractAdapter.getInstance().getCurrency();
//				$(R.id.advitem_tip).text(model.Filesize);
//				$(R.id.advitem_title).text(model.Name);
//				$(R.id.advitem_content).text(model.Text);
//				$(R.id.adbar_downtry).text("下载"+model.Action);
//				$(R.id.advitem_download).text("送"+model.Points+currency);
//
//				if (model.Icon != null) {
//					$(R.id.advitem_image).image(model.Icon);
//				} else if (!TextUtils.isEmpty(model.IconUrl)) {
//					$(R.id.advitem_image).image(model.IconUrl);
////				} else {
////						$(mIvImage).image(AfApp.get().getLogoId());
//				}
//			}
//		};
//	}
//
//	@Override
//	public void onItemClick(final AdCustom custom, int index) {
//		if (index == -1 || AfNetwork.getNetworkState(getContext()) == AfNetwork.TYPE_WIFI) {
//			$.dialog(getContext()).showDialog("温馨提示", "确定下载【"+custom.Name+"】吗？"/*+
//						DS.d("e4df9701b39819305900c7b52451fea31f6980e529583c25d7b9bf35aaecc" +
//								"0032ed01e1effcf3edc54e8ecc3d3ad203683cd8bea0ecc7171")+
//						AdvAttractAdapter.getInstance().getCurrency()*/
//					,"下载", (dialog, which) -> {
//						downloadAd(custom);
//						makeToastLong(DS.d("e4df9701b39819305900c7b52451fea31f6980e52" +
//								"9583c25d7b9bf35aaecc0032ed01e1effcf3edc54e8ecc3d3ad2" +
//								"03683cd8bea0ecc7171")+
//								AdvertAdapter.getInstance().getCurrency()//AdvAttractAdapter.getInstance().getCurrency()
//						);
//					},"取消", (dialog, which) -> $.event().post(new WpEvent(WpEvent.WP_DONWLOAD_CANCEL_ATTRACT)));
////				doShowDialog("温馨提示", "    软件正在下载中，请" +
////						"在下载完成之后半小时之内安装并打开30秒以上" +
////						"（并确保网络连接），才能获得"+
////						AdvAttractAdapter.getInstance().getCurrency());
////				doShowDialog("温馨提示", DS.d("7a02ad1ae8010ef5f8" +
////						"7f5123be1adff8d75eee3c3a4f676d792671c10dc" +
////						"038f396ea164b84c6ab496edbae6dfcfed87181d5" +
////						"2cdb8209ca41d8ab507a8d9a89b3797e70ddfde7" +
////						"387a2116da783575eefad6d2c13645d1e0d3227a" +
////						"270b3e8d7e904bff3278f78cc963277f0d301c9463" +
////						"c2f661f52f6c1e47bf86e4b8d2d6a38b9fa21286ae" +
////						"3c752efc")+
////						AdvAttractAdapter.getInstance().getCurrency());
//		}
//	}
//
//	@Override
//	public boolean setMoreShow(@NonNull TaskWithPaging task, @Nullable List<AdCustom> list) {
//		return false;
//	}
}
