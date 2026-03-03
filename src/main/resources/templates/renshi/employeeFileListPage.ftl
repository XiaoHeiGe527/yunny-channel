<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>员工档案管理系统</title>
    <#include "../common/head.ftl">
    <style>
        /* 侧边导航栏样式（仅控制布局，不影响外观） */
        .sidebar-container {
            position: fixed !important;
            top: 50px !important; /* 匹配标题栏高度 */
            left: 0 !important;
            width: 200px !important; /* 固定宽度 */
            height: calc(100% - 50px) !important; /* 充满标题栏下方空间 */
            box-shadow: 2px 0 5px rgba(0,0,0,0.1) !important;
            z-index: 999 !important; /* 确保在内容上方 */
            overflow-y: auto !important;
        }

        /* 主内容容器（避开导航栏和标题栏） */
        .main-container {
            margin-top: 50px; /* 避开标题栏 */
            margin-left: 200px; /* 避开侧边栏 */
            padding: 20px;
            box-sizing: border-box;
            min-height: calc(100vh - 50px); /* 确保内容区域高度充足 */
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f9;
            padding: 0; /* 清除默认内边距，避免与主容器冲突 */
            margin: 0; /* 清除默认外边距 */
            overflow-x: hidden; /* 禁止横向滚动 */
        }

        .layui-container {
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        table th,
        table td {
            border: 1px solid #e6e6e6;
            padding: 10px 15px;
            text-align: center;
        }

        table th {
            background-color: #f2f2f2;
            font-weight: 600;
        }

        .layui-laypage {
            text-align: center;
            margin-top: 20px;
        }

        .layui-card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            background-color: #ffffff;
        }

        .layui-card-header {
            background-color: #fff;
            border-bottom: 1px solid #e6e6e6;
            padding: 15px 20px;
            font-size: 18px;
            font-weight: 600;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .layui-card-body {
            padding: 20px;
        }

        .query-form {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: center;
            margin-bottom: 20px;
            background-color: #ffffff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .query-form input,
        .query-form select,
        .query-form button {
            height: 38px;
            border-radius: 4px;
            border: 1px solid #e0e0e0;
            padding: 0 10px;
        }

        .query-form button {
            background-color: #1890ff;
            color: #fff;
            cursor: pointer;
        }

        .query-form button:hover {
            background-color: #40a9ff;
        }

        .layui-btn-group {
            margin-bottom: 20px;
        }

        .nav a {
            color: #1890ff;
            text-decoration: none;
            margin-left: 15px;
        }

        .nav a:hover {
            text-decoration: underline;
        }

        .profession-suggestions {
            position: absolute;
            background-color: white;
            border: 1px solid #ccc;
            max-height: 200px;
            overflow-y: auto;
            z-index: 100;
            display: none;
        }

        .profession-suggestion {
            padding: 5px 10px;
            cursor: pointer;
        }

        .profession-suggestion:hover {
            background-color: #f0f0f0;
        }

        .download-btn {
            background-color: #1890ff;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 5px;
        }

        .download-original-btn {
            background-color: #22c55e;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 5px;
        }

        .view-btn {
            background-color: #6495ed;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 5px;
        }

        .edit-btn {
            background-color: #ff9800;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }

        .replace-btn {
            background-color: #ff4d4f;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }

        .download-btn:disabled,
        .download-original-btn:disabled,
        .view-btn:disabled,
        .edit-btn:disabled,
        .replace-btn:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }

        /* 图片预览弹窗样式 */
        .preview-container {
            padding: 20px;
            max-height: 80vh;
            overflow-y: auto;
        }

        .preview-image {
            display: block;
            margin: 0 auto 20px;
            max-width: 100%;
            max-height: 80vh;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
        }

        .page-indicator {
            text-align: center;
            margin-bottom: 10px;
            color: #666;
            font-size: 14px;
        }

        /* 响应式适配：小屏幕隐藏侧边栏 */
        @media (max-width: 768px) {
            .sidebar-container {
                display: none !important;
            }
            .main-container {
                margin-left: 0 !important;
                margin-top: 50px !important;
            }
        }
    </style>
</head>

<body>
<#include "../common/inHeader.ftl"> <!-- 标题栏 -->
<#include "../common/navigation.ftl"> <!-- 侧边导航栏 -->

<!-- 主内容容器：通过边距避开导航栏和标题栏 -->
<div class="main-container">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="query-form">
                <label for="employeeCode">员工编号：</label>
                <input type="text" id="employeeCode" placeholder="请输入员工编号">

                <label for="name">姓名：</label>
                <input type="text" id="name" placeholder="请输入姓名">

                <label for="department">部门：</label>
                <select id="department" class="layui-select">
                    <option value="">请选择部门</option>
                    <!-- 部门选项将通过JS动态加载 -->
                </select>

                <label for="rank">职级：</label>
                <select id="rank" class="layui-select">
                    <option value="">请选择职级</option>
                    <!-- 职级选项将通过JS动态加载 -->
                </select>

                <label for="profession">岗位：</label>
                <div style="position: relative;">
                    <input type="text" id="profession" placeholder="请输入岗位">
                    <div id="professionSuggestions" class="profession-suggestions"></div>
                </div>

                <label for="state">是否在职：</label>
                <select id="state" class="layui-select">
                    <option value="1">在职</option>
                    <option value="0">离职</option>
                    <option value="">全部</option>

                </select>

                <div class="layui-inline">
                    <button id="queryButton" class="layui-btn layui-btn-radius layui-btn-normal">查询</button>
                </div>
            </div>
            <div class="layui-btn-group">
                <button id="addButton" class="layui-btn">
                    <i class="layui-icon">&#xe608;</i> 添加
                </button>
            </div>
            <table id="employeeTable">
                <thead>
                <tr>
                    <th style="display: none;">ID</th>
                    <th>员工编号</th>
                    <th>姓名</th>
                    <th>部门</th>
                    <th>职级</th>
                    <th>岗位</th>
                    <th>文件位置编号</th>
                    <th>文件位置明细</th>
                    <th>是否在职</th>
                    <th>备注信息</th>
                    <th>员工附件</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div id="pageNav"></div>
        </div>
    </div>
</div>

<script>
    layui.use(['table', 'laypage', 'laydate', 'layer', 'form'], function () {
        var table = layui.table;
        var laypage = layui.laypage;
        var laydate = layui.laydate;
        var layer = layui.layer;
        var form = layui.form;

        // 从 FTL 变量中获取 token
        var token = '${Request.token}';
        var userNo = '${Request.userNo}';
        var permissions = [];

        // 当前页码
        var currentPage = 1;
        // 每页数量
        var pageSize = 20;

        // 权限标识
        var hasAddPermission = false;
        var hasDownloadPermission = false;
        var hasDownloadOriginalPermission = false;
        var hasEditPermission = false;
        var hasReplacePermission = false;
        var hasViewPermission = true; // 查看权限

        // 存储所有岗位数据
        var allProfessions = [];

        // 初始化日期选择器
        laydate.render({
            elem: '#startDate',
            type: 'date'
        });
        laydate.render({
            elem: '#endDate',
            type: 'date'
        });

        // 获取用户权限
        function loadPermissions() {
            $.ajax({
                url: '/systemResource/selectUserResourceList',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        permissions = response.data;
                        // 根据权限设置标志
                        hasAddPermission = permissions.includes('/renShiViewJump/employeeFile/handleFileUpload');
                        hasDownloadPermission = permissions.includes('/employeeFile/addWatermarkAndDownload');
                        hasDownloadOriginalPermission = permissions.includes('/employeeFile/downloadFile');
                        hasEditPermission = permissions.includes('/renShiViewJump/employeeFile/employeeFileEdit');
                        hasReplacePermission = permissions.includes('/renShiViewJump/employeeFile/replaceUpload');
                        // 可以根据实际需求添加查看权限判断

                        // 根据权限显示或隐藏添加按钮
                        if (!hasAddPermission) {
                            $('#addButton').hide();
                        }

                        // 加载其他数据
                        loadDepartments();
                        loadRanks();
                        loadAllProfessions();
                        fetchData(currentPage, pageSize, {});
                    } else {
                        alert('获取用户权限失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 加载部门下拉框数据
        function loadDepartments() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({category: "部门"}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        var departments = response.data;
                        var departmentSelect = $('#department');
                        departmentSelect.empty();
                        departmentSelect.append('<option value="">请选择部门</option>');
                        departments.forEach(function(department) {
                            departmentSelect.append('<option value="' + department.content + '">' + department.content + '</option>');
                        });
                        form.render('select');
                    } else {
                        alert('获取部门数据失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 加载职级下拉框数据
        function loadRanks() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({category: "职级"}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        var ranks = response.data;
                        var rankSelect = $('#rank');
                        rankSelect.empty();
                        rankSelect.append('<option value="">请选择职级</option>');
                        ranks.forEach(function(rank) {
                            rankSelect.append('<option value="' + rank.content + '">' + rank.content + '</option>');
                        });
                        form.render('select');
                    } else {
                        alert('获取职级数据失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 加载所有岗位数据
        function loadAllProfessions() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({category: "岗位"}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        allProfessions = response.data;
                        console.log('所有岗位数据已加载:', allProfessions);
                    } else {
                        alert('获取岗位数据失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 本地筛选岗位数据
        function filterProfessions(keyword) {
            if (!keyword || keyword.length === 0) {
                return allProfessions;
            }

            return allProfessions.filter(function(profession) {
                return profession.content && profession.content.toLowerCase().includes(keyword.toLowerCase());
            });
        }

        // 显示筛选后的岗位建议
        function showProfessionSuggestions(keyword) {
            var filteredProfessions = filterProfessions(keyword);
            var suggestionsContainer = $('#professionSuggestions');
            suggestionsContainer.empty();

            if (filteredProfessions.length > 0) {
                filteredProfessions.forEach(function(profession) {
                    var suggestion = $('<div class="profession-suggestion">' + profession.content + '</div>');
                    suggestion.click(function() {
                        $('#profession').val(profession.content);
                        suggestionsContainer.hide();
                    });
                    suggestionsContainer.append(suggestion);
                });
                suggestionsContainer.show();
            } else {
                suggestionsContainer.hide();
            }
        }

        // 初始化岗位输入框的事件监听
        $('#profession').on('focus input', function() {
            var keyword = $(this).val();
            showProfessionSuggestions(keyword);
        });

        // 点击页面其他地方隐藏建议框
        $(document).on('click', function(e) {
            if (!$(e.target).closest('#profession, #professionSuggestions').length) {
                $('#professionSuggestions').hide();
            }
        });

        // 查看员工图片
        function viewEmployeeImages(employeeCode) {
            // 显示加载中
            var loading = layer.load(2, {
                shade: [0.3, '#fff']
            });

            // 调用指定接口
            $.ajax({
                url: '/employeeImages/listByEmployeeCode',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({employeeCode: employeeCode}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    layer.close(loading);

                    if (response.code === 200 && response.data && response.data.length > 0) {
                        // 按页码排序
                        var sortedImages = response.data.sort(function(a, b) {
                            return a.pageNum - b.pageNum;
                        });

                        // 构建预览内容
                        var previewContent = '';
                        for (var i = 0; i < sortedImages.length; i++) {
                            var img = sortedImages[i];
                            previewContent += '<div class="page-indicator">第 ' + img.pageNum + ' 页</div>';
                            previewContent += '<img src="http://' + img.imageUrl + '" class="preview-image" ';
                            previewContent += 'alt="第 ' + img.pageNum + ' 页" ';
                            previewContent += 'onerror="this.src=\'data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0iIzY2NiIgZD0iTTEyIDJDNi40OCAyIDIgNi40OCAyIDEyczQuNDggMTAgMTAgMTAgMTAtNC40OCAxMC0xMC00LjQ4LTEwLTEwLTEwem0wIDE4YzQuNDIgMCA4LTQuNTggOC05cy0zLjU4LTktOC05LTggNC41OCA4LTkgOCA5LTggOSA0LjU4IDkgOS05eiIvPjxwYXRoIGZpbGw9IiNmZmYiIGQ9Ik0xNiAxMkg4Yy0uNTUgMC0xLS40NS0xLTF2LTRjMC0uNTUuNDUtMSAxLTFoMmMwIC41NS40NSAxIDEgMXY0YzAgLjU1LS40NSAxLTEgMWgtNnoiLz48L3N2Zz4=\'">';
                        }

                        // 创建弹窗
                        layer.open({
                            type: 1,
                            title: '员工档案预览',
                            area: ['90%', '90%'],
                            content: '<div class="preview-container">' + previewContent + '</div>',
                            maxmin: true
                        });
                    } else {
                        layer.msg('没有找到图片数据', {icon: 2});
                    }
                },
                error: function (response) {
                    layer.close(loading);
                    layer.msg('加载图片失败，请重试:'+response.responseText, {icon: 2});
                }
            });
        }

        // 渲染表格数据
        function renderTable(data) {
            var tableBody = $('#employeeTable tbody');
            tableBody.empty();

            if (data.length === 0) {
                tableBody.append('<tr><td colspan="12">暂无数据</td></tr>');
                return;
            }

            data.forEach(function (item) {
                var stateText = item.state === 1 ? '在职' : '离职';
                var disabledAttr = item.pdfFileUrl ? '' : 'disabled';
                var hasEmployeeCode = !!item.employeeCode;
                var viewDisabledAttr = hasEmployeeCode ? '' : 'disabled';

                // 生成附件列按钮（包含查看按钮）
                var attachmentButtons = '';

                // 添加查看按钮
                if (hasViewPermission) {
                    attachmentButtons += '<button class="view-btn" data-code="' + item.employeeCode + '" title="查看档案" ' + viewDisabledAttr + '>';
                    attachmentButtons += '<i class="layui-icon layui-icon-search"></i> 查看';
                    attachmentButtons += '</button>';
                }

                // 下载附件按钮
                if (hasDownloadPermission) {
                    attachmentButtons += '<button class="download-btn" data-filename="' + item.pdfFileUrl + '" ' + disabledAttr + '>下载附件</button>';
                }

                // 下载原件按钮
                if (hasDownloadOriginalPermission) {
                    attachmentButtons += '<button class="download-original-btn" data-filename="' + item.pdfFileUrl + '" ' + disabledAttr + '>下载原件</button>';
                }

                // 如果没有权限，显示提示
                if (attachmentButtons === '') {
                    attachmentButtons = '无相关权限';
                }

                // 生成操作列按钮
                var actionButtons = '';

                if (hasEditPermission) {
                    actionButtons += '<button class="edit-btn" data-id="' + item.id + '">编辑</button>';
                } else {
                    actionButtons += '无操作权限';
                }

                // 添加更替PDF按钮
                if (hasReplacePermission) {
                    if (actionButtons !== '无操作权限') {
                        actionButtons += ' | ';
                    }
                    actionButtons += '<button class="replace-btn" data-id="' + item.id + '">更替PDF</button>';
                } else {
                    if (actionButtons !== '无操作权限' && actionButtons !== '') {
                        actionButtons += ' | 无操作权限';
                    }
                }

                var row = '<tr>' +
                    '<td style="display: none;">' + item.id + '</td>' +
                    '<td>' + item.employeeCode + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' + item.department + '</td>' +
                    '<td>' + item.rank + '</td>' +
                    '<td>' + item.profession + '</td>' +
                    '<td>' + item.positionCode + '</td>' +
                    '<td>' + item.positionCodeRemarks + '</td>' +
                    '<td>' + stateText + '</td>' +
                    '<td>' + (item.remarks || '') + '</td>' +
                    '<td>' + attachmentButtons + '</td>' +
                    '<td>' + actionButtons + '</td>' +
                    '</tr>';
                tableBody.append(row);
            });

            // 绑定查看按钮事件
            $('.view-btn').click(function() {
                var employeeCode = $(this).data('code');
                if (employeeCode) {
                    viewEmployeeImages(employeeCode);
                } else {
                    layer.msg('没有员工编号信息', {icon: 2});
                }
            });

            // 绑定下载附件按钮事件
            $('.download-btn').click(function() {
                var btn = $(this);
                var remoteFileName = btn.data('filename');

                if (!remoteFileName) {
                    layer.msg('没有可下载的附件', {icon: 2});
                    return;
                }

                // 禁用按钮
                btn.prop('disabled', true);

                // 显示加载提示
                var loading = layer.load(1, {
                    shade: [0.1,'#fff']
                });

                // 创建 XMLHttpRequest 对象
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/employeeFile/addWatermarkAndDownload', true);
                xhr.setRequestHeader('token', token);
                xhr.responseType = 'blob';

                // 处理响应
                xhr.onload = function() {
                    layer.close(loading);
                    btn.prop('disabled', false);

                    if (xhr.status === 200) {
                        var blob = new Blob([xhr.response], { type: 'application/pdf' });
                        var url = URL.createObjectURL(blob);
                        var a = document.createElement('a');
                        a.href = url;
                        a.download = remoteFileName;
                        document.body.appendChild(a);
                        a.click();

                        setTimeout(function() {
                            document.body.removeChild(a);
                            URL.revokeObjectURL(url);
                        }, 100);

                        layer.msg('开始下载附件', {icon: 1});
                    } else {
                        layer.msg('下载失败: ' + xhr.statusText, {icon: 2});
                    }
                };

                xhr.onerror = function() {
                    layer.close(loading);
                    layer.msg('网络错误，请稍后重试', {icon: 2});
                    btn.prop('disabled', false);
                };

                var formData = new FormData();
                formData.append('remoteFileName', remoteFileName);
                xhr.send(formData);
            });

            // 绑定下载原件按钮事件
            $('.download-original-btn').click(function() {
                var btn = $(this);
                var remoteFileName = btn.data('filename');

                if (!remoteFileName) {
                    layer.msg('没有可下载的原件', {icon: 2});
                    return;
                }

                // 禁用按钮
                btn.prop('disabled', true);

                // 显示加载提示
                var loading = layer.load(1, {
                    shade: [0.1,'#fff']
                });

                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/employeeFile/downloadFile', true);
                xhr.setRequestHeader('token', token);
                xhr.responseType = 'blob';

                xhr.onload = function() {
                    layer.close(loading);
                    btn.prop('disabled', false);

                    if (xhr.status === 200) {
                        var blob = new Blob([xhr.response], { type: 'application/pdf' });
                        var url = URL.createObjectURL(blob);
                        var a = document.createElement('a');
                        a.href = url;
                        a.download = remoteFileName;
                        document.body.appendChild(a);
                        a.click();

                        setTimeout(function() {
                            document.body.removeChild(a);
                            URL.revokeObjectURL(url);
                        }, 100);

                        layer.msg('开始下载原件', {icon: 1});
                    } else {
                        layer.msg('下载失败: ' + xhr.statusText, {icon: 2});
                    }
                };

                xhr.onerror = function() {
                    layer.close(loading);
                    layer.msg('网络错误，请稍后重试', {icon: 2});
                    btn.prop('disabled', false);
                };

                var formData = new FormData();
                formData.append('remoteFileName', remoteFileName);
                xhr.send(formData);
            });

            // 绑定编辑按钮事件
            $('.edit-btn').click(function() {
                var id = $(this).data('id');

                var form = $('<form>', {
                    action: '/renShiViewJump/employeeFile/employeeFileEdit',
                    method: 'post'
                });
                form.append($('<input>', {
                    type: 'hidden',
                    name: 'id',
                    value: id
                }));
                form.append($('<input>', {
                    type: 'hidden',
                    name: 'token',
                    value: token
                }));
                form.appendTo('body').submit();
            });

            // 绑定更替PDF按钮事件
            $('.replace-btn').click(function() {
                var id = $(this).data('id');

                var form = $('<form>', {
                    action: '/renShiViewJump/employeeFile/replaceUpload',
                    method: 'post'
                });
                form.append($('<input>', {
                    type: 'hidden',
                    name: 'id',
                    value: id
                }));
                form.append($('<input>', {
                    type: 'hidden',
                    name: 'token',
                    value: token
                }));
                form.appendTo('body').submit();
            });
        }

        // 获取员工档案列表数据
        function fetchData(page, size, query) {
            var employeeCode = $('#employeeCode').val();
            var name = $('#name').val();
            var department = $('#department').val();
            var rank = $('#rank').val();
            var profession = $('#profession').val();
            var state = $('#state').val();

            var fullQuery = {
                employeeCode: employeeCode,
                name: name,
                department: department,
                rank: rank,
                profession: profession,
                state: state,
                currentPage: page,
                pageSize: size
            };

            console.log('即将请求的数据：', fullQuery);

            $.ajax({
                url: '/employeeFile/listByPage',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(fullQuery),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    console.log('请求成功，响应数据：', response);
                    if (response.code === 200) {
                        var resultMap = response.data;
                        var commonPager = resultMap;

                        renderTable(commonPager.dataList);

                        // 渲染分页导航
                        laypage.render({
                            elem: 'pageNav',
                            count: commonPager.page.totalCount,
                            curr: page,
                            limit: size,
                            layout: ['count', 'prev', 'page', 'next', 'limit'],
                            jump: function (obj, first) {
                                if (!first) {
                                    currentPage = obj.curr;
                                    pageSize = obj.limit;
                                    fetchData(currentPage, pageSize, query);
                                }
                            }
                        });
                    } else {
                        alert('请求失败：' + response.message);
                    }
                },
                error: function (error) {
                    console.error('请求出错:', error);
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 初始加载数据
        $(document).ready(function() {
            loadPermissions();
        });

        // 点击查询按钮事件
        $('#queryButton').click(function () {
            currentPage = 1;
            fetchData(currentPage, pageSize, {});
        });

        // 点击新增按钮事件
        $('#addButton').click(function () {
            var form = $('<form>', {
                action: '/renShiViewJump/employeeFile/handleFileUpload',
                method: 'post'
            });
            form.append($('<input>', {
                type: 'hidden',
                name: 'token',
                value: token
            }));
            form.appendTo('body').submit();
        });
    });
</script>
</body>

</html>