<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="footer">
    <div class="pull-right">
		欢迎使用  <strong>LMS</strong> ！
    </div>
    <div>
        <strong>Copyright</strong> created by org.sysu &copy; 2017
    </div>
</div>