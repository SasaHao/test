package com.letv.ssp.pages;

import org.openqa.selenium.By;

public class SSPAdPage {
	/**
	 * 媒体页面元素
	 */

	// 进入媒体页面
	public By mediaLink = By.partialLinkText("媒体");
	// 新建媒体
	public By newMediaLink = By.partialLinkText("新建");
	// 发布商选择
	public By publishId = By.name("publisher.id");
	// 媒体名称
	public By mediaName = By.name("name");
	// 保存按钮
	public By mediaSave = By.xpath("//*[@id='mediaForm']/div[4]/button[2]");

	/**
	 * 业务线页面元素
	 */

	// 业务线页面
	public By linerLink = By.linkText("业务线");
	// 新建业务线
	public By newLinerLink = By.linkText("新建");
	// 所属平台为网页端，需新增网站域名字段
	public By newWebLiner = By.xpath("//*[@id='platformTypeDiv']/div/label[1]");
	public By WebDomain = By.id("_domainUrl");
	// 所属平台为客户端
	public By newClientLiner = By
			.xpath("//*[@id='platformTypeDiv']/div/label[2]");
	// 业务线名称
	public By linerName = By.id("name");
	// 所属媒体
	public By mediaName_liner = By.id("_media");
	// 终端
	public By platForm = By.id("_platform");
	// 云视频产品线
	public By cloudProduct = By.id("_product");
	// 类别
	public By cateGory = By.name("category.id");
	// 日访问量
	public By pvLevel = By.id("pvLevel");
	// 保存按钮
	public By linerSave = By
			.xpath("//*[@id='content']/div[2]/div/div[1]/div[2]/form/div[11]/button[1]");
	// 退出系统
	public By linerLogout = By.partialLinkText("退出");
	
	/**
	 * 审核业务线页面元素
	 */
	// 审核业务线页面
	public By checkLinerLink = By.linkText("待审业务线");
	// 待审业务线名称
	public By linerNameCheck = By.id("q");
	// 查询待审业务线
	public By searchLiner = By.id("searchButton");
	// 审核通过按钮
	public String checkPass = "passButton_";
	// 退出系统
	public By checkLinerLogout = By.partialLinkText("退出");
	
	/**
	 * 新建SSP广告位页面各元素
	 */
	// 进入广告位页面
	public By adzoneLink = By.linkText("广告位");
	// 新建广告位
	public By newAdzoneLink = By.linkText("新建");
	// 广告位名称
	public By adzoneName = By.name("name");
	// 所属业务线
	public By belongLiner = By.id("_biz");
	// 广告位类型
	public By adzoneType = By.id("inputRollType");
	// 保存按钮（test017用户）
	public By adzoneSave1 = By.xpath("//*[@id='adzoneForm']/div[7]/button[2]");
	// 保存按钮（142用户）
	public By adzoneSave2 = By.xpath("//*[@id='adzoneForm']/div[8]/button[2]");
	
	/**
	 * 新建投放策略页面各元素
	 */
	// 点击配置管理页签
	public By managerLink_strategy = By.partialLinkText("配置管理");
	// 进入投放策略页面
	public By adstrategyLink = By.partialLinkText("投放策略");
	// 新建投放策略
	public By newAdstrategyLink = By.linkText("新建");
	// 策略名称
	public By strategyName = By.name("name");
	// 查询媒体
	public By mediaName_strategy = By.name("mediaName");
	// 选择媒体
	public By mediaNameSelect = By.xpath("//html/body/ul/li/a");
	// 选择平台
	public By platForm_strategy = By.id("platformId");
	// 选择广告类型
	public By adzoneType_strategy = By.id("adzoneType");
	// 查询广告位
	public By adzoneTypeQuery = By
			.xpath("//*[@id='launchPolicy']/div[4]/button");
	// 选择查询出的广告位
	public String adzoneTypeSelect1 = "//*[@id='adzone_";
	public String adzoneTypeSelect2 = "']/td[1]/input";	
	// 确认
	public By adzoneTypeCheck = By.linkText("确认");
	// 定向中国
	public By areaSelect = By.xpath("//*[@id='launchPolicy']/div[7]/button[1]");
	// 流量分配按钮
	public By flowDistribute = By
			.xpath("//*[@id='launchPolicy']/div[9]/button");
	// 选择广告服务
	public By adService = By.id("adServerdivide");
	// 分配占比
	public By adPercent = By.id("proportion");
	// 15秒时长占比
	public By adPercent15s = By.id("fifteenProportion");
	// 确认流量分配
	public By adPercentCheck = By
			.xpath("//*[@id='adService']/div/div/div[2]/a");
	// 前贴广告投放总时长
	public By adDuration = By.id("pretotalDuration");
	// 保存按钮
	public By strategySave = By
			.xpath("//*[@id='launchPolicy']/div[last()]/button[2]");
}
