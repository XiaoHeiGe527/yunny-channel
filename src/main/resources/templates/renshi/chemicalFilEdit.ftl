<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>化工档案编辑</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/public.css" media="all">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <#include "../common/head.ftl">
    <style>
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
            .layui-input-inline {
                width: 100% !important;
            }
        }

        /* 按钮样式优化 */
        .layui-btn + .layui-btn {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">化工档案编辑</div>
        <div class="layui-card-body">
            <form class="layui-form" id="chemicalFileForm" lay-filter="chemicalFileForm">
                <input type="hidden" name="id" value="${chemicalFile.id}">
                <input type="hidden" name="userNo" value="${userNo}">

                <div class="layui-form-item">
                    <label class="layui-form-label">文件编号</label>
                    <div class="layui-input-block">
                        <input type="text" name="documentCode" required lay-verify="required"
                               placeholder="请输入文件编号" autocomplete="off"
                               class="layui-input" value="${(chemicalFile.documentCode)!}">
                    </div>
                </div>

                <!-- 存放位置改为选填（移除required和lay-verify） -->
                <div class="layui-form-item">
                    <label class="layui-form-label">存放位置</label>
                    <div class="layui-input-block">
                        <div class="layui-input-inline">
                            <input type="text" name="positionCode" id="positionCode"
                                   placeholder="请选择存放位置（选填）" autocomplete="off" readonly
                                   class="layui-input" value="${(chemicalFile.positionCode)!}">
                        </div>
                        <button type="button" id="selectPositionBtn" class="layui-btn">
                            <i class="layui-icon">&#xe615;</i> 选择位置
                        </button>
                    </div>
                </div>

                <!-- 文件类型下拉框 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">文件类型</label>
                    <div class="layui-input-block">
                        <select name="typeCode" id="documentTypeSelect" required lay-verify="required">
                            <option value="">请选择文件类型</option>
                            <!-- 选项将通过JS动态加载 -->
                        </select>
                    </div>
                </div>

                <!-- 档案类型下拉框 -->
                <div class="layui-form-item">
                    <label class="layui-form-label">档案类型</label>
                    <div class="layui-input-block">
                        <select name="fileTypeCode" id="fileTypeSelect" required lay-verify="required">
                            <option value="">请选择档案类型</option>
                            <!-- 选项将通过JS动态加载 -->
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">归档年份</label>
                    <div class="layui-input-block">
                        <select name="placeTime" id="placeTimeSelect" required lay-verify="required">
                            <option value="">请选择归档年份</option>
                            <#list placeTimes as placeTime>
                                <option value="${placeTime.content}"
                                        <#if chemicalFile.placeTime?exists && chemicalFile.placeTime == placeTime.content>selected</#if>>
                                    ${placeTime.content}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">文件标题</label>
                    <div class="layui-input-block">
                        <input type="text" name="documentTitle" required lay-verify="required"
                               placeholder="请输入文件标题" autocomplete="off"
                               class="layui-input" value="${(chemicalFile.documentTitle)!}">
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">文件内容</label>
                    <div class="layui-input-block">
                        <textarea name="documentContent" placeholder="请输入文件内容"
                                  class="layui-textarea">${(chemicalFile.documentContent)!}</textarea>
                    </div>
                </div>

                <!-- 文件说明文本框 -->
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">文件说明</label>
                    <div class="layui-input-block">
                        <textarea name="documentDescribe" placeholder="请输入文件说明"
                                  class="layui-textarea">${(chemicalFile.documentDescribe)!}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">签字日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="signatureTime" id="signatureTime" required lay-verify="required|dateTime"
                               placeholder="请选择签字日期" autocomplete="off"
                               class="layui-input"
                               value="${(chemicalFile.signatureTime?date?string('yyyy-MM-dd HH:mm:ss'))!''}">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">到期日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="expirationDate" id="expirationDate"
                               placeholder="请选择到期日期" autocomplete="off"
                               class="layui-input"
                               value="${(chemicalFile.expirationDate?date?string('yyyy-MM-dd HH:mm:ss'))!''}">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">文件状态</label>
                    <div class="layui-input-block">
                        <select name="state" required lay-verify="required">
                            <option value="1" <#if chemicalFile.state?exists && chemicalFile.state == 1>selected</#if>>有效</option>
                            <option value="0" <#if chemicalFile.state?exists && chemicalFile.state == 0>selected</#if>>无效</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formSubmit">提交</button>
                        <button type="button" class="layui-btn layui-btn-primary" onclick="javascript:history.back();">返回</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
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
</div>

<script>
    layui.use(['form', 'layer', 'element', 'jquery', 'laydate'], function(){
        var form = layui.form;
        var layer = layui.layer;
        var element = layui.element;
        var $ = layui.jquery;
        var laydate = layui.laydate;

        // 自定义日期时间验证规则
        form.verify({
            dateTime: function(value, item) {
                if (value && !/^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/.test(value)) {
                    return '请输入正确的日期时间格式，如：2023-01-01 12:00:00';
                }
            }
        });

        // 日期格式转换函数 - 增强版
        function formatDateForLaydate(dateStr) {
            console.log('原始日期字符串:', dateStr); // 调试日志

            if (!dateStr || dateStr === 'null' || dateStr === '') {
                console.log('日期为空，返回空字符串');
                return '';
            }

            // 尝试多种日期格式解析
            try {
                // 处理可能的ISO格式：2023-01-01T12:00:00.000+08:00
                if (dateStr.includes('T')) {
                    console.log('检测到ISO格式日期');
                    var isoDate = dateStr.split('T')[0] + ' ' + dateStr.split('T')[1].split('.')[0];
                    console.log('转换后的日期:', isoDate);
                    return isoDate;
                }

                // 处理可能的带时区的格式：2023-01-01 12:00:00 +0800
                if (dateStr.match(/\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2} [+-]\d{4}/)) {
                    console.log('检测到带时区的日期格式');
                    var parts = dateStr.split(' ');
                    var datePart = parts[0] + ' ' + parts[1];
                    console.log('转换后的日期:', datePart);
                    return datePart;
                }

                // 处理可能的只有日期的格式：2023-01-01
                if (dateStr.match(/^\d{4}-\d{2}-\d{2}$/)) {
                    console.log('检测到只有日期的格式');
                    var fullDate = dateStr + ' 00:00:00';
                    console.log('转换后的日期:', fullDate);
                    return fullDate;
                }

                // 检查是否已经是正确格式
                if (/^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/.test(dateStr)) {
                    console.log('日期格式已正确:', dateStr);
                    return dateStr;
                }

                // 如果以上都不匹配，尝试使用JavaScript Date解析
                console.log('尝试使用JavaScript Date解析');
                var dateObj = new Date(dateStr);
                if (!isNaN(dateObj.getTime())) {
                    var formattedDate = dateObj.getFullYear() + '-' +
                        padZero(dateObj.getMonth() + 1) + '-' +
                        padZero(dateObj.getDate()) + ' ' +
                        padZero(dateObj.getHours()) + ':' +
                        padZero(dateObj.getMinutes()) + ':' +
                        padZero(dateObj.getSeconds());
                    console.log('JavaScript解析后的日期:', formattedDate);
                    return formattedDate;
                }

                console.error('无法解析的日期格式:', dateStr);
                return '';

            } catch (error) {
                console.error('日期格式转换出错:', error);
                return '';
            }
        }

        // 辅助函数：数字补零
        function padZero(num) {
            return (num < 10) ? '0' + num : num;
        }

        // 初始化日期选择器
        var signatureTimeInstance = laydate.render({
            elem: '#signatureTime',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss',
            value: formatDateForLaydate('${(chemicalFile.signatureTime?date?string('yyyy-MM-dd HH:mm:ss'))!''}'),
            done: function(value, date, endDate) {
                // 日期选择完成后更新表单值
                $(this.elem).val(value);
                form.render('input'); // 刷新表单渲染
            },
            error: function(value, date) {
                console.error('日期插件初始化错误:', value, date);
                layer.msg('日期插件初始化失败，请手动输入', {icon: 2});
            }
        });

        var expirationDateInstance = laydate.render({
            elem: '#expirationDate',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss',
            value: formatDateForLaydate('${(chemicalFile.expirationDate?date?string('yyyy-MM-dd HH:mm:ss'))!''}'),
            done: function(value, date, endDate) {
                // 日期选择完成后更新表单值
                $(this.elem).val(value);
                form.render('input'); // 刷新表单渲染
            },
            error: function(value, date) {
                console.error('日期插件初始化错误:', value, date);
                layer.msg('日期插件初始化失败，请手动输入', {icon: 2});
            }
        });

        // 获取Token
        var token = '${Request.token}';

        // 设置全局AJAX配置，添加Token到请求头
        $.ajaxSetup({
            headers: {
                'token': token
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // 统一错误处理
                if (jqXHR.status === 401) {
                    layer.msg('认证失败，请重新登录', {icon: 2});
                } else {
                    layer.msg('请求失败: ' + errorThrown, {icon: 2});
                }
            }
        });

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
                        }
                    }
                });
            }

            // 渲染位置列表
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
                        documentTypeSelect.append('<option value="">请选择文件类型</option>');

                        response.data.forEach(function(item) {
                            documentTypeSelect.append('<option value="' + item.codeNum + '">' + item.content + '</option>');
                        });

                        // 设置初始值
                        var initialTypeCode = '${(chemicalFile.typeCode)!}';
                        if (initialTypeCode) {
                            documentTypeSelect.val(initialTypeCode);
                            form.render('select');
                        }
                    }
                }
            });
        }

        // 初始化档案类型下拉框
        function initFileTypeSelect() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({category: "档案类型", remarks: "行政文件档案"}),
                success: function(response) {
                    if (response && response.code === 200 && response.data) {
                        var fileTypeSelect = $('#fileTypeSelect');
                        fileTypeSelect.empty();
                        fileTypeSelect.append('<option value="">请选择档案类型</option>');

                        response.data.forEach(function(item) {
                            fileTypeSelect.append('<option value="' + item.codeNum + '">' + item.content + '</option>');
                        });

                        // 设置初始值
                        var initialFileTypeCode = '${(chemicalFile.fileTypeCode)!}';
                        if (initialFileTypeCode !== '' && initialFileTypeCode !== 'null') {
                            fileTypeSelect.val(initialFileTypeCode);
                            form.render('select');
                        }
                    }
                }
            });
        }

        // 表单提交监听 - 直接从下拉框获取选中的文本值
        form.on('submit(formSubmit)', function(data) {
            console.log('表单提交数据:', data.field); // 调试用

            // 验证时间格式
            if (data.field.signatureTime && !/^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/.test(data.field.signatureTime)) {
                layer.msg('签字日期格式不正确，请使用yyyy-MM-dd HH:mm:ss格式', {icon: 2});
                return false;
            }
            if (data.field.expirationDate && !/^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/.test(data.field.expirationDate)) {
                layer.msg('到期日期格式不正确，请使用yyyy-MM-dd HH:mm:ss格式', {icon: 2});
                return false;
            }

            // 直接从下拉框获取选中的文本值
            var documentTypeSelect = $('#documentTypeSelect');
            var fileTypeSelect = $('#fileTypeSelect');

            var formData = $.extend({}, data.field);
            formData.documentType = documentTypeSelect.find('option:selected').text();
            formData.fileType = fileTypeSelect.find('option:selected').text();

            console.log('修正后的提交数据:', formData); // 调试用

            $.ajax({
                url: '/chemicalFile/chemicalFileEdit',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    if (response.code === 200) {
                        alert('数据提交成功');
                        // 创建一个隐藏的表单并提交 POST 请求
                        var form = $('<form>', {
                            action: '/renShiViewJump/chemicalFile/chemicalFileListPage',
                            method: 'post'
                        });
                        form.append($('<input>', {
                            type: 'hidden',
                            name: 'token',
                            value: token
                        }));
                        form.appendTo('body').submit();
                    } else {
                        alert('数据提交失败：' + response.message);
                    }
                },
                error: function(jqXHR) {
                    layer.msg('网络请求失败，请重试', {icon: 2});
                    console.error(jqXHR.responseText);
                }
            });
            return false;
        });

        // 初始化
        initPositionModal();
        initDocumentTypeSelect();
        initFileTypeSelect();
        form.render();
    });
</script>
</body>
</html>