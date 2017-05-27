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
	//�������ļ���ȡ��http://10.154.156.142:8080/exchange
	private static String host = Constant.API_ADDRESS;

	// ��ʼ�������б�
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
		//��ʼ������
		initParam();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		
	}
	
	/**
	 * ����post���󣬵��ýӿ�
	 * @param api
	 * @param maps
	 * @return
	 */
	public static String getJsonResp(String api, Map<String, String> maps) {
		String httpUrl = host +api;
		System.out.println("��������б�-----" + ParamUtils.map2Str(maps));
		System.out.println("�ӿ������ַ-----" + httpUrl);
		String resp = httpClientUtil.sendHttpPost(httpUrl, maps);
//		JSONObject json = new JSONObject();
//		json = JSONObject.fromObject(resp);
//		return json;
		System.out.println("�ӿڷ���ֵ------"+resp);
		return resp;
	}
	
	/**
	 * ��������ַ���
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
