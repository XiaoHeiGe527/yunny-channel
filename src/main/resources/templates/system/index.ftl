<!DOCTYPE html>
<html>
	<head>
		<title>前置机服务</title>
		<#include "../common/head.ftl">
		<@css_version paths=["css/global.css","css/index.css"]/>
		<@js_version paths=["js/index.js"]/>
	</head>

	<body>
		<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
			<div class="layui-header header header-demo">
				<div class="layui-main">
					<div class="admin-login-box">
						<a class="logo" style="left: 0;" href="${base_url}/index">
							<span style="font-size: 22px;">前置机服务</span>
						</a>
						<div class="admin-side-toggle">
							<i class="layui-icon">&#xe632;</i>
						</div>
						<div class="admin-side-full">
							<i class="layui-icon">&#xe630;</i>
						</div>
					</div>
					<ul class="layui-nav admin-header-item">
						<li class="layui-nav-item">
							<a href="javascript:;" class="admin-header-user">
								<img src="${base_url}/static/images/0.jpg" />
								<span id="userName">Jcode</span>
								<i class="layui-icon">&#xe61a;</i>  
							</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:;" onclick="contactAdmin();"><i class="layui-icon">&#xe611;</i>联系作者</a>
								</dd>
								<dd>
									<a href="javascript:;" id="logout"><i class="layui-icon">&#xe640;</i>注销</a>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
			</div>
			<div class="layui-side" id="admin-side">
				<div class="layui-side-scroll list" id="admin-navbar-side">
					<!-- 左侧菜单 -->
						<ul>

							<li>
								<a href="#" class="inactive active">前置机服务</a>
								<ul style="display: none" >
									<li>
										<a href="#" id="1" data-url="/qzj/rwxx" class="datacss">申请单列表</a>
									</li>

								</ul>
							</li>




<#--							<li>
								<a href="#" class="inactive active">生成器</a>
								<ul style="display: none" >
									<li>
										<a href="#" id="1" data-url="/generator/index" class="datacss">代码生成器</a>
									</li>
									<li>
										<a href="#" id="2" data-url="/design/index" class="datacss">数据库设计文档生成</a>
									</li>
								</ul>
							</li>
							<li>
								<a href="#" class="inactive active">工具站</a>
								<ul style="display: none" >
									<li>
										<a href="#" id="3" data-url="http://hutool.club/docs/#/" class="datacss">Hutool工具类库</a>
									</li>
									<li>
										<a href="#" id="4" data-url="http://www.bejson.com/" class="datacss">JSON工具站</a>
									</li>
								</ul>
							</li>-->
						</ul>
					
				</div>
			</div>
			<div class="layui-body" style="bottom: 0;border-left: solid 5px #1AA094;" id="admin-body">
				<div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab">
					<ul class="layui-tab-title">
						<li class="layui-this" onclick="openMenu('index')">
							<i class="layui-icon">&#xe609;</i>
							<cite id="tab_index">主页</cite>
						</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show">
							<iframe src="${base_url}/main"></iframe>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-footer footer footer-demo" id="admin-footer">
				<div class="layui-main">
					<p>本后台系统由lijian541x@163.com提供技术支持</p>
				</div>
			</div>

		<script>
			layui.use('layer', function() {
				var $ = layui.jquery,
					layer = layui.layer;
					
				$("#logout").click(function(){
				   $.post("${base_url}/logout",function(data){
				        if (data.code == 200){
				        	window.location.href = "${base_url}/login"; 
				        }
				    }, "json");
				});	
				
			});

			//联系管理员	
			function contactAdmin(){
				layer.alert('作者邮箱 : lijian541x@163.com', {
					 title:'联系方式' 
				    ,skin: 'layui-layer-molv'
				    ,closeBtn: 0
				    ,anim: 4 //动画类型
				  });
			}
			</script>
		</div>
	</body>

</html>