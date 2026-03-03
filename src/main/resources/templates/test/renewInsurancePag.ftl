<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>保险续签</title>
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
    </style>
</head>

<body>
<div class="layui-inline">
    <button id="returnButton" class="layui-btn layui-btn-radius layui-btn-primary">返回</button>
</div>

<h1> 《${Request.carNumber}》 保险续签</h1>
<div class="form-container">
    <form id="renewForm">
        <input type="hidden" id="id" value="${id}">
        <div class="form-group">
            <label for="renewalDate">续签日期：</label>
            <input type="text" id="renewalDate" placeholder="YYYY-MM-DD HH:MM:SS">
        </div>
        <div class="form-group">
            <label for="insured">投保人姓名：</label>
            <input type="text" id="insured">
        </div>
        <div class="form-group">
            <label for="renewalYears">续保年数：</label>
            <select id="renewalYears">
                <option value="1" selected>1 年</option>
                <option value="2">2 年</option>
                <option value="3">3 年</option>
                <option value="4">4 年</option>
                <option value="5">5 年</option>
                <option value="6">6 年</option>
                <option value="7">7 年</option>
                <option value="8">8 年</option>
                <option value="9">9 年</option>
                <option value="10">10 年</option>
            </select>
        </div>
        <button type="button" id="submitBtn">提交</button>
    </form>
</div>

<script>
    $(document).ready(function () {
        // 从 FTL 变量中获取 token
        var token = '${Request.token}';

        // 美化续签日期输入框
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#renewalDate',
                type: 'datetime'
            });
        });

        // 提交按钮点击事件
        $('#submitBtn').click(function () {
            var id = $('#id').val();
            var renewalDate = $('#renewalDate').val();
            var insured = $('#insured').val();
            var renewalYears = $('#renewalYears').val();

            if (!renewalDate || !insured) {
                alert('请填写续签日期和投保人姓名');
                return;
            }

            var data = {
                "id": parseInt(id),
                "renewalDate": renewalDate,
                "renewalYears": parseInt(renewalYears),
                "insured": insured
            };

            $.ajax({
                url: '/vehicleInsurance/renewInsurance',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        alert('续签成功');
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
                        alert('续签失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
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
    });
</script>
</body>

</html>
