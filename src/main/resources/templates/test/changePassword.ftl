<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改密码</title>
    <!-- 引入 文件 -->
    <#include "../common/head.ftl">
</head>
<body>
<div class="layui-container">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md6 layui-col-md-offset3">
            <div class="layui-card">
                <div class="layui-card-header">
                    <h2 class="layui-h2">修改密码</h2>
                </div>
                <div class="layui-card-body">
                    <form id="changePasswordForm" class="layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">旧密码</label>
                            <div class="layui-input-block">
                                <input type="password" id="oldPassword" name="oldPassword" required lay-verify="required" placeholder="请输入旧密码" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码</label>
                            <div class="layui-input-block">
                                <input type="password" id="password" name="password" required lay-verify="required|minLength" placeholder="请输入新密码" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">确认新密码</label>
                            <div class="layui-input-block">
                                <input type="password" id="confirmPassword" name="confirmPassword" required lay-verify="required|confirmPassword" placeholder="请再次输入新密码" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="changePassword">修改密码</button>
                                <button type="button" class="layui-btn layui-btn-primary" onclick="goBack()">返回上一页</button>
                            </div>
                        </div>
                    </form>
                    <div id="message" class="layui-text-danger"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    layui.use(['form', 'layer'], function() {
        var form = layui.form;
        var layer = layui.layer;
        var token = '${Request.token}';
        var userNo = '${Request.userNo}';

        // 自定义验证规则
        form.verify({
            minLength: function(value) {
                if (value.length < 6) {
                    return '密码长度不能小于6位';
                }
            },
            confirmPassword: function(value) {
                var password = $('#password').val();
                if (value!== password) {
                    return '两次输入的密码不一致';
                }
            }
        });

        // 监听表单提交
        form.on('submit(changePassword)', function(data) {
            var oldPassword = data.field.oldPassword;
            var password = data.field.password;

            var requestData = {
                userNo: userNo,
                password: password,
                oldPassword: oldPassword
            };

            $.ajax({
                url: '/sys/changePassword',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(requestData),
                headers: {
                    'token': token
                },
                success: function(response) {
                    if (response.code === 200) {
                        layer.msg('密码修改成功', {icon: 1}, function() {
                            $.ajax({
                                url: '/sys/logout',
                                type: 'POST',
                                data: {
                                    userNo: userNo
                                },
                                dataType: 'json',
                                headers: {
                                    'token': token
                                },
                                success: function(logoutResponse) {
                                    if (logoutResponse.code === 200) {
                                        window.location.href = '/login';
                                    } else {
                                        layer.msg('退出登录失败：' + logoutResponse.message, {icon: 2});
                                    }
                                },
                                error: function() {
                                    layer.msg('请求发生错误，请检查网络或接口地址。', {icon: 2});
                                }
                            });
                        });
                    } else {
                        layer.msg('密码修改失败: ' + response.message, {icon: 2});
                    }
                },
                error: function() {
                    layer.msg('网络错误，请稍后重试', {icon: 2});
                }
            });

            return false;
        });
    });

    function goBack() {
        window.history.back();
    }
</script>
</body>
</html>
