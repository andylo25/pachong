package com.andy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.apache.catalina.util.MD5Encoder;
import org.apache.commons.lang.StringUtils;

import com.itrus.util.FileUtils;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;

public class CheckSafe2 {

	static long start = System.currentTimeMillis();
	public static boolean checkSafe2(){
		if(!RandomUtils.countPercent(1)){
			return true;
		}
		if(PropKit.getBoolean("devMode",false)){
			if(System.currentTimeMillis()-start>1000*60*60*24*30){
				return true;
			}
		}else{
			String sign="";
			try {
				sign = new String(FileUtils.readBytesFromFile(PathKit.getRootClassPath() + "/"+"l"+"i"+"s"+"e"+"n"+"c"+"e"));
			} catch (IOException e) {
			}
			String other = "操"+"你"+"妈"+"别"+"破"+"解"+"，"+"哥"+"还"+"得"+"混"+"饭"+"吃"+"，"+"请"+"注"+"意"+"节"+"操";
			String ip = getMyIP(sign.substring(0, 4));
			sign = sign.substring(4);
			String fds = MD5Util.getEncryption(ip+other)+MD5Util.getEncryption(ip+other+ip)+MD5Util.getEncryption(ip+other+ip+other);
			fds = fds.substring(3);
			if(StringUtils.isNotBlank(sign) && sign.equals(fds)){
				return true;
			}
		}
		return false;
	}
	
	public static String getIp(){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
		String ip = addr.getHostAddress().toString();//获得本机IP
		String address = addr.getHostName().toString();//获得本机名称
		
		return ip+":"+address;
	}
	
	private static String getMyIP(String subUrl) {  
        InputStream ins = null;
        try {  
            URL url = new URL("h"+"t"+"t"+"p://"+subUrl+".ip138.com/ic.asp");  
            URLConnection con = url.openConnection();
            con.setConnectTimeout(2000);
            ins = con.getInputStream();  
            InputStreamReader isReader = new InputStreamReader(ins, "GB2312");  
            BufferedReader bReader = new BufferedReader(isReader);
            StringBuffer webContent = new StringBuffer();  
            String str = null;  
            while ((str = bReader.readLine()) != null) {  
                webContent.append(str);  
            }  
            int start = webContent.indexOf("[") + 1;  
            int end = webContent.indexOf("]");  
            return webContent.substring(start, end);  
        } catch (Exception e){
        	return null;
        } finally {  
            if (ins != null) {  
                try {
					ins.close();
				} catch (IOException e) {
				}  
            }  
        } 
    }  
	
	
}
