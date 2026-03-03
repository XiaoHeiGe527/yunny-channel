<!DOCTYPE html>
<html>

	<head>
		<title>Jcode代码生成器</title>
		<#include "../common/head.ftl">
		<@css_version paths=["css/main.css"]/>
	</head>

	<body>
		<div class="row" id="infoSwitch"> 
	        <fieldset class="layui-elem-field layui-field-title">
				<legend style="text-align: center;font-size: 30px;">欢迎使用前置机服务!</legend>
			</fieldset>
	    </div>
		
			<div class="layui-form-item">
				<div class="layui-accordion" style="margin-right:3%;">
					<div class='layui-form-item'>
						<div class="layui-collapse" lay-accordion>
						  <div class="layui-colla-item">
						    <h2 class="layui-colla-title">系统公告</h2>
						    <div class="layui-colla-content layui-show layui-content" style="text-align: center;">
						    	暂无系统公告
							</div>
						  </div>
						</div>
					</div>
				</div>
				
				<div class="layui-accordion">
				
				</div>
			</div>

		</div>
		
		<script>
			layui.use('element',function() {
			 		element = layui.element;				
			});
					
		</script>
	</body>
</html>