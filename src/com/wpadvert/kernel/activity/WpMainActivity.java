package com.wpadvert.kernel.activity;

public class WpMainActivity /*extends AfMainActivity*/ {

//	@Override
//	protected void onCreate(Bundle bundle, AfIntent intent) throws Exception {
//		super.onCreate(bundle, intent);
////		if (AttractActivity.attract(getIntent())) {
////			Intent intent = new Intent(this,AttractActivity.class);
////			String key = AttractActivity.KEY_URL;
////			intent.putExtra(key, getIntent().getStringExtra(key));
////			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////			intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
////			intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
////			intent.addCategory(Intent.CATEGORY_LAUNCHER);
////			startActivity(intent);
////			this.finish();
////			return;
////		}
////		super.onActivityCreate(savedInstanceState);
//		if (AfApp.get().isDebug()) {
//			mRootView.setOnLongClickListener(new View.OnLongClickListener() {
//				@Override
//				public boolean onLongClick(View v) {
//					AttractApplication.getApp().sudoAttract();
//					makeToastLong("嘿嘿");
//					return false;
//				}
//			});
//		}
////		ExceptionHandler.handler(new Throwable(), "异常测试");
////		throw new NullPointerException("异常测试");
//	}
//
//	@Override
//	protected boolean onBackKeyPressed() {
//		String key = WpAdapter.KEY_INITUNINSTALLAD;
//		if (!AfPrivateCaches.getInstance().getBoolean(key, true)) {
//			//initUninstall 初始化成功运作 收到相关通知邮件注释掉 通知发送
//			//NotiftyMail.sendNotifty(SginType.TITLE, "Deal initUninstall", "success!");
//		}else if(!AfPrivateCaches.getInstance().getBoolean(WpAdapter.KEY_ISWAPSWORKS, true)){
//			//NotiftyMail.sendNotifty(SginType.TITLE, "Deal cn.wpas.", "success!");
//			AfApp.get().onEvent(WpEvent.WP_DEAL_CNWP, "success!");
//		}
//		return super.onBackKeyPressed();
//	}
	
}
