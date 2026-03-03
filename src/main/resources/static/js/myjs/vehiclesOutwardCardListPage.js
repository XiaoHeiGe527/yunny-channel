layui.use(['form', 'laypage', 'laydate', 'layer'], function() {
    var form = layui.form;
    var laypage = layui.laypage;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var $ = layui.$;

    // 全局参数（统一命名风格）
    var token = window.token;
    var permissions = []; // 权限集合
    var currentPage = 1; // 当前页码
    var pageSize = 10; // 每页条数

    // 字典映射（统一管理）
    var carTypeMap = {
        1: "特三", 2: "丰田", 3: "希尔", 4: "五菱", 5: "春星", 6: "货",
        7: "长城货", 8: "长城", 9: "红宇", 10: "鸿星达", 11: "哈弗",
        12: "霸道", 13: "雷克萨斯", 14: "雪佛兰", 15: "奔驰", 16: "江特",
        17: "威尔法", 18: "奥迪", 19: "比亚迪", 20: "酷路泽", 21: "普拉多"
    };
    var activeStateMap = { 1: "可用", 2: "外出中" };
    var stateMap = { 0: "已使用的卡", 1: "可用卡", 3: "到期未使用" };

    // 页面初始化
    $(function() {
        initPermissions(); // 先获取权限
        initDateComponents(); // 初始化日期组件
        bindEvents(); // 绑定事件
    });

    /**
     * 初始化权限（控制按钮显示）
     */
    function initPermissions() {
        $.ajax({
            url: '/systemResource/selectUserResourceList',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({}),
            headers: { 'token': token },
            success: function(res) {
                if (res.code === 200) {
                    permissions = res.data;
                    // 根据权限控制操作列显示
                    if (!permissions.includes('/vehiclesOutwardCard/cardDepart')) {
                        $('#departColumn').hide();
                    }
                    loadCardList(); // 权限获取后加载数据
                } else {
                    layer.msg('获取权限失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('权限请求失败', { icon: 5 });
            }
        });
    }

    /**
     * 初始化日期选择器
     */
    function initDateComponents() {
        laydate.render({
            elem: '#openDateDateStart',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
        laydate.render({
            elem: '#openDateDateEnd',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
    }

    /**
     * 绑定所有事件
     */
    function bindEvents() {
        // 客户名称输入事件
        $('#vehicleCustomersName').on('click input', function() {
            var keyword = $(this).val().trim();
            showCustomerList(keyword);
        });

        // 点击页面其他区域隐藏客户列表
        $(document).click(function(e) {
            if (!$(e.target).closest('#vehicleCustomersName, #customerList').length) {
                $('#customerList').hide();
            }
        });

        // 查询按钮事件
        form.on('submit(searchBtn)', function() {
            currentPage = 1; // 重置页码
            loadCardList();
            return false;
        });

        // 重置按钮事件
        $('.layui-form').on('reset', function() {
            setTimeout(function() { // 重置后延迟加载
                currentPage = 1;
                loadCardList();
            }, 100);
        });
    }

    /**
     * 显示客户列表下拉
     */
    function showCustomerList(keyword) {
        $.ajax({
            url: '/vehicleCustomers/listAll',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ name: keyword }),
            headers: { 'token': token },
            success: function(res) {
                if (res.code === 200) {
                    var $list = $('#customerList');
                    $list.empty().css({
                        position: 'absolute',
                        top: $('#vehicleCustomersName').offset().top + 38,
                        left: $('#vehicleCustomersName').offset().left,
                        width: $('#vehicleCustomersName').outerWidth(),
                        border: '1px solid #eee',
                        backgroundColor: '#fff',
                        zIndex: 999,
                        maxHeight: '200px',
                        overflowY: 'auto'
                    });

                    res.data.forEach(function(customer) {
                        var $item = $('<div>').text(customer)
                            .css({ padding: '5px 10px', cursor: 'pointer' })
                            .hover(function() { $(this).css('backgroundColor', '#f5f5f5'); });

                        $item.click(function() {
                            $('#vehicleCustomersName').val(customer);
                            $list.hide();
                        });
                        $list.append($item);
                    });
                    $list.show();
                }
            }
        });
    }

    /**
     * 加载开卡列表数据
     */
    function loadCardList() {
        var params = {
            vehicleCustomersName: $('#vehicleCustomersName').val().trim(),
            carType: $('#carType').val(),
            openDateDateStart: $('#openDateDateStart').val(),
            openDateDateEnd: $('#openDateDateEnd').val(),
            carNumber: $('#carNumber').val().trim(),
            isReminder: $('#isReminder').val(),
            state: $('#state').val(),
            currentPage: currentPage,
            pageSize: pageSize
        };

        $.ajax({
            url: '/vehiclesOutwardCard/listByPage',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(params),
            headers: { 'token': token },
            success: function(res) {
                if (res.code === 200) {
                    var data = res.data;
                    renderTable(data.dataList);
                    renderPagination(data.page);
                } else {
                    layer.msg('获取数据失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('请求失败，请检查网络', { icon: 5 });
            }
        });
    }

    /**
     * 渲染表格数据
     */
    function renderTable(dataList) {
        var $tbody = $('#insuranceTable tbody');
        $tbody.empty();

        if (dataList.length === 0) {
            $tbody.append('<tr><td colspan="12" style="text-align: center;">暂无数据</td></tr>');
            return;
        }

        dataList.forEach(function(item) {
            var operateHtml = '';
            // 权限控制：显示发车按钮
            if (permissions.includes('/vehiclesOutwardCard/cardDepart')) {
                operateHtml = `<button class="layui-btn layui-btn-xs layui-btn-normal" 
                                    onclick="handleDepartClick('${item.cardNo}')">发车</button>`;
            }

            $tbody.append(`
                <tr>
                    <td class="hidden">${item.id || ''}</td>
                    <td class="hidden">${item.cardNo || ''}</td>
                    <td>${item.carNumber || '-'}</td>
                    <td>${carTypeMap[item.carType] || '-'}</td>
                    <td>${item.vehicleCustomersName || '-'}</td>
                    <td>${item.openDate || '-'}</td>
                    <td>${item.warningTime || '-'}</td>
                    <td>${item.expirationDate || '-'}</td>
                    <td>${stateMap[item.state] || '-'}</td>
                    <td>${activeStateMap[item.activeState] || '-'}</td>
                    <td>${item.remarks || '-'}</td>
                    <td>${operateHtml}</td>
                </tr>
            `);
        });
    }

    /**
     * 渲染分页控件
     */
    function renderPagination(pageInfo) {
        laypage.render({
            elem: 'pageNav',
            count: pageInfo.totalCount,
            limit: pageSize,
            curr: currentPage,
            layout: ['prev', 'page', 'next', 'count', 'limit'],
            limits: [10, 20, 30, 50],
            jump: function(obj, first) {
                if (!first) {
                    currentPage = obj.curr;
                    pageSize = obj.limit;
                    loadCardList();
                }
            }
        });
    }

    /**
     * 发车按钮点击事件
     */
    window.handleDepartClick = function(cardNo) {
        var $row = $(event.target).closest('tr');
        var activeState = $row.find('td:eq(9)').text();
        var carNumber = $row.find('td:eq(2)').text();

        if (activeState === '可用') {
            // 直接发车
            departCar(carNumber, cardNo);
        } else if (activeState === '外出中') {
            // 弹出可选车辆列表
            showCanDriveList(cardNo);
        }
    };

    /**
     * 显示可发车车辆列表弹窗
     */
    function showCanDriveList(cardNo) {
        $.ajax({
            url: '/companyVehicles/canDriveList',
            type: 'POST',
            headers: { 'token': token },
            success: function(res) {
                if (res.code === 200 && res.data.length > 0) {
                    var content = '<div style="padding: 15px;">';
                    res.data.forEach(function(vehicle) {
                        content += `<div class="layui-btn layui-btn-primary" 
                                        style="margin: 5px;" 
                                        onclick="selectVehicle('${vehicle.carNumber}', '${carTypeMap[vehicle.carType]}', '${cardNo}')">
                                        ${vehicle.carNumber} - ${carTypeMap[vehicle.carType]}
                                   </div>`;
                    });
                    content += '</div>';

                    layer.open({
                        type: 1,
                        title: '可选发车车辆',
                        area: ['600px', '400px'],
                        content: content,
                        shadeClose: true
                    });
                } else {
                    layer.msg('暂无可用发车车辆', { icon: 5 });
                }
            },
            error: function() {
                layer.msg('获取车辆列表失败', { icon: 5 });
            }
        });
    }

    /**
     * 选择车辆并发车
     */
    window.selectVehicle = function(carNumber, carType, cardNo) {
        layer.confirm(`确认为车辆【${carNumber}（${carType}）】发车？`, {
            icon: 3, title: '确认发车'
        }, function(index) {
            departCar(carNumber, cardNo);
            layer.close(index);
        });
    };

    /**
     * 执行发车请求
     */
    function departCar(carNumber, cardNo) {
        $.ajax({
            url: '/vehiclesOutwardCard/cardDepart',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ carNumber: carNumber, cardNo: cardNo }),
            headers: { 'token': token },
            success: function(res) {
                if (res.code === 200) {
                    layer.msg('发车成功', { icon: 1 });
                    loadCardList(); // 刷新列表
                } else {
                    layer.msg('发车失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('发车请求失败', { icon: 5 });
            }
        });
    }
});