<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="script.html"%>
<script type="text/javascript">
	$(function() {
		$("#saveBtn").click(
				function() {
					var res = $("#form").form("validate"); // 返回true 或者false 
					// alert(res);

					// 使用ajax进行异步数据提交
					if (res) {
						$.post("savaUpdateRole.do", $("#form").serialize(),
								function(data) {
									alert(data.msg);
									//关掉当前弹出窗口,刷新父界面
									parent.closeTopWindow();
								}, "json");
					}
				});
	});
</script>
</head>
<body>
	<section class="info-section">
	<form id="form" method="post">
		<table>
			<tr>
				<td class="text-title">角色名称：</td>
				<td class="text-content">
				<!-- <input type="hidden" name="roleId" value="${role.roleId}"> -->
				<input type="text" name="roleName" value="${role.roleName}"
					class="easyui-textbox theme-textbox-radius"
					data-options="required:true,validType:'length[6,20]'"></td>
			</tr>

			<tr>
				<td class="text-title">角色状态：</td>
				<td class="text-content"><select name="status"
					class="easyui-combobox theme-textbox-radius">
						<option value="1" ${role.status==1 ?"selected":""}>正常</option>
						<option value="0" ${role.status==1 ?"selected":""}>禁用</option>
						<option value="2" ${role.status==1 ?"selected":""}>已删除</option>
				</select></td>
			</tr>
			<tr>
				<td class="text-title">描述信息：</td>
				<td class="text-content"><textarea class="theme-textbox-radius"
						rows="5" cols="20" name="note">${user.note }</textarea></td>
			</tr>

			<tr>
				<td colspan="2" style="text-align: center; margin-top: 10px;">
					<a href="javascript:void(0);" id="saveBtn"
					class="easyui-linkbutton button-primary">保存</a> <a
					href="javascript:void(0);" id="resetBtn"
					class="easyui-linkbutton button-danger">重置</a>
				</td>
			</tr>
		</table>
	</form>
	</section>
</body>
</html>