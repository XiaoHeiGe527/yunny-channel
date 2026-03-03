<!DOCTYPE html>
<html>
<head>
    <#include "../common/head.ftl">
    <@css_version paths=["css/login.css"]/> 
</head>
<body>
<div class="layui-container">
    <div class="admin-login-background">
        <div class="layui-form login-form">
            <form class="layui-form" id="myForm">
                <div class="layui-form-item logo-title">
                    <h1>有邻业委会管理系统</h1>
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-username" for="mobile"></label>
                    <input type="text" name="account" lay-verify="required" autocomplete="off" placeholder="请输入帐号" value="jcode" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-password" for="password"></label>
                    <input type="password" name="password" lay-verify="required" lay-reqtext="密码不能为空" placeholder="密码" autocomplete="off" class="layui-input"
                     value="">
                </div>
                <div class="layui-form-item">
                    <label class="layui-icon layui-icon-picture" for="captcha"></label>
                    <input type="text" name="code" lay-verify="required" lay-reqtext="验证码不能为空" placeholder="图形验证码" autocomplete="off" class="layui-input verification captcha"
                     value="">
                    <div class="captcha-img">
                        <img id="code_img" title="点击刷新">
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid" lay-submit="" lay-filter="login">登 入</button>
                </div>
            </form>
        </div>
    </div>
</div>

<@js_version paths=["js/jquery.particleground.min.js","js/imgCheckCode.js"]/>
<script>
    layui.use(['form'], function () {
        var form = layui.form,
            layer = layui.layer;

        // 登录过期的时候，跳出ifram框架
        if (top.location != self.location) top.location = self.location;

        // 粒子线条背景
        $(document).ready(function(){
            $('.layui-container').particleground({
                dotColor:'#5cbdaa',
                lineColor:'#5cbdaa'
            });
        });

        // 进行登录操作
        form.on('submit(login)', function(data) {
            data = data.field;              
            if (!verification(data.code)) {
                layer.msg("验证码错误", { icon: 2, time: 1500 });
                return false;
            }
            axios.post('/login', data).then(resp => {
                layer.msg("登录成功，正在跳转首页", { icon: 1, time: 1000 }, function(){
                    location.href = "/index";            
                });
            });
            
            return false;
        });
        
    });
</script>
</body>
</html>