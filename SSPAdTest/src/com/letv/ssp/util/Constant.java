package com.letv.ssp.util;

public class Constant {
	// SSP�������ַ
	public static String SSP_BaseURl = ConfigManager.getInstance().getProperty(
			"ssp_baseurl");
	// LeCloud�������ַ
	public static String LeCloud_BaseURl = ConfigManager.getInstance()
			.getProperty("cloud_baseUrl");
	// LeCloud������Token��ַ
	public static String LeCloud_TokenURl = ConfigManager.getInstance()
			.getProperty("token_baseUrl");
	// Ӧ���̵���ҵ�������ַ
	public static String Store_BaseURl = ConfigManager.getInstance()
			.getProperty("store_baseUrl");
	// test017@test.com�û���Ϣ
	public static String Username_TestUser = ConfigManager.getInstance()
			.getProperty("sspuser_username");
	public static String Password_TestUser = ConfigManager.getInstance()
			.getProperty("sspuser_password");
	// 142�û���Ϣ
	public static String Username_ManagerUser = ConfigManager.getInstance()
			.getProperty("sspmanager_username");
	public static String Password_ManagerUser = ConfigManager.getInstance()
			.getProperty("sspmanager_password");
	// Lecloud����Ա��Ϣ
	public static String Lecloud_ManagerUsername = ConfigManager.getInstance()
			.getProperty("cloudmanager_username");
	public static String Lecloud_ManagerPassword = ConfigManager.getInstance()
			.getProperty("cloudmanager_password");
	// Ӧ���̵����Ա��Ϣ
	public static String Store_ManagerUsername = ConfigManager.getInstance()
			.getProperty("store_username");
	public static String Store_ManagerPassword = ConfigManager.getInstance()
			.getProperty("store_password");
	// Ӧ���̵���ҵ�������Ϣ
	public static String Store_AdPlanUsername = ConfigManager.getInstance()
			.getProperty("storeadplan_username");
	public static String Store_AdPlanPassword = ConfigManager.getInstance()
			.getProperty("storeadplan_password");
	// SSP�ն�
	public static String Plat_Form = ConfigManager.getInstance().getProperty(
			"platform");
	// ѡ�������ϵͳ
	public static String Adservice_System = ConfigManager.getInstance()
			.getProperty("ssp_adserviceSystem");
}
