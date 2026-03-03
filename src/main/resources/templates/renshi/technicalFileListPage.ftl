<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>技术档案管理系统</title>
    <#include "../common/head.ftl">
    <style>
        /* 侧边导航栏布局控制 */
        .sidebar-container {
            position: fixed !important;
            top: 50px !important; /* 避开标题栏（假设标题栏高度50px） */
            left: 0 !important;
            width: 200px !important;
            height: calc(100% - 50px) !important; /* 充满标题栏下方空间 */
            box-shadow: 2px 0 5px rgba(0,0,0,0.1) !important;
            z-index: 999 !important;
            overflow-y: auto !important;
        }

        /* 主内容容器（避开导航栏和标题栏） */
        .main-container {
            margin-top: 50px; /* 避开标题栏 */
            margin-left: 200px; /* 避开侧边栏 */
            padding: 20px;
            box-sizing: border-box;
            min-height: calc(100vh - 50px);
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f9;
            padding: 0; /* 清除默认内边距，避免布局冲突 */
            margin: 0;
            overflow-x: hidden;
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

        .download-btn:disabled,
        .download-original-btn:disabled,
        .view-btn:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }

        .edit-btn {
            background-color: #ff9800;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }

        .fixed-label {
            padding: 0 10px;
            line-height: 38px;
            background-color: #f5f5f5;
            border: 1px solid #e0e0e0;
            border-radius: 4px;
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

        /* 响应式适配 */
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
<#include "../common/inHeader.ftl">
<#include "../common/navigation.ftl"> <!-- 引入侧边导航栏 -->

<!-- 主内容容器：通过边距避开导航栏和标题栏 -->
<div class="main-container">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="query-form">
                <label for="documentCode">文件编号：</label>
                <input type="text" id="documentCode" placeholder="请输入文件编号">

                <!-- 档案类型固定为"技术文件"，不允许修改 -->
                <label>档案类型：</label>
                <div class="fixed-label">技术文件</div>
                <input type="hidden" id="fileType" value="技术文件">

                <label for="documentType">文件类型：</label>
                <select id="documentType" class="layui-select">
                    <option value="">请选择文件类型</option>
                    <!-- 文件类型选项将通过JS动态加载 -->
                </select>

                <label for="state">文件状态：</label>
                <select id="state" class="layui-select">
                    <option value="">全部</option>
                    <option value="1" selected>有效</option>
                    <option value="0">无效</option>
                </select>

                <label for="documentTitle">文件标题：</label>
                <input type="text" id="documentTitle" placeholder="请输入文件标题">

                <label for="documentContent">文件内容：</label>
                <input type="text" id="documentContent" placeholder="请输入文件内容">

                <label for="placeTime">归档年份：</label>
                <select id="placeTime" class="layui-select">
                    <option value="">请选择归档年份</option>
                    <!-- 归档年份选项将通过JS动态加载 -->
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
            <table id="chemicalFileTable">
                <thead>
                <tr>
                    <th style="display: none;">ID</th>
                    <!-- 修改：将"档案类型编码"替换为"档案序号" -->
                    <th>档案序号</th>
                    <th>档案类型</th>
                    <th>类型编号</th>
                    <th>文件类型</th>
                    <th>文件编号</th>
                    <th>文件标题</th>
                    <th>归档年份</th>
                    <th>签字日期</th>
                    <th>文件位置</th>
                    <th>文件内容</th>
                    <th>档案附件</th>
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
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage;
        var layer = layui.layer;

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
        var hasViewPermission = true; // 默认允许查看

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
                        hasAddPermission = permissions.includes('/renShiViewJump/chemicalFile/technicalFileAdd');
                        hasDownloadPermission = permissions.includes('/chemicalFile/addWatermarkAndDownload');
                        hasDownloadOriginalPermission = permissions.includes('/chemicalFile/downloadFile');
                        hasEditPermission = permissions.includes('/renShiViewJump/chemicalFile/chemicalFileEdit');

                        // 根据权限显示或隐藏添加按钮
                        if (!hasAddPermission) {
                            document.getElementById('addButton').style.display = 'none';
                        }

                        // 加载其他数据
                        loadDocumentTypes();
                        loadPlaceTimes();
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

        // 加载文件类型下拉框数据
        function loadDocumentTypes() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                // 添加remarks参数：技术类文件
                data: JSON.stringify({category: "文件类型", remarks: "技术类文件"}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        var documentTypes = response.data;
                        var documentTypeSelect = document.getElementById('documentType');
                        documentTypeSelect.innerHTML = '<option value="">请选择文件类型</option>';

                        for (var i = 0; i < documentTypes.length; i++) {
                            var type = documentTypes[i];
                            var option = document.createElement('option');
                            option.value = type.content;
                            option.text = type.content;
                            documentTypeSelect.appendChild(option);
                        }
                    } else {
                        alert('获取文件类型数据失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 加载归档年份下拉框数据
        function loadPlaceTimes() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({category: "归档年份"}),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        var placeTimes = response.data;
                        var placeTimeSelect = document.getElementById('placeTime');
                        placeTimeSelect.innerHTML = '<option value="">请选择归档年份</option>';

                        for (var i = 0; i < placeTimes.length; i++) {
                            var time = placeTimes[i];
                            var option = document.createElement('option');
                            option.value = time.content;
                            option.text = time.content;
                            placeTimeSelect.appendChild(option);
                        }
                    } else {
                        alert('获取归档年份数据失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 查看文件图片
        function viewFileImages(chemicalFileId) {
            // 显示加载中
            var loading = layer.load(2, {
                shade: [0.3, '#fff']
            });

            // 请求图片列表（使用相同的接口）
            $.ajax({
                url: '/chemicalFileImages/listByChemicalFileId',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({chemicalFileId: chemicalFileId}),
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
                            title: '文件预览',
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
            var tableBody = document.querySelector('#chemicalFileTable tbody');
            tableBody.innerHTML = '';

            if (data.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="13">暂无数据</td></tr>';
                return;
            }

            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                var disabledAttr = item.pdfFileUrl ? '' : 'disabled';

                // 生成附件列按钮（包含查看按钮）
                var attachmentButtons = '';

                // 添加查看按钮到档案附件列
                if (hasViewPermission) {
                    attachmentButtons += '<button class="view-btn" data-id="' + item.id + '" title="查看文件" ' + disabledAttr + '>';
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

                // 如果没有任何按钮权限，显示提示
                if (attachmentButtons === '') {
                    attachmentButtons = '无相关权限';
                }

                // 生成操作列按钮（只保留编辑按钮）
                var actionButtons = '';
                if (hasEditPermission) {
                    actionButtons += '<button class="edit-btn" data-id="' + item.id + '">编辑</button>';
                } else {
                    actionButtons = '无操作权限';
                }

                var row = document.createElement('tr');
                row.innerHTML = '<td style="display: none;">' + item.id + '</td>' +
                    '<td>' + item.id + '</td>' +  // 档案序号
                    '<td>' + (item.fileType || '') + '</td>' +
                    '<td>' + (item.typeCode || '') + '</td>' +
                    '<td>' + (item.documentType || '') + '</td>' +
                    '<td>' + (item.documentCode || '') + '</td>' +
                    '<td>' + (item.documentTitle || '') + '</td>' +
                    '<td>' + (item.placeTime || '') + '</td>' +
                    '<td>' + (item.signatureTime || '') + '</td>' +
                    '<td>' + (item.positionCodeRemarks || '') + '</td>' +
                    '<td>' + (item.documentContent || '') + '</td>' +
                    '<td>' + attachmentButtons + '</td>' +  // 档案附件列包含查看按钮
                    '<td>' + actionButtons + '</td>';
                tableBody.appendChild(row);
            }

            // 绑定查看按钮事件
            var viewButtons = document.querySelectorAll('.view-btn');
            for (var j = 0; j < viewButtons.length; j++) {
                viewButtons[j].onclick = function() {
                    var chemicalFileId = this.getAttribute('data-id');
                    viewFileImages(chemicalFileId);
                };
            }

            // 绑定下载附件按钮事件
            var downloadButtons = document.querySelectorAll('.download-btn');
            for (var k = 0; k < downloadButtons.length; k++) {
                downloadButtons[k].onclick = function() {
                    var remoteFileName = this.getAttribute('data-filename');
                    if (!remoteFileName) {
                        layer.msg('没有可下载的附件', {icon: 2});
                        return;
                    }

                    // 禁用按钮
                    this.disabled = true;

                    // 显示加载提示
                    var loading = layer.load(1, {
                        shade: [0.1,'#fff']
                    });

                    // 创建 XMLHttpRequest 对象
                    var xhr = new XMLHttpRequest();
                    xhr.open('POST', '/chemicalFile/addWatermarkAndDownload', true);
                    xhr.setRequestHeader('token', token);
                    xhr.responseType = 'blob';

                    // 处理响应
                    xhr.onload = function() {
                        layer.close(loading);
                        this.disabled = false;

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
                    }.bind(this);

                    xhr.onerror = function() {
                        layer.close(loading);
                        layer.msg('网络错误，请稍后重试', {icon: 2});
                        this.disabled = false;
                    }.bind(this);

                    var formData = new FormData();
                    formData.append('remoteFileName', remoteFileName);
                    xhr.send(formData);
                };
            }

            // 绑定下载原件按钮事件
            var originalButtons = document.querySelectorAll('.download-original-btn');
            for (var l = 0; l < originalButtons.length; l++) {
                originalButtons[l].onclick = function() {
                    var remoteFileName = this.getAttribute('data-filename');
                    if (!remoteFileName) {
                        layer.msg('没有可下载的原件', {icon: 2});
                        return;
                    }

                    // 禁用按钮
                    this.disabled = true;

                    // 显示加载提示
                    var loading = layer.load(1, {
                        shade: [0.1,'#fff']
                    });

                    var xhr = new XMLHttpRequest();
                    xhr.open('POST', '/chemicalFile/downloadFile', true);
                    xhr.setRequestHeader('token', token);
                    xhr.responseType = 'blob';

                    xhr.onload = function() {
                        layer.close(loading);
                        this.disabled = false;

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
                    }.bind(this);

                    xhr.onerror = function() {
                        layer.close(loading);
                        layer.msg('网络错误，请稍后重试', {icon: 2});
                        this.disabled = false;
                    }.bind(this);

                    var formData = new FormData();
                    formData.append('remoteFileName', remoteFileName);
                    xhr.send(formData);
                };
            }

            // 绑定编辑按钮事件
            var editButtons = document.querySelectorAll('.edit-btn');
            for (var m = 0; m < editButtons.length; m++) {
                editButtons[m].onclick = function() {
                    var id = this.getAttribute('data-id');
                    var form = document.createElement('form');
                    form.action = '/renShiViewJump/chemicalFile/technicalFilEdit';
                    form.method = 'post';

                    var idInput = document.createElement('input');
                    idInput.type = 'hidden';
                    idInput.name = 'id';
                    idInput.value = id;
                    form.appendChild(idInput);

                    var tokenInput = document.createElement('input');
                    tokenInput.type = 'hidden';
                    tokenInput.name = 'token';
                    tokenInput.value = token;
                    form.appendChild(tokenInput);

                    document.body.appendChild(form);
                    form.submit();
                };
            }
        }

        // 获取技术文件列表数据
        function fetchData(page, size, query) {
            var documentCode = document.getElementById('documentCode').value;
            var documentType = document.getElementById('documentType').value;
            // 固定档案类型为"技术文件"
            var fileType = "技术文件";
            var state = document.getElementById('state').value;
            var documentTitle = document.getElementById('documentTitle').value;
            var documentContent = document.getElementById('documentContent').value;
            var placeTime = document.getElementById('placeTime').value;

            var fullQuery = {
                documentCode: documentCode,
                documentType: documentType,
                fileType: fileType,  // 使用固定值
                state: state,
                documentTitle: documentTitle,
                documentContent: documentContent,
                placeTime: placeTime,
                currentPage: page,
                pageSize: size
            };

            console.log('即将请求的数据：', fullQuery);

            $.ajax({
                url: '/chemicalFile/listByPage',
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
                                    console.log('点击分页，当前页:', obj.curr, '每页数量:', obj.limit);
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
        window.onload = function() {
            loadPermissions();
        };

        // 点击查询按钮事件
        document.getElementById('queryButton').onclick = function () {
            currentPage = 1;
            fetchData(currentPage, pageSize, {});
        };

        // 点击新增按钮事件
        document.getElementById('addButton').onclick = function () {
            var form = document.createElement('form');
            form.action = '/renShiViewJump/chemicalFile/technicalFileAdd';
            form.method = 'post';

            var tokenInput = document.createElement('input');
            tokenInput.type = 'hidden';
            tokenInput.name = 'token';
            tokenInput.value = token;
            form.appendChild(tokenInput);

            // 添加档案类型参数，固定为"技术文件"
            var fileTypeInput = document.createElement('input');
            fileTypeInput.type = 'hidden';
            fileTypeInput.name = 'fileType';
            fileTypeInput.value = '技术文件';
            form.appendChild(fileTypeInput);

            document.body.appendChild(form);
            form.submit();
        };
    });
</script>
</body>

</html>