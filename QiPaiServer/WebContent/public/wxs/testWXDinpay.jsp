<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
       String orderId = sdf.format(new Date());
%>

<html>
  <head>    
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
	<form method="post" action="WXDinpay.jsp" target="_blank">
		<br>商 家 号(merchant_code)：<input Type="text" Name="merchant_code" value="2010416825"> *
		<br>服务类型(service_type)：<input Type="text" Name="service_type" value="wxpay"> *
		<br>服务器异步通知地址(notify_url)：<input Type="text" Name="notify_url" value="http://119.90.135.144:8080/qps/user/charge"> *				
		<br>接口版本(interface_version)：<input Type="text" Name="interface_version" value="V3.0"/> *																	
		<br>签名方式(sign_type)：
			<select name="sign_type">
				<option value="RSA-S">RSA-S</option>
				<option value="RSA">RSA</option>
			</select> *																		
		<br>商户订单号(order_no)：<input Type="text" Name="order_no" value="<%=orderId%>"> *
		<br>商户订单时间(order_time)：<input Type="text" Name="order_time" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>"/>*		
		<br>商户订单金额(order_amount)：<input Type="text" Name="order_amount" value="10"> *		
		<br>商品名称(product_name)：<input Type="text" Name="product_name" value="coin"> *
		<br><input Type="submit" Name="submit" value="微信支付测试">
	</form>
  </body>
</html>
