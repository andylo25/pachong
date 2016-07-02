<%@ page language="java" import="java.util.*, cn.zf.qrcode.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String webRootPath = request.getSession().getServletContext().getRealPath("/"); 
	
	String imgPath = webRootPath + "resources/images/logo_QRCode.png";   
	String logoPath = webRootPath + "resources/images/dinpay.png";  
	String encoderContent = (String) session.getAttribute("qrcode"); 
	if(encoderContent == null){
		encoderContent = "weixin://wxpay/bizpayurl?pr=grELAoH";
	}
	QrcodeDis myQrcode = new QrcodeDis();  
	myQrcode.createQRCode(encoderContent, imgPath, logoPath, 15);  
%>

<html>
  <head>   
    <title>My JSP 'index.jsp' starting page</title>
  </head>  
  <body>
    <div style="text-align:center"><img src="<%=basePath%>resources/images/logo_QRCode.png"/></div>
    <div style="text-align:center"><h3></>Qrcode:<%=encoderContent%><h3/></div>
  </body>
</html>
