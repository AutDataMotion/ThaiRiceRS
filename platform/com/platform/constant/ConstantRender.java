package com.platform.constant;

/**
 * 解析sql和param的类型
 * @author DHJ
 */
public interface ConstantRender {

	/**
	 * 解析sql和param的类型：beetl
	 */
	public static final String sql_renderType_beetl = "beetl";

	/**
	 * 解析sql和param的类型：velocity
	 */
	public static final String sql_renderType_velocity = "velocity";

	/**
	 * 解析sql和param的类型：freeMarker
	 */
	public static final String sql_renderType_freeMarker = "freeMarker";
	
	public static final String PATH_CTL_NAME = "pthc";
	public static final String PATH_VIEW_NAME = "pthv";
	
}
