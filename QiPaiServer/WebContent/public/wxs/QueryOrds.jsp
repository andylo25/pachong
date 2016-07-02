<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.itrus.util.sign.*" %>

<%
//////////////////////////////////// 请求参数 request parameters //////////////////////////////////////
		
		// 智付公钥  dinpay public key，商家后台拷贝
		String RSA_S_PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWOq5aHSTvdxGPDKZWSl6wrPpn"+
			"MHW+8lOgVU71jB2vFGuA6dwa/RpJKnz9zmoGryZlgUmfHANnN0uztkgwb+5mpgme"+
			"gBbNLuGqqHBpQHo2EsiAhgvgO3VRmWC8DARpzNxknsJTBhkUvZdy4GyrjnUrvsAR"+
			"g4VrFzKDWL0Yu3gunQIDAQAB";
	
		// 商家私钥  merchant private key，从openssl工具生成的rsa_private_key_pkcs8.txt文件拷贝
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

		// 接收表单提交参数(To receive the parameter)
		String service_type = request.getParameter("service_type");	
		String merchant_code = request.getParameter("merchant_code");	
		String interface_version = request.getParameter("interface_version");		
		String sign_type = request.getParameter("sign_type");		
		String order_no = request.getParameter("order_no");

		/** 数据签名
		签名规则定义如下：
		（1）参数列表中，除去sign_type、sign两个参数外，其它所有非空的参数都要参与签名，值为空的参数不用参与签名；
		（2）签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推，组成规则如下：
		参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n
		*/

		/** Data signature
		The definition of signature rule is as follows : 
		（1） In the list of parameters, except the two parameters of sign_type and sign, all the other parameters that are not in blank shall be signed, the parameter with value as blank doesn’t need to be signed; 
		（2） The sequence of signature shall be in the sequence of parameter name from a to z, in case of same first letter, then in accordance with the second letter, so on so forth, the composition rule is as follows : 
		Parameter name 1 = parameter value 1& parameter name 2 = parameter value 2& ......& parameter name N = parameter value N 
		*/

		StringBuffer signSrc= new StringBuffer();		
		signSrc.append("interface_version=").append(interface_version).append("&");
		signSrc.append("merchant_code=").append(merchant_code).append("&");
		signSrc.append("order_no=").append(order_no).append("&");	
		signSrc.append("service_type=").append(service_type);
		
		String signInfo = signSrc.toString();
		String sign = "" ;
		if("RSA-S".equals(sign_type)) {
					
			sign = RSAWithSoftware.signByPrivateKey(signInfo,RSA_S_PrivateKey) ;  // 商家签名（签名后报文发往dinpay）  
			System.out.println("RSA-S商家发送的签名字符串：" + signInfo.length() + " -->" + signInfo);
			System.out.println("RSA-S商家发送的签名：" + sign.length() + " -->" + sign + "\n");
		}else{
		
			String merPfxPath = "D:/1111110166.pfx";  // 商家的pfx证书文件位置(公私钥合一)
			String pfxPass = "87654321";			  // 商家的pfx证书密码
			RSAWithHardware mh = new RSAWithHardware();						
			mh.initSigner(merPfxPath, pfxPass);		  
			sign = mh.signByPriKey(signInfo);		  // 商家签名（签名后报文发往dinpay）
			System.out.println("RSA商家发送的签名字符串：" + signInfo.length() + " -->" + signInfo);
			System.out.println("RSA商家发送的签名：" + sign.length() + " -->" + sign + "\n");
		}
		
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
</head>
<body onLoad="document.dinpayForm.submit();">
  <form name="dinpayForm" method="post" action="https://query.dinpay.com/query" >
	<input type="hidden" name="sign" value="<%=sign%>" />
	<input type="hidden" name="service_type" value="<%=service_type%>" />
	<input type="hidden" name="merchant_code" value="<%=merchant_code%>" />
	<input type="hidden" name="interface_version" value="<%=interface_version%>" />	
	<input type="hidden" name="sign_type" value="<%=sign_type%>" />	
	<input type="hidden" name="order_no" value="<%=order_no%>"/>
  </form>
</body>
</html>
