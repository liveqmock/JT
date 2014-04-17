package com.mao.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MaoLogger {
	
	private static Logger logger = LoggerFactory 
	        .getLogger("com.zjrc.ETLMonitor"); 
	
	
	public static void info(String info) {
		logger.info(info);		
	}	
	public static void info(String info,Throwable e) {
		logger.error(info,e);
	}
	public static void debug(String info) {
		logger.debug(info);
	}
	public static void debug(String info,Throwable e) {
		logger.error(info,e);
	}
	public static void error(String info) {
		logger.error(info);
	}
	public static void error(String info,Throwable e) {
		logger.error(info,e);
	}
	public static void warn(String info) {
		logger.warn(info);
	}
	public static void warn(String info,Throwable e) {
		logger.error(info,e);
	}
	public static void trace(String info) {
		logger.trace(info);
	}
	public static void trace(String info,Throwable e) {
		logger.error(info,e);
	}
}
