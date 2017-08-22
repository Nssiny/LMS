<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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

    <title>LMS | 系统管理</title>
    <link rel="shortcut icon" href="img/gt_favicon.png">

    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<body>
    <div id="wrapper">
		<jsp:include page="/public/sidebar.jsp"></jsp:include>

		<div id="page-wrapper" class="gray-bg dashbard-1">
			<jsp:include page="/public/nav-top.jsp"></jsp:include>
            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-lg-9">
                    <h2>重新生成Lucene索引</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">重新生成Lucene索引</li>
                    </ol>
                </div>
            </div>
			<div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		            <div class="col-lg-12">
		                <div class="ibox">
		                	<div class="ibox-content">
								<h2>业务介绍</h2>
								<p>若馆藏检索出现BUG，可使用该业务重新生成索引。</p>
								<div class="row">
									<div class="col-lg-4">
										<button type="button" class="btn btn-danger" onclick="reFreshIndex()">重新生成索引</button>
									</div>
								</div>
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

	<script>
		function reFreshIndex() {
			$.ajax({
				type:'post',
				url:'/lms/createAllIndex.do',
				success:function(msg) {
					alert(msg.reasult);
				}
			});
		}
	</script>
</body>
</html>