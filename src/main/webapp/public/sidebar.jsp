<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <shiro:guest>  
                        <span class="icon icon-shield" style="font-size:30px; color:#3498db;"></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="todo!!!!!!!!!">
                            <span class="clear">
                                <span class="block m-t-xs">
                                    <strong class="font-bold">游客</strong>
                                </span>
                                <span class="text-muted text-xs block">欢迎使用LMS！</span>
                            </span>
                        </a>
                    </shiro:guest>
                    <shiro:hasPermission name="reader:*">
                        <span>
                            <a href="/lms/personal">
                                <img alt="image" class="img-circle" src="img/profile_small.jpg" />
                            </a>
                        </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
                            <span class="clear">
                                <span class="block m-t-xs">
                                    <strong class="font-bold">${infoUser.name}</strong>
                                </span>
                                <span class="text-muted text-xs block">个人中心</span>
                            </span>
                        </a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="admin:*">
                        <span class="icon icon-shield" style="font-size:30px; color:#3498db;"></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="todo!!!!!!!!!">
                            <span class="clear">
                                <span class="block m-t-xs">
                                    <strong class="font-bold"><shiro:principal/></strong>
                                </span>
                                <span class="text-muted text-xs block">欢迎使用LMS！</span>
                            </span>
                        </a>
                    </shiro:hasPermission>
                </div>
                <div class="logo-element">
                    LMS
                </div>
            </li>
            <li>
                <a href="index.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">首页</span></a>
            </li>
            <shiro:hasPermission name="RABC:*">
                <li>
                    <a href="index.html#"><i class="fa fa-th-large"></i> <span class="nav-label">RBAC权限管理</span> <span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="/lms/UserManagement">用户管理</a></li>
                        <li><a href="/lms/RoleManagement">角色管理</a></li>
                        <li><a href="/lms/PermissionManagement">权限管理</a></li>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="Operation:*">
                <li>
                    <a href="index.html#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">业务管理</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse">
                        <shiro:hasPermission name="ReaderManagement:*">
                            <li><a href="/lms/ReaderManagement">读者管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="ReaderCategoryManagement:*">
                            <li><a href="/lms/ReaderCategoryManagement">读者类型管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="InfoBookManagement:*">
                            <li><a href="/lms/InfoBookManagement">图书管理/增加书目</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="InfoCollectionManagement:*">
                            <li><a href="/lms/InfoCollectionManagement">馆藏信息管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="BorrowManagement:*">
                            <li><a href="/lms/BorrowManagement">图书借阅管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="ReturnManagement:*">
                            <li><a href="/lms/ReturnManagement">图书归还管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="ReturnErrorManagement:*">
                            <li><a href="/lms/ReturnErrorManagement">图书遗失/损坏管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="ReservationManagement:*">
                            <li><a href="/lms/ReservationManagement">图书预约管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="InfoPenaltyManagement:*">
                            <li><a href="/lms/InfoPenaltyManagement">惩罚管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="LuceneManagement:*">
                            <li><a href="/lms/LuceneManagement">重新生成Lucene索引</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="NewAccountManagement:*">
                            <li><a href="/lms/NewAccountManagement">新生账号生成</a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="admin:*">
                <li>
                    <a href="index.html#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">系统维护</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse">
                        <li><a href="#">数据备份</a></li>
                        <li><a href="#">数据还原</a></li>
                    </ul>
                </li>
                <li>
                    <a href="index.html#"><i class="fa fa-diamond"></i> <span class="nav-label">数据统计</span></a>
                </li>
            </shiro:hasPermission>
            <li>
                <a href="/lms/adminIndex"><i class="fa fa-diamond"></i> <span class="nav-label">概况</span></a>
            </li>
            <li>
                <a href="index.html#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">服务</span></a>
            </li>
            <li>
                <a href="mailbox.html"><i class="fa fa-envelope"></i> <span class="nav-label">资源</span><span class="label label-warning pull-right">16/24</span></a>
            </li>
            <li>
                <a href="metrics.html"><i class="fa fa-pie-chart"></i> <span class="nav-label">分馆</span>  </a>
            </li>
            <li>
                <a href="widgets.html"><i class="fa fa-flask"></i> <span class="nav-label">咨询台</span></a>
            </li>
            <shiro:user>
                <li>
                    <a href="/lms/resetPassword"><i class="fa fa-pie-chart"></i> <span class="nav-label">更改口令</span></a>
                </li>
            </shiro:user>
        </ul>

    </div>
</nav>
