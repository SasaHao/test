package com.letv.ssp.test;

import org.testng.annotations.*;
import com.letv.ssp.control.LoginPageControl;

public class LecloudSSPTest extends LoginPageControl {

	@Test(description = "云视频广告投放")
	public void testSSPLecloudAd() throws Exception {
		// 各名称生成
		GetName("云视频");
		String adType = "前贴";
		//广告格式 JPG  MP4 PNG  SWF  FLV  GIF
		String MimeType = "MP4";
		// 打开SSP系统页面
		loginPageC.SSPSystem();
		// test017@test.com用户登录SSP系统
		loginPageC.testUserLogin();
		// 新建媒体
		sspAdPageC.mediaCreate("云视频");
		// 新建业务线
		sspAdPageC.linerCreate();
		// test017@test.com退出系统
		sspAdPageC.linerLogout();
		// 142用户登录SSP系统
		loginPageC.managerUserLogin();
		// 审核业务线
		sspAdPageC.checkLiner();
		// 142用户退出SSP系统
		sspAdPageC.checkLinerLogout();
		// test017@test.com用户登录SSP系统
		loginPageC.testUserLogin();
		// 新建广告位
		sspAdPageC.adzoneCreate(adType);
		// 新建投放策略
		sspAdPageC.strategyCreate(adType);
		// 打开Lecloud系统页面
		loginPageC.LecoudManagerSystem();
		// 管理员登录Locloud系统
		loginPageC.lecloudUserLogin();
		// 新建Lecloud广告位
		dspAdPageC.adzoneCreate(adType,MimeType);
		// 发布商Token登录Lecloud系统
		loginPageC.LecoudUserSystem();
		// 发布商创建广告计划
		dspAdPageC.adplanCreate(adType, "", "");
	}
}
