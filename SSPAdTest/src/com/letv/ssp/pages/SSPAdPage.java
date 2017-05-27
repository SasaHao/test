package com.letv.ssp.pages;

import org.openqa.selenium.By;

public class SSPAdPage {
	/**
	 * ý��ҳ��Ԫ��
	 */

	// ����ý��ҳ��
	public By mediaLink = By.partialLinkText("ý��");
	// �½�ý��
	public By newMediaLink = By.partialLinkText("�½�");
	// ������ѡ��
	public By publishId = By.name("publisher.id");
	// ý������
	public By mediaName = By.name("name");
	// ���水ť
	public By mediaSave = By.xpath("//*[@id='mediaForm']/div[4]/button[2]");

	/**
	 * ҵ����ҳ��Ԫ��
	 */

	// ҵ����ҳ��
	public By linerLink = By.linkText("ҵ����");
	// �½�ҵ����
	public By newLinerLink = By.linkText("�½�");
	// ����ƽ̨Ϊ��ҳ�ˣ���������վ�����ֶ�
	public By newWebLiner = By.xpath("//*[@id='platformTypeDiv']/div/label[1]");
	public By WebDomain = By.id("_domainUrl");
	// ����ƽ̨Ϊ�ͻ���
	public By newClientLiner = By
			.xpath("//*[@id='platformTypeDiv']/div/label[2]");
	// ҵ��������
	public By linerName = By.id("name");
	// ����ý��
	public By mediaName_liner = By.id("_media");
	// �ն�
	public By platForm = By.id("_platform");
	// ����Ƶ��Ʒ��
	public By cloudProduct = By.id("_product");
	// ���
	public By cateGory = By.name("category.id");
	// �շ�����
	public By pvLevel = By.id("pvLevel");
	// ���水ť
	public By linerSave = By
			.xpath("//*[@id='content']/div[2]/div/div[1]/div[2]/form/div[11]/button[1]");
	// �˳�ϵͳ
	public By linerLogout = By.partialLinkText("�˳�");
	
	/**
	 * ���ҵ����ҳ��Ԫ��
	 */
	// ���ҵ����ҳ��
	public By checkLinerLink = By.linkText("����ҵ����");
	// ����ҵ��������
	public By linerNameCheck = By.id("q");
	// ��ѯ����ҵ����
	public By searchLiner = By.id("searchButton");
	// ���ͨ����ť
	public String checkPass = "passButton_";
	// �˳�ϵͳ
	public By checkLinerLogout = By.partialLinkText("�˳�");
	
	/**
	 * �½�SSP���λҳ���Ԫ��
	 */
	// ������λҳ��
	public By adzoneLink = By.linkText("���λ");
	// �½����λ
	public By newAdzoneLink = By.linkText("�½�");
	// ���λ����
	public By adzoneName = By.name("name");
	// ����ҵ����
	public By belongLiner = By.id("_biz");
	// ���λ����
	public By adzoneType = By.id("inputRollType");
	// ���水ť��test017�û���
	public By adzoneSave1 = By.xpath("//*[@id='adzoneForm']/div[7]/button[2]");
	// ���水ť��142�û���
	public By adzoneSave2 = By.xpath("//*[@id='adzoneForm']/div[8]/button[2]");
	
	/**
	 * �½�Ͷ�Ų���ҳ���Ԫ��
	 */
	// ������ù���ҳǩ
	public By managerLink_strategy = By.partialLinkText("���ù���");
	// ����Ͷ�Ų���ҳ��
	public By adstrategyLink = By.partialLinkText("Ͷ�Ų���");
	// �½�Ͷ�Ų���
	public By newAdstrategyLink = By.linkText("�½�");
	// ��������
	public By strategyName = By.name("name");
	// ��ѯý��
	public By mediaName_strategy = By.name("mediaName");
	// ѡ��ý��
	public By mediaNameSelect = By.xpath("//html/body/ul/li/a");
	// ѡ��ƽ̨
	public By platForm_strategy = By.id("platformId");
	// ѡ��������
	public By adzoneType_strategy = By.id("adzoneType");
	// ��ѯ���λ
	public By adzoneTypeQuery = By
			.xpath("//*[@id='launchPolicy']/div[4]/button");
	// ѡ���ѯ���Ĺ��λ
	public String adzoneTypeSelect1 = "//*[@id='adzone_";
	public String adzoneTypeSelect2 = "']/td[1]/input";	
	// ȷ��
	public By adzoneTypeCheck = By.linkText("ȷ��");
	// �����й�
	public By areaSelect = By.xpath("//*[@id='launchPolicy']/div[7]/button[1]");
	// �������䰴ť
	public By flowDistribute = By
			.xpath("//*[@id='launchPolicy']/div[9]/button");
	// ѡ�������
	public By adService = By.id("adServerdivide");
	// ����ռ��
	public By adPercent = By.id("proportion");
	// 15��ʱ��ռ��
	public By adPercent15s = By.id("fifteenProportion");
	// ȷ����������
	public By adPercentCheck = By
			.xpath("//*[@id='adService']/div/div/div[2]/a");
	// ǰ�����Ͷ����ʱ��
	public By adDuration = By.id("pretotalDuration");
	// ���水ť
	public By strategySave = By
			.xpath("//*[@id='launchPolicy']/div[last()]/button[2]");
}
