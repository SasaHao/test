package com.letv.ssp.api;

import org.testng.annotations.Test;

import com.letv.util.BaseCase;

/**
 * ������ͬ��ҵ���߽ӿ�
 * 
 * H5 5��{"ret_code":0,"ret_msg":{"id":801,"mediaId":410}}
 * Mվ-Android 4��{"ret_code":0,"ret_msg":{"id":800,"mediaId":410}}
 * 
 */
public class LeCloudBiz_WEB extends BaseCase{
	@Test(description="������ͬ��ҵ���߽ӿ�[lecloudBiz/update]")
	public void test_biz(){
//		http_param.put("id", "778");
		http_param.put("lecloudUid", "11111");
		http_param.put("platform.id", "4");
		http_param.put("domainUrl", "testle.com");
		http_param.put("category.id", "3");
		http_param.put("product.id", "120");  //101��ֱ��,120��ý��
		http_param.put("terminalName", "adtestss");
		http_param.put("token", "32d2722baa994d32b04367c7e0368f8e");
		
		//�������󣬵��ýӿ�
		getJsonResp("lecloudBiz/update",http_param);
		
	}
}
