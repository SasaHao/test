package com.letv.ssp.api;

import org.testng.annotations.Test;

import com.letv.util.BaseCase;

/**
 * ������ͬ��ҵ���߽ӿ�
 * 
 * iphone 7:{"ret_code":0,"ret_msg":{"id":799,"mediaId":410}}
 *
 */
public class LeCloudBiz_APP extends BaseCase{
	@Test(description="������ͬ��ҵ���߽ӿ�[lecloudBiz/update]")
	public void test_biz(){
		http_param.put("appPkgName", "com.iphone.letv");
		http_param.put("category.id", "3");
//		http_param.put("id", "409");  
		http_param.put("lecloudUid", "11111"); 
		http_param.put("platform.id", "7");
		http_param.put("product.id", "100");  //�Ƶ㲥
		http_param.put("terminalName", "adtestss");
		http_param.put("token", "32d2722baa994d32b04367c7e0368f8e");
		
		//�������󣬵��ýӿ�
		getJsonResp("lecloudBiz/update",http_param);
		
	}
}
