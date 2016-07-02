<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.itrus.util.sign.*" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body onLoad="document.dinpayForm.submit();">
		<form name="dinpayForm" method="post" action="https://pay.dinpay.com/gateway?input_charset=UTF-8" target="_self">
			<input type="hidden" name="sign"          value="pLKsNHvDOQkwtUcIYfbfuNWdbg1ICXP03ipJK1y/JmUQTlIZaJMsFKpY8HMMOsR5o+VT0gpus8uvAdZul6foOMgFYZSOeOM5B0HuAvQLZtCpKRYG+VOpR2QKW0HpP1XpWfFgtT02h6z6vSg/7MFZy4DDAzaj3DUeGF/96MWTTFY=" />
			<input type="hidden" name="merchant_code" value="2010416825" />
			<input type="hidden" name="bank_code"     value=""/>
			<input type="hidden" name="order_no"      value="BLMM2016061623521620"/>
			<input type="hidden" name="order_amount"  value="100"/>
			<input type="hidden" name="service_type"  value="direct_pay"/>
			<input type="hidden" name="pay_type"  value=""/>
			<input type="hidden" name="input_charset" value="UTF-8"/>
			<input type="hidden" name="notify_url"    value="http://119.90.135.144:8080/qps/user/charge">
			<input type="hidden" name="interface_version" value="V3.0"/>
			<input type="hidden" name="sign_type"     value="RSA-S"/>
			<input type="hidden" name="order_time"    value="2016-06-17 00:11:07"/>
			<input type="hidden" name="product_name"  value="test"/>
			<input Type="hidden" name="client_ip"     value=""/>
			<input Type="hidden" name="extend_param"  value=""/>
			<input Type="hidden" name="extra_return_param" value="20"/>
			<input Type="hidden" name="product_code"  value=""/>
			<input Type="hidden" name="product_desc"  value=""/>
			<input Type="hidden" name="product_num"   value=""/>
			<input Type="hidden" name="return_url"    value=""/>
			<input Type="hidden" name="show_url"      value=""/>
			<input Type="hidden" name="redo_flag"     value=""/>
		</form>
	</body>
</html>