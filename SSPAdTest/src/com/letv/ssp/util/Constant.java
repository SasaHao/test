package com.letv.ssp.util;

public class Constant {
	// SSP浏览器地址
	public static String SSP_BaseURl = ConfigManager.getInstance().getProperty(
			"ssp_baseurl");
	// LeCloud浏览器地址
	public static String LeCloud_BaseURl = ConfigManager.getInstance()
			.getProperty("cloud_baseUrl");
	// LeCloud发布商Token地址
	public static String LeCloud_TokenURl = ConfigManager.getInstance()
			.getProperty("token_baseUrl");
	// 应用商店企业广告主地址
	public static String Store_BaseURl = ConfigManager.getInstance()
			.getProperty("store_baseUrl");
	// test017@test.com用户信息
	public static String Username_TestUser = ConfigManager.getInstance()
			.getProperty("sspuser_username");
	public static String Password_TestUser = ConfigManager.getInstance()
			.getProperty("sspuser_password");
	// 142用户信息
	public static String Username_ManagerUser = ConfigManager.getInstance()
			.getProperty("sspmanager_username");
	public static String Password_ManagerUser = ConfigManager.getInstance()
			.getProperty("sspmanager_password");
	// Lecloud管理员信息
	public static String Lecloud_ManagerUsername = ConfigManager.getInstance()
			.getProperty("cloudmanager_username");
	public static String Lecloud_ManagerPassword = ConfigManager.getInstance()
			.getProperty("cloudmanager_password");
	// 应用商店管理员信息
	public static String Store_ManagerUsername = ConfigManager.getInstance()
			.getProperty("store_username");
	public static String Store_ManagerPassword = ConfigManager.getInstance()
			.getProperty("store_password");
	// 应用商店企业广告主信息
	public static String Store_AdPlanUsername = ConfigManager.getInstance()
			.getProperty("storeadplan_username");
	public static String Store_AdPlanPassword = ConfigManager.getInstance()
			.getProperty("storeadplan_password");
	// SSP终端
	public static String Plat_Form = ConfigManager.getInstance().getProperty(
			"platform");
	// 选择广告服务系统
	public static String Adservice_System = ConfigManager.getInstance()
			.getProperty("ssp_adserviceSystem");
}
