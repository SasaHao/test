package com.letv.ads.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CommandTool {
	
	/**
	 * 执行命令
	 * @param command
	 * @return
	 */
	public static ArrayList<String> execute(String command){
		return exec(command,null);
	}
	
	/**
	 * 在知道dir下执行命令
	 * @param command
	 * @param dir
	 * @return
	 */
	public static ArrayList<String> execute(String command,File dir){
		return exec(command,dir);
	}

	public static ArrayList<String> exec(String command,File dir){
		ArrayList<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			Process process =null;
			if(dir==null){ 
				System.out.println("exe " + command);
				process = Runtime.getRuntime().exec(command);
			}else{ 
				process = Runtime.getRuntime().exec(command,null,dir);
			}
//			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			reader= new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
			String line = null;
			System.out.println("start to readline()");
			while((line = reader.readLine())!=null){
				System.out.println(line); 
				result.add(line);
			}
			System.out.println("end the readline");
			process.waitFor();
			System.out.println("waitfor");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();
					System.out.println("reader close");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static ArrayList<String> exec(String[] command){
		ArrayList<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			Process process =null;
			process = Runtime.getRuntime().exec(command);
	//		reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			reader= new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
			String line = null;
			while((line = reader.readLine())!=null){
				System.out.println(line); 
				result.add(line);
			}
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args){
//		ArrayList<String> res = CommandTool.execute("adb -s 44f673b0 shell cat /system/build.prop"); 
//		for(String line:res){
//			if(line.startsWith("ro.product.brand") 
//					||line.startsWith("ro.product.model")
//					||line.startsWith("ro.build.version.release")){
//				System.out.println(line);
//			}
//		}
//		
//		execute("cmd /c copy /y renren.apk test_apk.apk",new File("C:\\Users\\Administrator\\qa_android\\apk"));
//		execute("cmd /c WinRAR d test_apk.apk META-INF",new File("C:\\Users\\Administrator\\qa_android\\apk"));
//		execute("cmd /c jarsigner -verbose -keystore debug.keystore -storepass android -keypass android -signedjar test_apk_resigned.apk test_apk.apk androiddebugkey",new File("C:\\Users\\Administrator\\qa_android\\apk"));
	
		execute("cmd /c ls");
		
//		File dir = new File("apk");
//		ArrayList<String> res2 = CommandTool.execute("aapt dump badging develop.apk | findstr \"package\"",dir);
	}
}
