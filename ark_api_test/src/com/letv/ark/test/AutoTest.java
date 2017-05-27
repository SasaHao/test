package com.letv.ark.test;

import java.io.File;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AutoTest extends BaseCase {

	private File xmlFile;

	public AutoTest(String file) {
		this.xmlFile = new File(file);
	}

	@Test(description = "测试用例")
	public void test() {

		/**
		 * 针对 每个测试用例做测试 1、获取api的uri，参数列表等，拼接http请求 2、发送请求 3、校验返回值
		 */
		String response = null; // 存放接口返回值
		boolean flag = true; // 存放校验结果
		// 文件名
		String caseName = xmlFile.getName();
		// 用例描述
		String description = getNodeValue(xmlFile, "/Api/Description");
		// uri
		String uri = getNodeValue(xmlFile, "/Api/Uri");
		// 请求方式
		String method = getNodeValue(xmlFile, "/Api/Method");
		// 请求参数
		http_param = getNodeMap(xmlFile, "//Param");
		System.out.println("开始测试," + uri + ",用例描述:" + description);
		// 发送请求
		try {
			if ("post".equalsIgnoreCase(method)) {
				response = httpPost(uri, http_param);
			} else if ("get".equalsIgnoreCase(method)) {
				response = httpGet(uri);
			} else {
				System.out.println("请检查测试用例xml中配置的method值,暂时支持在get/post");
				return;
			}
			// 校验结果
			Map<String, String> expectMap = getNodeMap(xmlFile, "//Res"); // 预期结果map
			String actual;
			for (Map.Entry<String, String> entry : expectMap.entrySet()) {

				System.out.println("Key = " + entry.getKey() + ", expect Value = " + entry.getValue());
				actual = getNodeValue(response, entry.getKey());
				if (!entry.getValue().equals(actual)) {
					flag = false;
					// 期望值和实际值不相等
					System.out.println("节点：" + entry.getKey() + "的预期值是：" + entry.getValue() + ",实际值是：" + actual);
				}
			}
			
		} catch (Exception e) {
			Assert.fail("测试用例:" + caseName + "运行报错,报错信息为：=:" + e.getMessage());
		}finally{
			System.out.println("-----------------------------------");
			Assert.assertTrue(flag, "测试用例-" + caseName + ",测试未通过，请查看详细log");
		}
		

	}
}
