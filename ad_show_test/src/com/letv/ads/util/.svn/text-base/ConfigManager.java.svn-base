package com.letv.ads.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 读取配置文件
 * 
 * @author Administrator
 * 
 */
public class ConfigManager {

	private Properties p;
	private static ConfigManager config;

	public static ConfigManager getInstance() {
		if (config == null)
			config = new ConfigManager();
		return config;
	}

	private ConfigManager() {
		p = new Properties();
		// InputStream in =
		// ConfigManager.class.getResourceAsStream("/config.properties");
		try {
			File file = new File("resources/config.properties");
			InputStream in = new FileInputStream(file);
			p.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
}
