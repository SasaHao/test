package com.leeco.ios.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.leeco.ios.model.AppElement;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class BaseCase extends AppElement{

	public static IOSDriver<IOSElement> driver;
	
	public static Log log = Log.getLogger(BaseCase.class);

	@BeforeSuite
	public void setUpBeforeSuite() {
		log.info("****************本次测试开始***********************");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", Constant.DEVICE_NAME);
		capabilities.setCapability("bundleId", Constant.BUNDLEID);
		capabilities.setCapability("udid", Constant.UDID);
		capabilities.setCapability("platformVersion", Constant.PLATFORMVERSION);
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("newCommandTimeout", "100000");
//		capabilities.setCapability("app", "/Users/haoxiaosha/Documents/jenkins/build/Letv_iPhone/6.11/Debug/Backup/DEV/LetvIphoneClient_2016-11-18_14-38-02.ipa");
		//xcode8新增属性，使用WebDriverAgent代理
		capabilities.setCapability("automationName", "XCUITest");
//		capabilities.setCapability("noReset", true);
		capabilities.setCapability("realDeviceLogger", "idevicesyslog");
		
		try {
			driver = new IOSDriver<IOSElement>(new URL("http://" + Constant.MAC_IP + ":4723/wd/hub"), capabilities);		
//			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			log.info(e.getMessage());
			log.info("appium连接失,败请检查网络是否连通！配置文件中MAC地址是："+Constant.MAC_IP);
			log.info("****************本次测试结束***********************");
		}catch (Exception e) {
			log.info(e.getMessage());
			log.info("客户端闪退,请检查app是否能正常启动！");
			log.info("****************本次测试结束***********************");
		}

	}

	@AfterSuite
	public void tearDownAfterSuite() {
	//	driver.closeApp();
		log.info("****************本次测试结束***********************");
	}

	@BeforeClass
	public void setUpBeforeClass() {
		
		
	}

	@AfterClass
	public void tearDownAfterClass() throws InterruptedException {
		driver.closeApp();
		//卸载webDriverAgent，避免报错
	//	driver.removeApp("com.facebook.WebDriverAgentRunner");
		
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public String getSystemTime() {

		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		return time;

	}
	
	public String getDate(){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	} 

	/**
	 * 等待 
	 * @param seconds 秒
	 */
	public void sleep(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行失败时截屏操作
	 * 
	 * @param drivername
	 * @param filename
	 */
	public void snapshot(TakesScreenshot drivername, String filename) {
		String currentPath = System.getProperty("user.dir") + File.separator + "failue_images";
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		try {
			log.info("save snapshot path is:" + currentPath + File.separator + filename);
			FileUtils.copyFile(scrFile, new File(currentPath + File.separator + filename));
		} catch (IOException e) {
			log.debug("Can't save screenshot");
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝文件,将filename1复制到filename2路径
	 * 
	 * @param filename1
	 * @param filename2
	 */
	private void copyFile(String filename1, String filename2) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(filename1);
			fo = new FileOutputStream(filename2);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 在设置中清除应用缓存
	 * @throws InterruptedException 
	 */
/*	private void clearCache() throws InterruptedException{
		log.info("点击【我的】，进入该页面");
		driver.findElement(my).click();
		log.info("滑动至【设置】");
		driver.scrollTo("设置");
		log.info("点击【设置】，进入该页面");
		driver.findElement(setting).click();
		log.info("点击【清除应用缓存】");
		driver.findElement(clear).click();
		log.info("点击【确定】");
		driver.findElement(ok).click();
		log.info("返回首页");
		driver.findElement(settingBack).click();
		driver.findElement(home).click();
		sleep(3);  //等待三秒
	}
*/	
	/**
	 * 判断某元素是否存在，存在则返回true，不存在则返回false
	 * @param by
	 * @return
	 */
	public boolean isElementExist(By by){
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			log.info(by.toString() + " 该元素不存在");
			return false;
		}
	}
	
	/**
	 * 在指定超时时间内元素是否存在，如存在则立即返回结果，如不存在则在超时后返回结果
	 * @param by
	 * @param timeOutInSeconds
	 * @return
	 */
	public boolean isElementExist(By by,int timeOutInSeconds){
		try {
			new WebDriverWait(driver,timeOutInSeconds)
						.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			System.out.println(by.toString() + " 在指定时间内未找到该元素");
			return false;
		}
	}
	
	/**
	 * 搜索结果列表电视集数的x坐标(iPhone5S)
	 * @return
	 */
	public int getX(){
		int xArray[] = {36,80,160,240};
		int index = (int) (Math.random()*4);
		return xArray[index];
	}
	
	/**
	 * 搜索结果列表电视集数的y坐标(iPhone5S)
	 * @return
	 */
	public int getY(){
		int yArray[] = {278,310};
		int index = (int) (Math.random()*2);
		return yArray[index];
	}
}
