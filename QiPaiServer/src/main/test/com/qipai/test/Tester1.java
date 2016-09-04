package com.qipai.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Tester1 {

	public static void main(String[] args) throws Exception {
		test1();
		test2();
	}
	
	public static void test1() throws UnsupportedEncodingException{
		System.out.println(URLEncoder.encode(URLEncoder.encode("LJrIx7TZxdLfeNRL4wBkdea8ec6bc8zMoKVCN3+4Tdz/0zuOGiAvENqG30jFfL3rNF2QpS4+JE4FYzSVnSAIe62DGStD7cwRhVJoN2LjouY35Mh0KNFmTjdHIPnW4u+WKtxz3nrDbARRB7+UWNovsEzVt48KV6qO3jI0fh4QZRc=", "UTF-8"),"UTF-8"));
		System.out.println(URLEncoder.encode(URLEncoder.encode("我要弹屏", "UTF-8"),"UTF-8"));
		String fds = URLDecoder.decode("&20#andy#%C3%A6%C2%88%C2%91%C3%A9%C2%98%C2%BF%C3%A7%C2%91%C2%B6%C3%A5%C2%92%C2%96%C3%A5%C2%95%C2%A1%C3%A5%C2%BA%C2%97%C3%A5%C2%8F%C2%91%C3%A7%C2%9A%C2%84%C3%A8%C2%AF%C2%B4%C3%A6%C2%B3%C2%95%C3%A6%C2%98%C2%AF20#andy#%C3%A6%C2%88%C2%91%C3%A9%C2%98%C2%BF%C3%A7%C2%91%C2%B6%C3%A5%C2%92%C2%96%C3%A5%C2%95%C2%A1%C3%A5%C2%BA%C2%97%C3%A5%C2%8F%C2%91%C3%A7%C2%9A%C2%84%C3%A8%C2%AF%C2%B4%C3%A6%C2%B3%C2%95%C3%A6%C2%98%C2%AF20#andy#%C3%A6%C2%88%C2%91%C3%A9%C2%98%C2%BF%C3%A7%C2%91%C2%B6%C3%A5%C2%92%C2%96%C3%A5%C2%95%C2%A1%C3%A5%C2%BA%C2%97%C3%A5%C2%8F%C2%91%C3%A7%C2%9A%C2%84%C3%A8%C2%AF%C2%B4%C3%A6%C2%B3%C2%95%C3%A6%C2%98%C2%AF","UTF-8");
		System.out.println(fds);
	}

	public static void test2(){
//		System.out.println(Integer.parseInt("3 "));
		System.out.println("13880245042".matches("^1[3|4|5|7|8]\\d{9}$"));
	}
}
