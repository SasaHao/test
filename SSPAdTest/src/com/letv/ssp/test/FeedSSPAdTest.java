package com.letv.ssp.test;

import org.testng.annotations.*;

import com.letv.ssp.control.LoginPageControl;

public class FeedSSPAdTest extends LoginPageControl {

	@Test(description = "信息流新建广告测试")
	public void testSSPFeedAd() throws Exception {	
		GetName("乐见");
		String adType = "信息流";
		//推广类型（广告打开方式）：应用推广、H5推广、电话推广 
		String openType = "电话推广";
		//图片样式选择：乐头条（小图、大图、组图）、乐见
		String showType = "乐见";
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
		// 新建应用商店广告位
		dspAdPageC.adzoneCreate(adType, "乐见");
		// 退出系统
		dspAdPageC.logout_store();
		// 打开企业广告组系统
		loginPageC.StoreUserSystem();
		// 企业广告组登录系统
		loginPageC.storeAdPublisher();
		// 企业广告组创建广告计划
		dspAdPageC.adplanCreate(adType,openType,showType);
		// 打开应用商店页面
		loginPageC.LecoudManagerSystem();
		// 应用商店管理员登录
		loginPageC.storePublisher();
		// 审核广告创意
		dspAdPageC.creativeCheck();
	}
}
