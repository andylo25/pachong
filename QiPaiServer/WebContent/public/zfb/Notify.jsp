<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.itrus.util.*" %>
<%@ page import="com.itrus.util.sign.*"%>
<%
/* *
 *功能：即时到账交易接口接入页
 *版本：3.0
 *日期：2013-08-01
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,
 *并非一定要使用该代码。该代码仅供学习和研究智付接口使用，仅为提供一个参考。
 **/
 ////////////////////////////////////请求参数//////////////////////////////////////

		//公钥
		String RSA_S_PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWOq5aHSTvdxGPDKZWSl6wrPpn"+
			"MHW+8lOgVU71jB2vFGuA6dwa/RpJKnz9zmoGryZlgUmfHANnN0uztkgwb+5mpgme"+
			"gBbNLuGqqHBpQHo2EsiAhgvgO3VRmWC8DARpzNxknsJTBhkUvZdy4GyrjnUrvsAR"+
			"g4VrFzKDWL0Yu3gunQIDAQAB";
	

			//商家私钥
		String RSA_S_PrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALf/+xHa1fDTCsLY"
			+ "PJLHy80aWq3djuV1T34sEsjp7UpLmV9zmOVMYXsoFNKQIcEzei4QdaqnVknzmIl7"
			+ "n1oXmAgHaSUF3qHjCttscDZcTWyrbXKSNr8arHv8hGJrfNB/Ea/+oSTIY7H5cAtW"
			+ "g6VmoPCHvqjafW8/UP60PdqYewrtAgMBAAECgYEAofXhsyK0RKoPg9jA4NabLuuu"
			+ "u/IU8ScklMQIuO8oHsiStXFUOSnVeImcYofaHmzIdDmqyU9IZgnUz9eQOcYg3Bot"
			+ "UdUPcGgoqAqDVtmftqjmldP6F6urFpXBazqBrrfJVIgLyNw4PGK6/EmdQxBEtqqg"
			+ "XppRv/ZVZzZPkwObEuECQQDenAam9eAuJYveHtAthkusutsVG5E3gJiXhRhoAqiS"
			+ "QC9mXLTgaWV7zJyA5zYPMvh6IviX/7H+Bqp14lT9wctFAkEA05ljSYShWTCFThtJ"
			+ "xJ2d8zq6xCjBgETAdhiH85O/VrdKpwITV/6psByUKp42IdqMJwOaBgnnct8iDK/T"
			+ "AJLniQJABdo+RodyVGRCUB2pRXkhZjInbl+iKr5jxKAIKzveqLGtTViknL3IoD+Z"
			+ "4b2yayXg6H0g4gYj7NTKCH1h1KYSrQJBALbgbcg/YbeU0NF1kibk1ns9+ebJFpvG"
			+ "T9SBVRZ2TjsjBNkcWR2HEp8LxB6lSEGwActCOJ8Zdjh4kpQGbcWkMYkCQAXBTFiy"
			+ "yImO+sfCccVuDSsWS+9jrc5KadHGIvhfoRjIj2VuUKzJ+mXbmXuXnOYmsAefjnMC"
			+ "I6gGtaqkzl527tw=";
			
		//接口版本(必选)固定值:V3.0
		String service_type = (String) request.getParameter("service_type");
		//通知类型
		String notify_type =(String) request.getParameter("notify_type");
		//通知 效验ID
		String notify_id =(String)request.getparameter("notify_id");
		//商户号
		String merchant_code= (String) request.getParameter("merchant_code");
		//接口版本号
		String interface_version= (String) request.getParameter("interface_version");
		//签名类型
		String sign_type= (String) request.getParameter("sign_type");
		
		String dinpaySign=request.getParameter("sign");
		//商家订单号
		String order_no= (String) request.getParameter("order_no");
		//订单时间
		String order_time= (String) request.getParameter("order_time");
		//订单金额
		String order_amount= (String) request.getParameter("order_amount");
		//公用回传参数
		String extra_return_param= (String) request.getParameter("extra_return_param");
		//智付交易订单号
		String trade_no= (String) request.getParameter("trade_no");
		//智付交易订单时间
		String trade_time =(String)request.getParameter("trade_time");
		//订单状态
		String trade_status =request.getParameter("trade_status");

		
	/*
	**
	 ** 签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推
	 ** 组成规则如下：
	 ** 参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n&key=key值
	 **/
	StringBuffer signSrc= new StringBuffer();
	
	//组织订单信息
	if(!"".equals(extra_return_param)){
		signSrc.append("extra_return_param=").append(extra_return_param).append("&");
	}
		signSrc.append("interface_version=").append(interface_version).append("&");
		signSrc.append("merchant_code=").append(merchant_code).append("&");
		signSrc.append("notify_id=").append(notify_id).append("&");
		signSrc.append("order_amount=").append(order_amount).append("&");
		signSrc.append("order_no=").append(order_no).append("&");
		signSrc.append("order_time=").append(order_time).append("&");
		signSrc.append("trade_no=").append(trade_no).append("&");
		signSrc.append("trade_status=").append(trade_status).append("&");
		signSrc.append("trade_time=").append(trade_time).append("&");
		signSrc.append("service_type=").append(service_type);

		

	String singInfo =signSrc.toString();
	String sign="";
	if("RSA-S".equals(sign_type)) {
		//签名
		sign =RSAWithSoftware.validateSignByPublicKey(singInfo,RSA_S_PublicKey,dinpaySign);
		System.out.println("RSA-S商家发送的签名：singInfo-->" + singInfo);
		System.out.println("RSA-S商家发送的签名：sign-->" + sign);
		}else{
		//设置密钥
		//商家签名（签名后报文发往dinpay）
		String merPfxPath = "G:/1111110166.pfx";// 商家的pfx证书位置文件(公私钥合一)
		String pfxPass = "87654321";
		RSAWithHardware mh = new RSAWithHardware();
		mh.initSigner(merPfxPath, pfxPass);
		System.out.println("商家发送的签名：singInfo" + singInfo);
		sign = mh.validateSignByPubKey("1111110166", singInfo, dinpaySign);
		System.out.println("商家发送的签名：" + sign.length() + " =>" + sign);
	}
	PrintWriter pw = response.getWriter();
	if(dinpaySign.equals(sign)) {  //验签成功（Signature correct）
		pw.print("SUCCESS"); 
	}else{	//验签失败，业务结束（End of the business）
		pw.print("Signature Error");
	}
%>
