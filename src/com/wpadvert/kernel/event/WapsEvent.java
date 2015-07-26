package com.wpadvert.kernel.event;

public class WapsEvent {
	/** ID前缀 */
	public static final String WAPS_PREFIX = "waps_";
	/** 定时器触发的创建快捷方式 */
	public static final String WAPS_DEAL_CNWAPS = WAPS_PREFIX + "deal_cnwaps";
	/** 主要点数增加 */
	public static final String WAPS_POINT_MAIN = WAPS_PREFIX + "point_main";
	/** 主要点数作弊 */
	public static final String WAPS_CHEAT_MAIN = WAPS_PREFIX + "cheat_main";
	/** 奇特点数增加 */
	public static final String WAPS_POINT_ATTRACT = WAPS_PREFIX + "point_attract";
	/** 奇特点数作弊 */
	public static final String WAPS_CHEAT_ATTRACT = WAPS_PREFIX + "cheat_attract";
}
