/**
 * index.js By Beginner Emain:zheng_jinfan@126.com
 * HomePage:http://www.zhengjinfan.cn
 */

var tab;
layui
		.config({
			base : 'static/js/'
		})
		.use(
				[ 'element', 'layer', 'tab' ],
				function() {
					var element = layui.element, $ = layui.jquery, layer = layui.layer;

					tab = layui.tab({
						elem : '.admin-nav-card' // 设置选项卡容器
						,
						maxSetting : {
							max : 15,
							tipMsg : '为了系统的流畅度，只能同时打开15个TAB页'
						},
						contextMenu : true
					});
					// iframe自适应
					$(window).on('resize', function() {
						var $content = $('.admin-nav-card .layui-tab-content');
						$content.height($(this).height() - 147);
						$content.find('iframe').each(function() {
							$(this).height($content.height());
						});
					}).resize();

					// 监听导航点击
					$("[data-url]").click(function() {
						var href = $(this).attr('data-url');
						var title = $(this).text();
						var id = $(this).attr('id');
						var data = {
							elem : $(this),
							field : {
								href : href,
								title : title,
								id : id
							}
						}
						tab.tabAdd(data.field);
					});

					$('.admin-side-toggle').on('click', function() {
						var sideWidth = $('#admin-side').width();
						if (sideWidth >= 100) {
							$('#admin-body').animate({
								left : '0'
							});
							$('#admin-side').animate({
								width : '0'
							});
							$('#admin-navbar-side').animate({
								width : '0'
							});
						} else {
							$('#admin-body').animate({
								left : '200px'
							});
							$('#admin-side').animate({
								width : '200px'
							});
							$('#admin-navbar-side').animate({
								width : '220px'
							});
						}
					});

					$('.admin-side-full').on('click', function() {
						var docElm = document.documentElement;
						// W3C
						if (docElm.requestFullscreen) {
							docElm.requestFullscreen();
						}
						// FireFox
						else if (docElm.mozRequestFullScreen) {
							docElm.mozRequestFullScreen();
						}
						// Chrome等
						else if (docElm.webkitRequestFullScreen) {
							docElm.webkitRequestFullScreen();
						}
						// IE11
						else if (elem.msRequestFullscreen) {
							elem.msRequestFullscreen();
						}
						layer.msg('按Esc即可退出全屏');
					});

					// 锁屏
					$('#lock').on('click', function() {
						lock($, layer);
					});

					// 手机设备的简单适配
					var treeMobile = $('.site-tree-mobile'), shadeMobile = $('.site-mobile-shade');
					treeMobile.on('click', function() {
						$('body').addClass('site-mobile');
					});
					shadeMobile.on('click', function() {
						$('body').removeClass('site-mobile');
					});
				});

function lock($, layer) {

	// 自定页
	layer.open({
		title : false,
		type : 1,
		closeBtn : 0,
		anim : 6,
		content : $('#lock-temp').html(),
		shade : [ 0.9, '#393D49' ],
		success : function(layero, lockIndex) {
			// 给显示用户名赋值
			$('#lockUserName').text($('#userName').text());
			layero.find('input[name=lockPwd]').on('focus', function() {
				var $this = $(this);
				if ($this.val() === '输入密码解锁..') {
					$this.val('').attr('type', 'password');
				}
			}).on('blur', function() {
				var $this = $(this);
				if ($this.val() === '' || $this.length === 0) {
					$this.attr('type', 'text').val('输入密码解锁..');
				}
			});
			// 在此处可以写一个请求到服务端删除相关身份认证，因为考虑到如果浏览器被强制刷新的时候，身份验证还存在的情况

			$.post("logout", function(data) {
			}, "json");
			// 绑定解锁按钮的点击事件
			layero.find('button#unlock').on('click', function() {
				var userMobile = $('#mobile').val();
				var pwd = $('#lockPwd').val();
				if (pwd === '输入密码解锁..' || pwd.length === 0) {
					layer.msg('请输入密码..', {
						icon : 2,
						time : 1000
					});
					return;
				}
				unlock(userMobile, pwd);
			});
			/**
			 * 解锁操作方法
			 * 
			 * @param {String}
			 *            用户名
			 * @param {String}
			 *            密码
			 */
			var unlock = function(userMobile, pwd) {
				// 这里可以使用ajax方法解锁
				$.post("unlock", {
					mobile : userMobile,
					password : pwd
				}, function(data) {
					// 验证成功
					if (data.code == 200) {
						// 关闭锁屏层
						layer.close(lockIndex);
					} else {
						layer.msg(data.message, {
							icon : 2,
							time : 1000
						});
					}
				}, 'json');
			};
		}
	});
};

/** ******* 左侧菜单控制*********** */
$(function() {

	$('.inactive').click(
			function() {
				if ($(this).siblings('ul').css('display') == 'none') {
					$(this).parent('li').siblings('li')
							.removeClass('inactives');
					$(this).addClass('inactives');
					$(this).siblings('ul').slideDown(100).children('li');
					$(this).parents('li').siblings('li').children('ul').parent(
							'li').children('a').removeClass('inactives');
					$(this).parents('li').siblings('li').children('ul')
							.slideUp(100);
				} else {
					// 控制自身变成+号
					$(this).removeClass('inactives');
					// 控制自身菜单下子菜单隐藏
					$(this).siblings('ul').slideUp(100);
					// 控制自身子菜单变成+号
					$(this).siblings('ul').children('li').children('ul')
							.parent('li').children('a').addClass('inactives');
					// 控制自身菜单下子菜单隐藏
					$(this).siblings('ul').children('li').children('ul')
							.slideUp(100);
					// 控制同级菜单只保持一个是展开的（-号显示）
					$(this).siblings('ul').children('li').children('a')
							.removeClass('inactives');
				}
			})

	// 菜单选中高亮
	$(".datacss").click(function() {
		$(".datacss").css("background-color", "");
		$(this).css("background-color", "yellow");
	});

});

// TAB切换控制展开左侧菜单选中
function openMenu(id) {
	$("#admin-navbar-side .datacss").css("background-color", "");
	$("#" + id).css("background-color", "yellow");
	$("#admin-navbar-side a").removeClass('inactives');
	$("#admin-navbar-side ul ul").css("display", "none");
	$("#" + id).parent().parent().css("display", "block");
	$("#" + id).parent().parent().parent().parent().css("display", "block");
	$("#" + id).parent().parent().prev().addClass('inactives');
	$("#" + id).parent().parent().parent().parent().prev()
			.addClass('inactives');
}
