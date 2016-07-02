package com.andy.ext;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.andy.util.FileUtil;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

public class RouteUtil {
	
	private static Logger logger = Logger.getLogger(RouteUtil.class);
	
	private static final String CONTROLLER_STR = "Controller";

	public static void initAnnoRoute(Routes routes){
		ArrayList<Class<?>> classes = FileUtil.getClasses(PropKit.get("scan.package"));
		int count = 0;
		for(Class<?> cl:classes){
			try {
				if(Controller.class.isAssignableFrom(cl)){
					String actinKey = getPrefix(cl);
					if(StringUtils.isNotBlank(actinKey)){
						if(logger.isDebugEnabled()){
							logger.debug("注册Controller:["+actinKey+"]->["+cl+"]");
						}
						routes.add(getPrefix(cl), (Class<? extends Controller>) cl);
						count++;
					}
				}
			} catch (Exception e) {
				logger.error(cl+"注入异常", e);
				continue;
			}
		}
		logger.error("注册Controller个数:"+count);
	}

	private static String getPrefix(Class<?> cl) {
		String result = "";
		if(cl.isAnnotationPresent(Action.class)){
			result = cl.getAnnotation(Action.class).value();
		}
		
		return result;
	}
}
