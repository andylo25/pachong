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
		String RSA_S_PrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANHMhO32WNS2XtjjxPoFYOqd5darDoJWmLcRPrTVoPdXOH76rP4ZruRK8Z9TS2vKvY1jjUXjLG6Uotgh5Ro2sGIVlQ/UTagpEGrAtbcVNh+BvBda6LkzNVVdzaG3i98vI0CMRChCWTYgZGhOnPaqJL473aDcVyubldujMHcPHtzzAgMBAAECgYEAh7M30AtljcCgPL2mrOlJtLeI8H8YFWoZwV+gJauAQDvkSQ2vU3QdUYDAJIPl5sTuMkPQHwI/vQ7a/d1+qKPzFXJjU69TxpBaEvMu3+Qa8sWRwwcntdLwkErR3VEwkc18sUt3ya8qkIzcXI2O4UL5m87gVUCD24N3MqpUcdxS7OECQQDuCwCcel8k63EiiI+yLl0KJDB+iyUNmMCWTizUrm9h2nVYQJYa7v+r4c7hbn+ieo/tylTGjPfgIWhYKXtlUppJAkEA4aAT6Z8Yb7jg2YfCSQ6N73esWQ7DAcLHB2ERE6A3whWyn3Gul7e1fH0I5dXUTLL+Sm1HqMnT1Z/SpGZv1wndWwJAAdDrrEXyu+Ff95d7ISzhYNZt2ElPew8TSRJHnUfHacO06Xt0qbfxE2qhnW6uYpr0xiepBerFaVxX43RNkLRUoQJBANPLwOV9QyMdGXUZy3dpAjPY0yCGiugMP2jd5XnW3ICEfbjOrmYdPXAurzuRDkkxfSPAkZQQYZoW/VT2E+Z4jCECQQChstVlaapHCv083a6jWPEkhbLWGCqbUSC9EppfEa4mopKVMCO0+rbYc3BuJ8OPchS+HDye0NwMKykUgZBJSvxQ";
				
		// 接收表单提交参数(To receive the parameter)
		String merchant_code = (String) request.getParameter("merchant_code");
		String service_type = (String) request.getParameter("service_type");
		String notify_url = (String) request.getParameter("notify_url");		
		String interface_version = (String) request.getParameter("interface_version");
		String sign_type = request.getParameter("sign_type");		
		String order_no = (String) request.getParameter("order_no");
		String order_time = (String) request.getParameter("order_time");
		String order_amount = (String) request.getParameter("order_amount");
		String product_name = (String) request.getParameter("product_name");	
		String redo_flag = null;
															
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
		signSrc.append("extra_return_param=").append("24").append("&");
		signSrc.append("interface_version=").append(interface_version).append("&");
		signSrc.append("merchant_code=").append(merchant_code).append("&");				
		signSrc.append("notify_url=").append(notify_url).append("&");	
		signSrc.append("order_amount=").append(order_amount).append("&");
		signSrc.append("order_no=").append(order_no).append("&");
		signSrc.append("order_time=").append(order_time).append("&");
		signSrc.append("product_name=").append(product_name).append("&");
		if(redo_flag == null){
			signSrc.append("service_type=").append(service_type);		
		}else{
			signSrc.append("service_type=").append(service_type).append("&");
			signSrc.append("redo_flag=").append(redo_flag);		
		}
				
		String signInfo =signSrc.toString();
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
			sign = mh.signByPriKey(signInfo);         // 商家签名（签名后报文发往dinpay）  
			System.out.println("RSA商家发送的签名字符串：" + signInfo.length() + " -->" + signInfo);
			System.out.println("RSA商家发送的签名：" + sign.length() + " -->" + sign + "\n");
		}				
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
</head>
<body onLoad="document.dinpayForm.submit();">
  <form name="dinpayForm" method="post" action="https://api.dinpay.com/gateway/api/weixin" >
	<input type="hidden" name="sign" value="<%=sign%>" />
	<input type="hidden" name="merchant_code" value="<%=merchant_code%>"/>	
	<input type="hidden" name="service_type" value="<%=service_type%>"/>
	<input type="hidden" name="notify_url" value="<%=notify_url%>"/>		
	<input type="hidden" name="interface_version" value="<%=interface_version%>"/>
	<input type="hidden" name="sign_type" value="<%=sign_type%>"/>		
	<input type="hidden" name="order_no" value="<%=order_no%>"/>
	<input type="hidden" name="order_time" value="<%=order_time%>"/>	
	<input type="hidden" name="order_amount" value="<%=order_amount%>"/>		
	<input type="hidden" name="product_name" value="<%=product_name%>"/>	
	<input type="hidden" name="extra_return_param" value="24"/>	
	<input type="hidden" name="redo_flag" value="<%=redo_flag%>"/>	
  </form>
</body>
</html>