package com.andy.ext;

import com.google.inject.Injector;
import com.google.inject.Key;

public class GuiceUtil {
	
	private static Injector injector_;
	
	public static void init(Injector injector){
		injector_ = injector;
	}
	
	public static <T> T getBean(Class<T> clasz){
		if(injector_ != null){
			return injector_.getInstance(clasz);
		}
		return null;
	}
	
//	public static <T> T getBean(String name){
//		if(injector_ != null){
//			return injector_.getInstance(new Key<T>());
//		}
//		return null;
//	}

}
