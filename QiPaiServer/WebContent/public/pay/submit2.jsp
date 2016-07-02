<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.itrus.util.sign.*" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body onLoad="document.dinpayForm.submit();">
		<form name="dinpayForm" method="post" action="http://127.0.0.1:8080/qps/user/charge" target="_self">
			<input type="hidden" name="sign"          value="LJrIx7TZxdLfeNRL4wBkdea8ec6bc8zMoKVCN3+4Tdz/0zuOGiAvENqG30jFfL3rNF2QpS4+JE4FYzSVnSAIe62DGStD7cwRhVJoN2LjouY35Mh0KNFmTjdHIPnW4u+WKtxz3nrDbARRB7+UWNovsEzVt48KV6qO3jI0fh4QZRc=" />
			<input type="hidden" name="merchant_code" value="2010416825" />
			<input type="hidden" name="bank_code"     value=""/>
			<input type="hidden" name="bank_seq_no"     value="201606261116352056208"/>
			<input type="hidden" name="order_no"      value="BLMM2016062611164620"/>
			<input type="hidden" name="order_amount"  value="1"/>
			<input type="hidden" name="trade_no"  value="1175769433"/>
			<input type="hidden" name="notify_type"  value="offline_notify"/>
			<input type="hidden" name="trade_status" value="SUCCESS"/>
			<input type="hidden" name="notify_id"    value="951ce42fd7ed4c1da8e69c6e800166b0">
			<input type="hidden" name="interface_version" value="V3.0"/>
			<input type="hidden" name="sign_type"     value="RSA-S"/>
			<input type="hidden" name="order_time"    value="2016-06-26 11:16:46"/>
			<input type="hidden" name="trade_time"  value="2016-06-26 11:16:35"/>
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