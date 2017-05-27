package com.letv.ads.util;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.sf.json.JSONObject;

public class AdTest extends BaseCase {

	@Test(description = "本地单独测试一个特定用例")
	public void test() {

		File _file = new File("test-case/public-tcl-023.txt");
		// 针对 每个测试用例做测试
		JSONObject test = FileTool.getTestCase(_file.getName());
		// 用例名称（描述）
		String caseName = test.getString("caseName");
		// 请求参数判断，cuid有时候需要传，有时候不需要，分情况
		String params = test.getString("params");
		
		/**
		 * tracking服务校验，不是show请求的广告测试，只需发送请求后校验response即可
		 * 
		 * **************tracking测试模块单独出来****************2016/6/6新增
		 */
		if (test.containsKey("flag") && "tracking".equals(test.getString("flag"))) {
			// 拼接tracking请求的url
			url = domain + params;
			String result = getResponse(url).toLowerCase().trim();  //tracking返回值
			String response = test.getString("response").toLowerCase().trim();    //期望的返回值
			Assert.assertEquals(result, response,"[" + caseName + "]测试未通过，tracking返回值["+result+"]与期望值["+response+"]不相等！");
			return;
		}
		/****************tracking测试模块结束*********************************/
		
		
		// 本地测试需要将数据手动sftp到250，准备好数据

		// 判断是否进行redis操作
		if (test.containsKey("redis")) {
			operateRedis(test);
		}

		// 判断是否进行memcached操作
		if (test.containsKey("memcached")) {
			operateMemcached(test);
		}

		// 拼接show请求的url
		if (!params.contains("cuid=")) {
			/**
			 * 频次测试，随机生成一个cuid，因为目前投的是1月出月2次，本次测试完，下次cuid在本月内无效 统一随机生成cuid，不用传入
			 */
			params += "&cuid=" + getUUID();
		}
		url = domain + "/s?" + params;

		/**
		 * 判断测试类型：1.普通广告 2.轮播 3.频次
		 */
		int freq = test.getInt("freq");
		int rotation = test.getInt("rotation");
		int total = test.getInt("total");

		if (rotation > 1 && total > 1) {// 轮播
			// 请求次数 = total*rotation,
			if (rotation == 2) {// 此处逻辑只适用于2轮播 rotation=2
				String temp = null; // 临时存放order_item_id,与下次广告的order_item_id进行对比
				for (int i = 0; i < total; i++) {
					for (int m = 0; m < rotation; m++) {// 每次请求得到的广告order_item_id不一样
						String response = getResponse(url);
						String order_item_id = getNodeValue(response, "/VAST/Ad[1]/@order_item_id");
						System.out.println("[" + caseName + "]轮播测试中，广告order_item_id是" + order_item_id);
						if (!order_item_id.equals(temp)) {
							temp = order_item_id;
						} else {
							Assert.fail("[" + caseName + "]轮播测试中，广告order_item_id+" + order_item_id + "重复出现，测试失败！");
						}
						// show返回值校验
						String msg = verifyResult(response, test, order_item_id);
						Assert.assertEquals(msg, "ok", "[" + caseName + "]测试未通过，部分字段未通过验证！");
					}
				}

			} else if (rotation > 2) {// rotation>2，只投一个广告，其他为空
				for (int i = 0; i < total; i++) {
					String temp = ""; // 存入每个广告的order_item_id
					for (int m = 0; m < rotation; m++) {// 每次请求得到的广告order_item_id不一样
						String response = getResponse(url);
						String order_item_id = getNodeValue(response, "/VAST/Ad[1]/@order_item_id");
						if (!order_item_id.isEmpty()) {
							// show返回值校验
							String msg = verifyResult(response, test, order_item_id);
							Assert.assertEquals(msg, "ok", "[" + caseName + "]测试未通过，部分字段未通过验证！");
							temp += order_item_id + ",";
						}
						System.out.println("轮播的所有广告拼接的order_item_id串：" + order_item_id);
					}
					if (temp.isEmpty()) {
						Assert.fail("[" + caseName + "]轮播测试中，广告order_item_id未出现，测试失败！");
					} else if (temp.split(",").length == 2) {// 只有一个order_item_id
						System.out.println("[" + caseName + "]轮播测试通过！");
					} else if (temp.split(",").length > 2) {
						Assert.fail("[" + caseName + "]轮播测试中，广告order_item_id重复出现，测试失败！");
					}
				}
			}

		} else if (freq > 0) {// 频次 通过订单项判断给哪个广告做频次
			if (!test.containsKey("order_item_id")) {
				Assert.fail("用例[" + caseName + "]广告频次测试，但未传入order_item_id，请传入正确的的测试用例！");
			}
			String order_item_id = test.getString("order_item_id");
			int freq_expect = freq;
			int freq_actual = 0;
			for (int j = 0; j < freq; j++) {
				String response = getResponse(url);
				// 获取返回的广告order_item_id
				String order_item_id2 = getNodeValue(response, "/VAST/Ad[1]/@order_item_id");
				if ("".equals(order_item_id2)) {
					Assert.fail("[" + caseName + "]测试未通过,该用例未返回广告,Ad节点为空！");
				}
				if (order_item_id.equals(order_item_id2)) {
					// show返回值校验
					String msg = verifyResult(response, test, order_item_id);
					Assert.assertEquals(msg, "ok", "[" + caseName + "]测试未通过，部分字段未通过验证！");
					// 发tracking
					String tUrl = getTrackingUrl(order_item_id, params, response);
					String tRes = getResponse(tUrl);
					if (tRes.trim().equals("OK")) {
						freq_actual++;
						System.out.println("[" + caseName + "]tracking 发送成功！");

					} else {
						System.out.println("[" + caseName + "]tracking 发送失败！");
					}
					if (freq_expect == freq_actual) {// 发送满频次数，跳出循环
						System.out.println("order_item_id=[" + order_item_id + "]的广告曝光 " + freq_actual + "次成功！");
						break;
					}
				} else {// 返回非频次广告
					freq++;
					// show返回值校验
					String msg = verifyResult(response, test, order_item_id2);
					Assert.assertEquals(msg, "ok", "[" + caseName + "]测试未通过，部分字段未通过验证！");
				}
				if (freq > 5) { // 5次还未出频次广告，跳出循环
					break;
				}
			}
			// 多发一次，1.刷到其他广告进行校验
			String response2 = getResponse(url);
			String order_item_id2 = getNodeValue(response2, "/VAST/Ad[1]/@order_item_id");
			if (!"".equals(order_item_id2) && !order_item_id.equals(order_item_id2)) {
				String msg = verifyResult(response2, test, order_item_id2);
				Assert.assertEquals(msg, "ok", "[" + caseName + "]测试未通过，部分字段未通过验证！");
			}
			// 2.刷不到频次广告order_item_id说明测试成功
			String adId = getNodeValue(response2, "/VAST/Ad[order_item_id=" + order_item_id + "]/@id");
			System.out.println("adId---------[" + adId + "]-----------------");
			Assert.assertEquals(freq_expect, freq_actual, "频次广告未请求成功，[" + caseName + "]频次测试未通过!");
			Assert.assertEquals(adId, "", "频次广告请求完毕，仍然出现[" + caseName + "]频次测试未通过!");
		} else {// 普通广告请求
			// 获取的vast串
			String response = getResponse(url);
			// show返回值校验
			String msg = verifyResult(response, test);
			Assert.assertEquals(msg, "ok", "[" + caseName + "]测试未通过，部分字段未通过验证！");
		}

		// 删除测试环境所有的key,以便下次测试
		// String msgInfo = redis.deleteAllKeys();
		// System.out.println("-----------删除测试环境所有的key,成功返回ok---------" +
		// msgInfo);
	}
}
