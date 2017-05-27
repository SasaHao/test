package com.leeco.protobuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.leeco.protobuf.Le_LTHeaderModelPB.PBDictionary;
import com.leeco.protobuf.Le_VFModelWapperPB.VFModelPB;
import com.leeco.protobuf.Le_VFModelWapperPB.VFModelWapperPB;
import com.leeco.protobuf.Le_VFModelWapperPB.VFVideoFilePB;
import com.leeco.protobuf.Le_VFModelWapperPB.VideoModelPB;

import net.sf.json.JSONObject;

public class ProtobufUtil {

	public static String getPlayResponse(String fileName) {
		JSONObject jsonObject = new JSONObject();  
		try {
			//反序列化
			VFModelWapperPB playInfo = Le_VFModelWapperPB.VFModelWapperPB.parseFrom(new FileInputStream(fileName));
			
			//获取有用信息，构造json字符串
			VFModelPB pb = playInfo.getData();
			PBDictionary adInfo = pb.getAdInfo();
			VideoModelPB videoInfo = pb.getVideoInfo();
			VFVideoFilePB videoFile = pb.getVideofile();
			String pid = videoInfo.getPid();
			String cid = videoInfo.getCid();
			String vid = videoInfo.getVid();
			String mmsid = videoFile.getMmsid();
			//构造videoInfo的json串
			String videoInfoJson = "{\"pid\":\""+pid+"\",\"cid\":\""+cid+"\",\"vid\":\""+vid+"\",\"mmsid\":\""+mmsid+"\"}";

			jsonObject.put("videoInfo",videoInfoJson);
			jsonObject.put("adInfo", adInfo.getStrDict());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	// 本地测试
	public static void main(String[] args) {

		System.out.println(getPlayResponse("D:\\temp\\play_response.txt"));
	}
}
