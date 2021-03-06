package com.leeco.ios.test;

import org.testng.annotations.Test;

import com.leeco.ios.util.AdMonitor;
import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Constant;
import com.leeco.ios.util.Log;

public class PlayAdTest extends BaseCase {
	
	@Test(description="播放器广告测试-前贴&标板")
	public void testPlayerAd_preroll() throws InterruptedException {
		log = Log.getLogger(PlayAdTest.class);
//		sleep(5);  //开机广告有5秒
		log.info("------播放器广告测试开始---(非点击)---");
		log.info("播放器广告_前贴&标板测试------开始------");
		log.info("点击【乐看搜索】");
		driver.findElement(searchBox).click();
		log.info("输入要搜索的电视名称");
		driver.findElement(searchInputBox).setValue(Constant.TV_NAME);
		log.info("点击键盘上的搜索按钮");
		driver.findElement(searchButton).click();
		sleep(10);  //等待显示所有结果
		log.info("随机点击播放一集");
		AdMonitor.clearLog();  //清空Fiddler log文件
		driver.tap(1, getX(), getY(), 1);  //因为搜索结果页面是webview，只能用坐标点击
		//前贴+中贴，播放时间，在test.properties文件中配置
		log.info("开始播放广告，前贴+标板");
		log.info("判断前贴广告是否正常展示(验证【声音】元素是否存在)");
		if(isElementExist(volume,10)){
			log.info("前贴广告展示正常！");
		}else{
			log.info("前贴广告未展示,测试中断！");
		}
		sleep(Constant.PREROALL_AD_TIME+5);
		log.info("开始播放正片");
		log.info("播放器广告_前贴&标板测试------结束------");
	}
	

	@Test(description="播放器广告测试-暂停", dependsOnMethods ="testPlayerAd_preroll")
	public void testPlayerAd_pause() {
		log = Log.getLogger(PlayAdTest.class);
		log.info("播放器广告_暂停------开始------");
		//点击一下正片，呈现出暂停按钮
		driver.tap(1, 160, 100, 1);  //iphone5S(160,100)
		log.info("点击【暂停】");
//		driver.findElement(playHalfPause).click();
		driver.tap(1, 28, 180, 1);  //iphone5S(28,180)
		driver.tap(1, 28, 180, 1);  
		log.info("判断是否出现暂停广告");
		if(isElementExist(playHalfPauseAd,10)){
			log.info("暂停广告展示正常！");
			log.info("关闭暂停广告");
	//		driver.findElement(playHalfPauseAdClose).click();
			driver.tap(1, 257, 64, 1); 
		}else{
			log.info("暂停广告未展示！");
		}
		log.info("继续播放正片");
		driver.tap(1, 160, 100, 1);   //点一下屏幕，出现暂停按钮
//		driver.findElement(playHalfPause).click();
		driver.tap(1, 28, 180, 1);  //iphone5S(28,180)，点击暂停按钮
		log.info("播放器广告_暂停------结束------");
	}

	@Test(description="播放器广告测试-角标", dependsOnMethods ="testPlayerAd_pause")
	public void testPlayerAd_conner() {
		log = Log.getLogger(PlayAdTest.class);
		log.info("播放器广告_角标------开始------");
		//点击一下正片，呈现出暂停按钮
		log.info("切换至全屏观看");
		driver.tap(1, 160, 100, 1);  //iphone5S(160,100)
		sleep(1);
//		driver.findElement(fullScreen).click();   //点不到，用坐标
		driver.tap(1, 295, 180, 1);  //iphone5S
		driver.tap(1, 295, 180, 1); 
		log.info("判断是否出现角标广告");
		if(isElementExist(playConner,40)){  //2min的时候出现角标，等待30s
			log.info("角标广告展示正常！");
		}else{
			log.info("角标广告未展示！");
		}
		log.info("摇一摇，去除角标");
//		driver.shake();   //dubug模式 摇一摇不起作用
		sleep(2);
		log.info("播放器广告_角标------结束------");
	}
	
	@Test(description="播放器广告测试-中贴", dependsOnMethods ="testPlayerAd_conner")
	public void testPlayerAd_midroll() {
		log = Log.getLogger(PlayAdTest.class);
		log.info("播放器广告_中贴------开始------");
		log.info("观看正片，等待中贴广告出现");
		log.info("判断中贴广告是否展示验证【声音】元素是否存在");
		if(isElementExist(volume,Constant.MIDROALL_AD_SHOW)){
			sleep(2);
			log.info("中贴广告展示正常！");
		}else{
			log.info("中贴广告未展示！");
		}
		log.info("等待中贴广告播放完毕");
		sleep(Constant.MIDROALL_AD_TIME+15); 
		log.info("播放器广告_中贴------结束------");
		log.info("播放器广告---监测和上报数据校验");
		boolean flag = AdMonitor.verifyTracking(0);  //不点击
		boolean flag2 = AdMonitor.verifyReport(0);
		if(flag){
			log.info("播放器广告tracking数据校验成功，测试完毕");
		}else{
			log.error("播放器广告tracking数据校验失败，详细原因请查看log");
		}
		if(flag2){
			log.info("播放器广告report数据校验成功，测试完毕");
		}else{
			log.error("播放器广告report数据校验失败，详细原因请查看log");
		}
	}
	
	@Test(description="播放器广告测试完毕-返回首页", dependsOnMethods ="testPlayerAd_midroll")
	public void testPlayerAd_back() {
		log = Log.getLogger(PlayAdTest.class);
		sleep(5);
		log.info("点击返回，退出播放页");
		driver.tap(1, 160, 100, 1);   //点一下屏幕，出现返回按钮
		sleep(2);
//		driver.findElement(playBack).click();
		driver.tap(1, 20, 45, 1);   //退出全屏
		sleep(2);
//		driver.findElement(playHalfBack).click();
		driver.tap(1, 20, 40, 1);  //从半屏返回 
		sleep(2);
		//点击【搜索结果页-取消】按钮，webview元素不能定位
		driver.tap(1, 290, 40, 1);  //iphone5S(290,40)
		//搜索页【取消】
		driver.findElement(searchCancel).click();
		driver.findElement(home).click();
		log.info("------播放器广告测试完毕，返回首页------");
		sleep(3);
	}
}
