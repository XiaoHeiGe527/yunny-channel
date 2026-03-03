<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>系统日志查询</title>
    <#include "../common/head.ftl">
    <style>
        /* 核心：调整内容区域边距，避开标题栏和侧边栏 */
        .main-container {
            margin-top: 50px; /* 与顶部标题栏高度一致，避开标题栏 */
            margin-left: 200px; /* 与侧边导航宽度一致，避开侧边栏 */
            padding: 15px; /* 内容内边距，避免紧贴边缘 */
        }

        /* 内容弹窗样式 */
        .content-popup {
            max-width: 800px;
            max-height: 600px;
            overflow-y: auto;
            padding: 20px;
            line-height: 1.6;
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

        .query-form .layui-select,
        .query-form .layui-input {
            width: 160px;
        }

        .query-form button {
            margin-left: 5px;
        }
    </style>
</head>

<body>
<#include "../common/inHeader.ftl"> <!-- 标题栏 -->
<#include "../common/navigation.ftl"> <!-- 侧边导航栏（新增） -->

<!-- 主内容容器：通过边距避开标题栏和侧边栏 -->
<div class="main-container">
    <div class="layui-card">
        <div class="layui-card-body">
            <!-- 查询条件表单 -->
            <div class="query-form">
                <label for="operationType">操作类型：</label>
                <select class="layui-select" id="operationType" style="width: 160px;">
                    <option value="">请选择操作类型</option>
                    <option value="1">增加</option>
                    <option value="2">修改</option>
                    <option value="3">删除</option>
                    <option value="4">下载</option>
                </select>

                <label for="url">接口名称：</label>
                <input type="text" id="url" placeholder="请输入接口名称" class="layui-input" style="width: 160px;">

                <button id="queryButton" class="layui-btn layui-btn-radius layui-btn-normal">查询</button>
                <button id="queryAllButton" class="layui-btn layui-bg-orange">重置查询</button>
            </div>

            <!-- 数据表格 -->
            <table id="logTable" class="layui-table">
                <thead>
                <tr>
                    <th>操作类型</th>
                    <th>路径</th>
                    <th>操作人</th>
                    <th>IP</th>
                    <th>操作时间</th>
                    <th>备注</th>
                    <th>操作内容</th>
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

        // 操作类型映射对象
        var operationTypeMap = {
            '1': '增加',
            '2': '修改',
            '3': '删除',
            '4': '下载'
        };

        // 初始化查询
        fetchData(currentPage, pageSize);

        function fetchData(page, size) {
            var params = {
                operationType: $('#operationType').val() || null,
                url: $('#url').val().trim() || null,
                currentPage: page,
                pageSize: size
            };

            $.ajax({
                url: '/systemLog/listByPage',
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
            var tbody = $('#logTable tbody');
            tbody.empty();

            $.each(dataList, function (index, item) {
                // 创建查看详情按钮
                var contentBtn = '<button class="layui-btn layui-btn-xs layui-btn-primary" ' +
                    'onclick="showContent(\'' + encodeURIComponent(item.content) + '\')">查看详情</button>';

                var row = '<tr>' +
                    '<td>' + operationTypeMap[item.operationType] + '</td>' +
                    '<td>' + item.url + '</td>' +
                    '<td>' + item.operatorName + '</td>' +
                    '<td>' + item.ip + '</td>' +
                    '<td>' + item.createTime + '</td>' +
                    '<td>' + item.remarks + '</td>' +
                    '<td>' + contentBtn + '</td>' +
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
            $('#operationType').val('');
            $('#url').val('');
            currentPage = 1;
            fetchData(currentPage, pageSize);
        });

        // 内容弹窗函数
        window.showContent = function (content) {
            layer.open({
                type: 1,
                title: '操作详情',
                content: '<div class="content-popup">' + decodeURIComponent(content) + '</div>',
                area: ['80%', '80%'],
                shade: 0.3
            });
        };
    });
</script>
</body>

</html>