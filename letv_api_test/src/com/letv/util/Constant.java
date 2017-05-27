package com.letv.util;

/**
 * 常量列表
 * @author haoxiaosha
 *
 */
public class Constant {

	//接口请求地址
	public static String API_ADDRESS = getProperty("API_ADDRESS");
	
	public static String getProperty(String key) {
		try {
			return ConfigManager.getInstance().getProperty(key);
		}catch(Exception e){
			e.printStackTrace();
		}
		return key;
	}	
}
