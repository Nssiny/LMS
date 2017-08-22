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
    <link href="css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">
    <link href="css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="css/plugins/steps/jquery.steps.css" rel="stylesheet">
	<link href="css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">

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
                    <h2>读者管理</h2>
                    <ol class="breadcrumb">
                        <li><a href="/">业务管理</a></li>
                        <li class="active">读者管理</li>
                    </ol>
                </div>
            </div>
            <div class="wrapper wrapper-content animated fadeInRight">
            	<div class="row">
            		<div class="col-lg-12">
            			<div class="ibox">
            				<div class="ibox-content">
            					<h2>业务介绍</h2>
            					<p>支持重置读者账号密码，以及更改其借书证相关信息。</p>
            					<form id="rmSteps" action="/lms/ReaderManagement/rmidForm.do">
            						<h1>读者账号</h1>
            						<fieldset>
            							<div class="row">
            								<div class="col-lg-6">
	            								<div class="form-group">
	            									<label for="rmLoginID" class="control-label">读者账号 *</label>
	            									<input id="rmLoginID" name="rmLoginID" type="text" class="form-control" value="" required>
	            								</div>
            								</div>
            							</div>
            						</fieldset>
            						<h1>信息修改</h1>
            						<fieldset>
            							<div class="ibox float-e-margins">
            								<div class="ibox-title">
					                            <div class="ibox-tools">
					                                <a href="javascript:void(0)" onclick="formEdit()">
					                                    <i class="fa fa-edit"></i>
					                                </a>
					                                <a href="javascript:void(0)" onclick="formReset()">
					                                    <i class="fa fa-undo"></i>
					                                </a>
					                            </div>
            								</div>
            								<div class="ibox-content">
		            							<div class="row">
		            								<div class="col-md-6">
		            									<h2 class="font-Bold m-b-xs">基本信息</h2>
		            									<hr>
		            									<div class="form-group">
			            									<label class="control-label">读者账号 *</label>
			            									<p id="rmLoginIDshow" class="form-control-static"></p>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmName" class="control-label">姓名 *</label>
			            									<input id="rmName" name="rmName" type="text" class="form-control" value="" readonly required>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmPassword" class="control-label">密码  *</label>
			            									<input id="rmPassword" name="rmPassword" type="password" class="form-control" value="" readonly required>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmPhone" class="control-label">联系电话 </label>
			            									<input id="rmPhone" name="rmPhone" type="text" class="form-control" value="" readonly>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmEmail" class="control-label">E-mail </label>
			            									<input id="rmEmail" name="rmEmail" type="email" class="form-control" value="" readonly>
		            									</div>
		            								</div>
			            							<div class="col-md-6">
			            								<h2 class="font-Bold m-b-xs">借阅证</h2>
			            								<hr>
		            									<div class="form-group">
			            									<label for="rmCategory" class="control-label">读者类型  *</label>
			            									<div id="rmCategory">
			            										<div class="row">
			            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="rmCategory" value="1" style="display: inline-block;"> <i></i> 本科生 </label>
			            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="rmCategory" value="2" style="display: inline-block;"> <i></i> 研究生 </label>
			            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="rmCategory" value="3" style="display: inline-block;"> <i></i> 博士生 </label>
		            											</div>
		            											<div class="row">
			            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="rmCategory" value="4" style="display: inline-block;"> <i></i> 讲&nbsp;&nbsp;&nbsp;&nbsp;师 </label>
			            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="rmCategory" value="5" style="display: inline-block;"> <i></i> 副教授 </label>
			            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio" name="rmCategory" value="6" style="display: inline-block;"> <i></i> 教&nbsp;&nbsp;&nbsp;&nbsp;授 </label>
		            											</div>
		            										</div>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmAllocate" class="control-label">发证日期 *</label>
			            									<input id="rmAllocate" name="rmAllocate" type="text" class="form-control" data-mask="9999-99-99" value="" readonly required>
			            									<span class="help-block">yyyy-MM-dd</span>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmExpire" class="control-label">失效日期 *</label>
		            										<input id="rmExpire" name="rmExpire" type="text" class="form-control" data-mask="9999-99-99" value="" readonly required>
		            										<span class="help-block">yyyy-MM-dd</span>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmPenalty" class="control-label">违规次数 *</label>
			            									<input id="rmPenalty" name="rmPenalty" type="text" class="form-control" value="" readonly required>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmBorrowed" class="control-label">已借图书 *</label>
			            									<input id="rmBorrowed" name="rmBorrowed" type="text" class="form-control" value="" readonly required>
		            									</div>
		            									<div class="form-group">
			            									<label for="rmStatus" class="control-label">状态 *</label>
		            										<div id="rmStatus" class="i-checks">
		            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio"  name="rmStatus" value="0" style="display: inline-block;" required> <i></i> 注销 </label>
		            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio"  name="rmStatus" value="1" style="display: inline-block;"> <i></i> 正常 </label>
		            											<label style="margin-left: 10px;padding-left: 20px;font-weight: 400;"> <input type="radio"  name="rmStatus" value="2" style="display: inline-block;"> <i></i> 冻结 </label>
		            										</div>
		            									</div>
			            							</div>
		            							</div>
            								</div>
            							</div>
            						</fieldset>
            					</form>
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
    
    <!-- Steps -->
    <script src="js/plugins/staps/jquery.steps.min.js"></script>

    <!-- Jquery Validate -->
    <script src="js/plugins/validate/jquery.validate.min.js"></script>
    
    <!-- Jquery form js -->
    <script src="js/jquery.form.js"></script>
    
    <!-- iCheck -->
    <script src="js/plugins/iCheck/icheck.min.js"></script>
    
	<!-- Input Mask-->
	<script src="js/plugins/jasny/jasny-bootstrap.min.js"></script>
    
    <script>
    	$(document).ready(function(){
			var rmFormOP = {
				type: 'POST',
				dataType: 'json',
				url: '/lms/rmidForm.do',
				success: function(msg) {
					if(msg.reasult == 0) {
						alert("更改失败！")
						formReset();
					} else if(msg.reasult == 1) {
						alert("更改成功！");
						formReset();
					}
				},
				error: function() {
					alert("提交失败！");
					formReset();
				}
			};
			$('#rmSteps').ajaxForm(rmFormOP).submit(function(){return false;});
    		$("#rmSteps").steps({
    			bodyTag: "fieldset",
    			showFinishButtonAlways: false,
    			enableCancelButton: false,
    			onStepChanging: function(event, currentIndex, newIndex) {
                    // Always allow going backward even if the current step contains invalid fields!
                    if(currentIndex > newIndex) {
                    	location.reload();
                        return true;
                    }

                    var rmSteps = $(this);
                    // Clean up if user went backward before
                    if(currentIndex < newIndex) {
                    	$(".body:eq(" + newIndex + ") label.error", rmSteps).remove();
                    	$(".body:eq(" + newIndex + ") .error", rmSteps).removeClass("error");
                    }
                    // Disable validation on fields that are disabled or hidden.
                    rmSteps.validate().settings.ignore = ":disabled,:hidden";
                    // Start validation; Prevent going forward if false
                    return rmSteps.valid();
    			},
    			onStepChanged: function(event, currentIndex, priorIndex) {
    				if(currentIndex === 1) {
    					var login_id = $("#rmLoginID").val();
    					rmShowDetail(login_id);
 						$(".actions.clearfix").find("a:last").attr('href', '#');
						$(".actions.clearfix").find("li:last").attr('class', 'disabled');
    				}
    			},
    			onFinishing: function(event, currentIndex) {
    				var rmSteps = $(this);
    				rmSteps.validate().settings.ignore = ":disabled";
    				return rmSteps.valid();
    			},
    			onFinished: function(event, currentIndex) {
    				$('#rmSteps').submit();
 					$(".actions.clearfix").find("a:last").attr('href', '#');
					$(".actions.clearfix").find("li:last").attr('class', 'disabled');
    				//formReset();
    			}
    		}).validate({
    			errorPlacement: function(error, element) {
    				element.before(error);
    			},
    			rules: {
    				rmPenalty: {
    					digits:true
    				},
    				rmBorrowed: {
    					digits:true
    				}
    			}
    		});
    	});

 		function formReset() {
 			var login_id = $("#rmLoginID").val();
 			rmShowDetail(login_id);
 			$("#rmSteps").find('input').not(':radio').attr('readonly',true);
 			$("#rmSteps").find(':radio').attr('disabled',true);
 			$(".actions.clearfix").find("a:last").attr('href', '#');
			$(".actions.clearfix").find("li:last").attr('class', 'disabled');
 		}

 		function formEdit() {
 			$("#rmSteps").find('input').not(':radio').removeAttr('readonly');
 			$("#rmSteps").find(':radio').removeAttr('disabled');
 			$(".actions.clearfix").find("a:last").attr('href', '#finish');
			$(".actions.clearfix").find("li:last").removeAttr('class');
 		}

 		function rmShowDetail(login_id) {
			$.ajax({
				url: '/lms/ReaderManagement/rmShow.do',
				type: 'GET',
				dataType: 'json',
				contentType:'application/json;charset=UTF-8',
				data: {login_id: login_id},
				success: function(msg) {
					if(msg.reasult == "0") {
						alert("系统没有该账号记录，请确认账号输入是否正确！");
						location.reload();
					} else if(msg.reasult == "1") {
						alert("账号记录未激活借书证，请确认账号输入是否正确！");
						location.reload();
					} else {
						$("#rmLoginIDshow").html("");
						$("#rmLoginIDshow").append(msg.user.loginId);
						document.getElementById("rmName").value = msg.userInfo.name;
						document.getElementById("rmPassword").value = msg.user.password;
						document.getElementById("rmPhone").value = msg.userInfo.phone;
						document.getElementById("rmEmail").value = msg.userInfo.email;
						$("input[name='rmCategory'][value='"+msg.readerLicense.categoryId+"']").prop("checked",true);
						document.getElementById("rmAllocate").value = msg.readerLicense.gmtAllocate;
						document.getElementById("rmExpire").value = msg.readerLicense.gmtExpire;
						document.getElementById("rmPenalty").value = msg.readerLicense.penaltyCount;
						document.getElementById("rmBorrowed").value = msg.readerLicense.borrowed;
						$("input[name='rmStatus'][value='"+msg.readerLicense.status+"']").prop("checked",true);
						$("#rmSteps").find(':radio').attr('disabled',true);
					}
				}
			});
 		}
    </script>
</body>
</html>