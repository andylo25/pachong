<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.itrus.util.sign.*" %>
<%
request.setCharacterEncoding("UTF-8");

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


	// 接收表单提交参数(To receive the parameter)
	String merchant_code = request.getParameter("merchant_code");
	String interface_version = request.getParameter("interface_version");
	String sign_type = request.getParameter("sign_type");
	String service_type = request.getParameter("service_type");
	String order_no = request.getParameter("order_no");
	String trade_no = request.getParameter("trade_no");


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
	StringBuilder signStr = new StringBuilder();
	signStr.append("interface_version=").append(interface_version).append("&");
	signStr.append("merchant_code=").append(merchant_code).append("&");
	signStr.append("order_no=").append(order_no).append("&");
	signStr.append("service_type=").append(service_type).append("&");
	if(!"".equals(trade_no)){
	signStr.append("trade_no=").append(trade_no);
	}
	String merSign = "";
	String signInfo = signStr.toString();
	if("RSA-S".equals(sign_type)){
	merSign=RSAWithSoftware.signByPrivateKey(signInfo,RSA_S_PrivateKey);
	}else{
	String merPfxPath = "G:/1111110166.pfx";// 商家的pfx证书位置文件(公私钥合一)
	String pfxPass = "87654321";
	RSAWithHardware mh = new RSAWithHardware();
	// 商家签名（签名后报文发往dinpay）
	mh.initSigner(merPfxPath, pfxPass);
	merSign = mh.signByPriKey(signInfo);
	}

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body onLoad="javascript:document.getElementById('queryForm').submit();">
		<form  id="queryForm" action="https://query.dinpay.com/query" method="post" target="_self">
			<input type="hidden" name="service_type" value="<%=service_type%>" />
			<input type="hidden" name="interface_version" value="<%=interface_version%>" />
			<input type="hidden" name="sign_type" value="<%=sign_type%>" />
			<input type="hidden" name="sign" value="<%=merSign%>" />
			<input type="hidden" name="order_no" value="<%=order_no%>"/>
			<input type="hidden" name="merchant_code" value="<%=merchant_code%>" />
			<input type="hidden" name="trade_no" value="<%=trade_no%>" />
		</form>
	</body>
</html>