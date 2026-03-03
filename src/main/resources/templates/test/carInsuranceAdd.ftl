<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>批量创建车辆保险信息</title>
    <#include "../common/head.ftl">
    <!-- 引入 Layui CSS -->
    <link rel="stylesheet" href="https://cdn.staticfile.org/layui/2.8.11/css/layui.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f9;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .layui-btn {
            transition: background-color 0.3s ease;
        }

        .layui-btn:hover {
            opacity: 0.9;
        }

        .insurance-forms-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .insurance-form {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            margin-bottom: 20px;
            border-bottom: 1px solid #e6e6e6;
            padding-bottom: 20px;
        }

        .insurance-form:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }

        .form-group {
            margin: 10px;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            min-width: 200px;
        }

        .form-group label {
            font-weight: 600;
            margin-bottom: 5px;
            color: #555;
        }

        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-group input.readonly {
            background-color: #f2f2f2;
        }

        .button-group {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
    </style>
</head>

<body>
<h1>批量创建车辆保险信息</h1>

<div class="layui-inline">
    <button id="returnButton" class="layui-btn layui-btn-radius layui-btn-primary">返回</button>
</div>

<div class="insurance-forms-container" id="insurance-forms">
    <div class="insurance-form">
        <div class="form-group">
            <label for="underwritingDate">承保日期：</label>
            <input type="text" class="underwritingDate" placeholder="YYYY-MM-DD HH:MM:SS">
        </div>
        <div class="form-group">
            <label for="insured">投保人：</label>
            <input type="text" class="insured">
        </div>
        <div class="form-group">
            <label for="carNumber">车牌号：</label>
            <input type="text" class="carNumber">
        </div>
        <div class="form-group">
            <label for="carType">车型：</label>
            <!-- 使用 Layui 的 select 样式 -->
            <select class="carType layui-select" id="carType">
                <option value="">请选择车型</option>
                <option value="1" data-cn="特三">特三</option>
                <option value="2" data-cn="丰田">丰田</option>
                <option value="3" data-cn="希尔">希尔</option>
                <option value="4" data-cn="五菱">五菱</option>
                <option value="5" data-cn="春星">春星</option>
                <option value="6" data-cn="货">货</option>
                <option value="7" data-cn="长城货">长城货</option>
                <option value="8" data-cn="长城">长城</option>
                <option value="9" data-cn="红宇">红宇</option>
                <option value="10" data-cn="鸿星达">鸿星达</option>
                <option value="11" data-cn="哈弗">哈弗</option>
                <option value="12" data-cn="霸道">霸道</option>
                <option value="13" data-cn="雷克萨斯">雷克萨斯</option>
                <option value="14" data-cn="雪佛兰">雪佛兰</option>
                <option value="15" data-cn="奔驰">奔驰</option>
                <option value="16" data-cn="江特">江特</option>
                <option value="17" data-cn="威尔法">威尔法</option>
                <option value="18" data-cn="奥迪">奥迪</option>
                <option value="19" data-cn="比亚迪">比亚迪</option>
                <option value="20" data-cn="酷路泽">酷路泽</option>
                <option value="21" data-cn="普拉多">普拉多</option>
            </select>
        </div>
        <div class="form-group">
            <label for="compulsoryInsurance">交强险信息（金额）：</label>
            <input type="text" class="compulsoryInsurance amount-input">
        </div>
        <div class="form-group">
            <label for="vehicleAndVesselTax">车船税信息（金额）：</label>
            <input type="text" class="vehicleAndVesselTax amount-input">
        </div>
        <div class="form-group">
            <label for="businessTax">商业税信息（金额）：</label>
            <input type="text" class="businessTax amount-input">
        </div>
        <div class="form-group">
            <label for="nonMotorInsurance">非车险信息（金额）：</label>
            <input type="text" class="nonMotorInsurance amount-input">
        </div>
        <div class="form-group">
            <label for="total">总计金额：</label>
            <input type="text" class="total" readonly>
        </div>
        <div class="form-group">
            <label for="remarks">备注：</label>
            <input type="text" class="remarks" placeholder="请输入备注信息" maxlength="50">
        </div>
    </div>
</div>

<div class="button-group">
    <button class="layui-btn" id="add-btn">
        <i class="layui-icon">&#xe608;</i> 添加
    </button>
    <button id="remove-btn" class="layui-btn layui-btn-danger">删除</button>
    <button id="submit-btn" class="layui-btn layui-btn-radius layui-btn-normal">提交</button>
</div>

<script src="https://cdn.staticfile.org/jquery/3.7.1/jquery.min.js"></script>
<!-- 引入 Layui JS -->
<script src="https://cdn.staticfile.org/layui/2.8.11/layui.min.js"></script>
<script>
    $(document).ready(function () {
        // 从 FTL 变量中获取 token
        var token = '${Request.token}';

        // 克隆初始表单用于添加新项
        var template = $('.insurance-form').first().clone();

        // 提前加载 Layui 模块
        layui.use(['laydate', 'form'], function () {
            var laydate = layui.laydate;
            var form = layui.form;

            // 计算总金额的函数
            function calculateTotal(form) {
                var compulsoryInsurance = parseFloat(form.find('.compulsoryInsurance').val()) || 0;
                var vehicleAndVesselTax = parseFloat(form.find('.vehicleAndVesselTax').val()) || 0;
                var businessTax = parseFloat(form.find('.businessTax').val()) || 0;
                var nonMotorInsurance = parseFloat(form.find('.nonMotorInsurance').val()) || 0;
                var total = compulsoryInsurance + vehicleAndVesselTax + businessTax + nonMotorInsurance;
                form.find('.total').val(total.toFixed(2));
            }

            // 为金额输入框添加事件监听器
            $(document).on('input', '.amount-input', function () {
                var value = $(this).val();
                // 去除非数字和非小数点字符
                value = value.replace(/[^0-9.]/g, '');
                // 处理多个小数点的情况
                var parts = value.split('.');
                if (parts.length > 2) {
                    value = parts[0] + '.' + parts.slice(1).join('');
                }
                // 处理小数点后超过两位的情况
                parts = value.split('.');
                if (parts.length === 2 && parts[1].length > 2) {
                    parts[1] = parts[1].slice(0, 2);
                    value = parts[0] + '.' + parts[1];
                }
                $(this).val(value);
                calculateTotal($(this).closest('.insurance-form'));
            });

            // 添加新表单
            $('#add-btn').click(function () {
                var newForm = template.clone();
                newForm.find('.amount-input').val('');
                newForm.find('.total').val('');
                $('#insurance-forms').append(newForm);

                // 对新添加表单的承保日期输入框初始化 laydate 组件
                laydate.render({
                    elem: newForm.find('.underwritingDate')[0],
                    type: 'datetime'
                });

                // 对新添加表单的下拉单初始化 layui 表单组件
                form.render('select');
            });

            // 删除最后一个表单
            $('#remove-btn').click(function () {
                if ($('.insurance-form').length > 1) {
                    $('.insurance-form').last().remove();
                }
            });

            // 提交数据
            $('#submit-btn').click(function () {
                // 弹出确认提示框
                var isConfirmed = confirm('你是否要新增本次数据？');
                if (isConfirmed) {
                    var data = [];
                    var hasEmptyCarType = false;
                    $('.insurance-form').each(function () {
                        var selectedOption = $(this).find('.carType option:selected');
                        var carType = selectedOption.val();
                        if (carType === '') {
                            hasEmptyCarType = true;
                            return false; // 跳出 each 循环
                        }
                        var formData = {
                            underwritingDate: $(this).find('.underwritingDate').val(),
                            insured: $(this).find('.insured').val(),
                            carNumber: $(this).find('.carNumber').val(),
                            carType: carType,
                            carTypeCn: selectedOption.data('cn'),
                            compulsoryInsurance: $(this).find('.compulsoryInsurance').val(),
                            vehicleAndVesselTax: $(this).find('.vehicleAndVesselTax').val(),
                            businessTax: $(this).find('.businessTax').val(),
                            nonMotorInsurance: $(this).find('.nonMotorInsurance').val(),
                            total: $(this).find('.total').val(),
                            remarks: $(this).find('.remarks').val()
                        };
                        data.push(formData);
                    });

                    if (hasEmptyCarType) {
                        alert('请为每个表单选择车型后再提交！');
                        return;
                    }

                    $.ajax({
                        url: '/vehicleInsurance/batchInsert',
                        type: 'POST',
                        contentType: 'application/json; charset=utf-8',
                        data: JSON.stringify(data),
                        dataType: 'json',
                        headers: {
                            'token': token
                        },
                        success: function (response) {
                            if (response.code === 200) {
                                alert('数据提交成功');
                                // 创建一个隐藏的表单并提交 POST 请求
                                var form = $('<form>', {
                                    action: '/viewJump/vehicleInsurance/query',
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
                        error: function () {
                            alert('请求发生错误，请检查网络或接口地址。');
                        }
                    });
                }
            });

            // 点击返回列表事件
            $('#returnButton').click(function () {
                // 创建一个隐藏的表单并提交 POST 请求
                var form = $('<form>', {
                    action: '/viewJump/vehicleInsurance/query',
                    method: 'post'
                });
                form.append($('<input>', {
                    type: 'hidden',
                    name: 'token',
                    value: token
                }));
                form.appendTo('body').submit();
            });

            // 初始化页面已有的承保日期输入框的 laydate 组件
            laydate.render({
                elem: $('.underwritingDate')[0],
                type: 'datetime'
            });

            // 初始化页面已有的下拉单的 layui 表单组件
            form.render('select');
        });
    });
</script>
</body>

</html>
