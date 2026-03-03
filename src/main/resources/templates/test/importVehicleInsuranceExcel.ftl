<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>导入车辆保险 Excel</title>
    <link rel="stylesheet" href="/plugins/layui/css/layui.css">
    <#include "../common/head.ftl">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
        }

        .layui-card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .layui-card-header {
            background-color: #fff;
            border-bottom: 1px solid #e6e6e6;
            padding: 15px 20px;
            font-size: 18px;
            font-weight: 600;
        }

        .layui-card-body {
            padding: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .layui-btn {
            width: 100px;
        }
    </style>
</head>

<body>
<#--<#include "../common/inHeader.ftl">-->
<div class="layui-card">
    <div class="layui-card-header">
        导入车辆保险 Excel
    </div>
    <div class="layui-card-body">
        <button type="button" class="layui-btn" id="uploadExcel">
            <i class="layui-icon">&#xe67c;</i>选择 Excel 文件
        </button>
    </div>
    <div class="layui-card-body">
        <button type="button" id="backButton" class="layui-btn layui-btn-default">返回上一页</button>
    </div>
</div>

<script src="/plugins/jquery/jquery-3.4.1.min.js"></script>
<script src="/plugins/layui/layui.js"></script>
<script>
    layui.use(['layer', 'upload'], function () {
        var layer = layui.layer;
        var upload = layui.upload;
        // 从 FTL 变量中获取 token
        var token = '${Request.token}';

        // 创建一个上传组件
        var uploadInst = upload.render({
            elem: '#uploadExcel',
            url: '/vehicleInsurance/importVehicleInsuranceExcel',
            accept: 'file',
            exts: 'xlsx|xls',
            size: 1024 * 1024, // 1G
            headers: {
                'token': token
            },
            choose: function (obj) {
                // 验证文件大小
                var files = obj.pushFile();
                var file = Object.values(files)[0];
                if (file.size > 1 * 1024 * 1024 * 1024) {
                    layer.msg('文件大小不能超过 1G', { icon: 5 });
                    delete files[Object.keys(files)[0]];
                    return false;
                }
            },
            done: function (response) {
                if (response.code === 200) {
                    layer.msg('导入成功，导入数据条数：' + response.data, { icon: 1 });
                    // 创建一个隐藏的表单并提交 POST 请求
                    var form = $('<form>', {
                        action: '/viewJump/vehicleInsurance/query',
                        method: 'post'
                    });
                    form.append($('<input>', {
                        type: 'hidden',
                        name: 'token',
                        value: token
                    }));
                    form.appendTo('body').submit();
                } else {
                    layer.msg('导入失败：' + response.message, { icon: 5 });
                }
            },
            error: function () {
                layer.msg('请求发生错误，请检查网络或接口地址', { icon: 5 });
            }
        });

        // 返回上一页按钮点击事件
        $('#backButton').click(function () {
            window.history.back();
        });
    });
</script>
</body>

</html>
