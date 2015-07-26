package com.wpadvert.kernel.application;

import android.annotation.SuppressLint;

import com.andframe.application.AfApplication;
import com.andframe.application.AfExceptionHandler;
import com.andframe.caches.AfPrivateCaches;
import com.andframe.thread.AfDispatch;
import com.wpadvert.kernel.WpAdapter;

public class WpExceptionHandler extends AfExceptionHandler{

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
					AfPrivateCaches.getInstance().put(WpAdapter.KEY_INITUNINSTALLAD, false);
					//成功处理这个异常，并且成功防止再次发生
					//handler(ex, "Deal Width UnsatisfiedLinkError");
					startForeground();
					return;
				}
			}
		}else if(isWaps){
			AfPrivateCaches.getInstance().put(WpAdapter.KEY_ISWAPSWORKS, false);
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
