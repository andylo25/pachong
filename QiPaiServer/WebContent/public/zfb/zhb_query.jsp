<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.itrus.util.sign.*" %>
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
		//商户号
		String merchant_code= (String) request.getParameter("merchant_code");
		//接口版本号
		String interface_version= (String) request.getParameter("interface_version");
		//签名类型
		String sign_type= (String) request.getParameter("sign_type");
		//商家订单号
		String order_no= (String) request.getParameter("order_no");
		//智付订单号
		String trade_no= (String) request.getParameter("trade_no");
		
	/*
	**
	 ** 签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推
	 ** 组成规则如下：
	 ** 参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n&key=key值
	 **/
	StringBuffer signSrc= new StringBuffer();
	
	//组织订单信息
		signSrc.append("interface_version=").append(interface_version).append("&");
		signSrc.append("merchant_code=").append(merchant_code).append("&");
		signSrc.append("order_no=").append(order_no).append("&");
		signSrc.append("service_type=").append(service_type).append("&");
		signSrc.append("trade_no=").append(trade_no);
	String singInfo =signSrc.toString();
	String sign="";
	if("RSA-S".equals(sign_type)) {
		//签名
		sign =RSAWithSoftware.signByPrivateKey(singInfo,RSA_S_PrivateKey);
		System.out.println("RSA-S商家发送的签名：singInfo-->" + singInfo);
		System.out.println("RSA-S商家发送的签名：sign-->" + sign);
		}else{
		//商家签名（签名后报文发往dinpay）
		String merPfxPath = "G:/1111110166.pfx";// 商家的pfx证书位置文件(公私钥合一)
		String pfxPass = "87654321";
		RSAWithHardware mh = new RSAWithHardware();
		mh.initSigner(merPfxPath, pfxPass);
		System.out.println("商家发送的签名：singInfo" + singInfo);
		sign = mh.signByPriKey(singInfo);
		System.out.println("商家发送的签名：" + sign.length() + " =>" + sign);
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onLoad="document.dinpayForm.submit();">
 <form name="dinpayForm" method="post" action="https://query.dinpay.com/query"> <!-- 注意 非UTF-8编码的商家网站 此地址必须后接编码格式 -->
	<!-- <form name="dinpayForm" method="post" action="https://pay.dinpay.com/AccessGateway/gateway?input_charset=UTF-8"> -->
	<input type="hidden" name="sign" value="<%=sign%>" />
	<input type="hidden" name="interface_version" value="<%=interface_version%>"/>
	<input type="hidden" name="merchant_code" value="<%=merchant_code%>"/>
	<input type="hidden" name="order_no" value="<%=order_no%>" />
	<input type="hidden" name="service_type" value="<%=service_type%>">
	<input type="hidden" name="sign_type" value="<%=sign_type%>">
	<input type="hidden" name="trade_no" value="<%=trade_no%>">
	</form>
</body>
</html>



