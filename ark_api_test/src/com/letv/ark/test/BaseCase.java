package com.letv.ark.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.letv.ark.util.Constant;
import com.letv.ark.util.HttpClientUtil;
import com.letv.ark.util.ParamUtils;
public class BaseCase {
	public Map<String, String> http_param;
	public static HttpClientUtil httpClientUtil;
	private static String host = Constant.API_ADDRESS;
	// xml文件对应的doc对象
	private static Document doc;
	// 生成XPath对象
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("-------------本次测试开始------------");
	}

	@AfterSuite
	public void afterSuite() {
		// 释放资源
		System.out.println("-------------本次测试结束------------");
	}

	/**
	 * 获取document对象  
	 * @param json  json串
	 * @return
	 */
	private Document getDocument(String json) {

		Document doc = null;
		try {
			InputSource is = new InputSource(new StringReader(json));
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
	
	/**
	 *  获取document对象
	 * @param xmlFile  xml文件
	 * @return
	 */
	private Document getDocument(File xmlFile) {
		Document doc = null;
		try {
			InputStream is = new FileInputStream(xmlFile);
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
	
	/**
	 * 获取参数列表和校验节点Map
	 * @param xmlFile 测试用例文件名
	 * @param nodeXpath 
	 * 		     参数列表xpath表示//Param; 结果列表xpath表示为//Res
	 * @return
	 */
	public Map<String, String> getNodeMap(File xmlFile, String nodeXpath) {
		Map<String, String> params = new HashMap<String, String>();
		String key, value;
		try {
			doc = getDocument(xmlFile);
			XPathExpression expr = xpath.compile(nodeXpath);
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				key = nodes.item(i).getAttributes().getNamedItem("Name").getNodeValue();
				value = nodes.item(i).getTextContent();
	//			System.out.println("Parameter = " + key +":"+ value);
				params.put(key, value);
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} 
		return params;
	}
	
	/**
	 * 通过xpath获取xml文件的节点值
	 * @param xmlFile 测试用例文件名
	 * @param nodeXpath 节点的xpath路径
	 * @return
	 */
	public  String getNodeValue(File xmlFile, String nodeXpath) {
		String value = "";
		try {
			doc =getDocument(xmlFile);
			value = (String) xpath.evaluate(nodeXpath, doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 通过xpath获取返回值的节点值
	 * @param response
	 * @param nodeXpath
	 * @return
	 */
	public String getNodeValue(String response, String nodeXpath) {
		String value = "";
		try {
			doc = getDocument(response);
			value = (String) xpath.evaluate(nodeXpath, doc, XPathConstants.STRING);
		}catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 发生get请求
	 * @param api
	 * @return
	 */
	public String httpGet(String api) {
		String httpUrl = host +api;
		System.out.println("接口请求地址-----" + httpUrl);
		String resp = httpClientUtil.sendHttpsGet(httpUrl);
		System.out.println("接口返回值------"+resp);
		return resp;
	}
	
	/**
	 * 发送post请求
	 * @param api
	 * @param maps
	 * @return
	 */
	public String httpPost(String api, Map<String, String> maps) {
		String httpUrl = host +api;
		System.out.println("传入参数列表-----" + ParamUtils.map2Str(maps));
		System.out.println("接口请求地址-----" + httpUrl);
		String resp = httpClientUtil.sendHttpPost(httpUrl, maps);
		System.out.println("接口返回值------"+resp);
		return resp;
	}
	
	/**
	 * 随机生成字符串
	 * @param length
	 * @return
	 */
	public String getRandomStr(int length){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
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

}
