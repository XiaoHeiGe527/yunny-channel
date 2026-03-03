<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>车辆保险信息分页列表</title>
    <#include "../common/head.ftl">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f9;
            padding: 20px;
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

        .amount-display {
            font-size: 16px;
            font-weight: 600;
            color: #333;
            background-color: #f9f9f9;
            border: 1px solid #e0e0e0;
            padding: 8px 12px;
            border-radius: 4px;
            margin-left: auto;
        }

        #insuranceReminderLink {
            color: #ff9800; /* 这里使用了橙色，你可以根据需要修改颜色值 */
        }

    </style>
</head>

<body>
<#include "../common/inHeader.ftl">
<div class="layui-card">
    <div class="layui-card-body">
        <div class="query-form">
            <label for="underwritingDateStart">承保日期开始时间：</label>
            <input type="text" id="underwritingDateStart" placeholder="YYYY-MM-DD HH:MM:SS">
            <label for="underwritingDateEnd">承保日期结束时间：</label>
            <input type="text" id="underwritingDateEnd" placeholder="YYYY-MM-DD HH:MM:SS">
            <label for="insured">投保人：</label>
            <input type="text" id="insured" placeholder="投保人姓名">
            <label for="carNumber">车牌号：</label>
            <input type="text" id="carNumber" placeholder="车牌号">
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


            <div class="layui-inline">
                <button id="queryButton" class="layui-btn layui-btn-radius layui-btn-normal" >查询临期</button>
                <button id="queryAllButton" class="layui-btn layui-bg-orange">查询所有</button>
                <span class="amount-display">当前结果集共计金额： <span id="sum-value">0</span>&nbsp;&nbsp;元</span>
            </div>
        </div>
        <div class="layui-btn-group">
            <button id="addButton" class="layui-btn">
                <i class="layui-icon">&#xe608;</i> 添加
            </button>
        </div>
        <table id="insuranceTable">
            <thead>
            <tr>
                <th>ID</th>
                <th>承保日期</th>
                <th>投保人</th>
                <th>车牌号</th>
                <th>车型</th>
                <th>交强险</th>
                <th>车船税</th>
                <th>商业税</th>
                <th>非车险</th>
                <th>共计</th>
                <th>修改时间</th>
                <th>承保日期提醒时间</th>
                <th>备注</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
        <div id="pageNav"></div>
    </div>
</div>

<script>
    layui.use(['table', 'laypage', 'laydate'], function () {
        var table = layui.table;
        var laypage = layui.laypage;
        var laydate = layui.laydate;

        // 从 FTL 变量中获取 token
        var token = '${Request.token}';
        var userNo = '${Request.userNo}';
        var permissions = [];

        // 当前页码
        var currentPage = 1;
        // 每页数量
        var pageSize = 20;

        // 美化时间组件
        laydate.render({
            elem: '#underwritingDateStart',
            type: 'datetime'
        });
        laydate.render({
            elem: '#underwritingDateEnd',
            type: 'datetime'
        });

        // 获取用户权限
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
                    // 根据权限显示或隐藏添加按钮
                    if (!permissions.includes('/viewJump/vehicleInsurance/add')) {
                        $('#addButton').hide();
                    }
                } else {
                    alert('获取用户权限失败：' + response.message);
                }
            },
            error: function () {
                alert('请求发生错误，请检查网络或接口地址。');
            }
        });

        function renderTable(data) {
            var tableBody = $('#insuranceTable tbody');
            tableBody.empty();
            data.forEach(function (item) {
                var row = '<tr>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.underwritingDate + '</td>' +
                    '<td>' + item.insured + '</td>' +
                    '<td>' + item.carNumber + '</td>' +
                    '<td>' + item.carTypeCn + '</td>' +
                    '<td>' + item.compulsoryInsurance + '</td>' +
                    '<td>' + item.vehicleAndVesselTax + '</td>' +
                    '<td>' + item.businessTax + '</td>' +
                    '<td>' + item.nonMotorInsurance + '</td>' +
                    '<td>' + item.total + '</td>' +
                    '<td>' + item.updateTime + '</td>' +
                    '<td>' + item.policyExpiryAlert + '</td>' +
                    '<td>' + (item.remarks || '') + '</td>' +
                    '</tr>';
                tableBody.append(row);
            });
        }

        function fetchData(page, size, query) {
            var carNumber = $('#carNumber').val();
            var carType = $('#carType').val(); // 获取车型下拉框的值
            var fullQuery = {
                underwritingDateStart: $('#underwritingDateStart').val(),
                underwritingDateEnd: $('#underwritingDateEnd').val(),
                insured: $('#insured').val(),
                carNumber: carNumber,
                carType: carType,
                currentPage: page,
                pageSize: size
            };
            console.log('即将请求的数据：', fullQuery);
            $.ajax({
                url: '/vehicleInsurance/listByPage',
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
                        var commonPager = resultMap.commonPager;
                        var sum = resultMap.sum;
                        // 更新页面上的总计金额显示
                        $('#sum-value').text(sum);

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
                                    // 重新请求数据
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
        fetchData(currentPage, pageSize, {});

        // 点击查询按钮事件
        $('#queryButton').click(function () {
            var underwritingDateStart = $('#underwritingDateStart').val();
            var underwritingDateEnd = $('#underwritingDateEnd').val();
            var insured = $('#insured').val();
            var carNumber = $('#carNumber').val();
            var carType = $('#carType').val(); // 获取车型下拉框的值

            var query = {
                underwritingDateStart: underwritingDateStart,
                underwritingDateEnd: underwritingDateEnd,
                insured: insured,
                carType: carType,
                carNumber: carNumber,
                token: token
            };

            // 创建一个隐藏的表单并提交 POST 请求
            var form = $('<form>', {
                action: '/viewJump/vehicleInsurance/query',
                method: 'post'
            });
            for (var key in query) {
                form.append($('<input>', {
                    type: 'hidden',
                    name: key,
                    value: query[key]
                }));
            }
            form.appendTo('body').submit();
        });

        // 点击查询所有按钮事件
        $('#queryAllButton').click(function () {
            currentPage = 1;
            fetchData(currentPage, pageSize, {});
        });

        // 点击新增按钮事件
        $('#addButton').click(function () {
            // 创建一个隐藏的表单并提交 POST 请求
            var form = $('<form>', {
                action: '/viewJump/vehicleInsurance/add',
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