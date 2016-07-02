<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.itrus.util.sign.*" %>
<%

	//公钥   dinpay public key
	String RSA_S_PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWOq5aHSTvdxGPDKZWSl6wrPpn"+
	"MHW+8lOgVU71jB2vFGuA6dwa/RpJKnz9zmoGryZlgUmfHANnN0uztkgwb+5mpgme"+
	"gBbNLuGqqHBpQHo2EsiAhgvgO3VRmWC8DARpzNxknsJTBhkUvZdy4GyrjnUrvsAR"+
	"g4VrFzKDWL0Yu3gunQIDAQAB";


	//商家私钥   merchant private key
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



	// 接收表单参数（To receive the parameter）

	String merchant_code = (String)request.getParameter("merchant_code");

	String service_type = (String)request.getParameter("service_type");

	String interface_version = (String)request.getParameter("interface_version");

	String sign_type = request.getParameter("sign_type");

	String pay_type = request.getParameter("pay_type");

	String input_charset = (String)request.getParameter("input_charset");

	String notify_url = (String)request.getParameter("notify_url");

	String order_no = (String)request.getParameter("order_no");

	String order_time = (String)request.getParameter("order_time");

	String order_amount = (String)request.getParameter("order_amount");

	String product_name = (String)request.getParameter("product_name");

	String product_code = (String)request.getParameter("product_code");

	String product_desc = (String)request.getParameter("product_desc");

	String product_num = (String)request.getParameter("product_num");

	String show_url = (String)request.getParameter("show_url");

	String client_ip = (String)request.getParameter("client_ip");

	String bank_code = (String)request.getParameter("bank_code");

	String redo_flag = (String)request.getParameter("redo_flag");

	String extend_param = (String)request.getParameter("extend_param");

	String extra_return_param = (String)request.getParameter("extra_return_param");

	String return_url = (String)request.getParameter("return_url");

/*************************** 数据签名 ***************************
签名规则定义如下：
（1）参数列表中，除去sign_type、sign两个参数外，其它所有非空的参数都要参与签名，值为空的参数不用参与签名；
（2）签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推，组成规则如下：
参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n
*/

/*************************** Data signature ***************************
The definition of signature rule is as follows : 
（1） In the list of parameters, except the two parameters of sign_type and sign, all the other parameters that are not in blank shall be signed, the parameter with value as blank doesn’t need to be signed; 
（2） The sequence of signature shall be in the sequence of parameter name from a to z, in case of same first letter, then in accordance with the second letter, so on so forth, the composition rule is as follows : 
Parameter name 1 = parameter value 1& parameter name 2 = parameter value 2& ......& parameter name N = parameter value N
*/
	StringBuffer signStr= new StringBuffer();
	signStr.append("input_charset=").append(input_charset).append("&");
	signStr.append("interface_version=").append(interface_version).append("&");
	signStr.append("merchant_code=").append(merchant_code).append("&");
	signStr.append("notify_url=").append(notify_url).append("&");
	signStr.append("order_amount=").append(order_amount).append("&");
	signStr.append("order_no=").append(order_no).append("&");
	signStr.append("order_time=").append(order_time).append("&");
	signStr.append("product_name=").append(product_name).append("&");
	signStr.append("service_type=").append(service_type);
	String signInfo = signStr.toString();
	String sign="";
	if("RSA-S".equals(sign_type)){
	sign=RSAWithSoftware.signByPrivateKey(signInfo,RSA_S_PrivateKey);
	}else{
		String merPfxPath = "G:/1111110166.pfx";// 商家的pfx证书位置文件(公私钥合一)
		String pfxPass = "87654321";
		RSAWithHardware mh = new RSAWithHardware();
		// 商家签名（签名后报文发往dinpay）
		mh.initSigner(merPfxPath, pfxPass);
		sign = mh.signByPriKey(signInfo);
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body onLoad="document.dinpayForm.submit();">
		<form name="dinpayForm" method="post" action="https://pay.dinpay.com/gateway?input_charset=UTF-8" target="_self">
			<input type="hidden" name="sign"          value="<%=sign%>" />
			<input type="hidden" name="merchant_code" value="<%=merchant_code%>" />
			<input type="hidden" name="bank_code"     value="<%=bank_code%>"/>
			<input type="hidden" name="order_no"      value="<%=order_no%>"/>
			<input type="hidden" name="order_amount"  value="<%=order_amount%>"/>
			<input type="hidden" name="service_type"  value="<%=service_type%>"/>
			<input type="hidden" name="pay_type"  value="<%=pay_type%>"/>
			<input type="hidden" name="input_charset" value="<%=input_charset%>"/>
			<input type="hidden" name="notify_url"    value="<%=notify_url%>">
			<input type="hidden" name="interface_version" value="<%=interface_version%>"/>
			<input type="hidden" name="sign_type"     value="<%=sign_type%>"/>
			<input type="hidden" name="order_time"    value="<%=order_time%>"/>
			<input type="hidden" name="product_name"  value="<%=product_name%>"/>
			<input Type="hidden" name="client_ip"     value="<%=client_ip%>"/>
			<input Type="hidden" name="extend_param"  value="<%=extend_param%>"/>
			<input Type="hidden" name="extra_return_param" value="<%=extra_return_param%>"/>
			<input Type="hidden" name="product_code"  value="<%=product_code%>"/>
			<input Type="hidden" name="product_desc"  value="<%=product_desc%>"/>
			<input Type="hidden" name="product_num"   value="<%=product_num%>"/>
			<input Type="hidden" name="return_url"    value="<%=return_url%>"/>
			<input Type="hidden" name="show_url"      value="<%=show_url%>"/>
			<input Type="hidden" name="redo_flag"     value="<%=redo_flag%>"/>
		</form>
	</body>
</html>