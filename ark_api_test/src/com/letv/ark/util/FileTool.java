package com.letv.ark.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

	private static List<String> list = new ArrayList<String>();

	/**
	 * 获取所有测试用例文件(全路径)
	 * 
	 * @param testCaseDir
	 * @return
	 */
	public static List<String> getFiles(String testCaseDir) {
		File dir = new File(testCaseDir);

		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) { // 如果是文件夹，递归调用
					getFiles(file.getAbsolutePath());

				} else {
					if (!file.getName().startsWith(".")) {// 过滤隐藏文件
		//				System.out.println("文件列表----------" + file.getAbsolutePath());
						list.add(file.getAbsolutePath());
					}
				}
			}
		} else {
			System.out.println("输入的文件路径有误，不是一个文件夹！");
		}

		return list;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
/*		List<String> files = getFiles("test-case");
		for (String file : files) {
			System.out.println(file);
	//		getNodeMap(file,"//Res");
		}*/

	//	System.out.println(getNodeValue("D:\\workspace\\ark_api_test\\test-case\\loginout\\Login.xml","/Api/Uri"));

	}
}
