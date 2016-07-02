package com.qipai.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.qipai.common.model.User;
import com.qipai.web.UserController;

public class Tester {

	public static void main(String[] args) throws Exception {
//		test1();
		System.out.println(Math.ceil(0.1));
//		test2();
//		test3();
//		test4();
		test5();
	}
	public static void test1(){
		List<User> list = new CopyOnWriteArrayList<User>();
		Collections.sort(list,new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getBankCoin().compareTo(o2.getBankCoin());
			}
		});
	}
	
	public static void test2(){
		List<Integer> list = new ArrayList<Integer>(210000000);
//		list.add(10);
		Integer fs = null;
		long start = System.currentTimeMillis();
//		for(int k=0;k<2;k++){
//		for(int i=0;i<21000;i++){
			for(int j=0;j<210000000;j++){
				list.add(10);
			}
//		}
//		}
		System.out.println(System.currentTimeMillis()-start);
		Integer[] ia = new Integer[210000000];
		start = System.currentTimeMillis();
//		for(int k=0;k<1;k++){
//		for(int i=0;i<21000;i++){
			for(int j=0;j<210000000;j++){
				ia[j] = 10;
			}
//		}
//		}
		System.out.println(System.currentTimeMillis()-start);
	}
	
	public static void test3() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		UserController con = new UserController();
		long start = System.currentTimeMillis();
		Method method = UserController.class.getDeclaredMethod("mask",new Class[]{});
		for(int j=0;j<21000000;j++){
			method.invoke(con, null);
		}
		System.out.println(System.currentTimeMillis()-start);
		start = System.currentTimeMillis();
		for(int j=0;j<21000000;j++){
			con.mask();
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	
	public static void test4() throws InstantiationException, IllegalAccessException{
		long start = System.currentTimeMillis();
		for(int j=0;j<210000;j++){
//			UserController.class.newInstance();
			new UserController();
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	
	public static void test5(){
		List<Game> games = new CopyOnWriteArrayList<Game>();
		games.add(new Game());
		games.add(new Game());
		int a = 0;
		for(Game g:games){
			if(a++ == 1){
				games.remove(g);
			}
		}
		System.out.println(games.size());
	}
	
}
