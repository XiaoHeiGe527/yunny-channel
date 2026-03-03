$(document).ready(function () {
    // 从全局变量中获取 token
    var token = window.token;
    var permissions = [];

    // 更新特定字段的函数（移到全局作用域）
    function updateFields(id, $row) {
        // 发送请求获取单个车辆的最新数据
        $.ajax({
            url: '/companyVehicles/getById',
            type: 'GET',
            data: {
                "id": id
            },
            dataType: 'json',
            headers: {
                'token': token
            },
            success: function (response) {
                if (response.code === 200) {
                    var item = response.data;

                    if (item) {
                        var carTypeText = "";
                        switch (item.carType) {
                            case 1:
                                carTypeText = "特三";
                                break;
                            case 2:
                                carTypeText = "丰田";
                                break;
                            case 3:
                                carTypeText = "希尔";
                                break;
                            case 4:
                                carTypeText = "五菱";
                                break;
                            case 5:
                                carTypeText = "春星";
                                break;
                            case 6:
                                carTypeText = "货";
                                break;
                            case 7:
                                carTypeText = "长城货";
                                break;
                            case 8:
                                carTypeText = "长城";
                                break;
                            case 9:
                                carTypeText = "红宇";
                                break;
                            case 10:
                                carTypeText = "鸿星达";
                                break;
                            case 11:
                                carTypeText = "哈弗";
                                break;
                            case 12:
                                carTypeText = "霸道";
                                break;
                            case 13:
                                carTypeText = "雷克萨斯";
                                break;
                            case 14:
                                carTypeText = "雪佛兰";
                                break;
                            case 15:
                                carTypeText = "奔驰";
                                break;
                            case 16:
                                carTypeText = "江特";
                                break;
                            case 17:
                                carTypeText = "威尔法";
                                break;
                            case 18:
                                carTypeText = "奥迪";
                                break;
                            case 19:
                                carTypeText = "比亚迪";
                                break;
                            case 20:
                                carTypeText = "酷路泽";
                                break;
                            case 21:
                                carTypeText = "普拉多";
                                break;
                            default:
                                carTypeText = "";
                        }

                        var stateText = "";
                        switch (item.state) {
                            case 0:
                                stateText = "报废车辆";
                                break;
                            case 1:
                                stateText = "正常使用";
                                break;
                            case 2:
                                stateText = "维修中";
                                break;
                            case 3:
                                stateText = "其他";
                                break;
                            default:
                                stateText = "";
                        }

                        var activeStateText = "";
                        switch (item.activeState) {
                            case 1:
                                activeStateText = "可用";
                                break;
                            case 2:
                                activeStateText = "外出中";
                                break;
                            default:
                                activeStateText = "";
                        }

                        // 根据车辆状态决定显示内容
                        var addressText = item.activeState === 2 ? (item.address || '') : '车辆未发车地址未定义';

                        // 选择性更新字段
                        $row.find('.car-owner').text(item.carOwner);
                        $row.find('.car-number').text(item.carNumber);
                        $row.find('.car-type').text(carTypeText);
                        $row.find('.remarks').text(item.remarks);
                        $row.find('.state').text(stateText);
                        $row.find('.active-state').text(activeStateText);
                        $row.find('.address').text(addressText);

                        // 隐藏还车按钮
                        $row.find('.return-btn').hide();

                        // 添加成功提示
                        layer.msg('还车成功', {icon: 1, time: 1500});
                    }
                } else {
                    alert('获取车辆信息失败：' + response.message);
                    // 恢复按钮状态
                    $row.find('.return-btn').html('还车').attr('disabled', false);
                }
            },
            error: function () {
                alert('请求发生错误，请检查网络或接口地址。');
                // 恢复按钮状态
                $row.find('.return-btn').html('还车').attr('disabled', false);
            }
        });
    }

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
                // 根据权限显示或隐藏还车操作列
                if (!permissions.includes('/companyVehicles/returnCompanyVehicles')) {
                    $('#returnColumn').hide();
                }
                // 根据权限显示或隐藏开卡操作列
                if (!permissions.includes('/viewJump/companyVehicles/renewInsurance')) {
                    $('#openCardColumn').hide();
                }
                initData();
            } else {
                alert('获取用户权限失败：' + response.message);
            }
        },
        error: function () {
            alert('请求发生错误，请检查网络或接口地址。');
        }
    });

    // 事件委托 - 为还车按钮绑定点击事件
    $(document).on('click', '.return-btn', function () {
        var $btn = $(this);
        var $row = $btn.closest('tr');
        var carNumber = $row.data('car-number');
        var id = $row.data('id');

        // 显示加载状态
        $btn.html('<i class="layui-icon layui-icon-loading"></i> 处理中...').attr('disabled', true);

        $.ajax({
            url: '/companyVehicles/returnCompanyVehicles',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                "carNumber": carNumber
            }),
            dataType: 'json',
            headers: {
                'token': token
            },
            success: function (response) {
                if (response.code === 200) {
                    // 获取最新数据并更新特定字段
                    updateFields(id, $row);
                } else {
                    alert('还车失败：' + response.message);
                    // 恢复按钮状态
                    $btn.html('还车').attr('disabled', false);
                }
            },
            error: function () {
                alert('请求发生错误，请检查网络或接口地址。');
                // 恢复按钮状态
                $btn.html('还车').attr('disabled', false);
            }
        });
    });

    // 事件委托 - 为开卡按钮绑定点击事件
    $(document).on('click', '.layui-btn-normal', function () {
        var id = $(this).closest('tr').find('.hidden').text();
        // 创建一个隐藏的表单并提交 POST 请求
        var form = $('<form>', {
            action: '/viewJump/companyVehicles/renewInsurance',
            method: 'post'
        });
        form.append($('<input>', {
            type: 'hidden',
            name: 'token',
            value: token
        }));
        form.append($('<input>', {
            type: 'hidden',
            name: 'id',
            value: id
        }));
        form.appendTo('body').submit();
    });

    function initData() {
        layui.use('laypage', function () {
            var laypage = layui.laypage;

            function fetchData(url, query) {
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
                            var data = response.data.commonPager.dataList;
                            var tableBody = $('#insuranceTable tbody');
                            tableBody.empty();
                            data.forEach(function (item) {
                                var carTypeText = "";
                                switch (item.carType) {
                                    case 1:
                                        carTypeText = "特三";
                                        break;
                                    case 2:
                                        carTypeText = "丰田";
                                        break;
                                    case 3:
                                        carTypeText = "希尔";
                                        break;
                                    case 4:
                                        carTypeText = "五菱";
                                        break;
                                    case 5:
                                        carTypeText = "春星";
                                        break;
                                    case 6:
                                        carTypeText = "货";
                                        break;
                                    case 7:
                                        carTypeText = "长城货";
                                        break;
                                    case 8:
                                        carTypeText = "长城";
                                        break;
                                    case 9:
                                        carTypeText = "红宇";
                                        break;
                                    case 10:
                                        carTypeText = "鸿星达";
                                        break;
                                    case 11:
                                        carTypeText = "哈弗";
                                        break;
                                    case 12:
                                        carTypeText = "霸道";
                                        break;
                                    case 13:
                                        carTypeText = "雷克萨斯";
                                        break;
                                    case 14:
                                        carTypeText = "雪佛兰";
                                        break;
                                    case 15:
                                        carTypeText = "奔驰";
                                        break;
                                    case 16:
                                        carTypeText = "江特";
                                        break;
                                    case 17:
                                        carTypeText = "威尔法";
                                        break;
                                    case 18:
                                        carTypeText = "奥迪";
                                        break;
                                    case 19:
                                        carTypeText = "比亚迪";
                                        break;
                                    case 20:
                                        carTypeText = "酷路泽";
                                        break;
                                    case 21:
                                        carTypeText = "普拉多";
                                        break;
                                    default:
                                        carTypeText = "";
                                }

                                var stateText = "";
                                switch (item.state) {
                                    case 0:
                                        stateText = "报废车辆";
                                        break;
                                    case 1:
                                        stateText = "正常使用";
                                        break;
                                    case 2:
                                        stateText = "维修中";
                                        break;
                                    case 3:
                                        stateText = "其他";
                                        break;
                                    default:
                                        stateText = "";
                                }

                                var activeStateText = "";
                                switch (item.activeState) {
                                    case 1:
                                        activeStateText = "可用";
                                        break;
                                    case 2:
                                        activeStateText = "外出中";
                                        break;
                                    default:
                                        activeStateText = "";
                                }

                                // 根据车辆状态决定显示内容
                                var addressText = item.activeState === 2 ? (item.address || '') : '车辆未发车地址未定义';

                                var returnButton = '';
                                var openCardButton = '';
                                var row = '<tr data-id="' + item.id + '" data-car-number="' + item.carNumber + '">' +
                                    '<td class="hidden">' + item.id + '</td>' +
                                    '<td class="car-owner">' + item.carOwner + '</td>' +
                                    '<td class="car-number">' + item.carNumber + '</td>' +
                                    '<td class="car-type">' + carTypeText + '</td>' +
                                    '<td class="remarks">' + item.remarks + '</td>' +
                                    '<td class="state">' + stateText + '</td>' +
                                    '<td class="active-state">' + activeStateText + '</td>' +
                                    '<td class="address">' + addressText + '</td>';

                                if (permissions.includes('/companyVehicles/returnCompanyVehicles')) {
                                    if (item.activeState === 2) {
                                        returnButton = '<button type="button" class="layui-btn layui-btn-danger layui-btn-radius return-btn">还车</button>';
                                    }
                                    row += '<td class="return-td">' + returnButton + '</td>';
                                } else {
                                    row += '<td></td>';
                                }

                                if (permissions.includes('/viewJump/companyVehicles/renewInsurance')) {
                                    openCardButton = '<button type="button" class="layui-btn layui-btn-normal">开卡</button>';
                                    row += '<td>' + openCardButton + '</td>';
                                } else {
                                    row += '<td></td>';
                                }

                                row += '</tr>';
                                tableBody.append(row);
                            });

                            // 初始化分页插件
                            laypage.render({
                                elem: 'page',
                                count: response.data.commonPager.page.totalCount,
                                limit: response.data.commonPager.page.pageSize,
                                curr: response.data.commonPager.page.currentPage,
                                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                                jump: function (obj, first) {
                                    if (!first) {
                                        query.currentPage = obj.curr;
                                        query.pageSize = obj.limit;
                                        fetchData(url, query);
                                    }
                                }
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

            // 初始加载数据
            var initialQuery = {
                "carNumber": "",
                "carOwner": "",
                "carType": "",
                "activeState": "",
                "isManage": "1",
                "state": "",
                "currentPage": 1,
                "pageSize": 20
            };
            fetchData('/companyVehicles/listByPage', initialQuery);

            // 点击查询按钮事件
            $('#queryButton').click(function () {
                var carOwner = $('#carOwner').val();
                var carNumber = $('#carNumber').val();
                var carType = $('#carType').val();
                var activeState = $('#activeState').val();
                var state = $('#state').val();
                var isManage = $('#isManage').val();

                var query = {
                    "carNumber": carNumber,
                    "carOwner": carOwner,
                    "carType": carType,
                    "activeState": activeState,
                    "isManage": isManage,
                    "state": state,
                    "currentPage": 1,
                    "pageSize": 20
                };

                fetchData('/companyVehicles/listByPage', query);
            });
        });
    }
});