package com.wpadvert.kernel.application;

import android.content.Context;
import android.content.Intent;

import com.andadvert.AdvertAdapter;
import com.attract.activity.AdvAttractActivity;
import com.attract.application.AdvAttractAdapter;
import com.wpadvert.kernel.PointKernelAttract;

public class WpAttractAdapter extends AdvAttractAdapter{

	@Override
	public void showAppOffers(Context context) {
		// TODO Auto-generated method stub
		super.showAppOffers(context);
		context.startActivity(new Intent(context, AdvAttractActivity.class));
	}
	
	@Override
	public void showGameOffers(Context context) {
		// TODO Auto-generated method stub
		super.showGameOffers(context);
		context.startActivity(new Intent(context, AdvAttractActivity.class));
	}
	
	@Override
	public void showOffers(Context context) {
		// TODO Auto-generated method stub
		super.showOffers(context);
		context.startActivity(new Intent(context, AdvAttractActivity.class));
	}
	
	@Override
	public void getPoints(Context context, AttPointsNotifier notifier) {
		// TODO Auto-generated method stub
		int point = PointKernelAttract.getPoint();
		String currency = getCurrency();
		notifier.getPoints(currency , point);
	}
	
	@Override
	public void spendPoints(Context context, int spend,
			AttPointsNotifier notifier) {
		// TODO Auto-generated method stub
		int point = PointKernelAttract.spendPoints(spend);
		String currency = getCurrency();
		notifier.getPoints(currency, point);
	}
	
	@Override
	public void awardPoints(Context context, int award,
			AttPointsNotifier notifier) {
		// TODO Auto-generated method stub
		int point = PointKernelAttract.awardPoints(award);
		String currency = getCurrency();
		notifier.getPoints(currency, point);
	}
	
	@Override
	public String getChannel() {
		// TODO Auto-generated method stub
		return AdvertAdapter.getInstance().getChannel();
	}
	
	@Override
	public String getCurrency() {
		// TODO Auto-generated method stub
		return "M币";//AdvertAdapter.getInstance().getCurrency();
	}
	
}