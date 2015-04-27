package com.wapsadvert.kernel.application;

import android.annotation.SuppressLint;

import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.thread.AfDispatch;
import com.wapsadvert.kernel.WapsAdapter;

public class WapsExceptionHandler extends AfExceptionHandler{

	@Override
	@SuppressLint("HandlerLeak")
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		Throwable oex = ex;
		boolean isWaps = false;
		while (!(ex instanceof UnsatisfiedLinkError) && ex.getCause() != null) {
			for (StackTraceElement element : ex.getStackTrace()) {
				if (element.toString().indexOf("cn.waps.") >= 0
						|| element.toString().indexOf("com.appoffer.") >= 0) {
					isWaps = true;
					break;
				}
			}
			ex = ex.getCause();
		}
		if (ex instanceof UnsatisfiedLinkError) {
			for (StackTraceElement element : ex.getStackTrace()) {
				if (isWaps || element.toString().indexOf("cn.waps.") >= 0
						|| element.toString().indexOf("com.appoffer.") >= 0) {
					AfPrivateCaches.getInstance().put(WapsAdapter.KEY_INITUNINSTALLAD, false);
					//�ɹ���������쳣�����ҳɹ���ֹ�ٴη���
					//handler(ex, "Deal Width UnsatisfiedLinkError");
					startForeground();
					return;
				}
			}
		}else if(isWaps){
			AfPrivateCaches.getInstance().put(WapsAdapter.KEY_ISWAPSWORKS, false);
			handler(ex, "Deal Width cn.waps.");
			startForeground();
		}
		super.uncaughtException(thread, oex);
	}

	private void startForeground() {
		// TODO Auto-generated method stub
		AfApplication.dispatch(new AfDispatch() {
			@Override
			protected void onDispatch() {
				// TODO Auto-generated method stub
				AfApplication.getApp().startForeground();
			}
		});
	}
	
	
}
