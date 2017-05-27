package com.letv.ads.util;

import java.io.File;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

/**
 * 测试工厂类
 * @author haoxiaosha
 *
 */
public class TestngFactory {
	 
	@DataProvider(name = "datasource")
	public static Object[][] getDatasource() {
		List<File> fileList = FileTool.getFiles(Constant.CASE_FILE );
		int size = fileList.size(); 
		Object[][] retObjArr = new Object[size][];
		for (int i = 0; i < size; i++) {
			retObjArr[i] = new Object[] { fileList.get(i) };
		}
		return retObjArr;
	}
	

	@Factory(dataProvider = "datasource")
	public Object[] createTest(File file) {
		System.out.println(file.getAbsolutePath());
		Object[] result = new Object[1];
		result[0] = new AutoTest(file);
		return result;
	}
	

/*	public static void main(String[] args) {

		Object arr[][] = getDatasource();
		for (int i = 0; i < arr.length; i++) {
			Object[] arr2 = arr[i];
			for (int c = 0; c < arr2.length; c++) {
				System.out.println(arr2[c]);
			}
		}
	}*/
}
