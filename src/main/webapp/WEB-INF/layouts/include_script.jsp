<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!-- 引入的JS文件放置在页面的后面，可以让页面的加载速度更快 -->
<!-- JQUERY -->
<script src="${ctx}/static/js/jquery/jquery-2.1.1.min.js"></script>
<!-- JQUERY UI-->
<script src="${ctx}/static/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<!-- BOOTSTRAP -->
<script src="${ctx}/static/bootstrap-dist/js/bootstrap.min.js"></script>

<!-- BLOCK UI -->
<script type="text/javascript" src="${ctx}/static/js/jQuery-BlockUI/jquery.blockUI.min.js"></script>

<!-- SLIMSCROLL -->
<script type="text/javascript" src="${ctx}/static/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jQuery-slimScroll-1.3.0/slimScrollHorizontal.min.js"></script>

<!-- JQUERY-VALIDATE -->
<script type="text/javascript" src="${ctx}/static/js/jquery-validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-validate/additional-methods.min.js"></script>

<!-- 弹出框 -->
<script src="${ctx}/static/js/bootstrap-modal/js/bootstrap-modalmanager.js"></script>
<script src="${ctx}/static/js/bootstrap-modal/js/bootstrap-modal.js"></script>

<!-- COOKIE -->
<script type="text/javascript" src="${ctx}/static/js/jQuery-Cookie/jquery.cookie.min.js"></script>


<script>
<!-- 修改用户密码! -->
function updatePassword(){
	$("#updatePassword .modal-body >.alert").remove();
	jQuery.ajax({
		url: "${ctx}/user/updatePassword",
		data:{"oldPassword":$("#oldPassword").val(),"newPassword":$("#newPassword").val(),"confirmPassword":$("#confirmPassword").val()},
		type: "post",
		dataType: "json",
		success: function(msg) {
			var alertStr='';
			if(msg.status){
				alertStr=alertStr+'<div class="alert alert-block alert-success fade in">';
				alertStr=alertStr+'<a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>';
				alertStr=alertStr+'<h4 style="margin: 0;"><i class="fa fa-heart"></i> '+msg.message+' </h4>';
				alertStr=alertStr+'</div>';
				
				App.blockUI($("#updatePassword"));
				window.setTimeout(function () {
					App.unblockUI($("#updatePassword"));
					$('#updatePasswordCancel').trigger("click");
	            }, 1000);
			}else{
				alertStr=alertStr+'<div class="alert alert-block alert-warning fade in">';
				alertStr=alertStr+'<a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>';
				alertStr=alertStr+'<h4 style="margin: 0;"><i class="fa fa-exclamation-circle"></i> '+msg.message+' </h4>';
				alertStr=alertStr+'</div>';
			}

			$("#updatePassword .modal-body").prepend(alertStr);

		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
             alert(XMLHttpRequest.status);
             alert(XMLHttpRequest.readyState);
             alert(textStatus);
         }
	});

}
</script>
