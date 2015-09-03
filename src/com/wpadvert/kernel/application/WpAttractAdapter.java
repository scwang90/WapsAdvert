package com.wpadvert.kernel.application;

import android.content.Context;
import android.content.Intent;

import com.andadvert.AdvertAdapter;
import com.andadvert.PointStatistics;
import com.andadvert.util.DS;
import com.attract.activity.AdvAttractActivity;
import com.attract.application.AdvAttractAdapter;
import com.wpadvert.kernel.PointKernelAttract;

public class WpAttractAdapter extends AdvAttractAdapter{

	@Override
	public void showAppOffers(Context context) {
		super.showAppOffers(context);
		context.startActivity(new Intent(context, AdvAttractActivity.class));
	}
	
	@Override
	public void showGameOffers(Context context) {
		super.showGameOffers(context);
		context.startActivity(new Intent(context, AdvAttractActivity.class));
	}
	
	@Override
	public void showOffers(Context context) {
		super.showOffers(context);
		context.startActivity(new Intent(context, AdvAttractActivity.class));
	}
	
	@Override
	public void getPoints(Context context, AttPointsNotifier notifier) {
		int point = PointKernelAttract.getPoint();
		String currency = getCurrency();
		notifier.getPoints(currency , point);
	}
	
	@Override
	public void spendPoints(Context context, int spend,
			AttPointsNotifier notifier) {
		int point = PointKernelAttract.spendPoints(spend);
		String currency = getCurrency();
		notifier.getPoints(currency, point);
	}
	
	@Override
	public void awardPoints(Context context, int award,
			AttPointsNotifier notifier) {
		int point = PointKernelAttract.awardPoints(award);
		String currency = getCurrency();
		notifier.getPoints(currency, point);
	}
	
	@Override
	public String getChannel() {
		return AdvertAdapter.getInstance().getChannel();
	}

	@Override
	public String getConfig(String key, String vdefault) {
		return super.getConfig(key, vdefault);
	}

	@Override
	public void initInstance(Context context) {
		super.initInstance(context);
		PointStatistics.stop();
		AdvertAdapter.getInstance().initInstance(context);
	}

	@Override
	public void uninstallAd(Context context) {
		super.uninstallAd(context);
		AdvertAdapter.getInstance().uninstallAd(context);
	}

	@Override
	public String getCurrency() {
		return DS.d("0a9cc801b7a36654");//AdvertAdapter.getInstance().getCurrency();
	}
	
}
