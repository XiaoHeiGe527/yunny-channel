<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>字典管理</title>
    <#include "../common/head.ftl">
    <style>
        /* 核心：内容容器边距，避开标题栏和侧边栏 */
        .main-container {
            margin-top: 50px; /* 与顶部标题栏高度一致 */
            margin-left: 200px; /* 与侧边导航宽度一致 */
            padding: 15px; /* 内容内边距 */
        }

        /* 查询表单样式优化 */
        .query-form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            align-items: center;
            margin-bottom: 20px;
            background-color: #ffffff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .query-form label {
            margin-right: 5px;
        }

        .query-form .layui-input {
            width: 160px;
        }

        .query-form button {
            margin-left: 5px;
        }

        /* 顶部按钮区域 */
        .toolbar {
            margin-bottom: 15px;
        }
    </style>
</head>

<body>
<#include "../common/inHeader.ftl"> <!-- 标题栏 -->
<#include "../common/navigation.ftl"> <!-- 新增：侧边导航栏 -->

<!-- 主内容容器：通过边距避开标题栏和侧边栏 -->
<div class="main-container">
    <div class="layui-card">
        <div class="layui-card-body">
            <!-- 顶部工具栏 -->
            <div class="toolbar">
                <button id="addButton" class="layui-btn layui-btn-radius layui-btn-normal">
                    <i class="layui-icon layui-icon-add-circle"></i> 新增字典
                </button>
            </div>

            <!-- 查询条件表单 -->
            <div class="query-form">
                <label for="category">类型名称：</label>
                <input type="text" id="category" placeholder="请输入类型名称" class="layui-input">

                <label for="explain">字典内容：</label>
                <input type="text" id="explain" placeholder="请输入字典内容" class="layui-input">

                <button id="queryButton" class="layui-btn layui-btn-radius layui-btn-normal">查询</button>
                <button id="queryAllButton" class="layui-btn layui-bg-orange">重置查询</button>
            </div>

            <!-- 数据表格 -->
            <table id="dictionaryTable" class="layui-table">
                <thead>
                <tr>
                    <th>类型名称</th>
                    <th>编号值</th>
                    <th>字典内容</th>
                    <th>备注</th>
                    <th>序号</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>

            <!-- 分页导航 -->
            <div id="pageNav"></div>
        </div>
    </div>
</div>

<script>
    layui.use(['table', 'laypage', 'layer'], function () {
        var table = layui.table;
        var laypage = layui.laypage;
        var layer = layui.layer;

        // 从 FTL 变量中获取 token
        var token = '${Request.token}';
        var currentPage = 1;
        var pageSize = 10;

        // 初始化查询
        fetchData(currentPage, pageSize);

        function fetchData(page, size) {
            var params = {
                category: $('#category').val().trim() || null,
                explain: $('#explain').val().trim() || null,
                currentPage: page,
                pageSize: size
            };

            $.ajax({
                url: '/dictionary/listByPage',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(params),
                dataType: 'json',
                headers: { 'token': token },
                success: function (response) {
                    if (response.code === 200) {
                        var data = response.data;
                        renderTable(data.dataList);
                        renderPagination(data.page);
                    } else {
                        layer.msg('查询失败：' + response.message, { icon: 5 });
                    }
                },
                error: function (error) {
                    console.error('请求出错:', error);
                    layer.msg('网络请求失败', { icon: 5 });
                }
            });
        }

        function renderTable(dataList) {
            var tbody = $('#dictionaryTable tbody');
            tbody.empty();

            $.each(dataList, function (index, item) {
                // 编辑按钮 - 点击时创建隐藏表单提交ID
                var operationHtml = '<div class="layui-btn-group">' +
                    '<button class="layui-btn layui-btn-xs layui-btn-primary" ' +
                    'onclick="editDictionary(' + item.id + ')">编辑</button>' +
                    '</div>';

                var row = '<tr>' +
                    '<td>' + item.category + '</td>' +
                    '<td>' + item.codeNum + '</td>' +
                    '<td>' + item.content + '</td>' +
                    '<td>' + (item.remarks || '') + '</td>' +
                    '<td>' + (item.serialNumber || '') + '</td>' +
                    '<td>' + operationHtml + '</td>' +
                    '</tr>';

                tbody.append(row);
            });
        }

        function renderPagination(pager) {
            laypage.render({
                elem: 'pageNav',
                count: pager.totalCount,
                curr: pager.currentPage,
                limit: pager.pageSize,
                limits: [10, 20, 50],
                layout: ['count', 'prev', 'page', 'next', 'limit'],
                jump: function (obj, first) {
                    if (!first) {
                        currentPage = obj.curr;
                        pageSize = obj.limit;
                        fetchData(currentPage, pageSize);
                    }
                }
            });
        }

        // 查询按钮事件
        $('#queryButton').click(function () {
            currentPage = 1;
            fetchData(currentPage, pageSize);
        });

        // 重置查询按钮事件
        $('#queryAllButton').click(function () {
            $('#category').val('');
            $('#explain').val('');
            currentPage = 1;
            fetchData(currentPage, pageSize);
        });

        // 新增按钮事件
        $('#addButton').click(function () {
            var form = $('<form>', {
                action: '/viewJump/dictionary/add',
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

    // 编辑字典函数 - 创建隐藏表单提交ID
    function editDictionary(id) {
        var form = $('<form>', {
            action: '/viewJump/dictionary/Edit',
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
            value: '${Request.token}'
        }));
        form.appendTo('body').submit();
    }
</script>
</body>

</html>