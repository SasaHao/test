package com.leeco.ios.test;
//import com.leeco.ios.util.AdMonitor;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import com.leeco.ios.model.AppElement;
import com.leeco.ios.util.BaseCase;
import com.leeco.ios.util.Constant;
//import com.leeco.ios.util.Log;


public class RePlayAdTest extends BaseCase{
	
	@Test(description="前贴广告播放完毕后重复切换集数广告测试")
	public void testPlayPread() throws InterruptedException{
		driver.findElement(searchBox).click();
		driver.findElement(searchInputBox).setValue(Constant.TV_NAME);
		sleep(1);
		driver.tap(1,310,490,1);//searchbutton(iPhone5s)
		//driver.findElement(searchButton).click();
		sleep(2);
		driver.tap(1,10,261,1); //火线三兄弟第一集坐标
		sleep(6);
		By item[] ={item1,item2,item3,item4,item5,item6};
		for(int i=0;i<=49;i++){
			//driver.swipe( 192, 456, 100, 456, 30);
			driver.findElement(item[i%6]).click();
		        if(isElementExist(volume,10)){
			       System.out.println("第"+(i+1)+"次前贴播放");
			       sleep(60);
		         }else {
			        System.out.println("此次前贴广告没有展示");
			
		}
	}
	driver.closeApp();
	sleep(3);
	}
	
}