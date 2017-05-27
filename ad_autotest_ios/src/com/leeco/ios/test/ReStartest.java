package com.leeco.ios.test;

import org.testng.annotations.Test;


//import com.leeco.ios.util.AdMonitor;
import com.leeco.ios.util.BaseCase;
//import com.leeco.ios.util.Log;

public class ReStartest extends BaseCase {
	
	@Test(description="开机广告测试")
	public void reStartTest() throws InterruptedException {
		//log = Log.getLogger(ReStartest.class);
		for(int i=1;i<=50;i++)
		{ //log.info("开机广告测第"+i+"测试");
		  System.out.println("开机广告第"+i+"次启动成功");
		  Thread.sleep(2000);
		  //log.info("关闭app，重新启动");
		  driver.closeApp();
		  Thread.sleep(1000);
		  driver.launchApp();
		}
		//log.info("开机广告测试------结束------");
	}
}
