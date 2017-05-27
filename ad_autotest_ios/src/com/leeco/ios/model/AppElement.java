package com.leeco.ios.model;

import org.openqa.selenium.By;

/**
 * 元素列表 eg. 按钮、输入框、文本
 * 
 * @author haoxiaosha
 *
 */
public class AppElement {
	
	//播放记录button
//	public By record =  By.id("play record");
	
	public By record = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeNavigationBar[1]/XCUIElementTypeOther[2]/XCUIElementTypeButton[1]");
	
	//播放记录第一条
	public By recordIndex1 = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeButton[2]");

	/**
	 * 乐看搜索
	 */
	public By searchBox = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeNavigationBar[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
//	public By searchBox = By.id("main top search");

	/**
	 * 乐看搜索输入框
	 */
	public By searchInputBox = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeNavigationBar[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextField[1]");

	/**
	 * 搜索页-取消
	 */
//	public By searchCancel = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[2]");
	public By searchCancel = By.id("取消");

	
	/**
	 * 键盘上的搜索button
	 */
//	public By searchButton = By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAKeyboard[1]/UIAButton[4]");	
	public By searchButton = By.id("确认");
	public By searchButton1 = By.id("搜索");
	
	//播放
	public By play = By.id(" 播放");
	//播放第一集
	public By num1 = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[2]/XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeButton[1]");	
	
	/**
	 * 首页
	 */
//	public By home = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[2]");
	public By home =  By.id("首页");
	
	/**
	 * 发现
	 */
	public By find = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[5]");
//	public By find =  By.id("发现");
	
	/**
	 * 我的
	 */
//	public By my = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[6]");
	public By my =  By.id("我的");
	
	/**
	 * 发现页-专题
	 */
	public By topic = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[1]/UIAButton[2]");
	
	/**
	 * 专题页-列表第5个是广告
	 */
	public By topicAd = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[5]");
	
	/**
	 * 专题页-列表第5个-详情页标题
	 */
	public By topicAdText = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAStaticText[1]");

	/**
	 * 专题页-列表第5个-详情页webview
	 */
	public By topicAdView = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAWebView[1]");

	/**
	 * 我的-设置-返回/广告-返回/专题-返回
	 */
//	public By back = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIANavigationBar[1]/UIAButton[1]");
	public By back = By.id("nav back normal");

	/**
	 * 播放页-全屏按钮
	 */
//	public By fullScreen = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[3]");
	public By fullScreen = By.id("btn fullscreen");
	
	/**
	 * 播放页-跳过广告（广告-右上角，广告秒数）
	 */
	public By adskip = By.id("adskip full background");
	
	/**
	 * 播放页-了解详情（广告-左下角）
	 */
	public By detail = By.id("了解详情>");
	
	/**
	 * 广告-详情页-分享按钮
	 */
	public By ad_more = By.id("moreicon");
	
	/**
	 * 广告-详情页-view
	 */
	public By ad_view = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAWebView[1]");
	
	
	/**
	 * 播放页-声音（广告-右下角）
	 */
	public By volume = By.id("btnvolumenormal normal");
	
	
	/**
	 * 播放页-广告半屏-返回
	 */
	public By playHalfBack = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]/XCUIElementTypeButton[1]");
//	public By playHalfBack = By.id("half back normal");
	
	//半屏广告倒计时image
	public By playHalfImage = By.xpath("//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]/XCUIElementTypeImage[1]");
	
	
	/**
	 * 播放页-全屏-暂停按钮
	 */
//	public By playPause = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[20]");
	public By playPause = By.id("pause normal");
	
	/**
	 * 播放页-全屏-角标广告摇一摇按钮
	 */
	public By playConner = By.id("\"摇一摇\" 去广告");
	
	
	/**
	 * 播放页-半屏-暂停广告
	 */
	public By playHalfPauseAd = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[18]");
	
	
	/**
	 * 播放页-半屏-暂停广告-关闭按钮
	 */
	public By playHalfPauseAdClose = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAButton[35]");
	
	
	/**
	 * 播放页-电视剧-返回
	 */
	public By playBack = By.id("top back normal");
	
	/**
	 * 我的-设置
	 */
//	public By setting = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[13]/UIAStaticText[1]");
	public By setting = By.id("设置");
	
	/**
	 * 我的-设置-清除应用缓存
	 */
//	public By clear = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[2]/UIATableView[1]/UIATableCell[6]/UIAStaticText[1]");
	public By clear = By.id("清除应用缓存");
	
	/**
	 * 我的-设置-清除应用缓存-确定
	 */
//	public By ok = By.xpath("//UIAApplication[1]/UIAWindow[4]/UIAAlert[1]/UIACollectionView[1]/UIACollectionCell[2]/UIAButton[1]");
	public By ok = By.id("确定");
	
	public By item1=By.id("1");
	public By item2=By.id("2");
	public By item3=By.id("3");
	public By item4=By.id("4");
	public By item5=By.id("5");
	public By item6=By.id("6");
	
	
}
