<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport"    content="width=device-width, initial-scale=1.0">
	<meta name="description" content="signin">
	<meta name="author"      content="org.sysu">
	
    <title>Login</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">
	
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/icomoon.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

  <body class="gray-bg">
	<div class="middle-box text-center loginscreen animated fadeInDown">
	    <div>
	        <div>
	
	            <!-- <h1 class="logo-name">LMS</h1> -->
				<h1><span class="icon icon-shield"></span></h1>
				
	        </div>
	        <h3>Welcome to LMS</h3>
	        <p>

	        </p>
	        <p>用户登录</p>
	        <form class="m-t" role="form" action="/lms/checkLogin.do" method="post">
	            <div class="form-group">
	                <input type="text" name="user" class="form-control" placeholder="账号" required="">
	            </div>
	            <div class="form-group">
	                <input type="password" name="password" class="form-control" placeholder="密码" required="">
	            </div>
	            <button type="submit" class="btn btn-primary block full-width m-b">Login</button>
	
	            <!-- <a href="login.html#"><small>忘记密码？</small></a> -->
	            <p class="text-muted text-center"><small>初始密码为123</small></p>
	        </form>
	        <p class="m-t"> <small>created by org.sysu &copy; 2017</small> </p>
	    </div>
	</div>

	<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>
