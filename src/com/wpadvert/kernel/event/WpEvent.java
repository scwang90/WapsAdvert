package com.wpadvert.kernel.event;

public class WpEvent {
	/** ID前缀 */
	public static final String WP_PREFIX = "wp_";
	/** 定时器触发的创建快捷方式 */
	public static final String WP_DEAL_CNWP = WP_PREFIX + "deal_cnwp";
	/** 主要点数增加 */
	public static final String WP_POINT_MAIN = WP_PREFIX + "point_main";
	/** 主要点数作弊 */
	public static final String WP_CHEAT_MAIN = WP_PREFIX + "cheat_main";
	/** 奇特点数增加 */
	public static final String WP_POINT_ATTRACT = WP_PREFIX + "point_attract";
	/** 奇特点数作弊 */
	public static final String WP_CHEAT_ATTRACT = WP_PREFIX + "cheat_attract";
}
