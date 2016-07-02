<%@ page language="java" import="java.util.*, cn.zf.qrcode.*" pageEncoding="UTF-8"%>

<%	
	String merchant_code = (String) session.getAttribute("merchant_code");
	String order_no = (String) session.getAttribute("order_no"); 
	String order_amount = (String) session.getAttribute("order_amount"); 
	String order_time = (String) session.getAttribute("order_time"); 
	String trade_no = (String) session.getAttribute("trade_no");
	String trade_status = (String) session.getAttribute("trade_status"); 
	String trade_time = (String) session.getAttribute("trade_time");  
%>

<html>
  <head>   
    <title>My JSP 'index.jsp' starting page</title>
  </head>  
  <body>
  	<div style="text-align:center">
		<table border="1" >
			<tr><th>查询结果</th></tr>
    		<tr><td>商 家 号(merchant_code)--> <%=merchant_code%></td></tr>
			<tr><td>商户订单号(order_no)--> <%=order_no%></td></tr>
			<tr><td>商户订单金额(order_amount)--> <%=order_amount%></td></tr>
			<tr><td>商户订单时间(order_time)--> <%=order_time%></td></tr>
			<tr><td>智付订单号(trade_no)--> <%=trade_no%></td></tr>
			<tr><td>智付订单状态(trade_status)--> <%=trade_status%></td></tr>
			<tr><td>智付订单时间(trade_time)--> <%=trade_time%></td></tr>
		</table>
	</div>
  </body>
</html>
