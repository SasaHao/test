package com.letv.ssp.api;

import org.testng.annotations.Test;

import com.letv.util.BaseCase;

/**
 * ������ͬ���û��ӿ�
 * ��������б�-----mail=adtest%40le.com&token=32d2722baa994d32b04367c7e0368f8e&name=adtest&lecloudUid=2222&url=www.letv.com&mobile=18811111111
        �ӿ������ַ-----http://10.118.28.53:8080/ssp/lecloudOperator/update
        �ӿڷ���ֵ------{"ret_code":0,"ret_msg":""}
 *
 */
public class LeCloudOperator extends BaseCase{
	@Test(description="������ͬ���û��ӿ�_����[lecloudOperator.update]")
	public void test_operator_add(){
		http_param.put("lecloudUid", "11111");
		http_param.put("name", "adtestss");
		http_param.put("mail", "cq109cq@163.com");  //�û�����
		http_param.put("mobile", "18811111111");  //�û���ϵ��ʽ
//		http_param.put("url", "www.letv.com");   //ý��url
		http_param.put("token", "32d2722baa994d32b04367c7e0368f8e");    //������֤
		
		//�������󣬵��ýӿ�
		getJsonResp("lecloudOperator/update",http_param);
		
	}
}
