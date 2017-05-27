package com.letv.ssp.control;

import com.letv.ssp.pages.LoginPage;
import com.letv.ssp.test.BaseTest;
import com.letv.ssp.util.Constant;

public class LoginPageControl extends BaseTest {
	public LoginPage loginPage = new LoginPage();

	// SSP业务系统地址
	public void SSPSystem() throws Exception {
		String SSPBaseUrl = Constant.SSP_BaseURl;
		driver.get(SSPBaseUrl);
	}

	// Lecloud业务系统地址
	public void LecoudManagerSystem() throws Exception {
		String LecloudBaseUrl = Constant.LeCloud_BaseURl;
		driver.get(LecloudBaseUrl);
	}

	// Lecloud Token系统地址
	public void LecoudUserSystem() throws Exception {
		String LecloudTokenUrl = Constant.LeCloud_TokenURl;
		driver.get(LecloudTokenUrl);

	}
	//应用商店企业广告业系统地址
	public void StoreUserSystem() throws Exception {
		String StoreBaseUrl = Constant.Store_BaseURl;
		driver.get(StoreBaseUrl);

	}

	// test017@test.com用户登录
	public void testUserLogin() throws Exception {
		String userName = Constant.Username_TestUser;
		String passWord = Constant.Password_TestUser;
		driver.findElement(loginPage.username).clear();
		driver.findElement(loginPage.username).sendKeys(userName);
		driver.findElement(loginPage.password).clear();
		driver.findElement(loginPage.password).sendKeys(passWord);
		driver.findElement(loginPage.submit).click();
		Thread.sleep(5000);
	}

	// 142用户登录
	public void managerUserLogin() throws Exception {
		String userName = Constant.Username_ManagerUser;
		String passWord = Constant.Password_ManagerUser;
		driver.findElement(loginPage.username).clear();
		driver.findElement(loginPage.username).sendKeys(userName);
		driver.findElement(loginPage.password).clear();
		driver.findElement(loginPage.password).sendKeys(passWord);
		driver.findElement(loginPage.submit).click();
		;
		Thread.sleep(5000);
	}

	// Lecloud管理员用户登录
	public void lecloudUserLogin() throws Exception {
		String userName = Constant.Lecloud_ManagerUsername;
		String passWord = Constant.Lecloud_ManagerPassword;
		driver.findElement(loginPage.username).clear();
		driver.findElement(loginPage.username).sendKeys(userName);
		driver.findElement(loginPage.password).clear();
		driver.findElement(loginPage.password).sendKeys(passWord);
		driver.findElement(loginPage.password).submit();
		Thread.sleep(5000);
	}

	// 应用商店管理员评级
	public void storePublisher() throws Exception {
		String userName = Constant.Store_ManagerUsername;
		String passWord = Constant.Store_ManagerPassword;
		driver.findElement(loginPage.username).clear();
		driver.findElement(loginPage.username).sendKeys(userName);
		driver.findElement(loginPage.password).clear();
		driver.findElement(loginPage.password).sendKeys(passWord);
		driver.findElement(loginPage.password).submit();
		Thread.sleep(5000);
	}

	// 应用商店企业广告主登录创建广告计划
	public void storeAdPublisher() throws Exception {
		String userName = Constant.Store_AdPlanUsername;
		String passWord = Constant.Store_AdPlanPassword;
		driver.findElement(loginPage.store_username).clear();
		driver.findElement(loginPage.store_username).sendKeys(userName);
		driver.findElement(loginPage.store_password).clear();
		driver.findElement(loginPage.store_password).sendKeys(passWord);
		driver.findElement(loginPage.store_submit).click();
		Thread.sleep(5000);
	}
}
