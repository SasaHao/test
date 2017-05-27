package com.letv.ads.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class FileTool {

	/**
	 * 读取test-case目录下的文件内容，转换成JSONObject返回
	 * 
	 * @param testCase
	 * @return
	 */
	public static JSONObject getTestCase(String testCase) {
		String testCasePath = Constant.CASE_FILE + File.separator + testCase;
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(testCasePath), "UTF-8");  
			BufferedReader br = new BufferedReader(inputStreamReader);
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str.trim());
			}
			br.close();
			inputStreamReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject obj = JSONObject.fromObject(sb.toString());
		return obj;
	}

	/**
	 * 获取所有测试用例文件
	 * 
	 * @param testCaseDir
	 * @return
	 */
	public static List<File> getFiles(String testCaseDir) {
		File dir = new File(testCaseDir);
		List<File> list = new ArrayList<File>();
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (!file.getName().startsWith(".")) {
					list.add(file);
				}
			}
		}

		return list;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(getTestCase("public-pbgg-001.txt"));
		// List<File> files = getFiles("test-case");
		// for(File file:files){
		// System.out.println(file.getName());
		// }

		// String time = String.valueOf(new Date().getTime());
		// System.out.println(time);
		RedisUtil r =RedisUtil.getInstance();
		System.out.println(r.deleteAllKeys());
				
	}
}
