package com.letv.ark.test;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import com.letv.ark.util.Constant;
import com.letv.ark.util.FileTool;

/**
 * 测试工厂类
 * @author haoxiaosha
 *
 */
public class TestngFactory {
	 
	@DataProvider(name = "datasource")
	public static Object[][] getDatasource() {
		List<String> fileList = FileTool.getFiles(Constant.CASE_FILE);
		int size = fileList.size(); 
		Object[][] retObjArr = new Object[size][];
		for (int i = 0; i < size; i++) {
			retObjArr[i] = new Object[] { fileList.get(i) };
		}
		return retObjArr;
	}
	

	@Factory(dataProvider = "datasource")
	public Object[] createTest(String fileName) {
		System.out.println(fileName);
		Object[] result = new Object[1];
		result[0] = new AutoTest(fileName);
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
