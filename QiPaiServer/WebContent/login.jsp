<%@page contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String resources = basePath+"/resources";
request.setAttribute("basePath", basePath);
request.setAttribute("extjs", resources+"/extjs");
request.setAttribute("css", resources+"/css");
request.setAttribute("images", resources+"/images");
request.setAttribute("data", resources+"/data");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>棋牌后台管理</title>
<link href="${css}/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${css}/style.css">

</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"><img src="${images}/logo.png" width="196" height="46"></div>
<div class="login_boder">

<form id="form1" action="${basePath}/user/login" method="post">
<div class="login_padding" id="login_model">
	<h3 style="color:red;">${error_msg}</h3>
	<br>
  <input type="hidden" name="admin" value="1"/>
  <h2>用户名</h2>
  <label>
    <input type="text" id="userName" name="userName" class="txt_input txt_input2" onfocus="if (value ==&#39;Your name&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;Your name&#39;}" value="Your name">
  </label>
  <h2>密码</h2>
  <label>
    <input type="password" name="pwd" id="userpwd" class="txt_input" onfocus="if (value ==&#39;******&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;******&#39;}" value="******">
  </label>
 
</form>
 
  <p class="forgot"><a id="iforget" href="javascript:void(0);">忘记密码?</a></p>
  <div class="rem_sub">
  <div class="rem_sub_l">
  <input type="checkbox" name="checkbox" id="save_me">
   <label for="save_me">记住登录信息</label>
   </div>
    <label>
      <input type="submit" class="sub_button" name="button" id="button" value="登录" style="opacity: 0.7;">
    </label>
  </div>
</div>

<div id="forget_model" class="login_padding" style="display:none">
<br>
   <h1>Forgot password</h1>
   <br>
   <div class="forget_model_h2">(请输入注册邮箱.)</div>
    <label>
    <input type="text" id="usrmail" class="txt_input txt_input2">
   </label>

 
  <div class="rem_sub">
    <label>
     <input type="submit" class="sub_buttons" name="button" id="Retrievenow" value="Retrieve now" style="opacity: 0.7;">
     <input type="submit" class="sub_button" name="button" id="denglou" value="Return" style="opacity: 0.7;">
    
    </label>
  </div>
</div>



<!--login_padding  Sign up end-->
</div><!--login_m end-->
 <br> <br>


</body></html>