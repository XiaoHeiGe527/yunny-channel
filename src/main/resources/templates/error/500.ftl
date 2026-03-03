<!DOCTYPE html>
<html>

<head>
	<title>500 - 系统错误</title>
	<#include "../common/head.ftl">
	<style>
		.back-btn {
			margin-top: 30px;
			text-align: center;
		}
		.back-btn a {
			display: inline-block;
			padding: 10px 30px;
			background-color: #1E9FFF;
			color: white;
			border-radius: 4px;
			text-decoration: none;
			font-size: 18px;
			transition: all 0.3s;
		}
		.back-btn a:hover {
			background-color: #1AA094;
		}
	</style>
</head>

<body>
<div class="admin-main">
	<fieldset class="layui-elem-field">
		<div class="layui-field-box">
			<fieldset class="layui-elem-field layui-field-title">
			<#--	<legend style="text-align: center; font-size: 10px;">500</legend>-->
				<div class="layui-field-box" style="text-align: center;">
					<p style="font-size: 20px;">亲出错了，请联系管理员!</p></br>
					<p style="font-size: 30px; color: red;">${errorMsg!}</p>

					<!-- 添加返回按钮 -->
					<div class="back-btn">
						<a href="javascript:history.go(-1);">返回上一页</a>
					</div>
				</div>
			</fieldset>
		</div>
	</fieldset>
</div>
</body>
</html>