<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>员工档案替换</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../common/head.ftl">
    <style>
        .layui-upload-file {
            display: none !important;
        }
        .upload-container {
            margin: 30px auto;
            width: 90%;
            max-width: 600px;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .layui-progress {
            margin: 20px 0;
        }
        .upload-title {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }
    </style>
</head>
<body>
<div class="upload-container">
    <h2 class="upload-title">员工档案PDF替换</h2>

    <form class="layui-form" id="uploadForm">
        <input type="hidden" id="token" value="${Request.token}">
        <!-- 固定ID参数 -->
        <input type="hidden" id="fileId" name="id" value="${employeeFile.id}">

        <div class="layui-form-item">
            <label class="layui-form-label">${(employeeFile.name)!}的档案</label>
            <div class="layui-input-block">
                <input type="text" id="displayId" readonly 
                       class="layui-input" value="${employeeFile.employeeCode}">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">上传 PDF</label>
            <div class="layui-input-block">
                <div class="layui-upload">
                    <button type="button" class="layui-btn" id="selectFileBtn">
                        <i class="layui-icon">&#xe67c;</i> 选择文件
                    </button>
                    <input type="file" id="pdfFile" name="file" accept=".pdf"
                           class="layui-upload-file">
                    <div id="fileInfo" class="layui-form-mid layui-word-aux">
                        未选择文件
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="button" id="uploadBtn" class="layui-btn layui-btn-normal">
                    <i class="layui-icon">&#xe642;</i> 提交替换
                </button>
                <button type="button" onclick="history.back()" class="layui-btn layui-btn-primary">
                    <i class="layui-icon">&#xe603;</i> 返回
                </button>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-progress" id="uploadProgress" lay-filter="uploadProgress">
                <div class="layui-progress-bar" lay-percent="0%"></div>
            </div>
            <div id="progressText" class="layui-form-mid layui-word-aux">
                等待上传...
            </div>
        </div>

        <div class="layui-form-item">
            <div id="resultMsg" class="layui-form-mid"></div>
        </div>
    </form>
</div>

<script>
    layui.use(['layer', 'form', 'upload', 'element', 'jquery'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var element = layui.element;
        var $ = layui.jquery;

        // 获取Token
        var token = $('#token').val();
        var fileId = $('#fileId').val();

        // 设置全局AJAX配置，添加Token到请求头
        $.ajaxSetup({
            headers: {
                'token': token
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) {
                    layer.msg('认证失败，请重新登录', {icon: 2});
                } else {
                    layer.msg('请求失败: ' + errorThrown, {icon: 2});
                }
            }
        });

        // 文件选择按钮点击事件
        $('#selectFileBtn').on('click', function() {
            $('#pdfFile').click();
        });

        // 文件选择变化事件
        $('#pdfFile').on('change', function(e) {
            var file = e.target.files[0];
            if (file) {
                // 检查文件大小 (1GB = 1073741824 字节)
                if (file.size > 1073741824) {
                    layer.msg('文件大小超过1GB限制', {icon: 2});
                    $(this).val('');
                    $('#fileInfo').text('未选择文件');
                    return;
                }

                // 显示文件信息
                var fileSize = (file.size / (1024 * 1024)).toFixed(2) + ' MB';
                $('#fileInfo').text(file.name + ' (' + fileSize + ')');
            } else {
                $('#fileInfo').text('未选择文件');
            }
        });

        // 上传按钮点击事件
        $('#uploadBtn').on('click', function() {
            var fileInput = $('#pdfFile')[0];
            var file = fileInput.files[0];

            // 验证
            if (!file) {
                layer.msg('请选择PDF文件', {icon: 2});
                return;
            }

            // 验证文件类型
            var fileName = file.name;
            if (!fileName.endsWith('.pdf') && !fileName.endsWith('.PDF')) {
                layer.msg('仅支持PDF文件上传', {icon: 2});
                return;
            }

            // 创建表单数据
            var formData = new FormData();
            formData.append('file', file);
            formData.append('id', fileId);

            // 显示进度条
            element.progress('uploadProgress', '0%');
            $('#progressText').text('上传中...');
            $('#resultMsg').text('');

            // 禁用按钮
            $(this).attr('disabled', true).addClass('layui-btn-disabled');

            // 发送AJAX请求到指定接口
            $.ajax({
                url: '/employeeFile/replaceUpload',
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                xhr: function() {
                    var xhr = new XMLHttpRequest();
                    xhr.upload.addEventListener('progress', function(e) {
                        if (e.lengthComputable) {
                            var percentComplete = (e.loaded / e.total) * 100;
                            element.progress('uploadProgress', percentComplete + '%');
                            $('#progressText').text('上传中... ' + percentComplete.toFixed(0) + '%');
                        }
                    });
                    return xhr;
                },
                success: function(response) {
                    if (response && response.code === 200) {
                        element.progress('uploadProgress', '100%');
                        $('#progressText').text('上传完成');
                        $('#resultMsg').html('<span class="layui-badge layui-bg-green">成功</span> ' + response.message);
                        layer.msg('档案PDF替换成功', {icon: 1});
                    } else {
                        var msg = response && response.message ? response.message : '替换失败，未知错误';
                        $('#resultMsg').html('<span class="layui-badge layui-bg-red">失败</span> ' + msg);
                        layer.msg(msg, {icon: 2});
                    }
                },
                complete: function() {
                    // 启用按钮
                    $('#uploadBtn').attr('disabled', false).removeClass('layui-btn-disabled');
                }
            });
        });

        // 初始化
        form.render();
    });
</script>
</body>
</html>