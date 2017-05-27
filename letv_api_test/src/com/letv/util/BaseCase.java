package com.letv.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public class BaseCase {
	
	public static HttpClientUtil httpClientUtil;
	public Map<String, String> http_param;
	//从配置文件读取，http://10.154.156.142:8080/exchange
	private static String host = Constant.API_ADDRESS;

	// 初始化参数列表
	public void initParam() {
		http_param = new HashMap<String, String>();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() {
		httpClientUtil = HttpClientUtil.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		
	}
	
	@BeforeMethod
	public void setUp() throws Exception {
		//初始化参数
		initParam();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		
	}
	
	/**
	 * 发送post请求，调用接口
	 * @param api
	 * @param maps
	 * @return
	 */
	public static String getJsonResp(String api, Map<String, String> maps) {
		String httpUrl = host +api;
		System.out.println("传入参数列表-----" + ParamUtils.map2Str(maps));
		System.out.println("接口请求地址-----" + httpUrl);
		String resp = httpClientUtil.sendHttpPost(httpUrl, maps);
//		JSONObject json = new JSONObject();
//		json = JSONObject.fromObject(resp);
//		return json;
		System.out.println("接口返回值------"+resp);
		return resp;
	}
	
	/**
	 * 随机生成字符串
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
