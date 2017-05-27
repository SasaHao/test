package com.leeco.ios.crash.test;

import org.testng.annotations.Test;

import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Constant;
import com.leeco.ios.util.Log;

public class CrashTest extends BaseCase {

	@Test(description = "用例1-开机广告测试",priority = 1)
	public void testStartApp() {
		log = Log.getLogger(CrashTest.class);
		log.info("开机广告测试------开始------");
		for (int i = 1; i < 50; i++) {
			log.info("-------------开启app，第" + i + "次---------------");
			sleep(5);
			driver.closeApp();
			sleep(2);
			driver.launchApp();
		}
		log.info("-------------开启app，第100次---------------");
		sleep(5);
		driver.closeApp();
		log.info("开机广告测试------结束------");
	}

	@Test(description = "用例2-刷电视剧广告请求",priority = 2)
	public void testPlayAd() {
		log = Log.getLogger(CrashTest.class);
		log.info("刷电视剧广告请求测试------开始------");
		for (int i = 1; i < 21; i++) {
			log.info("----------第" + i + "次-----------");
			sleep(5);
			log.info("点击【搜索】");
			// driver.findElement(searchBox).click();
			driver.tap(1, 127, 42, 1); // 坐标点击搜索框
			sleep(2);
			log.info("输入要搜索的电视名称");
			driver.findElement(searchInputBox).setValue(Constant.TV_NAME);
			log.info("点击键盘上的【确认】按钮");
			driver.findElement(searchButton).click(); // 确认
			log.info("点击键盘上的【搜索】按钮");
			driver.findElement(searchButton1).click(); // 搜索
			sleep(4); // 等待显示所有结果
			// for (int i = 1; i < 20; i++) {
			log.info("播放第一集");
			// driver.tap(1, 27, 305, 1);
			// driver.findElement(play).click();
			driver.findElement(num1).click();
			sleep(10);
			// log.info("验证广告正常播放，有倒计时");------------目前定位不到这个元素
		//    Assert.assertTrue(isElementExist(playHalfImage,5)); //5s之内找到
			// log.info("返回"); //无法点击
			// driver.findElement(playHalfBack).click();
			// driver.tap(1, 17, 37, 1);
			log.info("重启app,再次请求广告");
			driver.closeApp();
			sleep(3);
			driver.launchApp();
		}

		log.info("刷电视剧广告请求测试------结束------");
	}

	@Test(description = "用例3-查看播放记录",priority = 3)
	public void testPlayRecord() {
		log = Log.getLogger(CrashTest.class);
		log.info("查看播放记录测试------开始------");
		for (int i = 1; i < 21; i++) {
			log.info("----------第" + i + "次-----------");
			sleep(5);
			log.info("点击右上角播放记录图标------------");
			driver.findElement(record).click();
			sleep(3);
			log.info(" 点击第一条播放记录----下一集--------");
	//		driver.findElement(recordIndex1).click();
		    driver.tap(1, 348, 140, 1);    //使用坐标
			sleep(60);
			log.info("重启app,再次查看播放记录");
			driver.closeApp();
			sleep(3);
			driver.launchApp();
		}
		log.info("查看播放记录测试------结束------");
	}

}
