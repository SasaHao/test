package com.leeco.ios.test;
//import com.leeco.ios.util.AdMonitor;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.leeco.ios.model.AppElement;
import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Constant;
//import com.leeco.ios.util.Log;


public class RePauseAdTest extends BaseCase{
	
	@Test(description="重复点击暂停广告测试")
	public void testPauseAd() throws InterruptedException{
		driver.launchApp();
		driver.findElement(searchBox).click();
		driver.findElement(searchInputBox).setValue(Constant.TV_NAME);
		sleep(1);
		driver.tap(1,310,490,1);//searchbutton(iPhone5s)
		//driver.findElement(searchButton).click();
		sleep(2);
		//点击第一集视频观看
		driver.tap(1,10,261,1);
		sleep(75);
		//driver.sendKeyEvent(3);
		
		for(int i=1;i<=50;i++){
		  //点击一下屏幕出现暂停按钮
		  driver.tap(1, 100, 150, 1);  //iPhone5s(100,150)
		  //点击暂停按钮
		  driver.tap(1, 0, 166, 1); //iPhone5s(0,166)
		  if(isElementExist(playHalfPauseAd,10)){
			System.out.println("暂停广告展示成功"+i+"次；");  
			//driver.tap(1, 257, 64, 1); 
			}else{
			System.out.println("第"+i+"次暂停广告未展示！");
		  }
		  sleep(1);
		  //点击屏幕
		driver.tap(1, 20, 100, 1);
		//点击暂停按钮,视频继续播放
		driver.tap(1, 0, 166, 1); 
	    sleep(1);
		}
	    driver.closeApp();
		}
	}