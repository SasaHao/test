package com.leeco.ios.test;

import org.testng.annotations.Test;

import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Log;

public class StartFocusAdTest extends BaseCase {

	@Test(description = "用例1-开机广告测试")
	public void testStartAd() throws InterruptedException {
		log = Log.getLogger(StartAdTest.class);
		log.info("开机广告测试------开始------");
		for (int i = 1; i < 101; i++) {
			log.info("-------------开启app，第"+i+"次---------------");
			Thread.sleep(5000);
			driver.closeApp();
			Thread.sleep(2000);
			driver.launchApp();
		}
		log.info("开机广告测试------结束------");
	}

	@Test(description = "开机广告&焦点图测试")
	public void testAd() throws InterruptedException {
		log = Log.getLogger(StartFocusAdTest.class);
		log.info("开机广告&焦点图测试------开始------");
		log.info("打开app");
		Thread.sleep(5000);
		for (int i = 1; i <= 100; i++) { // 点击焦点图的广告，并重启app
			log.info("滑动点击焦点图");

			log.info("关闭app，重新启动-----" + i);
			driver.closeApp();
			driver.launchApp();
			Thread.sleep(5000);
		}

		log.info("开机广告&焦点图测试------结束------");
	}
}
