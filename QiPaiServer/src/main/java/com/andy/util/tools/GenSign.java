package com.andy.util.tools;

import org.apache.catalina.util.MD5Encoder;

import com.andy.util.MD5Util;

import sun.security.rsa.RSASignature.MD5withRSA;

public class GenSign {

	public static void main(String[] args) {
		String ip="121.204.196.13";
		String other = "操你妈别破解，哥还得混饭吃，请注意节操";
		String sign = MD5Util.getEncryption(ip+other)+MD5Util.getEncryption(ip+other+ip)+MD5Util.getEncryption(ip+other+ip+other);
		System.out.println(sign.substring(3));
	}
}
