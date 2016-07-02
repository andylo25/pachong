package com.andy.ext;

import java.util.ArrayList;

import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.andy.util.FileUtil;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.jfinal.kit.PropKit;

public class GuiceAnnoModule extends AbstractModule {
	
	private Logger logger = Logger.getLogger(GuiceAnnoModule.class);

	@Override
	protected void configure() {
		ArrayList<Class<?>> classes = FileUtil.getClasses(PropKit.get("scan.package"));
		int count = 0;
		for(Class<?> cl:classes){
			try {
				if(cl.isAnnotationPresent(Service.class)){
					String name = cl.getAnnotation(Service.class).value();
					Class<?>[] ints = cl.getInterfaces();
					if(ints != null && ints.length > 0){
						for(Class inf:ints){
							bind(inf).to(cl).in(Singleton.class);
							if(name != null && name.length() > 0){
								bind(inf).annotatedWith(Names.named(name)).to(cl).in(Singleton.class);
							}
						}
					}
//					bind(cl).in(Singleton.class);
//					Class spcs = null;
//					while((spcs = cl.getSuperclass()) != null){
//						bind(spcs).to(cl);
//						spcs = spcs.getSuperclass();
//					}
					count++;
				}
			} catch (Exception e) {
				logger.error(cl+"注入异常", e);
				continue;
			}
		}
		logger.error("注册service个数:"+count);
	}

}
