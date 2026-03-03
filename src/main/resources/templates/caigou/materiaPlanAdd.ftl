<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增物资计划</title>
    <#include "../common/head.ftl">
    <style>
        .layui-form-label {
            width: 120px;
        }
        .layui-input-block {
            margin-left: 150px;
        }
        /* 加宽计划事由输入框 */
        #planReason {
            width: 600px;
        }
        /* 明细表格样式 */
        .detail-table {
            width: 100%;
            margin: 15px 0;
            border-collapse: collapse;
        }
        .detail-table th, .detail-table td {
            border: 1px solid #e6e6e6;
            padding: 8px 10px;
            text-align: center;
        }
        .detail-table th {
            background-color: #f2f2f2;
            font-weight: 600;
        }
        .detail-table .layui-input {
            width: 100%;
            margin: 0;
        }
        .add-btn {
            margin-left: 150px;
            margin-bottom: 10px;
        }
    </style>
</head>

<body>
<#--<#include "../common/inHeader.ftl">-->
<div class="layui-container">
    <div class="layui-row layui-col-space10">
        <div class="layui-col-md10 layui-col-md-offset1">
            <div class="layui-card">
                <div class="layui-card-header">
                    <h2 class="layui-h2">新增物资计划</h2>
                </div>
                <div class="layui-card-body">
                    <form id="materialPlanForm" class="layui-form">
                        <!-- 隐藏用户号输入框 -->
                        <input type="hidden" id="userNo" value="${Request.userNo}">
                        <input type="hidden" id="token" value="${Request.token}">

                        <!-- 基础信息区域 -->
                        <div class="layui-form-item">
                            <label class="layui-form-label">计划类型</label>
                            <div class="layui-input-block">
                                <select id="planType" name="planType" lay-verify="required" class="layui-select">
                                    <option value="">请选择计划类型</option>
                                </select>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">申请部门</label>
                            <div class="layui-input-block">
                                <input type="text" id="applyDept" name="applyDept" readonly
                                       placeholder="加载中..." class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">计划事由</label>
                            <div class="layui-input-block">
                                <input type="text" id="planReason" name="planReason" lay-verify="required"
                                       placeholder="请输入计划事由" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">计划备注</label>
                            <div class="layui-input-block">
                                <input type="text" id="remark" name="remark"
                                       placeholder="请输入计划备注（可选）" class="layui-input">
                            </div>
                        </div>

                        <!-- 明细列表区域：新增“备注（可选）”列 -->
                        <div class="layui-form-item">
                            <label class="layui-form-label">物资明细</label>
                            <div class="layui-input-block">
                                <button type="button" class="layui-btn layui-btn-normal add-btn" id="addDetail">
                                    <i class="layui-icon layui-icon-add"></i> 添加明细
                                </button>
                                <table class="detail-table" id="detailTable">
                                    <thead>
                                    <tr>
                                        <th>材料名称 <span class="layui-text-danger">*</span></th>
                                        <th>规格（可选）</th>
                                        <th>数量 <span class="layui-text-danger">*</span></th>
                                        <th>单位 <span class="layui-text-danger">*</span></th>
                                        <th>备注（可选）</th> <!-- 新增备注表头 -->
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- 初始明细行：新增备注输入框 -->
                                    <tr class="detail-row">
                                        <td>
                                            <input type="text" name="materialName" lay-verify="required"
                                                   placeholder="请输入材料名称" class="layui-input">
                                        </td>
                                        <td>
                                            <input type="text" name="specification"
                                                   placeholder="请输入规格（可选）" class="layui-input">
                                        </td>
                                        <td>
                                            <input type="text" name="quantity" lay-verify="required|positiveInt"
                                                   placeholder="请输入数量" class="layui-input">
                                        </td>
                                        <td>
                                            <input type="text" name="unit" lay-verify="required"
                                                   placeholder="请输入单位" class="layui-input">
                                        </td>
                                        <td> <!-- 新增备注列单元格 -->
                                            <input type="text" name="detailRemark"
                                                   placeholder="请输入备注（可选）" class="layui-input">
                                        </td>
                                        <td>
                                            <button type="button" class="layui-btn layui-btn-xs layui-btn-danger delete-detail">
                                                删除
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- 提交按钮区域 -->
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="submitPlan">确认提交</button>
                                <button type="button" class="layui-btn layui-btn-primary" onclick="goBack()">返回</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    layui.use(['form', 'layer', 'jquery'], function() {
        var form = layui.form;
        var layer = layui.layer;
        var $ = layui.jquery;
        var token = $('#token').val();
        var userNo = $('#userNo').val();

        // 1. 页面初始化：加载计划类型和部门信息
        initPageData();

        // 2. 自定义表单验证规则
        form.verify({
            // 数量：正整数校验
            positiveInt: function(value) {
                var reg = /^[1-9]\d*$/;
                if (!value) {
                    return '请输入数量';
                }
                if (!reg.test(value)) {
                    return '数量必须是正整数';
                }
            }
        });

        // 3. 动态添加明细行：克隆时自动包含新增的备注输入框
        $('#addDetail').click(function() {
            var newRow = $('.detail-row').eq(0).clone(true);
            // 清空克隆行的所有输入值（含备注）
            newRow.find('input').val('');
            // 添加到表格
            $('#detailTable tbody').append(newRow);
        });

        // 4. 动态删除明细行（至少保留一行）
        $(document).on('click', '.delete-detail', function() {
            var rowCount = $('.detail-row').length;
            if (rowCount <= 1) {
                layer.msg('至少保留一行明细', {icon: 2});
                return;
            }
            $(this).closest('.detail-row').remove();
        });

        // 5. 表单提交处理：新增收集备注字段（detail.remark）
        form.on('submit(submitPlan)', function(data) {
            // 5.1 收集明细列表数据：新增remark字段
            var detailList = [];
            $('.detail-row').each(function(index, row) {
                var $row = $(row);
                var detail = {
                    materialName: $row.find('input[name="materialName"]').val().trim(),
                    specification: $row.find('input[name="specification"]').val().trim() || undefined,
                    quantity: parseInt($row.find('input[name="quantity"]').val().trim()),
                    unit: $row.find('input[name="unit"]').val().trim(),
                    remark: $row.find('input[name="detailRemark"]').val().trim() || undefined // 收集明细备注
                };
                // 移除空的可选字段（规格、备注），避免传递空字符串
                if (!detail.specification) delete detail.specification;
                if (!detail.remark) delete detail.remark; // 空备注不传递
                detailList.push(detail);
            });

            // 5.2 构建提交参数（与接口一致）
            var submitData = {
                planType: $('#planType').val(),
                applyDept: $('#applyDept').val(),
                planReason: $('#planReason').val().trim(),
                remark: $('#remark').val().trim() || undefined,
                detailList: detailList
            };
            // 移除空的计划备注字段
            if (!submitData.remark) delete submitData.remark;

            // 5.3 调用提交接口
            $.ajax({
                url: '/materialPlanMain/submit',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(submitData),
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200) {
                        layer.msg(response.data, {icon: 1, time: 2000}, function() {
                            // 提交成功后跳转至物资计划列表页
                            var form = $('<form>', {
                                action: '/materialPlanViewJump/materialPlanMain/materialPlanMainListPage',
                                method: 'post'
                            });
                            form.append($('<input>', {
                                type: 'hidden',
                                name: 'token',
                                value: token
                            }));
                            form.appendTo('body').submit();
                        });
                    } else {
                        layer.msg('提交失败：' + response.message, {icon: 2});
                    }
                },
                error: function() {
                    layer.msg('网络错误，请稍后重试', {icon: 2});
                }
            });

            return false;
        });

        // 6. 页面初始化函数：加载计划类型和部门
        function initPageData() {
            // 6.1 加载计划类型（审批流程配置）
            $.ajax({
                url: '/approvalFlowConfig/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({flowStatus: '1'}),
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200 && response.data.length > 0) {
                        var $planType = $('#planType');
                        $.each(response.data, function(index, item) {
                            $planType.append('<option value="' + item.planType + '">' + item.planType + '</option>');
                        });
                        form.render('select'); // 重新渲染下拉框
                    } else {
                        layer.msg('未获取到计划类型数据', {icon: 2});
                    }
                },
                error: function() {
                    layer.msg('计划类型接口请求失败', {icon: 2});
                }
            });

            // 6.2 加载申请部门
            $.ajax({
                url: '/systemUserDepartment/getUserDepartment',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({userNo: userNo, isMainDept: 1}),
                headers: { 'token': token },
                success: function(response) {
                    if (response.code === 200 && response.data) {
                        $('#applyDept').val(response.data.deptName);
                    } else {
                        $('#applyDept').val('未获取到部门信息');
                        layer.msg('未获取到部门数据', {icon: 2});
                    }
                },
                error: function() {
                    $('#applyDept').val('部门接口请求失败');
                    layer.msg('部门接口请求失败', {icon: 2});
                }
            });
        }
    });

    // 返回上一页函数
    function goBack() {
        window.history.back();
    }
</script>
</body>
</html>