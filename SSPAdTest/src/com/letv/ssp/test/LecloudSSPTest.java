package com.letv.ssp.test;

import org.testng.annotations.*;
import com.letv.ssp.control.LoginPageControl;

public class LecloudSSPTest extends LoginPageControl {

	@Test(description = "����Ƶ���Ͷ��")
	public void testSSPLecloudAd() throws Exception {
		// ����������
		GetName("����Ƶ");
		String adType = "ǰ��";
		//����ʽ JPG  MP4 PNG  SWF  FLV  GIF
		String MimeType = "MP4";
		// ��SSPϵͳҳ��
		loginPageC.SSPSystem();
		// test017@test.com�û���¼SSPϵͳ
		loginPageC.testUserLogin();
		// �½�ý��
		sspAdPageC.mediaCreate("����Ƶ");
		// �½�ҵ����
		sspAdPageC.linerCreate();
		// test017@test.com�˳�ϵͳ
		sspAdPageC.linerLogout();
		// 142�û���¼SSPϵͳ
		loginPageC.managerUserLogin();
		// ���ҵ����
		sspAdPageC.checkLiner();
		// 142�û��˳�SSPϵͳ
		sspAdPageC.checkLinerLogout();
		// test017@test.com�û���¼SSPϵͳ
		loginPageC.testUserLogin();
		// �½����λ
		sspAdPageC.adzoneCreate(adType);
		// �½�Ͷ�Ų���
		sspAdPageC.strategyCreate(adType);
		// ��Lecloudϵͳҳ��
		loginPageC.LecoudManagerSystem();
		// ����Ա��¼Locloudϵͳ
		loginPageC.lecloudUserLogin();
		// �½�Lecloud���λ
		dspAdPageC.adzoneCreate(adType,MimeType);
		// ������Token��¼Lecloudϵͳ
		loginPageC.LecoudUserSystem();
		// �����̴������ƻ�
		dspAdPageC.adplanCreate(adType, "", "");
	}
}
