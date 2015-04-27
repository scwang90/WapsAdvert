package com.wapsadvert.kernel.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.andframe.activity.AfMainActivity;
import com.andframe.application.AfApplication;
import com.andframe.caches.AfPrivateCaches;
import com.andmail.NotiftyMail;
import com.andmail.NotiftyMail.SginType;
import com.attract.application.AttractApplication;
import com.wapsadvert.kernel.WapsAdapter;

public class WapsMainActivity extends AfMainActivity{

	@Override
	protected void onActivityCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		if (AttractActivity.attract(getIntent())) {
//			Intent intent = new Intent(this,AttractActivity.class);
//			String key = AttractActivity.KEY_URL;
//			intent.putExtra(key, getIntent().getStringExtra(key));
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//			intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
//			intent.addCategory(Intent.CATEGORY_LAUNCHER);
//			startActivity(intent);
//			this.finish();
//			return;
//		}
//		super.onActivityCreate(savedInstanceState);
		if (AfApplication.getApp().isDebug()) {
			mRoot.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					AttractApplication.getApp().sudoAttract();
					makeToastLong("嘿嘿");
					return false;
				}
			});
		}
//		ExceptionHandler.handler(new Throwable(), "异常测试");
//		throw new NullPointerException("异常测试");
	};
	
	
	@Override
	protected boolean onBackKeyPressed() {
		// TODO Auto-generated method stub
		String key = WapsAdapter.KEY_INITUNINSTALLAD;
		if (!AfPrivateCaches.getInstance().getBoolean(key, true)) {
			//initUninstall 初始化成功运作 收到相关通知邮件注释掉 通知发送
			//NotiftyMail.sendNotifty(SginType.TITLE, "Deal initUninstall", "success!");
		}else if(!AfPrivateCaches.getInstance().getBoolean(WapsAdapter.KEY_ISWAPSWORKS, true)){
			NotiftyMail.sendNotifty(SginType.TITLE, "Deal cn.wpas.", "success!");
		}
		return super.onBackKeyPressed();
	}
	
}
