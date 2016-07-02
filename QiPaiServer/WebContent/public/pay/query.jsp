<%@ page language="java" import="java.util.*,java.text.*"
    pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
	<body>
		<form method="post" action="getXmlData.jsp" target="_blank">

			商 家 号（Merchant ID）：<input type="text" name="merchant_code"  value="1111110166" />*<br>

			接口版本（Interface Version）：<input type="text" name="interface_version"  value="V3.0" />*<br>
			<br>签名方式：
			<select name="sign_type">
			<option value="RSA-S">RSA-S</option>
			<option value="RSA">RSA</option>
			</select><br>

			服务类型（Service Type）：<input type="text" name="service_type"  value="single_trade_query" />*<br>

			商家订单号（Order NO.）：<input type="text" name="order_no"  value="3123123" />*<br>

			智付订单号（Trade NO.）：<input type="text" name="trade_no"  value="1232132" />
			<p><input type="submit" name="submit" value="提交（Submit）" /></p>

		</form>
	</body>
</html>