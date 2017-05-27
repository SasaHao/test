package com.leeco.ios.test;


import org.testng.annotations.Test;

import com.leeco.ios.util.AdMonitor;
import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Log;

import io.appium.java_client.ios.IOSElement;

public class TopicAdTest extends BaseCase {
	
	@Test(description="专题广告测试")
	public void testTopicAd(){
		log = Log.getLogger(TopicAdTest.class);
		log.info("专题页广告测试------开始------");
		log.info("点击【发现】，进入发现页");
		driver.findElement(find).click();
		log.info("点击【专题】，进入专题页");
		driver.findElement(topic).click();
		log.info("点击【专题列表第5个】，进入广告页");
		driver.findElement(topicAd).click();
		//判断广告页标题
		IOSElement view = driver.findElement(topicAdView);
		if(view.isDisplayed() && driver.findElement(ad_more).isDisplayed()){
			log.info("专题页广告显示正常！");
		}else{
			snapshot(driver,this.getClass().getSimpleName()+"_screen_"+getSystemTime()+".png");
			log.error("专题页广告未显示，请查看截图和log！");
		}
		log.info("专题页广告---监测和上报数据校验");
		boolean flag = AdMonitor.verifyTracking(1);  //点击1次
		if(flag){
			log.info("专题页广告tracking数据校验成功，测试完毕");
		}else{
			log.error("专题页广告tracking数据校验失败，详细原因请查看log");
		}
		
		log.info("返回app首页");
		driver.findElement(back).click();
		driver.findElement(back).click();
		driver.findElement(home).click();
		sleep(3);
		log.info("专题页广告测试------结束------");
	}
}
