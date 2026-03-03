<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>行政文件录入</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../common/head.ftl">
    <style>
        .layui-upload-file {
            display: none !important;
        }
        .upload-container {
            margin: 30px auto;
            width: 90%;
            max-width: 1000px; /* 加宽容器避免标签换行 */
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .layui-progress {
            margin: 20px 0;
        }
        .search-result {
            position: absolute;
            z-index: 999;
            background: #fff;
            border: 1px solid #e6e6e6;
            width: 100%;
            max-height: 200px;
            overflow-y: auto;
            display: none;
        }
        .search-item {
            padding: 8px 10px;
            cursor: pointer;
        }
        .search-item:hover {
            background-color: #f2f2f2;
        }
        .position-grid {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 10px;
            padding: 10px;
        }
        .position-item {
            border: 1px solid #e6e6e6;
            padding: 10px;
            text-align: center;
            cursor: pointer;
        }
        .position-item:hover {
            background-color: #f2f2f2;
        }
        .position-item.selected {
            background-color: #5FB878;
            color: white;
        }

        /* 核心优化：解决标签换行问题 */
        .layui-form-item {
            display: flex;
            align-items: center; /* 垂直居中对齐 */
            margin-bottom: 15px;
        }
        .layui-form-label {
            width: 120px; /* 固定标签宽度 */
            flex-shrink: 0; /* 禁止标签收缩 */
            text-align: right;
            padding-right: 15px;
            white-space: nowrap; /* 强制不换行 */
        }
        .layui-input-block {
            flex: 1; /* 输入区域自适应宽度 */
            margin-left: 0; /* 取消默认左外边距 */
        }
        /* 适配小屏幕 */
        @media (max-width: 768px) {
            .layui-form-item {
                flex-direction: column;
                align-items: flex-start;
            }
            .layui-form-label {
                width: auto;
                text-align: left;
                margin-bottom: 5px;
            }
        }

        /* 按钮区域样式优化 */
        .layui-form-item:last-of-type {
            margin-top: 20px;
        }
        .layui-btn + .layui-btn {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="upload-container">
    <h2 class="layui-h2" style="text-align: center; margin-bottom: 30px;">行政文件上传</h2>

    <form class="layui-form" id="uploadForm">
        <input type="hidden" id="token" value="${Request.token}">

        <div class="layui-form-item">
            <label class="layui-form-label">档案类型</label>
            <div class="layui-input-block">
                <select name="archiveType" id="archiveTypeSelect" required lay-verify="required">
                    <!-- 选项将通过JS动态加载 -->
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">文件编号</label>
            <div class="layui-input-block">
                <input type="text" name="documentCode" required lay-verify="required"
                       placeholder="请输入文件编号" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">文件标题</label>
            <div class="layui-input-block">
                <input type="text" name="documentTitle" required lay-verify="required"
                       placeholder="请输入文件标题" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">文件内容</label>
            <div class="layui-input-block">
                <input type="text" name="documentContent" required lay-verify="required"
                       placeholder="请输入文件内容" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">文件类型</label>
            <div class="layui-input-block">
                <select name="documentType" id="documentTypeSelect" required lay-verify="required">
                    <!-- 选项将通过JS动态加载 -->
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">签字日期</label>
            <div class="layui-input-block">
                <input type="text" name="signatureTime" id="signatureTime" required lay-verify="required"
                       placeholder="请选择签字日期" autocomplete="off"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">归档年份</label>
            <div class="layui-input-block">
                <select name="placeTime" id="placeTimeSelect" required lay-verify="required">
                    <option value="">请选择归档年份</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">文件说明</label>
            <div class="layui-input-block">
                <textarea name="documentDescribe" placeholder="请输入文件说明" class="layui-textarea"></textarea>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">档案位置</label>
            <div class="layui-input-block">
                <div class="layui-input-inline" style="width: 300px;">
                    <input type="text" name="positionCode" id="positionCode" readonly
                           placeholder="请选择档案存放位置" autocomplete="off"
                           class="layui-input">
                </div>
                <button type="button" id="selectPositionBtn" class="layui-btn">
                    <i class="layui-icon">&#xe615;</i> 选择位置
                </button>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">文件到期时间</label>
            <div class="layui-input-block">
                <input type="text" name="expirationDate" id="expirationDate" required lay-verify="required"
                       placeholder="请选择文件到期时间" autocomplete="off"
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

<!-- 位置选择弹出层 -->
<div id="positionModal" class="layui-layer-wrap" style="display: none;">
    <div class="layui-layer-title">选择档案存放位置</div>
    <div class="layui-layer-content" style="padding: 10px;">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="text" id="positionSearch" placeholder="搜索位置编码" class="layui-input">
            </div>
        </div>
        <div id="positionList" class="position-grid"></div>
    </div>
    <!-- 移除了确定和取消按钮 -->
</div>

<script>
    layui.use(['layer', 'form', 'upload', 'element', 'jquery', 'laydate'], function() {
        var layer = layui.layer;
        var form = layui.form;
        var element = layui.element;
        var $ = layui.jquery;
        var laydate = layui.laydate;

        // 获取Token
        var token = $('#token').val();

        // 设置全局AJAX配置，添加Token到请求头
        $.ajaxSetup({
            headers: {
                'token': token
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // 统一错误处理
                if (jqXHR.status === 401) {
                    layer.msg('认证失败，请重新登录', {icon: 2});
                    // 可以添加跳转到登录页的逻辑
                } else {
                    layer.msg('请求失败: ' + errorThrown, {icon: 2});
                }
            }
        });

        // 初始化文件类型下拉框
        function initDocumentTypeSelect() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({category: "文件类型", remarks: "行政类文件"}),
                success: function(response) {
                    if (response && response.code === 200 && response.data) {
                        var documentTypeSelect = $('#documentTypeSelect');
                        documentTypeSelect.empty();

                        // 添加所有选项
                        response.data.forEach(function(item) {
                            documentTypeSelect.append('<option value="' + item.codeNum + '">' + item.content + '</option>');
                        });

                        // 默认选中第一个选项
                        if (response.data.length > 0) {
                            documentTypeSelect.val(response.data[0].codeNum);
                        }

                        form.render('select');
                    } else {
                        handleApiError(response);
                    }
                }
            });
        }

        // 初始化档案类型下拉框
        function initArchiveTypeSelect() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({category: "档案类型", remarks: "行政文件档案"}),
                success: function(response) {
                    if (response && response.code === 200 && response.data) {
                        var archiveTypeSelect = $('#archiveTypeSelect');
                        archiveTypeSelect.empty();

                        // 添加所有选项
                        response.data.forEach(function(item) {
                            archiveTypeSelect.append('<option value="' + item.codeNum + '">' + item.content + '</option>');
                        });

                        // 默认选中第一个选项
                        if (response.data.length > 0) {
                            archiveTypeSelect.val(response.data[0].codeNum);
                        }

                        form.render('select');
                    } else {
                        handleApiError(response);
                    }
                }
            });
        }

        // 初始化归档年份下拉框
        function initPlaceTimeSelect() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({category: '归档年份'}),
                success: function(response) {
                    if (response && response.code === 200 && response.data) {
                        var placeTimeSelect = $('#placeTimeSelect');
                        response.data.forEach(function(item) {
                            placeTimeSelect.append('<option value="' + item.codeNum + '">' + item.content + '</option>');
                        });
                        form.render('select');
                    } else {
                        handleApiError(response);
                    }
                }
            });
        }

        // 初始化日期选择器 - 修改日期格式为包含时间
        function initDatePickers() {
            // 签字日期
            laydate.render({
                elem: '#signatureTime',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                done: function(value, date) {
                    // 日期选择完成后的回调
                }
            });

            // 文件到期时间
            laydate.render({
                elem: '#expirationDate',
                type: 'datetime',
                format: 'yyyy-MM-dd HH:mm:ss',
                done: function(value, date) {
                    // 日期选择完成后的回调
                }
            });
        }

        // 初始化位置选择弹出层
        function initPositionModal() {
            var positionModal = $('#positionModal');
            var positionList = $('#positionList');
            var allPositions = []; // 存储所有位置数据

            // 打开位置选择弹出层
            $('#selectPositionBtn').on('click', function() {
                if (allPositions.length === 0) {
                    loadPositions('');
                } else {
                    renderPositions(''); // 如果已有数据，直接渲染
                }
                positionModal.show();

                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['600px', '400px'],
                    content: positionModal,
                    shadeClose: true
                });
            });

            // 搜索位置
            $('#positionSearch').on('input', function() {
                var keyword = $(this).val().trim();
                renderPositions(keyword);
            });

            // 加载位置列表
            function loadPositions(keyword) {
                positionList.html('<div style="text-align: center; padding: 20px;">加载中...</div>');

                $.ajax({
                    url: '/filePosition/listByQuery',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        fileType: 3
                    }),
                    success: function(response) {
                        if (response && response.code === 200 && response.data) {
                            allPositions = response.data; // 保存原始数据
                            renderPositions(keyword);
                        } else {
                            positionList.html('<div style="text-align: center; padding: 20px; color: #999;">获取位置列表失败</div>');
                            handleApiError(response);
                        }
                    }
                });
            }

            // 渲染位置列表（保持原始顺序）
            function renderPositions(keyword) {
                positionList.empty();

                if (allPositions.length === 0) {
                    positionList.html('<div style="text-align: center; padding: 20px; color: #999;">没有位置数据</div>');
                    return;
                }

                // 过滤并保持原始顺序
                var filteredPositions = keyword
                    ? allPositions.filter(item => item.positionCode.toLowerCase().includes(keyword.toLowerCase()))
            : allPositions;

                if (filteredPositions.length > 0) {
                    filteredPositions.forEach(function(item) {
                        var positionItem = $('<div class="position-item">' + item.positionCode + '</div>');
                        positionItem.on('click', function() {
                            // 选择位置并关闭弹出层
                            $('#positionCode').val(item.positionCode);
                            layer.closeAll();
                        });
                        positionList.append(positionItem);
                    });
                } else {
                    positionList.html('<div style="text-align: center; padding: 20px; color: #999;">没有找到匹配的位置</div>');
                }
            }
        }

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

            // 验证档案类型是否选择
            var archiveType = $('#archiveTypeSelect').val();
            if (!archiveType) {
                layer.msg('请选择档案类型', {icon: 2});
                return;
            }

            // 创建表单数据
            var formData = new FormData();
            formData.append('file', file);

            // 添加其他表单字段
            $('#uploadForm input, #uploadForm select, #uploadForm textarea').each(function() {
                var name = $(this).attr('name');
                if (name && name !== 'file') {
                    formData.append(name, $(this).val());
                }
            });

            // 特别处理typeCode参数（取自文件类型下拉框的value）
            var typeCode = $('#documentTypeSelect').val();
            formData.append('typeCode', typeCode);

            // 特别处理documentType参数（取自文件类型下拉框的文本）
            var documentTypeText = $('#documentTypeSelect option:selected').text().trim();
            // 移除可能的逗号或其他不期望的字符
            documentTypeText = documentTypeText.replace(/,/g, ''); // 移除所有逗号
            formData.append('documentType', documentTypeText);

            // 处理档案类型参数
            var archiveTypeText = $('#archiveTypeSelect option:selected').text().trim();
            archiveTypeText = archiveTypeText.replace(/,/g, ''); // 移除所有逗号
            formData.append('fileType', archiveTypeText); // 档案类型文本
            formData.append('fileTypeCode', archiveType); // 档案类型代码

            // 显示进度条
            element.progress('uploadProgress', '0%');
            $('#progressText').text('上传中...');
            $('#resultMsg').text('');

            // 禁用按钮
            $(this).attr('disabled', true).addClass('layui-btn-disabled');

            // 发送AJAX请求
            $.ajax({
                url: '/chemicalFile/handleFileUpload',  // 修改为新的API路径
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
                        layer.msg('上传成功', {icon: 1});
                    } else {
                        var msg = response && response.message ? response.message : '上传失败，未知错误';
                        $('#resultMsg').html('<span class="layui-badge layui-bg-red">失败</span> ' + msg);
                        layer.msg(msg, {icon: 2});
                        handleApiError(response);
                    }
                },
                complete: function() {
                    // 启用按钮
                    $('#uploadBtn').attr('disabled', false).removeClass('layui-btn-disabled');
                }
            });
        });

        // 统一处理API错误
        function handleApiError(response) {
            if (response && response.message) {
                layer.msg('接口错误: ' + response.message, {icon: 2});
            } else {
                layer.msg('未知错误，请重试', {icon: 2});
            }
        }

        // 初始化
        initDocumentTypeSelect();
        initArchiveTypeSelect();
        initPlaceTimeSelect();
        initDatePickers();
        initPositionModal();
    });
</script>
</body>
</html>