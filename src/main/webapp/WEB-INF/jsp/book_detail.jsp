<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>LMS</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="css/plugins/slick/slick.css" rel="stylesheet">
    <link href="css/plugins/slick/slick-theme.css" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<body>
    <div id="wrapper">
		<jsp:include page="/public/sidebar.jsp"></jsp:include>

		<div id="page-wrapper" class="gray-bg dashbard-1">
			<jsp:include page="/public/nav-top.jsp"></jsp:include>
	        <div class="row wrapper border-bottom white-bg page-heading">
	            <div class="col-lg-10">
	                <h2>图书详情</h2>
	                <ol class="breadcrumb">
	                    <li><a href="/">首页</a></li>
	                    <li class="active">图书详情</li>
	                </ol>
	            </div>
	        </div>
	        <div class="wrapper wrapper-content animated fadeInRight">
	            <div class="row">
	                <div class="col-lg-12">
	                    <div class="ibox product-detail">
	                        <div class="ibox-content">
	                            <div class="row">
	                                <div class="col-md-5">
	                                    <div class="product-images">
	                                        <div>
	                                            <div class="image-imitation">
	                                                [IMAGE 1]
	                                            </div>
	                                        </div>
	                                        <div>
	                                            <div class="image-imitation">
	                                                [IMAGE 2]
	                                            </div>
	                                        </div>
	                                        <div>
	                                            <div class="image-imitation">
	                                                [IMAGE 3]
	                                            </div>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="col-md-7">
	                                    <h2 class="font-bold m-b-xs">
	                                        ${infoBook.title}
	                                    </h2>
	                                    <hr>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>ISBN:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.isbn}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>著者:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.author}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>译者:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.translator}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>绘者:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.painter}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>出版社:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.publisher}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>出版年:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		<fmt:formatDate value="${infoBook.publishedin}" pattern="yyyy" />
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>简介:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.description}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>中图法分类号:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.clc}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>卷册说明:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.volume}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>语种分类:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		${infoBook.languages}
	                                    	</div>
	                                    </div>
	                                    <div class="row">
	                                    	<div class="col-md-3">
	                                    		<h4>单价:</h4>
	                                    	</div>
	                                    	<div class="col-md-9 text-muted" style="line-height:25px!important">
	                                    		RMB:${infoBook.price}
	                                    	</div>
	                                    </div>
	                                    <hr>
	                                    <div>
	                                    	<shiro:hasPermission name="reader:*">
		                                        <div class="btn-group">
		                                            <button class="btn btn-primary btn-sm" onclick="AddCollection()"><i class="fa fa-star"></i> 添加收藏</button>
		                                        </div>
	                                        </shiro:hasPermission>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <div class="row">
	            	<div class="col-lg-12">
		                <div class="ibox float-e-margins">
		                    <div class="ibox-title">
		                        <h5>馆藏复本</h5>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content">
		                        <table class="table">
		                            <thead>
			                            <tr>
			                                <th>#</th>
			                                <th>条码</th>
			                                <th>索书号</th>
			                                <th>馆藏位置</th>
			                                <th>馆藏类型</th>
			                                <th>借阅状态</th>
			                                <th>预约状态</th>
			                            </tr>
		                            </thead>
		                            <tbody>
		                            	<c:forEach items="${list}" var="list">
		                            		<c:if test="${not empty list}">
					                            <tr>
					                                <td><c:if test="${list.isBorrowing == 1 && list.isReservation == 0}"><a href="javascript:void(0)" onclick="rreservation('${list.barCode }')">预约</a></c:if></td>
					                                <td>${list.barCode}</td>
					                                <td>${list.callno}</td>
					                                <td>${list.located}</td>
					                                <td>${list.type}</td>
					                                <td>${list.isBorrowing}</td>
					                                <td>${list.isReservation}</td>
					                            </tr>
		                            		</c:if>
		                            		<c:if test="${empty list}">
					                            <tr>
					                            	<td>图书馆暂未收录该图书！</td>
					                            </tr>
		                            		</c:if>
		                            	</c:forEach>
		                            </tbody>
		                        </table>
		                    </div>
		                </div>
	            	</div>
	            </div>
	        </div>
			<jsp:include page="/public/footer.jsp"></jsp:include>
		</div>
    </div>

    <!-- Mainly scripts -->
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
   	<script src="js/inspinia.js"></script>
    <script src="js/plugins/pace/pace.min.js"></script>

    <!-- jQuery UI -->
    <script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>

	<!-- slick carousel-->
	<script src="js/plugins/slick/slick.min.js"></script>
	<script>
	    $(document).ready(function(){
	
	
	        $('.product-images').slick({
	            dots: true
	        });
	
	    });
	    
	    function rreservation(barCode) {
	    	$.ajax({
				url: '/lms/rreservation.do',
				type: 'GET',
				dataType: 'json',
				contentType:'application/json;charset=UTF-8',
				data:{barCode: barCode},
				success:function(msg) {
					alert(msg.reasult);
					javascript:location.reload();
				},
				error:function() {
					alert("预约执行失败！");
				}
	    	});
	    }
	    
	    function AddCollection() {
	    	var bid = GetQueryString("bid");
            $.post('/lms/addCollection.do', {bid: bid}, function(msg) {
            	alert(msg.result);
            }, 'json');
	    };
		
		
		function GetQueryString(name) {
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if(r!=null)return  unescape(r[2]); return null;
		};
	</script>
</body>
</html>