package com.wpadvert.kernel.application;

import android.app.Activity;

public class WpBackService {
	
	public interface IBackService{
		boolean SetBackground(Activity activity);
	}

	private static IBackService mIBackService;
	
	public static void bindService(IBackService service){
		mIBackService = service;
	}

	public static boolean SetBackground(Activity activity) {
		return mIBackService != null && mIBackService.SetBackground(activity);
	}

}
