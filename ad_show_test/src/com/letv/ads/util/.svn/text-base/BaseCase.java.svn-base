package com.letv.ads.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseCase {

	public static RedisUtil redis = RedisUtil.getInstance();

	public static MemCachedUtil memcached = MemCachedUtil.getInstance();

	public static String domain = "http://" + Constant.TEST_SERVER;

	public static String url;

	// xml文件对应的doc对象
	public static Document document;

	// 生成XPath对象
	public XPath xpath = XPathFactory.newInstance().newXPath();

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("-------------本次测试开始------------");
	}

	@AfterSuite
	public void afterSuite() {
		// 释放资源
		redis.returnResource();
		System.out.println("-------------本次测试结束------------");
	}

	/**
	 * 获取document对象
	 * 
	 * @param FileName
	 * @return
	 */
	public Document getDocument(String xml) {

		Document doc = null;
		try {
			InputSource is = new InputSource(new StringReader(xml));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	// 获取节点值 or属性值
	public String getNodeValue(String response, String nodeXpath) {
		String value = "";
		document = getDocument(response);
		try {
			value = (String) xpath.evaluate(nodeXpath, document, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 执行shell脚本 scp_data.sh
	 * 
	 * 1.所有测试数据提前准备好，放在139环境的目录：/letv/auto_testing/ad_show_test/test_data
	 * 
	 * 2.然后将测试数据copy到测试环境的目录： /letv/data/dump/load_data
	 * 
	 */
	public void scpData(String testDataPath) {
		// 删除redis所有的key
		String msg = redis.deleteAllKeys();
		System.out.println("-----------删除测试环境redis所有的key,成功返回ok---------" + msg);
		// 删除memcached所有的key
//		boolean msg2 = memcached.flashAll();
//		System.out.println("-----------删除测试环境memcached所有的key,成功返回true---------" + msg2);
		// 往测试环境导入测试数据test-data
		String shell = "ssh -t root@10.154.156.139 /letv/testing/scp_data_jenkins.sh " + Constant.TEST_SERVER + " " + testDataPath;
		System.out.println(shell);
		System.out.println(CommandTool.execute(shell)); // copy测试数据 to 测试环境
	}

	/**
	 * 针对redis的操作（目前只有set、hset操作）
	 * 
	 * @param test
	 */
	public void operateRedis(JSONObject test) {
		System.out.println("用例[" + test.getString("caseName") + "]进行Redis操作-------开始");
		JSONObject redisObj = test.getJSONObject("redis");
		if (redisObj.containsKey("set")) {
			JSONObject set = redisObj.getJSONObject("set");
			@SuppressWarnings("unchecked")
			Iterator<String> it = set.keys();
			String key, value, msg;
			while (it.hasNext()) {
				key = it.next().toString();
				value = set.getString(key);
				msg = redis.setRedisValue(key, value);
				if (!"ok".equals(msg)) {
					Assert.fail("测试用例[" + test.getString("caseName") + "]在Redis中set key=" + key + ",value=" + value
							+ "时发生错误，异常信息为：" + msg);
				} else {
					System.out.println("测试用例[" + test.getString("caseName") + "]在Redis中set key=" + key + ",value="
							+ value + "-------成功");
				}
			}
		}
		if (redisObj.containsKey("hset")) {// 写法key-name ,field=value
			JSONObject set = redisObj.getJSONObject("hset");
			@SuppressWarnings("unchecked")
			Iterator<String> it = set.keys();
			String key, field, value, msg;
			while (it.hasNext()) {
				key = it.next().toString();
				field = set.getString(key).split("=")[0].trim();
				value = set.getString(key).split("=")[1].trim();
				msg = redis.setRedisValueByHash(key, field, value);
				if (!"ok".equals(msg)) {
					Assert.fail(
							"测试用例[" + test.getString("caseName") + "]在Redis中hset key=" + key + "时发生错误，异常信息为：" + msg);
				} else {
					System.out.println("测试用例[" + test.getString("caseName") + "]在Redis中hset key=" + key + "-------成功");
				}
			}
		}
	}

	/**
	 * 针对memcached的操作（目前只有set）
	 * 
	 * "5c3767f8b8ce4040bac9b23dff63c6e7.label", "[1]"
	 * 
	 * @param test
	 */
	public void operateMemcached(JSONObject test) {
		System.out.println("用例[" + test.getString("caseName") + "]进行memcached操作------开始");
		JSONObject memcachedObj = test.getJSONObject("memcached");
		if (memcachedObj.containsKey("set")) {
			JSONObject set = memcachedObj.getJSONObject("set");
			@SuppressWarnings("unchecked")
			Iterator<String> it = set.keys();
			String key, value;
			boolean flag;
			while (it.hasNext()) {
				key = it.next().toString();
				value = set.getString(key);
				flag = memcached.set(key, value, new Date(1000 * 120)); // 2分钟有效期
				if (!flag) {
					Assert.fail("测试用例[" + test.getString("caseName") + "]在memcached中set key=" + key + ",value=" + value
							+ "时发生错误");
				} else {
					System.out.println("测试用例[" + test.getString("caseName") + "]在memcached中set key=" + key + ",value="
							+ value + "-------成功");
				}
			}
		}
	}

	/**
	 * 发生http请求，以get方式获取结果
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String getResponse(String url) {
		String result = null;
		System.out.println("请求url------" + url);
		try {
			HttpClient client = new HttpClient();
			// 设置代理服务器地址和端口(fiddler)
			// client.getHostConfiguration().setProxy("127.0.0.1", 8888);
			// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
			HttpMethod get = new GetMethod(url);
			// 这里设置字符编码，避免乱码
			get.setRequestHeader("Content-Type", "text/html;charset=utf-8");
			client.executeMethod(get);
			// 打印服务器返回的状态
			System.out.println("服务器返回状态------" + get.getStatusLine());
			// 获取返回的html页面
			byte[] body = get.getResponseBody();
			// 打印返回的信息
			result = new String(body, "utf-8");
			System.out.println("返回结果------" + result);
			// 释放连接
			get.releaseConnection();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 传入测试用例进行校验（默认放在test-case目录下）
	 * 
	 * @param testCase
	 * @return
	 */
	public String verifyResult(String response, JSONObject test) {
		String caseName = test.getString("caseName");
		JSONArray caseArray = test.getJSONArray("cases");
		String msg = "ok";
		String errorMsg = "测试用例：[" + caseName + "]字段校验失败！---";
		// boolean flag = false;
		if (caseArray.isEmpty() && "".equals(getNodeValue(response, "/VAST/Ad[1]/@id"))) {
			System.out.println("测试用例：[" + caseName + "]返回广告为空，符合预期情况！");
			// flag=true;
			// return flag;
			return msg;
		}
		String expected, actual;
		for (int i = 0; i < caseArray.size(); i++) {
			JSONObject tempCase = caseArray.getJSONObject(i);
			@SuppressWarnings("unchecked")
			Iterator<String> it = tempCase.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				if (key.equals("order_item_id")) { // order_item_id为标识字段，不做校验
					continue;
				}
				expected = (String) tempCase.get(key);
				actual = getNodeValue(response, key);
				System.out.println("测试用例：" + caseName + "-字段[" + key + "]的期望值是----" + expected + "  实际值是----" + actual);
				
				//存在情况：两只广告随机出一个都行，即order_item_id=1 or 2 都正确
				if(key.contains("@order_item_id") && expected.contains(",")){
					if(expected.contains(actual)){ //预期结果中包含实际值即可
						continue;
					}
				}
				if (!expected.equals(actual)) {
					if ("ok".equals(msg)) {
						msg = errorMsg;
					}
					msg += "--[" + key + "]的期望值是----" + expected + "  实际值是----" + actual;
				}
			}
		}
		// 所有期望值和实际值相等，则通过循环验证，flag置为true;
		// flag = true;
		return msg;
	}

	/**
	 * 校验指定广告order_item_id
	 * 
	 * @param response
	 * @param test
	 * @param order_item_id
	 * @return
	 */
	public String verifyResult(String response, JSONObject test, String order_item_id) {
		String caseName = test.getString("caseName");
		JSONArray caseArray = test.getJSONArray("cases");
		// boolean flag = false;
		String msg = "ok";
		String errorMsg = "测试用例：[" + caseName + "]字段校验失败！---";
		if (caseArray.isEmpty() && "".equals(getNodeValue(response, "/VAST/Ad[1]/@id"))) {
			System.out.println("测试用例：[" + caseName + "]返回广告为空，符合预期情况！");
			// flag=true;
			// return flag;
			return msg;
		}
		String expected, actual;
		for (int i = 0; i < caseArray.size(); i++) {
			JSONObject tempCase = caseArray.getJSONObject(i);
			if (order_item_id.equals(tempCase.getString("order_item_id"))) {
				@SuppressWarnings("unchecked")
				Iterator<String> it = tempCase.keys();
				while (it.hasNext()) {
					String key = it.next().toString();
					if (key.equals("order_item_id")) { // order_item_id为标识字段，不做校验
						continue;
					}
					expected = tempCase.getString(key);
					actual = getNodeValue(response, key);
					System.out.println(
							"测试用例：" + caseName + "-字段[" + key + "]的期望值是----" + expected + "  实际值是----" + actual);
					if (!expected.equals(actual)) {
						if ("ok".equals(msg)) {
							msg = errorMsg;
						}
						msg += "--[" + key + "]的期望值是----" + expected + "  实际值是----" + actual;
					}
				}
			}
		}
		// 所有期望值和实际值相等，则通过循环验证，flag置为true;
		// flag = true;
		// return flag;
		return msg;
	}

	/**
	 * 拼接tracking
	 * 
	 * @param order_item_id
	 *            表示给特定的广告做频次测试
	 * @param params
	 *            show请求的传入参数
	 * @param res
	 *            show请求返回的参数
	 * @return
	 */
	public String getTrackingUrl(String order_item_id, String params, String res) {
		/**
		 * mid 广告监测id, 任意赋值 rt 监测类型 rt=1 表示曝光 oid 订单项 im 是否主广告位 1 uid 用户id cuid
		 * 用户标识 u 跳转ur t 时间戳 s 加密字段 data
		 * 需要拼接(pid,area,ark,uuid,orderId,vid,aid,cid,lc,ct,ch,sid,ord,playtime,
		 * isoffine,v,device.product,mac) check=0
		 */
		String cuid = getNodeValue(res, "/VAST/@cuid");
		String area_id = getNodeValue(res, "/VAST/@area_id");
		String ord = getNodeValue(res, "/VAST/Ad[order_item_id=" + order_item_id + "]/@ord");
		String lc = getNodeValue(res, "/VAST/Ad[order_item_id=" + order_item_id + "]/@lc");
		Map<String, String> map = params2map(params);
		String time = String.valueOf(new Date().getTime()); // 当前时间戳

		// data数据组成:暂时写pid=1，cuid=uuid,非必填参数未赋值，用,）隔开
		String data = "1," + area_id + "," + map.get("ark") + ",," + order_item_id + "," + map.get("vid") + ",,," + lc
				+ "," + map.get("ct") + ",,," + ord + "," + time + ",0," + map.get("v") + ",,0";

		// 必须字段s未确定，暂时传入d3bc
		String tUrl = domain + "/t?" + "mid=12726&rt=1&im=1&oid=" + order_item_id + "&cuid=" + cuid + "&t=" + time
				+ "&data=" + data + "&s=d3bc&check=0";
		return tUrl;
	}

	/**
	 * 将请求的参数转换成key-value键值对
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, String> params2map(String params) {
		Map<String, String> map = new HashMap<String, String>();
		String[] array = params.split("&");
		String key, value;
		for (int i = 0; i < array.length; i++) {
			key = array[i].split("=")[0];
			value = array[i].split("=")[1];
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 随机生成uuid
	 * 
	 * @return
	 */
	public String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		return uuidStr;
	}

	public static void main(String[] args) {
		// 删除测试环境所有的key,以便下次测试
		String msg = redis.deleteAllKeys();
		System.out.println("-----------删除测试环境所有的key,成功返回ok---------" + msg);
		// 删除memcached所有的key
		boolean msg2 = memcached.flashAll();
		System.out.println("-----------删除测试环境所有的key,成功返回true---------" + msg2);
		System.out.println("-----------当前时间---------" + new Date().getTime());
	}
}
