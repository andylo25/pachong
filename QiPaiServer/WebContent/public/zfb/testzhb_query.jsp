<%@ page language="java" import="java.util.*,java.text.*"
    pageEncoding="UTF-8"%>
<%
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
       String orderId = sdf.format(new Date());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form name="Form1" method="post" action="zhb_query.jsp" target="_blank">
	<br>服务类型(service_type)：<input Type="text" Name="service_type" value="single_trade_query">
	<br>商 家 号(merchant_code)：<input Type="text" Name="merchant_code" value="1111110166">
	<br>接口版本(interface_version)：<input Type="text" Name="interface_version" value="V3.0"/>
	<br>签名方式(sign_type)：
	<select name="sign_type">
		<option value="RSA-S">RSA-S</option>
		<option value="RSA">RSA</option>
	</select>
	<br>商户网站唯一订单号(order_no)：<input Type="text" Name="order_no" value=""/>
	<br>智付订单号(trade_no)：<input Type="text" Name="trade_no" value="">
	<br><input type="submit" name="btn" value="在线支付">
</form>
</body>

</html>