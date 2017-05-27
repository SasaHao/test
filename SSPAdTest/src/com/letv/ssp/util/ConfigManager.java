package com.letv.ssp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class ConfigManager {

	private Properties p;
	private Properties p_store;
	private static ConfigManager config;

	public static ConfigManager getInstance() {
		if (config == null)
			config = new ConfigManager();
		return config;
	}

	private ConfigManager() {
		p = new Properties();
		p_store = new Properties();
		try {
			File file = new File("configs/test.properties");
			InputStream in = new FileInputStream(file);
			p.load(in);
			File file_store = new File("configs/test.properties");
			InputStream in_store = new FileInputStream(file_store);
			p_store.load(in_store);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//云视频类型
	public String getProperty(String key) {
		String result = null;
		try {
			result = new String(p.getProperty(key).getBytes("ISO-8859-1"),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	//应用商店类型
	public String getProperty_stroe(String key) {
		String result = null;
		try {
			result = new String(p_store.getProperty(key).getBytes("ISO-8859-1"),
					"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
