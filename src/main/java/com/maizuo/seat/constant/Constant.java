package com.maizuo.seat.constant;

/**
 * 整个应用通用的常量 <br>
 * <b>类描述:</b>
 * 
 * <pre>
 * |
 * </pre>
 * 
 * @see
 */
public class Constant<T> {
	/**
	 * 用户对象放到Session中的键名称
	 */
	public static final String USER_CONTEXT = "USER";

	/**
	 * 将登录前的URL放到Session中的键名称
	 */
	public static final String LOGIN_TO_URL = "toUrl";
	/**
	 * 默认的访问服务器路径
	 */
	public static final String DEFAULT_URL = "http://seat.maizuo.com";
	
	/**火凤凰*/
	public final static String PHOENIX_REQUEST_URL = "http://211.152.35.36:9106/Corp/DI_DataSender_V4.asmx?WSDL";
	public final static String PHOENIX_ACCESS_ACCOUNT = "SYLZ";
	public final static String PHOENIX_ACCESS_PASSWORD = "pv7un_l3_1";

	/**南方火凤凰*/
	public final static String PHOENIX_SOUTH_REQUEST_URL = "";
	public final static String PHOENIX_SOUTH_ACCESS_ACCOUNT = "SYLZ";
	public final static String PHOENIX_SOUTH_ACCESS_PASSWORD = "pv7un_l3_1";

}
