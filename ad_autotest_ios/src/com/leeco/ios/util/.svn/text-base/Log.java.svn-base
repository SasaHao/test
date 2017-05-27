package com.leeco.ios.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static Logger log = Logger.getLogger(Log.class);
	private static Log logger;
	private static String className;
	public static Log getLogger(Class<?> T) {
		init();
		log = Logger.getLogger(T);
		logger = new Log();
		className=Thread.currentThread().getStackTrace()[2].getClassName()+"---"; 
		return logger;
	}

	public static void init() {
		PropertyConfigurator.configure(Constant.LOG4J_CONFIG);
	}

	public void debug(Object message) {
		log.debug(className+message);
	}

	public void debug(Object message, Throwable t) {
		log.debug(className+message, t);
	}

	public void info(Object message) {
		log.info(className+message);
	}

	public void info(Object message, Throwable t) {
		log.info(className+message, t);
	}

	public void warn(Object message) {
		log.warn(className+message);
	}

	public void warn(Object message, Throwable t) {
		log.warn(className+message, t);
	}

	public void error(Object message) {
		log.error(className+message);
	}

	public void error(Object message, Throwable t) {
		log.error(className+message, t);
	}
}