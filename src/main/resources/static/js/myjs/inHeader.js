$(document).ready(function () {
    // 从全局变量获取FTL注入的参数
    const { token, userNo, gpsMonitoringUrl } = window.navConfig;
    let permissions = [];
    const currentPagePath = window.location.pathname;
    const openMenus = new Set(); // 存储展开的折叠菜单

    // 权限标识常量
// 权限标识常量
    const PERMISSIONS = {
        gpsMonitoring: '/index/monitoring/liveSurveillance',
        technicalFile: '/renShiViewJump/chemicalFile/technicalFileListPage',
        systemUser: '/viewJump/systemUser/listByPage',
        systemRole: '/viewJump/systemRole/listByPage', // 新增：系统角色管理权限标识
        materialPlanSubmit: '/materialPlanViewJump/materialPlanMain/submit',
        materialPlanList: '/materialPlanViewJump/materialPlanMain/materialPlanMainListPage'
    };

    // 初始化入口
    function init() {
        loadPermissions();
    }

    // 1. 获取用户权限
    function loadPermissions() {
        $.ajax({
            url: '/systemResource/selectUserResourceList',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({}),
            dataType: 'json',
            headers: { 'token': token },
            success: function (response) {
                if (response.code === 200) {
                    permissions = response.data;
                    initCollapseMenu();
                    handlePermissionVisibility();
                    initEventHandlers();
                    highlightNavItem();
                    initGlobalClickHandler();
                } else {
                    alert('获取用户权限失败：' + response.message);
                }
            },
            error: function () {
                alert('请求发生错误，请检查网络或接口地址。');
            }
        });
    }

    // 2. 初始化折叠菜单事件
    function initCollapseMenu() {
        $('#purchaseManagementLi, #vehicleManagementLi, #archivesManagementParentLi, #systemManagementLi')
            .on('click', function(e) {
                e.stopPropagation();
                toggleMenu(this);
            });
    }

    // 切换菜单展开/折叠状态
    function toggleMenu(menuItem) {
        $(menuItem).toggleClass('layui-nav-itemed');
        if ($(menuItem).hasClass('layui-nav-itemed')) {
            openMenus.add(menuItem);
        } else {
            openMenus.delete(menuItem);
        }
    }

    // 3. 根据权限显示/隐藏菜单项
    function handlePermissionVisibility() {
        // 采购管理菜单
        const hasPurchasePermission = permissions.includes(PERMISSIONS.materialPlanSubmit) ||
            permissions.includes(PERMISSIONS.materialPlanList);
        if (!hasPurchasePermission) {
            $('#purchaseManagementLi').hide();
        } else {
            $('#materialPlanSubmitLiChild').toggle(permissions.includes(PERMISSIONS.materialPlanSubmit));
            $('#materialPlanListLiChild').toggle(permissions.includes(PERMISSIONS.materialPlanList));
        }

        // 车辆管理菜单
        const hasVehiclePermission = permissions.includes('/viewJump/companyVehicles/listByPage') ||
            permissions.includes('/viewJump/vehicleInsurance/query') ||
            permissions.includes('/viewJump/vehiclesOutwardCard/listByPage') ||
            permissions.includes(PERMISSIONS.gpsMonitoring);
        if (!hasVehiclePermission) {
            $('#vehicleManagementLi').hide();
        } else {
            $('#insuranceReminderLiChild').toggle(permissions.includes('/viewJump/vehicleInsurance/query'));
            $('#companyVehiclesListByPageLiChild').toggle(permissions.includes('/viewJump/companyVehicles/listByPage'));
            $('#vehiclesOutwardCardListPageLiChild').toggle(permissions.includes('/viewJump/vehiclesOutwardCard/listByPage'));
            $('#gpsMonitoringLiChild').toggle(permissions.includes(PERMISSIONS.gpsMonitoring));
        }

        // 档案管理菜单
        const hasArchivesPermission = permissions.includes('/renShiViewJump/employeeFile/employeeFileListPage') ||
            permissions.includes('/renShiViewJump/chemicalFile/handleFileUpload') ||
            permissions.includes(PERMISSIONS.technicalFile);
        if (!hasArchivesPermission) {
            $('#archivesManagementParentLi').hide();
        } else {
            $('#archivesManagementLiChild').toggle(permissions.includes('/renShiViewJump/employeeFile/employeeFileListPage'));
            $('#chemicalFileLiChild').toggle(permissions.includes('/renShiViewJump/chemicalFile/chemicalFileListPage'));
            $('#technicalFileLiChild').toggle(permissions.includes(PERMISSIONS.technicalFile));
        }

        // 系统管理菜单
        // 系统管理菜单
        const hasSystemPermission = permissions.includes('/viewJump/systemLog/listByPage') ||
            permissions.includes('/viewJump/dictionary/listByPage') ||
            permissions.includes(PERMISSIONS.systemUser) ||
            permissions.includes(PERMISSIONS.systemRole); // 新增：包含角色管理权限
        if (!hasSystemPermission) {
            $('#systemManagementLi').hide();
        } else {
            $('#systemLogLiChild').toggle(permissions.includes('/viewJump/systemLog/listByPage'));
            $('#dictionaryLiChild').toggle(permissions.includes('/viewJump/dictionary/listByPage'));
            $('#systemUserLiChild').toggle(permissions.includes(PERMISSIONS.systemUser));
            // 新增：系统角色管理的显示控制
            $('#systemRoleLiChild').toggle(permissions.includes(PERMISSIONS.systemRole));
        }

        // 其他独立菜单项
        $('#companyVehiclesListByPageLi').toggle(permissions.includes('/viewJump/companyVehicles/listByPage'));
        $('#vehiclesOutwardCardListPageLi').toggle(permissions.includes('/viewJump/vehiclesOutwardCard/listByPage'));
        $('#insuranceReminderLi').toggle(permissions.includes('/viewJump/vehicleInsurance/carInsuranceListPage'));
        $('#systemLogLi').toggle(permissions.includes('/viewJump/systemLog/listByPage'));
        $('#dictionaryLi').toggle(permissions.includes('/viewJump/dictionary/listByPage'));
    }

    // 4. 根据当前页面高亮导航项
    function highlightNavItem() {
        const pathNavMap = {
            [PERMISSIONS.materialPlanSubmit]: { mainItem: '#purchaseManagementLi', childItem: '#materialPlanSubmitLiChild' },
            [PERMISSIONS.materialPlanList]: { mainItem: '#purchaseManagementLi', childItem: '#materialPlanListLiChild' },
            '/renShiViewJump/employeeFile/employeeFileListPage': { mainItem: '#archivesManagementParentLi', childItem: '#archivesManagementLiChild' },
            '/renShiViewJump/chemicalFile/chemicalFileListPage': { mainItem: '#archivesManagementParentLi', childItem: '#chemicalFileLiChild' },
            [PERMISSIONS.technicalFile]: { mainItem: '#archivesManagementParentLi', childItem: '#technicalFileLiChild' },
            '/viewJump/systemLog/listByPage': { mainItem: '#systemManagementLi', childItem: '#systemLogLiChild' },
            '/viewJump/dictionary/listByPage': { mainItem: '#systemManagementLi', childItem: '#dictionaryLiChild' },
            [PERMISSIONS.systemUser]: { mainItem: '#systemManagementLi', childItem: '#systemUserLiChild' },
            [PERMISSIONS.systemRole]: { mainItem: '#systemManagementLi', childItem: '#systemRoleLiChild' },
            '/viewJump/companyVehicles/listByPage': { mainItem: '#vehicleManagementLi', childItem: '#companyVehiclesListByPageLiChild' },
            '/viewJump/vehicleInsurance/query': { mainItem: '#vehicleManagementLi', childItem: '#insuranceReminderLiChild' },
            '/viewJump/vehiclesOutwardCard/listByPage': { mainItem: '#vehicleManagementLi', childItem: '#vehiclesOutwardCardListPageLiChild' },
            [PERMISSIONS.gpsMonitoring]: { mainItem: '#vehicleManagementLi', childItem: '#gpsMonitoringLiChild' },
            '/viewJump/homepage': { mainItem: '#homepageLi' }
        };

        for (const path in pathNavMap) {
            if (currentPagePath.includes(path)) {
                const { mainItem, childItem } = pathNavMap[path];
                $(mainItem).addClass('layui-this');
                if (childItem) {
                    $(mainItem).addClass('layui-nav-itemed');
                    $(childItem).find('a').css('color', '#FFD700');
                }
                return;
            }
        }

        // 默认高亮首页
        $('#homepageLi').addClass('layui-this');
    }

    // 5. 初始化事件绑定
    function initEventHandlers() {
        // 采购管理
        $('#materialPlanSubmitLinkChild').click(() => submitPostForm(PERMISSIONS.materialPlanSubmit));
        $('#materialPlanListLinkChild').click(() => submitPostForm(PERMISSIONS.materialPlanList));

        // 修改密码
        $('#changePasswordLink').click(() => submitPostForm('/viewJump/vehicleInsurance/changePassword'));

        // 退出登录
        $('#outLink').click(() => {
            $.ajax({
                    url: '/sys/logout',
                    type: 'POST',
                    data: { userNo },
                    dataType: 'json',
                    headers: { 'token': token },
                    success: (res) => res.code === 200 ? window.location.href = '/login' : alert('退出失败：' + res.message),
                error: () => alert('请求错误，请检查网络')
    });
    });

        // 车辆管理相关
        $('#insuranceReminderLink, #insuranceReminderLinkChild').click(() => submitPostForm('/viewJump/vehicleInsurance/query'));
        $('#companyVehiclesListByPage, #companyVehiclesListByPageChild').click(() => submitPostForm('/viewJump/companyVehicles/listByPage'));
        $('#vehiclesOutwardCardListPageLink, #vehiclesOutwardCardListPageLinkChild').click(() => submitPostForm('/viewJump/vehiclesOutwardCard/listByPage'));
        $('#gpsMonitoringLinkChild').click(() => window.location.href = gpsMonitoringUrl);

        // 首页
        $('#homepage').click(() => submitPostForm('/viewJump/homepage'));

        // 档案管理
        $('#archivesManagementLink, #archivesManagementLinkChild').click(() => submitPostForm('/renShiViewJump/employeeFile/employeeFileListPage'));
        $('#chemicalFileLink, #chemicalFileLinkChild').click(() => submitPostForm('/renShiViewJump/chemicalFile/chemicalFileListPage'));
        $('#technicalFileLinkChild').click(() => submitPostForm(PERMISSIONS.technicalFile));

        // 系统管理
        $('#systemLogLink, #systemLogLinkChild').click(() => submitPostForm('/viewJump/systemLog/listByPage'));
        $('#dictionaryLink, #dictionaryLinkChild').click(() => submitPostForm('/viewJump/dictionary/listByPage'));
        $('#systemUserLinkChild').click(() => submitPostForm(PERMISSIONS.systemUser));
        // 新增：系统角色管理点击事件
        $('#systemRoleLinkChild').click(() => submitPostForm(PERMISSIONS.systemRole));
    }

    // 提交POST表单工具函数
    function submitPostForm(actionUrl) {
        $('<form>', {
            action: actionUrl,
            method: 'post',
            style: 'display: none;'
        }).append($('<input>', {
            type: 'hidden',
            name: 'token',
            value: token
        })).appendTo('body').submit();
    }

    // 6. 全局点击关闭菜单
    function initGlobalClickHandler() {
        $(document).on('click', (event) => {
            let isClickInsideMenu = false;
        $('.layui-nav-item.layui-nav-itemed').each(function() {
            if ($(event.target).closest(this).length) {
                isClickInsideMenu = true;
                return false;
            }
        });
        if (!isClickInsideMenu) closeAllMenus();
    });
    }

    // 关闭所有展开的菜单
    function closeAllMenus() {
        openMenus.forEach(menu => $(menu).removeClass('layui-nav-itemed'));
        openMenus.clear();
    }

    // 启动初始化
    init();
});