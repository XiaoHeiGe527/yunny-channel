<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>PDF 文件上传</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../common/head.ftl">
    <style>
        .layui-upload-file {
            display: none !important;
        }
        .upload-container {
            margin: 30px auto;
            width: 90%;
            max-width: 800px;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .layui-progress {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="upload-container">
    <h2 class="layui-h2" style="text-align: center; margin-bottom: 30px;">PDF 文件上传</h2>

    <form class="layui-form" id="uploadForm">
        <input type="hidden" id="fileNo" value="PDF_20250519_123456">
        <input type="hidden" id="token" value="${Request.token}">

        <div class="layui-form-item">
            <label class="layui-form-label">文件标签</label>
            <div class="layui-input-block">
                <input type="text" name="fileLabel" required lay-verify="required"
                       placeholder="请输入文件标签" autocomplete="off"
                       class="layui-input">
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
                    <i class="layui-icon">&#xe642;</i> 提交上传
                </button>
                <button type="reset" class="layui-btn layui-btn-primary">
                    <i class="layui-icon">&#xe640;</i> 重置
                </button>
                <button type="button" id="sampleFileBtn" class="layui-btn layui-btn-primary">
                    <i class="layui-icon">&#xe64c;</i> 无水印文档样例
                </button>
                <!-- 添加文档样例2按钮 -->
                <button type="button" id="sampleFileBtn2" class="layui-btn layui-btn-primary">
                    <i class="layui-icon">&#xe64c;</i> 有水印文档样例
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
    layui.use(['layer', 'form', 'upload', 'element'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var element = layui.element;

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
            var fileLabel = $('input[name="fileLabel"]').val();
            var fileNo = $('#fileNo').val();
            var token = $('#token').val();

            // 验证
            if (!file) {
                layer.msg('请选择PDF文件', {icon: 2});
                return;
            }

            if (!fileLabel) {
                layer.msg('请输入文件标签', {icon: 2});
                return;
            }

            // 创建表单数据
            var formData = new FormData();
            formData.append('file', file);
            formData.append('fileNo', fileNo);
            formData.append('fileLabel', fileLabel);

            // 显示进度条
            element.progress('uploadProgress', '0%');
            $('#progressText').text('上传中...');
            $('#resultMsg').text('');

            // 禁用按钮
            $(this).attr('disabled', true).addClass('layui-btn-disabled');

            // 发送AJAX请求
            $.ajax({
                url: '/renShi/handleFileUpload',
                type: 'POST',
                data: formData,
                headers: {
                    'token': token
                },
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
                        $('#resultMsg').html('<span class="layui-badge layui-bg-green">成功</span> ' + response.message);
                        layer.msg('上传成功', {icon: 1});
                    } else {
                        var msg = response && response.message ? response.message : '上传失败，未知错误';
                        $('#resultMsg').html('<span class="layui-badge layui-bg-red">失败</span> ' + msg);
                        layer.msg(msg, {icon: 2});
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#resultMsg').html('<span class="layui-badge layui-bg-red">失败</span> 服务器错误: ' + errorThrown);
                    layer.msg('上传失败: ' + errorThrown, {icon: 2});
                },
                complete: function() {
                    // 启用按钮
                    $('#uploadBtn').attr('disabled', false).removeClass('layui-btn-disabled');
                }
            });
        });

        // 文档样例按钮点击事件
        $('#sampleFileBtn').on('click', function() {
            var token = $('#token').val();
            var remoteFileName = '关于开展第24个全国“安全生产月”活动函.pdf';

            // 显示加载提示
            var loading = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });

            // 创建 XMLHttpRequest 对象
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/renShi/downloadFile', true);
            xhr.setRequestHeader('token', token); // 设置 token 请求头
            xhr.responseType = 'blob'; // 接收二进制数据

            // 处理响应
            xhr.onload = function() {
                layer.close(loading); // 关闭加载提示

                if (xhr.status === 200) {
                    // 创建下载链接
                    var blob = new Blob([xhr.response], { type: 'application/pdf' });
                    var url = URL.createObjectURL(blob);
                    var a = document.createElement('a');
                    a.href = url;
                    a.download = remoteFileName;
                    document.body.appendChild(a);
                    a.click();

                    // 清理资源
                    setTimeout(function() {
                        document.body.removeChild(a);
                        URL.revokeObjectURL(url);
                    }, 100);

                    layer.msg('开始下载文档样例', {icon: 1});
                } else {
                    layer.msg('下载失败: ' + xhr.statusText, {icon: 2});
                }
            };

            // 处理错误
            xhr.onerror = function() {
                layer.close(loading);
                layer.msg('网络错误，请稍后重试', {icon: 2});
            };

            // 发送请求
            var formData = new FormData();
            formData.append('remoteFileName', remoteFileName);
            xhr.send(formData);
        });

        // 文档样例2按钮点击事件
        $('#sampleFileBtn2').on('click', function() {
            var token = $('#token').val();
            var remoteFileName = '关于开展第24个全国“安全生产月”活动函.pdf';
            // 添加水印文本参数
            var watermarkText = '当前海外员工ID:[${Request.userNo}]';

            // 显示加载提示
            var loading = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });

            // 创建 XMLHttpRequest 对象
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/renShi/addWatermarkAndDownload', true);
            xhr.setRequestHeader('token', token); // 设置 token 请求头
            xhr.responseType = 'blob'; // 接收二进制数据

            // 处理响应
            xhr.onload = function() {
                layer.close(loading); // 关闭加载提示

                if (xhr.status === 200) {
                    // 创建下载链接
                    var blob = new Blob([xhr.response], { type: 'application/pdf' });
                    var url = URL.createObjectURL(blob);
                    var a = document.createElement('a');
                    a.href = url;
                    a.download = remoteFileName;
                    document.body.appendChild(a);
                    a.click();

                    // 清理资源
                    setTimeout(function() {
                        document.body.removeChild(a);
                        URL.revokeObjectURL(url);
                    }, 100);

                    layer.msg('开始下载文档样例2', {icon: 1});
                } else {
                    layer.msg('下载失败: ' + xhr.statusText, {icon: 2});
                }
            };

            // 处理错误
            xhr.onerror = function() {
                layer.close(loading);
                layer.msg('网络错误，请稍后重试', {icon: 2});
            };

            // 发送请求，添加水印文本参数
            var formData = new FormData();
            formData.append('remoteFileName', remoteFileName);
            formData.append('watermarkText', watermarkText);
            xhr.send(formData);
        });
    });
</script>
</body>
</html>