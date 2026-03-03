/**
 * date:2019/08/16
 * author:Mr.Chung
 * description:此处放layui自定义扩展
 */


/** common.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
layui.define(['layer'], function(exports) {
	"use strict";

	var $ = layui.jquery,
		layer = layui.layer;

	var common = {
		/**
		 * 抛出一个异常错误信息
		 * @param {String} msg
		 */
		throwError: function(msg) {
			throw new Error(msg);
			return;
		},
		/**
		 * 弹出一个错误提示
		 * @param {String} msg
		 */
		msgError: function(msg) {
			layer.msg(msg, {
				icon: 5
			});
			return;
		}
	};

	exports('common', common);
});


window.rootPath = (function (src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

// http request 拦截器
axios.interceptors.request.use(
	config => {
//		config.baseURL = serviceBaseURL
		config.withCredentials = true // 允许携带token ,这个是解决跨域产生的相关问题
		config.timeout = 10000
//		var token = sessionStorage.getItem('login_token');
		config.headers = {
			'request_mode': 'asynchronous',
			'Content-Type': 'application/json;charset=utf-8'
		}
		return config
	},
	error => {
		layer.msg("网络请求异常,请稍后重试", { icon: 2,time: 2000 });
		return Promise.reject(error)
	}
)


//在 response 拦截器实现
axios.interceptors.response.use(
	response => {
		if (response.status === 200) {
			if (response.data.code === 200) {
				return Promise.resolve(response.data);
			} else if (response.data.code == 1001) {
				layer.msg("登录超时请重新登录", { icon: 2,time: 2000}, function() {
					window.location.href = "/login";
				});
				return Promise.reject(response.data);
			} else {
				layer.msg(response.data.message, { icon: 2,time: 2000 });
				return Promise.reject(response.data);
			}
		}
		layer.msg('网络请求异常,请稍后重试', { icon: 2, time: 2000 });
		return Promise.reject(response.data);
	},
	error => {
		layer.msg('网络请求异常,请稍后重试', { icon: 2, time: 2000 });
		return Promise.reject(error)
	}
)


$(document).ready(function (){
	//绑定图片显示大图
	$(".showbigimg").click(function(){  
	     var src = $(this).attr("src");
	      layer.open({
	        type: 1
	        ,title: false //不显示标题栏
	        ,closeBtn: true
	        ,area: ['80%', '60%']
	        ,shade: 0.8
	        ,content: '<img src="'+src+'"  style="width:100%; height:100%;">'
	      });
	}); 
	
	//关闭窗口
	$('#layerClose').on('click', function() {
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	});	
	
});
