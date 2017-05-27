package com.leeco.ios.test;

import org.testng.annotations.Test;

import com.leeco.ios.util.AdMonitor;
import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Log;

public class StartAdTest extends BaseCase {
	
	@Test(description="开机广告测试")
	public void testStartAd() throws InterruptedException {
		log = Log.getLogger(StartAdTest.class);
		log.info("开机广告测试------开始------");
		log.info("打开app，获取开机广告show请求");
		Thread.sleep(5000);
		log.info("关闭app，重新启动，获取tracking请求，本次的tracking和上次的show对应");
		driver.closeApp();
		driver.launchApp();
		Thread.sleep(5000);
		log.info("对监测到的数据进行校验");
		boolean flag = AdMonitor.startAppVerify();
		if(flag){
			log.info("开机广告监测数据校验成功，测试完毕");
		}else{
			log.error("开机广告测试失败，详细原因请查看log");
		}
		log.info("开机广告测试------结束------");
	}
}
