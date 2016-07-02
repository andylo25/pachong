package com.qipai.util;

import org.apache.log4j.Logger;

public class ProfileUtil {
	
	private static Logger logger = Logger.getLogger(ProfileUtil.class);
	private static ThreadLocal<Long> profiling = new ThreadLocal<Long>();
	
	public static void init(){
		profiling.set(System.currentTimeMillis());
	}
	
	public static void phash(){
		logger.error("执行耗时："+(System.currentTimeMillis()-profiling.get()));
	}
	
	public static void end(){
		logger.error("执行耗时："+(System.currentTimeMillis()-profiling.get()));
		profiling.remove();
	}
	
}
