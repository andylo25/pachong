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
<form name="Form1" method="post" action="zhb_pay.jsp" target="_blank">
	<br>服务类型(service_type)：<input Type="text" Name="service_type" value="zhb_pay">
	<br>商 家 号(merchant_code)：<input Type="text" Name="merchant_code" value="1111110166">
	<br>字符集编码(input_charset)：<input Type="text" Name="input_charset" value="UTF-8">
	<br>服务器异步返回地址(notify_url)：<input Type="text" name="notify_url" value="http://172.21.53.191/MerchantReceiveDemo/merchantReceiveServlet"/>
	<br>接口版本(interface_version)：<input Type="text" Name="interface_version" value="V3.0"/>
	<br>签名方式(sign_type)：
	<select name="sign_type">
		<option value="RSA-S">RSA-S</option>
		<option value="RSA">RSA</option>
	</select>
	<br>商户网站唯一订单号(order_no)：<input Type="text" Name="order_no" value="<%=orderId%>"/>
	<br>商户订单时间(order_time)：<input Type="text" Name="order_time" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())%>"/>
	<br>订单金额(order_amount)：<input Type="text" Name="order_amount" value="0.01"/>
	<br>商品名称(product_name)：<input Type="text" Name="product_name" value="nalibuduile"/>
	<br>商品展示地址(show_url)：<input Type="text" Name="show_url" value=""/>
	<br>商品编号(product_code)：<input Type="text" Name="product_code" value=""/>
	<br>商品数量(product_num)：<input Type="text" Name="product_num" value="">
	<br>商品描述(product_desc)：<input Type="text" Name="product_desc" value="">
	<br>公用回传参数(extra_return_param)：<input Type="text" Name="extra_return_param" value="">
	<br>扩展参数(extend_param)：<input Type="text" Name="extend_param" value="">
	<br><input type="submit" name="btn" value="在线支付">
</form>
</body>

</html>