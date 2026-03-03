<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>物资计划列表</title>
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

        /* 查询表单样式优化 */
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

        .query-form .layui-form-item {
            margin-bottom: 0;
        }

        .query-form label {
            margin-right: 5px;
        }

        .query-form .layui-input,
        .query-form .layui-select {
            width: 180px;
        }

        .query-form .layui-btn-group {
            margin-left: auto;
        }

        /* 表格样式优化 */
        .layui-table {
            margin-bottom: 15px;
        }

        .status-tag {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }

        .status-1 {
            background-color: #e8f4fd;
            color: #1E90FF;
        }

        .status-2 {
            background-color: #fff7e8;
            color: #FF7D00;
        }

        .status-3 {
            background-color: #f0f9eb;
            color: #00B42A;
        }

        .status-4 {
            background-color: #fee;
            color: #F53F3F;
        }

        /* 明细弹窗表格样式 */
        .detail-dialog-table {
            width: 100%;
            margin-top: 15px;
            border-collapse: collapse;
        }

        .detail-dialog-table th,
        .detail-dialog-table td {
            border: 1px solid #e6e6e6;
            padding: 8px 12px;
            text-align: center;
            font-size: 13px;
        }

        .detail-dialog-table th {
            background-color: #f2f2f2;
            font-weight: 600;
        }

        /* 计划基本信息样式 */
        .plan-base-info {
            line-height: 2;
            font-size: 14px;
        }

        .plan-base-info span {
            display: inline-block;
            width: 120px;
            color: #666;
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
    <div class="layui-container">
        <div class="layui-card">
            <div class="layui-card-body">
                <!-- 查询条件表单 -->
                <form class="query-form" lay-filter="queryForm">
                    <div class="layui-form-item">
                        <label for="planType">计划类型：</label>
                        <select id="planType" name="planType" lay-verify="" class="layui-select">
                            <option value="">全部</option>
                            <!-- 动态加载计划类型选项 -->
                        </select>
                    </div>

                    <div class="layui-form-item">
                        <label for="applyDept">申请部门：</label>
                        <select id="applyDept" name="applyDept" class="layui-select">
                            <option value="">全部</option>
                            <!-- 动态加载部门选项 -->
                        </select>
                    </div>

                    <div class="layui-form-item">
                        <label for="startTime">开始日期：</label>
                        <input type="text" id="startTime" name="startTime" placeholder="请选择开始日期" class="layui-input" readonly>
                    </div>

                    <div class="layui-form-item">
                        <label for="endTime">结束日期：</label>
                        <input type="text" id="endTime" name="endTime" placeholder="请选择结束日期" class="layui-input" readonly>
                    </div>

                    <div class="layui-btn-group">
                        <button type="button" class="layui-btn layui-btn-normal" id="queryButton">
                            <i class="layui-icon layui-icon-search"></i> 查询
                        </button>
                        <button type="button" class="layui-btn layui-btn-primary" id="resetButton">
                            <i class="layui-icon layui-icon-refresh"></i> 重置
                        </button>
                    </div>
                </form>

                <!-- 数据表格 -->
                <table class="layui-table" lay-size="sm">
                    <thead>
                    <tr>
                        <th>计划单号</th>
                        <th>计划类型</th>
                        <th>申请部门</th>
                        <th>申请人</th>
                        <th>计划事由</th>
                        <th>计划日期</th>
                        <th>计划状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="tableBody">
                    <!-- 表格数据将通过JS动态加载 -->
                    </tbody>
                </table>

                <!-- 分页导航 -->
                <div id="pagination" style="text-align: right;"></div>
            </div>
        </div>
    </div>
</div>

<script>
    layui.use(['laydate', 'laypage', 'layer', 'form'], function() {
        var laydate = layui.laydate;
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form;
        var $ = layui.jquery;

        // 从FTL变量获取token
        var token = '${Request.token}';
        var currentPage = 1;
        var pageSize = 10;
        var totalCount = 0;
        // 存储当前页所有计划数据（用于快速获取明细）
        var currentPagePlanData = [];

        // 1. 初始化日期选择器
        laydate.render({
            elem: '#startTime',
            type: 'date',
            format: 'yyyy-MM-dd'
        });
        laydate.render({
            elem: '#endTime',
            type: 'date',
            format: 'yyyy-MM-dd'
        });

        // 2. 页面初始化：先加载下拉框数据，再加载表格数据
        $(document).ready(function() {
            loadPlanTypes(); // 加载计划类型（审批流程接口）
            loadDepartments(); // 加载部门（字典接口）
            setTimeout(loadData, 500); // 延迟加载表格，确保下拉框数据已加载
        });

        // 3. 加载计划类型（调用审批流程配置接口）
        function loadPlanTypes() {
            $.ajax({
                url: '/approvalFlowConfig/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({ flowStatus: '1' }), // 只查有效的
                dataType: 'json',
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200 && response.data.length > 0) {
                        var $planType = $('#planType');
                        $.each(response.data, function(index, item) {
                            $planType.append('<option value="' + item.planType + '">' + item.planType + '</option>');
                        });
                        form.render('select'); // 重新渲染下拉框
                    } else {
                        layer.msg('获取计划类型失败：' + response.message, { icon: 2 });
                    }
                },
                error: function() {
                    layer.msg('计划类型接口请求失败', { icon: 2 });
                }
            });
        }

        // 4. 加载部门（调用字典接口，参考员工档案页面逻辑）
        function loadDepartments() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({ category: "部门" }), // 字典类别为“部门”
                dataType: 'json',
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200 && response.data.length > 0) {
                        var $applyDept = $('#applyDept');
                        $.each(response.data, function(index, item) {
                            $applyDept.append('<option value="' + item.content + '">' + item.content + '</option>');
                        });
                        form.render('select'); // 重新渲染下拉框
                    } else {
                        layer.msg('获取部门数据失败：' + response.message, { icon: 2 });
                    }
                },
                error: function() {
                    layer.msg('部门接口请求失败', { icon: 2 });
                }
            });
        }

        // 5. 加载表格数据（新增：存储当前页数据）
        function loadData() {
            var params = {
                currentPage: currentPage,
                pageSize: pageSize,
                planType: $('#planType').val() || '',
                applyDept: $('#applyDept').val() || '',
                startTime: $('#startTime').val() || '',
                endTime: $('#endTime').val() || ''
            };

            $.ajax({
                url: '/materialPlanMain/listByPage',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(params),
                dataType: 'json',
                headers: { 'token': token },
                beforeSend: function() {
                    layer.load(2); // 加载动画
                },
                success: function(response) {
                    layer.closeAll('loading'); // 关闭加载动画

                    if (response.code === 200) {
                        var data = response.data;
                        totalCount = data.page.totalCount;
                        currentPagePlanData = data.dataList; // 存储当前页计划数据
                        renderTable(data.dataList);
                        renderPagination(data.page);
                    } else {
                        layer.msg('获取数据失败：' + response.message, { icon: 2 });
                    }
                },
                error: function() {
                    layer.closeAll('loading');
                    layer.msg('网络错误，请稍后重试', { icon: 2 });
                }
            });
        }

        // 6. 渲染表格（修改：存储完整计划数据到按钮，添加审批按钮）
        function renderTable(dataList) {
            var tbody = $('#tableBody');
            tbody.empty();

            if (!dataList || dataList.length === 0) {
                tbody.append('<tr><td colspan="8" style="text-align: center;">暂无数据</td></tr>');
                return;
            }

            $.each(dataList, function(index, item) {
                // 格式化计划日期（处理ISO格式时间）
                var planDate = item.planDate
                    ? new Date(item.planDate.replace('T', ' ')).toLocaleString()
                    : '';

                // 计划状态文本映射（参考常见业务逻辑）
                var statusMap = {
                    1: { text: '待审批', cls: 'status-1' },
                    2: { text: '审批中', cls: 'status-2' },
                    3: { text: '已通过', cls: 'status-3' },
                    4: { text: '已驳回', cls: 'status-4' }
                };
                var status = statusMap[item.planStatus] || { text: '未知状态', cls: '' };

                // 操作列：新增审批按钮（与查看并列），存储完整计划数据
                var operationHtml = '<div class="layui-btn-group">' +
                    '<button class="layui-btn layui-btn-xs layui-btn-primary view-detail" ' +
                    'data-planindex="' + index + '">查看</button>' +
                    '<button class="layui-btn layui-btn-xs layui-btn-normal approve-plan" ' +
                    'data-planno="' + item.planNo + '" data-planstatus="' + item.planStatus + '">审批</button>' +
                    '</div>';

                var row = '<tr>' +
                    '<td>' + item.planNo + '</td>' +
                    '<td>' + item.planType + '</td>' +
                    '<td>' + item.applyDept + '</td>' +
                    '<td>' + item.applicantNo + '</td>' +
                    '<td>' + (item.planReason || '') + '</td>' +
                    '<td>' + planDate + '</td>' +
                    '<td><span class="status-tag ' + status.cls + '">' + status.text + '</span></td>' +
                    '<td>' + operationHtml + '</td>' +
                    '</tr>';

                tbody.append(row);
            });

            // 绑定查看详情事件（通过索引获取本地数据）
            $('.view-detail').click(function() {
                var planIndex = $(this).attr('data-planindex');
                var planData = currentPagePlanData[planIndex];
                viewPlanDetail(planData);
            });

            // 绑定审批按钮事件（基础弹窗，后期可扩展）
            $('.approve-plan').click(function() {
                var planNo = $(this).attr('data-planno');
                var planStatus = $(this).attr('data-planstatus');
                handleApprove(planNo, planStatus);
            });
        }

        // 7. 渲染分页
        function renderPagination(pageInfo) {
            laypage.render({
                elem: 'pagination',
                count: totalCount,
                curr: pageInfo.currentPage,
                limit: pageInfo.pageSize,
                limits: [10, 20, 50],
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                jump: function(obj, first) {
                    if (!first) {
                        currentPage = obj.curr;
                        pageSize = obj.limit;
                        loadData();
                    }
                }
            });
        }

        // 8. 查看计划详情（修改：弹窗展示本地明细，隐藏ID）
        function viewPlanDetail(planData) {
            // 格式化计划日期
            var planDate = planData.planDate
                ? new Date(planData.planDate.replace('T', ' ')).toLocaleString()
                : '';
            // 格式化状态文本
            var statusMap = {
                1: '待审批', 2: '审批中', 3: '已通过', 4: '已驳回'
            };
            var planStatusText = statusMap[planData.planStatus] || '未知状态';

            // 生成基本信息HTML
            var baseInfoHtml = '<div class="plan-base-info">' +
                '<p><span>计划单号：</span>' + planData.planNo + '</p>' +
                '<p><span>计划类型：</span>' + planData.planType + '</p>' +
                '<p><span>申请部门：</span>' + planData.applyDept + '</p>' +
                '<p><span>申请人：</span>' + planData.applicantNo + '</p>' +
                '<p><span>计划事由：</span>' + (planData.planReason || '') + '</p>' +
                '<p><span>计划日期：</span>' + planDate + '</p>' +
                '<p><span>计划状态：</span>' + planStatusText + '</p>' +
                '<p><span>备注：</span>' + (planData.remark || '无') + '</p>' +
                '</div>';

            // 生成明细表格HTML（隐藏ID，显示其他字段）
            var detailList = planData.detailList || [];
            var detailTableHtml = '<table class="detail-dialog-table">' +
                '<thead>' +
                '<tr>' +
                '<th>计划单号</th>' +
                '<th>材料名称</th>' +
                '<th>规格</th>' +
                '<th>数量</th>' +
                '<th>单位</th>' +
                '<th>备注</th>' +
                '<th>创建时间</th>' +
                '</tr>' +
                '</thead>' +
                '<tbody>';

            if (detailList.length === 0) {
                detailTableHtml += '<tr><td colspan="7" style="text-align: center;">暂无明细数据</td></tr>';
            } else {
                $.each(detailList, function(index, detail) {
                    // 格式化创建时间
                    var createTime = detail.createTime
                        ? new Date(detail.createTime).toLocaleString()
                        : '';
                    detailTableHtml += '<tr>' +
                        '<td>' + detail.planNo + '</td>' +
                        '<td>' + detail.materialName + '</td>' +
                        '<td>' + (detail.specification || '') + '</td>' +
                        '<td>' + detail.quantity + '</td>' +
                        '<td>' + detail.unit + '</td>' +
                        '<td>' + (detail.remark || '') + '</td>' +
                        '<td>' + createTime + '</td>' +
                        '</tr>';
                });
            }
            detailTableHtml += '</tbody></table>';

            // 弹窗展示（本地数据，无需再请求接口）
            layer.open({
                type: 1,
                title: '物资计划详情 - ' + planData.planNo,
                content: baseInfoHtml + detailTableHtml,
                area: ['90%', '80%'],
                maxmin: true,
                shade: 0.3,
                success: function(layero, index) {
                    // 弹窗样式微调
                    layero.find('.layui-layer-content').css('padding', '20px');
                }
            });
        }

        // 9. 审批按钮事件（基础弹窗，后期可完善逻辑）
        function handleApprove(planNo, planStatus) {
            // 状态判断示例（后期可扩展：只有待审批/审批中可操作）
            var statusTips = {
                1: '待审批', 2: '审批中', 3: '已通过（不可审批）', 4: '已驳回（不可审批）'
            };
            var statusTip = statusTips[planStatus] || '未知状态（不可审批）';

            // 基础弹窗（后期可替换为审批表单）
            layer.open({
                type: 1,
                title: '审批物资计划 - ' + planNo,
                content: '<div style="padding: 30px; line-height: 2;">' +
                    '<p>计划单号：' + planNo + '</p>' +
                    '<p>当前状态：' + statusTip + '</p>' +
                    '<p style="color: #666; margin-top: 15px;">后期可在此处添加审批表单（同意/驳回+意见）</p>' +
                    '</div>',
                area: ['500px', '300px'],
                shade: 0.3,
                btn: ['暂存', '取消'],
                btn1: function(index, layero) {
                    layer.msg('审批操作暂存（后期完善逻辑）', { icon: 1 });
                    layer.close(index);
                }
            });
        }

        // 10. 查询按钮事件
        $('#queryButton').click(function() {
            currentPage = 1; // 重置为第一页
            loadData();
        });

        // 11. 重置按钮事件
        $('#resetButton').click(function() {
            $('#planType').val('');
            $('#applyDept').val('');
            $('#startTime').val('');
            $('#endTime').val('');
            form.render('select'); // 重新渲染下拉框
            currentPage = 1;
            loadData();
        });
    });
</script>

</html>