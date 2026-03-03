<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>编辑字典</title>
    <#include "../common/head.ftl">
    <style>
        .layui-form-label {
            width: 120px;
        }
        .layui-input-block {
            margin-left: 150px;
        }
    </style>
</head>

<body>
<div class="layui-container">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md6 layui-col-md-offset3">
            <div class="layui-card">
                <div class="layui-card-header">
                    <h2 class="layui-h2">编辑字典</h2>
                </div>
                <div class="layui-card-body">
                    <form id="dictionaryForm" class="layui-form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">字典类别</label>
                            <div class="layui-input-block">
                                <input type="text" id="category" name="category" required lay-verify="required" placeholder="请输入字典类别" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">字典编码</label>
                            <div class="layui-input-block">
                                <input type="text" id="codeNum" name="codeNum" required lay-verify="required" placeholder="请输入字典编码" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">字典内容</label>
                            <div class="layui-input-block">
                                <input type="text" id="content" name="content" required lay-verify="required" placeholder="请输入字典内容" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">字典备注</label>
                            <div class="layui-input-block">
                                <input type="text" id="remarks" name="remarks" placeholder="请输入字典备注" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">字典序号</label>
                            <div class="layui-input-block">
                                <input type="text" id="serialNumber" name="serialNumber" placeholder="请输入字典序号" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="editDictionary">确认修改</button>
                                <button type="button" class="layui-btn layui-btn-primary" onclick="goBack()">返回</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    layui.use(['form', 'layer', 'jquery'], function() {
        var form = layui.form;
        var layer = layui.layer;
        var $ = layui.jquery;
        var token = '${Request.token}';
        var dictionaryId = '${Request.id}'; // 从服务端变量获取ID
        
        if (!dictionaryId || dictionaryId === 'null') {
            layer.msg('缺少字典ID参数', {icon: 2});
            setTimeout(function() {
                window.history.back();
            }, 1500);
            return;
        }
        
        // 加载字典详情
        function loadDictionaryDetail() {
            $.ajax({
                url: '/dictionary/getById',
                type: 'POST',
                data: { id: dictionaryId },
                dataType: 'json',
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200) {
                        var data = response.data;
                        $('#category').val(data.category);
                        $('#codeNum').val(data.codeNum);
                        $('#content').val(data.content);
                        $('#remarks').val(data.remarks);
                        $('#serialNumber').val(data.serialNumber);
                        form.render(); // 重新渲染表单
                    } else {
                        layer.msg('获取字典详情失败：' + response.message, {icon: 2});
                    }
                },
                error: function() {
                    layer.msg('网络错误，请稍后重试', {icon: 2});
                }
            });
        }
        
        // 加载详情数据
        loadDictionaryDetail();
        
        // 监听表单提交
        form.on('submit(editDictionary)', function(data) {
            var requestData = {
                id: dictionaryId,
                category: data.field.category,
                codeNum: data.field.codeNum,
                content: data.field.content,
                remarks: data.field.remarks,
                serialNumber: data.field.serialNumber
            };
            
            $.ajax({
                url: '/dictionary/update',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(requestData),
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200) {
                        layer.msg('字典修改成功', {icon: 1}, function() {
                            // 创建隐藏表单提交跳转
                            var form = $('<form>', {
                                action: '/viewJump/dictionary/listByPage',
                                method: 'post'
                            });
                            form.append($('<input>', {
                                type: 'hidden',
                                name: 'token',
                                value: token
                            }));
                            form.appendTo('body').submit();
                        });
                    } else {
                        layer.msg('字典修改失败：' + response.message, {icon: 2});
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