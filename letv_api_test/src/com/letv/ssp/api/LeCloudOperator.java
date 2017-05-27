package com.letv.ssp.api;

import org.testng.annotations.Test;

import com.letv.util.BaseCase;

/**
 * 乐视云同步用户接口
 * 传入参数列表-----mail=adtest%40le.com&token=32d2722baa994d32b04367c7e0368f8e&name=adtest&lecloudUid=2222&url=www.letv.com&mobile=18811111111
        接口请求地址-----http://10.118.28.53:8080/ssp/lecloudOperator/update
        接口返回值------{"ret_code":0,"ret_msg":""}
 *
 */
public class LeCloudOperator extends BaseCase{
	@Test(description="乐视云同步用户接口_新增[lecloudOperator.update]")
	public void test_operator_add(){
		http_param.put("lecloudUid", "11111");
		http_param.put("name", "adtestss");
		http_param.put("mail", "cq109cq@163.com");  //用户邮箱
		http_param.put("mobile", "18811111111");  //用户联系方式
//		http_param.put("url", "www.letv.com");   //媒体url
		http_param.put("token", "32d2722baa994d32b04367c7e0368f8e");    //用于验证
		
		//发送请求，调用接口
		getJsonResp("lecloudOperator/update",http_param);
		
	}
}
