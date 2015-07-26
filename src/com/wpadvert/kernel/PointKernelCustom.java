package com.wpadvert.kernel;

import com.andadvert.model.AdCustom;

public class PointKernelCustom{

	static PointKernel kernel = new PointKernel("93089433021020214102");

	public static final int DEFAULE_POINT = PointKernel.DEFAULE_POINT;

	public static boolean doStatisticsAdInfo(AdCustom info) {
		return kernel.doStatisticsAdInfo(info);
	}

	public static int getPoint() {
		return kernel.getPoint();
	}

	public static int spendPoints(int spend) {
		return kernel.spendPoints(spend);
	}

	public static int awardPoints(int award) {
		return kernel.awardPoints(award);
	}

}
