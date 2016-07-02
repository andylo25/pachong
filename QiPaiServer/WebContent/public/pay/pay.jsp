<%@ page language="java" import="java.util.*,java.text.*"
    pageEncoding="UTF-8"%>
<html>
	<head>
	</head>
	<body>
		<form method="post" action="MerToDinpay.jsp" target="_blank">

			商 家 号（Merchant ID）：<input type="text" name="merchant_code" value="1111110166"> * <br/>

			服务类型（Service Type）：<input type="text" name="service_type" value="direct_pay"> * <br/>

			接口版本（Interface Version）：<input type="text" name="interface_version" value="V3.0"/> * <br/>
			<br>签名方式：
			<select name="sign_type">
			<option value="RSA-S">RSA-S</option>
			<option value="RSA">RSA</option>
			</select><br>

			参数编码字符集（Parameter Charset）：<input type="text" name="input_charset" value="UTF-8"/> *<br/>

			服务器异步通知地址（Notify URL）：<input type="text" name="notify_url" value="http://www.dinpay.com"/> * <br/>

			商家订单编号（Order No.）：<input type="text" name="order_no" value="DP1062783025"/> * <br/>

			商家订单时间（Order Time）：<input type="text" name="order_time" value="2015-07-27 14:30:25"/> * <br/>

			订单金额（Order Amount）：<input type="text" name="order_amount" value="0.01"/> * <br/>

			商品名称（Product Name）：<input type="text" name="product_name" value="testPay"/> * <br/>

			支付类型（Pay Type）：<input type="text" name="pay_type" value="">  <br/>

			商品编号（Product ID）：<input type="text" name="product_code" value=""><br/>

			商品描述（Product Desc）：<input type="text" name="product_desc" value=""><br/>

			商品数量（Product Num）：<input type="text" name="product_num" value=""><br/>

			商品展示URL（Product Show URL）：<input type="text" name="show_url" value=""><br/>

			客户端IP（Client IP）：<input type="text" name="client_ip" value=""><br/>

			直连通道标识（Bank Code）：<input type="text" name="bank_code" value=""><br/>

			商家订单号重复校验（Redo Flag）：<input type="text" name="redo_flag" value=""><br/>

			公用业务扩展参数（Extend Param）：<input type="text" name="extend_param" value=""><br/>

			公用回传参数（Extra Return Param）：<input type="text" name="extra_return_param" value=""><br/>

			页面跳转同步通知地址（Return URL）：<input type="text" name="return_url" value="">	

			<p><input type="submit" name="submit" value="提交（Submit）"></p>
		</form>
	</body>
</html>