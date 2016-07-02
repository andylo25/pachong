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

<head>
	<title>棋牌后台管理</title>
    <script type="text/javascript">
    	var basePath = "${basePath}";
    	var appPath = "${basePath}/admin/app";
    	var extjs = "${basePath}/resources/extjs";
    	var dataPath = "${basePath}/resources/data";
    	var imgPath = "${basePath}/resources/images";
    	var gl_login_userName = "${userName}";
    </script>
    <script type="text/javascript" src="config.js"></script>
	
	<!-- Ext JS Files -->
	<script type="text/javascript" src="${extjs}/shared/include-ext.js"></script>
	<script type="text/javascript" src="${extjs}/local/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${extjs}/shared/options-toolbar.js"></script>
    
    <!-- App Files -->
	<link rel="stylesheet" type="text/css" href="${css}/layout-browser.css">
	<link rel="stylesheet" type="text/css" href="${extjs}/ux/grid/css/GridFilters.css" />
    <link rel="stylesheet" type="text/css" href="${extjs}/ux/grid/css/RangeMenu.css" />
    <script type="text/javascript" src="index.js"></script>
    
</head>
<body>
	<div style="display:none;">

        <!-- Start page content -->
        <div id="start-div">
            <div style="margin-left:100px;">
                <h2>Welcome!</h2>
                <p>后台管理系统</p>
            </div>
        </div>

        <!-- Basic layouts -->
    </div>
</body>
</html>