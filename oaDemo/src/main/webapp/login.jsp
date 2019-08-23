<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<%@include file="script.html"%>
<style type="text/css">
body {
	background-image: url("static/images/DWBackground.jpg");
	background-size: cover;
	position: relative;
	overflow: hidden;
}

h1 {
	text-align: center;
	margin-top: 50px;
}
</style>
</head>
<body>
	<h1>如影随形OA办公自动化系统</h1>


	<div id="box" class="easyui-dialog" title="管理员登录"
		data-options="closable:false,draggable:false"
		style="width: 400px; height: 300px; padding: 10px;">

		<form action="${pageContext.request.contextPath}/login"
			name="loginForm" method="post">
			<div style="margin-left: 50px; margin-top: 50px;">
				<div style="margin-bottom: 20px;">
					<div>
						用户名称: <input name="userName" id="user_name" class="easyui-textbox"
							data-options="required:true" style="width: 200px; height: 32px"
							value="admin" />
					</div>
				</div>
				<div style="margin-bottom: 20px">
					<div>
						用户密码: <input name="passWord" id="pass_word"
							class="easyui-textbox" type="password"
							style="width: 200px; height: 32px" data-options="" value="admin" />
					</div>
				</div>
				<div>
					<input class="easyui-linkbutton" iconCls="icon-ok" type="submit"
						style="width: 100px; height: 32px; margin-left: 50px" value="登录"
						name="sub" />
				</div>
			</div>
		</form>
	</div>





</body>
</html>
