package com.letv.ark.util;


/**
 * 常量
 * @author haoxiaosha
 *
 */
public class Constant {

	//测试环境
	public static final String API_ADDRESS = ConfigManager.getInstance().getProperty("API_ADDRESS");
	
	//需要运行的测试用例文件
	public static final String CASE_FILE = ConfigManager.getInstance().getProperty("CASE_FILE");
		
}
