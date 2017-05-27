package com.letv.ssp.test;

import org.testng.annotations.*;

import com.letv.ssp.control.LoginPageControl;

public class StoreSSPAdTest extends LoginPageControl {

	@Test(description = "应用商店新建广告测试")
	public void testSSPStoreAd() throws Exception {	
		GetName("应用商店");
		String adType = "应用推荐";
		// 打开SSP系统页面
		loginPageC.SSPSystem();
		// 142用户登录SSP系统
		loginPageC.managerUserLogin();
		// 新建媒体
		sspAdPageC.mediaCreate("小屏");
		// 新建业务线
		sspAdPageC.linerCreate();
		// 审核业务线
		sspAdPageC.checkLiner();
		// 新建SSP广告位
		sspAdPageC.adzoneCreate(adType);
		// 打开应用商店页面
		loginPageC.LecoudManagerSystem();
		// 应用商店管理员登录
		loginPageC.storePublisher();
		// 应用商店管理员评级
		dspAdPageC.checkSSPAdzone();
		// 新建应用商店广告位
		dspAdPageC.adzoneCreate(adType, "");
		// 退出系统
		dspAdPageC.logout_store();
		// 打开企业广告组系统
		loginPageC.StoreUserSystem();
		// 企业广告组登录系统
		loginPageC.storeAdPublisher();
		// 企业广告组创建广告计划
		dspAdPageC.adplanCreate(adType, "","");
		// 打开应用商店页面
		loginPageC.LecoudManagerSystem();
		// 应用商店管理员登录
		loginPageC.storePublisher();
		// 审核广告创意
		dspAdPageC.creativeCheck();
	}
}
