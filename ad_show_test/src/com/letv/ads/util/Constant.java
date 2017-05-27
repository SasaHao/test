package com.letv.ads.util;

/**
 * 常量
 * @author haoxiaosha
 *
 */
public class Constant {

	//测试环境
	public static final String TEST_SERVER = ConfigManager.getInstance().getProperty("TEST_SERVER");
	
	//redis端口号
	public static final int REDIS_PORT = Integer.parseInt(ConfigManager.getInstance().getProperty("REDIS_PORT"));

	//memcached端口号
	public static final int MEMCACHED_PORT = Integer.parseInt(ConfigManager.getInstance().getProperty("MEMCACHED_PORT"));
	
	//需要运行的测试用例文件
	public static final String CASE_FILE = ConfigManager.getInstance().getProperty("CASE_FILE");
		
}
