<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>车辆保险到期提醒列表</title>
    <#include "../common/head.ftl">
    <style>
        /* 侧边栏基础样式（仅保留布局控制，不设置外观样式） */
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

        /* 主内容容器（避开侧边栏和标题栏） */
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
            padding: 0;
            margin: 0;
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

        .handle-btn {
            background-color: #1890ff;
            color: #fff;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            cursor: pointer;
        }

        .handle-btn:hover {
            background-color: #40a9ff;
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
            color: #ff9800;
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

<!-- 主内容容器 -->
<div class="main-container">
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
                    <button id="queryButton" class="layui-btn layui-bg-orange">查询临期</button>
                    <button id="queryListPageButton" class="layui-btn layui-btn-radius layui-btn-normal">查询所有</button>
                    <span class="amount-display">当前结果集共计金额： <span id="sum-value">0</span>&nbsp&nbsp元</span>
                </div>
            </div>
            <div class="layui-btn-group">
                <button id="addButton" class="layui-btn">
                    <i class="layui-icon">&#xe608;</i> 添加
                </button>
            </div>
            <div class="layui-btn-group">
                <button id="importExcelButton" class="layui-btn">
                    <i class="layui-icon">&#xe608;</i> Excel导入
                </button>
            </div>
            <table id="insuranceTable">
                <thead>
                <tr>
                    <th class="hidden">ID</th>
                    <th>承保日期</th>
                    <th>投保人</th>
                    <th>车型</th>
                    <th>车牌号</th>
                    <th>交强险信息</th>
                    <th>车船税信息</th>
                    <th>商业税信息</th>
                    <th>非车险信息</th>
                    <th>总计金额</th>
                    <th>保单到期提醒</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>

<script>
    // 页面加载后检查导航栏是否存在（调试用）
    $(document).ready(function() {
        setTimeout(function() {
            if ($('.sidebar-container').length === 0) {
                console.warn('导航栏未加载，请检查navigation.ftl路径是否正确');
            }
        }, 300);

        // 从 FTL 变量中获取 token
        var token = '${Request.token}';
        var userNo = '${Request.userNo}';
        var permissions = [];

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
                    // 根据权限显示或隐藏 Excel 导入按钮
                    if (!permissions.includes('/viewJump/vehicleInsurance/importVehicleInsuranceExcel')) {
                        $('#importExcelButton').hide();
                    }
                } else {
                    alert('获取用户权限失败：' + response.message);
                }
            },
            error: function () {
                alert('请求发生错误，请检查网络或接口地址。');
            }
        });

        function fetchInsuranceData(url, query) {
            $.ajax({
                url: url,
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(query),
                dataType: 'json',
                headers: {
                    'token': token
                },
                success: function (response) {
                    if (response.code === 200) {
                        var data = response.data.list || response.data.commonPager.dataList;
                        var sum = response.data.sum;
                        // 更新页面上的总计金额显示
                        $('#sum-value').text(sum);

                        var tableBody = $('#insuranceTable tbody');
                        tableBody.empty();
                        data.forEach(function (item) {
                            var row = '<tr>' +
                                '<td class="hidden">' + item.id + '</td>' +
                                '<td>' + item.underwritingDate + '</td>' +
                                '<td>' + item.insured + '</td>' +
                                '<td>' + item.carTypeCn + '</td>' +
                                '<td>' + item.carNumber + '</td>' +
                                '<td>' + item.compulsoryInsurance + '</td>' +
                                '<td>' + item.vehicleAndVesselTax + '</td>' +
                                '<td>' + item.businessTax + '</td>' +
                                '<td>' + item.nonMotorInsurance + '</td>' +
                                '<td>' + item.total + '</td>' +
                                '<td>' + item.policyExpiryAlert + '</td>';
                            // 判断是否添加处理按钮
                            if (url === '/vehicleInsurance/listByExpirationReminder' && permissions.includes('/vehicleInsurance/renewInsurance')) {
                                row += '<td><button class="handle-btn">处理</button></td>';
                            } else {
                                row += '<td></td>';
                            }
                            row += '</tr>';
                            tableBody.append(row);
                        });

                        // 为处理按钮绑定点击事件
                        $('.handle-btn').click(function () {
                            var id = $(this).closest('tr').find('.hidden').text();
                            // 创建一个隐藏的表单并提交 POST 请求
                            var form = $('<form>', {
                                action: '/viewJump/vehicleInsurance/renewInsurance',
                                method: 'post'
                            });
                            form.append($('<input>', {
                                type: 'hidden',
                                name: 'id',
                                value: id
                            }));
                            // 添加 token 作为隐藏字段
                            form.append($('<input>', {
                                type: 'hidden',
                                name: 'token',
                                value: token
                            }));
                            form.appendTo('body').submit();
                        });
                    } else {
                        alert('请求失败：' + response.message);
                    }
                },
                error: function () {
                    alert('请求发生错误，请检查网络或接口地址。');
                }
            });
        }

        // 初始加载全部数据
        var carNumber = $('#carNumber').val();
        var underwritingDateStart = $('#underwritingDateStart').val();
        var underwritingDateEnd = $('#underwritingDateEnd').val();
        var insured = $('#insured').val();
        var carType = $('#carType').val();

        var initialQuery = {
            underwritingDateStart: underwritingDateStart,
            underwritingDateEnd: underwritingDateEnd,
            carNumber: carNumber,
            carType: carType,
            insured: insured
        };
        fetchInsuranceData('/vehicleInsurance/listByExpirationReminder', initialQuery);

        // 点击查询按钮事件
        $('#queryButton').click(function () {
            var underwritingDateStart = $('#underwritingDateStart').val();
            var underwritingDateEnd = $('#underwritingDateEnd').val();
            var insured = $('#insured').val();
            var carNumber = $('#carNumber').val();
            var carType = $('#carType').val();

            var query = {
                underwritingDateStart: underwritingDateStart,
                underwritingDateEnd: underwritingDateEnd,
                carNumber: carNumber,
                carType: carType,
                insured: insured
            };

            fetchInsuranceData('/vehicleInsurance/listByExpirationReminder', query);
        });

        // 点击查询所有按钮事件
        $('#queryListPageButton').click(function () {
            var underwritingDateStart = $('#underwritingDateStart').val();
            var underwritingDateEnd = $('#underwritingDateEnd').val();
            var insured = $('#insured').val();
            var carNumber = $('#carNumber').val();
            var carType = $('#carType').val();

            var query = {
                underwritingDateStart: underwritingDateStart,
                underwritingDateEnd: underwritingDateEnd,
                carNumber: carNumber,
                carType: carType,
                insured: insured
            };

            fetchInsuranceData('/vehicleInsurance/listAll', query);
        });

        // 点击新增按钮事件
        $('#addButton').click(function () {
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

        // 点击分页列表按钮
        $('#queryListPageButton').click(function () {
            var form = $('<form>', {
                action: '/viewJump/vehicleInsurance/carInsuranceListPage',
                method: 'post'
            });
            form.append($('<input>', {
                type: 'hidden',
                name: 'token',
                value: token
            }));
            form.appendTo('body').submit();
        });

        // 点击 Excel 导入按钮事件
        $('#importExcelButton').click(function () {
            var form = $('<form>', {
                action: '/viewJump/vehicleInsurance/importVehicleInsuranceExcel',
                method: 'post'
            });
            form.append($('<input>', {
                type: 'hidden',
                name: 'token',
                value: token
            }));
            form.appendTo('body').submit();
        });

        // 美化时间组件
        layui.use('laydate', function () {
            var laydate = layui.laydate;

            // 开始时间
            laydate.render({
                elem: '#underwritingDateStart',
                type: 'datetime'
            });

            // 结束时间
            laydate.render({
                elem: '#underwritingDateEnd',
                type: 'datetime'
            });
        });

        // 定时刷新功能，一小时（3600000 毫秒）刷新一次
        setInterval(function () {
            var carNumber = $('#carNumber').val();
            var underwritingDateStart = $('#underwritingDateStart').val();
            var underwritingDateEnd = $('#underwritingDateEnd').val();
            var insured = $('#insured').val();
            var carType = $('#carType').val();
            var query = {
                underwritingDateStart: underwritingDateStart,
                underwritingDateEnd: underwritingDateEnd,
                carNumber: carNumber,
                carType: carType,
                insured: insured
            };
            fetchInsuranceData('/vehicleInsurance/listByExpirationReminder', query);
        }, 3600000);
    });
</script>
</body>

</html>