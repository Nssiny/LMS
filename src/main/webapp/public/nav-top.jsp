<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="row border-bottom">
	<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
            <form role="search" class="navbar-form-custom" action="/lms/ftSearch.do">
                <div class="form-group">
                    <input type="text" placeholder="馆藏查询" class="form-control" name="search_input" id="search_input">
                </div>
            </form>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li>
                <span class="m-r-sm text-muted welcome-message">Welcome to LMS.</span>
            </li>
            <li>
            	<shiro:guest>
	                <a href="/lms/login.do">
	                    <i class="fa fa-sign-out"></i> 登录
	                </a>
                </shiro:guest>
                <shiro:user>
	                <a href="/lms/logout">
	                    <i class="fa fa-sign-out"></i> 退出
	                </a>
                </shiro:user>
            </li>
        </ul>
	</nav>
</div>