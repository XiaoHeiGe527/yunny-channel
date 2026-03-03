<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>给车开卡</title>
    <#include "../common/head.ftl">
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

        .form-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            margin: 0 auto;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            padding: 10px 20px;
            border-radius: 4px;
        }

        #submitBtn {
            background-color: #007BFF;
            color: #fff;
            border: none;
            cursor: pointer;
        }

        #submitBtn:hover {
            background-color: #0056b3;
        }

        #returnButton {
            margin-bottom: 20px;
        }

        #customerList {
            position: absolute;
            background-color: white;
            border: 1px solid #ccc;
            width: 360px;
            max-height: 200px;
            overflow-y: auto;
            display: none;
        }

        #customerList div {
            padding: 5px;
            cursor: pointer;
        }

        #customerList div:hover {
            background-color: #f0f0f0;
        }
    </style>
</head>

<body>
<div class="layui-inline">
    <button id="returnButton" class="layui-btn layui-btn-radius layui-btn-primary">返回</button>
</div>

<h1> 《${carNumber}》 给车开卡</h1>
<div class="form-container">
    <form id="cardOpeningForm">
        <input type="hidden" id="carNumber" value="${carNumber}">
        <div class="form-group">
            <label for="vehicleCustomersName">客户名称</label>
            <input type="text" id="vehicleCustomersName" placeholder="请选择客户">
            <div id="customerList"></div>
        </div>
        <!-- 移除卡剩余天数，添加卡到期日期 -->
        <div class="form-group">
            <label for="expirationDate">卡到期日期</label>
            <input type="text" id="expirationDate" readonly>
        </div>
        <div class="form-group">
            <label for="openDate">开卡日期</label>
            <input type="text" id="openDate">
        </div>
        <div class="form-group">
            <label for="remarks">备注说明</label>
            <input type="text" id="remarks">
        </div>
        <button type="button" id="submitBtn">提交</button>
    </form>
</div>

<script>
    $(document).ready(function () {
        // 从 FTL 变量中获取 token
        var token = '${Request.token}';

        // 美化开卡日期输入框
        layui.use('laydate', function () {
            var laydate = layui.laydate;

            // 开卡日期渲染
            laydate.render({
                elem: '#openDate',
                type: 'datetime',
                done: function (value, date, endDate) {
                    // 开卡日期变化时，重新计算到期日期
                    calculateExpirationDate();
                }
            });

            // 卡到期日期渲染（只读）
            laydate.render({
                elem: '#expirationDate',
                type: 'datetime',
                readonly: true
            });

            // 设置默认值为当前时间
            var now = new Date();
            var year = now.getFullYear();
            var month = String(now.getMonth() + 1).padStart(2, '0');
            var day = String(now.getDate()).padStart(2, '0');
            var hours = String('00');
            var minutes = String('00');
            var seconds = String('00');
            $('#openDate').val(year + '-' + month + '-' + day +' '+ hours + ':' + minutes + ':' + seconds);

            // 初始化计算到期日期
            calculateExpirationDate();
        });

        // 客户名称输入框点击事件
        $('#vehicleCustomersName').click(function () {
            showCustomerList('');
        });

        // 客户名称输入框输入事件
        $('#vehicleCustomersName').on('input', function () {
            var keyword = $(this).val();
            showCustomerList(keyword);
        });

        // 显示客户列表
        function showCustomerList(keyword) {
            $.ajax({
                url: '/vehicleCustomers/listAll',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({ "name": keyword }),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        var customerList = $('#customerList');
                        customerList.empty();
                        response.data.forEach(function (customer) {
                            var div = $('<div>').text(customer);
                            div.click(function () {
                                $('#vehicleCustomersName').val(customer);
                                customerList.hide();
                            });
                            customerList.append(div);
                        });
                        customerList.show();
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 计算到期日期（开卡日期+15天）
        function calculateExpirationDate() {
            var openDateStr = $('#openDate').val();
            if (!openDateStr) return;

            // 解析开卡日期
            var parts = openDateStr.split(/[- :]/);
            var openDate = new Date(
                parseInt(parts[0]),
                parseInt(parts[1]) - 1,
                parseInt(parts[2]),
                parseInt(parts[3]),
                parseInt(parts[4]),
                parseInt(parts[5])
            );

            // 计算到期日期（+15天）
            var expirationDate = new Date(openDate);
            expirationDate.setDate(expirationDate.getDate() + 15);

            // 格式化日期
            var expYear = expirationDate.getFullYear();
            var expMonth = String(expirationDate.getMonth() + 1).padStart(2, '0');
            var expDay = String(expirationDate.getDate()).padStart(2, '0');
            var expHours = String(expirationDate.getHours()).padStart(2, '0');
            var expMinutes = String(expirationDate.getMinutes()).padStart(2, '0');
            var expSeconds = String(expirationDate.getSeconds()).padStart(2, '0');

            // 设置到期日期
            $('#expirationDate').val(
                expYear + '-' + expMonth + '-' + expDay + ' ' +
                expHours + ':' + expMinutes + ':' + expSeconds
            );
        }

        // 提交按钮点击事件
        $('#submitBtn').click(function () {
            var carNumber = $('#carNumber').val();
            var vehicleCustomersName = $('#vehicleCustomersName').val();
            // 移除daysRemaining，添加expirationDate
            var expirationDate = $('#expirationDate').val();
            var openDate = $('#openDate').val();
            var remarks = $('#remarks').val();

            if (!vehicleCustomersName) {
                alert('请选择客户');
                return;
            }

            var data = {
                "carNumber": carNumber,
                // 移除daysRemaining字段
                "openDate": openDate,
                "expirationDate": expirationDate, // 添加到期日期
                "vehicleCustomersName": vehicleCustomersName,
                "remarks": remarks
            };

            $.ajax({
                url: '/vehiclesOutwardCard/create',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        alert('开卡成功');
                        // 创建一个隐藏的表单并提交 POST 请求
                        var form = $('<form>', {
                            action: '/viewJump/companyVehicles/listByPage',
                            method: 'post'
                        });
                        form.append($('<input>', {
                            type: 'hidden',
                            name: 'token',
                            value: token
                        }));
                        form.appendTo('body').submit();
                    } else {
                        alert('开卡失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        });

        // 点击返回按钮事件
        $('#returnButton').click(function () {
            window.history.back();
        });
    });
</script>
</body>

</html>