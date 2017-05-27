package com.letv.ssp.test;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import com.letv.ssp.control.DSPAdPageControl;
import com.letv.ssp.control.LoginPageControl;
import com.letv.ssp.control.SSPAdPageControl;


public class BaseTest {
	public LoginPageControl loginPageC;
	public SSPAdPageControl sspAdPageC;
	public DSPAdPageControl dspAdPageC;
	public static WebDriver driver;
	public static String newLinerId;
	public static String newSSPAdzoneId;
	public static String mediaName;
	public static String linerName;
	public static String sspAdzoneName;
	public static String strategyName;
	public static String dspadzoneName;
	public static String dspadplanName;
	public static String feedCreativeName;
	public static String feedCreativeTitle;
	public static String feedCreativeSource;
	
	@BeforeTest
	public void beforeTest() {
		//Firefox���������
		System.setProperty("webdriver.firefox.marionette","C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");
		driver = new FirefoxDriver();
//		System.setProperty("webdriver.chrome.driver","C:\\Users\\tianhaixia\\Downloads\\chromedriver_win32\\chromedriver.exe");
//		DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
//		chromeCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
//		driver = new ChromeDriver(chromeCapabilities);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		loginPageC = new LoginPageControl();
		sspAdPageC = new SSPAdPageControl();
		dspAdPageC = new DSPAdPageControl();
	}

	@AfterTest
	public void afterTest() {
		driver.close();
	}
	
	/**
	 * ��������ַ���
	 * @param length
	 * @return
	 */
	public String getRandomStr(int length){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	//����ý�����ơ�ҵ�������ơ�SSP���λ���ơ��������ơ�DSP���λ���ơ����ƻ�����
	public void GetName(String AdPlat){
		String randomNumber = getRandomStr(4);
		mediaName = AdPlat+"ý��_"+randomNumber;
		linerName = AdPlat+"ҵ����_"+randomNumber;
		sspAdzoneName = AdPlat+"SSP���λ_"+randomNumber;
		strategyName = AdPlat+"����_"+randomNumber;
		dspadzoneName = AdPlat+"���λ_"+randomNumber;
		dspadplanName = AdPlat+"���ƻ�_"+randomNumber;	
		feedCreativeName = AdPlat+"����_"+randomNumber;	
		feedCreativeTitle = AdPlat+"����_"+randomNumber;	
		feedCreativeSource = AdPlat+"��Դ_"+randomNumber;		
	}
}