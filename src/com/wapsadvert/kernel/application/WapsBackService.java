package com.wapsadvert.kernel.application;

import android.app.Activity;

public class WapsBackService {
	
	public interface IBackService{
		boolean SetBackground(Activity activity);
	}

	private static IBackService mIBackService;
	
	public static void bindService(IBackService service){
		mIBackService = service;
	}

	public static boolean SetBackground(Activity activity) {
		// TODO Auto-generated method stub
		if (mIBackService != null) {
			return mIBackService.SetBackground(activity);
		}
		return false;
	}

}