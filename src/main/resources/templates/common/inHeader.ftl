<!DOCTYPE html>
<style>
    /* 顶部标题栏核心样式 */
    .top-header {
        height: 50px;
        background-color: #1E90FF;
        color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 20px;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        z-index: 9999;
    }

    .system-title {
        font-size: 18px;
        font-weight: bold;
        display: flex;
        align-items: center;
        gap: 10px;
    }
    .system-title i {
        font-size: 22px;
    }

    .user-actions {
        display: flex;
        align-items: center;
        gap: 20px;
    }
    .user-actions a {
        color: #fff;
        text-decoration: none;
        font-size: 14px;
        cursor: pointer;
        display: flex;
        align-items: center;
        gap: 5px;
        position: relative;
    }
    .user-actions a:hover {
        color: #FFD700;
    }
    .user-actions a i {
        font-size: 16px;
    }

    /* 消息铃铛样式 */
    .message-bell {
        position: relative;
    }
    .message-count {
        position: absolute;
        top: -8px;
        right: -8px;
        background-color: #FF4500;
        color: white;
        font-size: 12px;
        width: 18px;
        height: 18px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
    }

    /* 铃铛抖动动画 */
    @keyframes shake {
        0%, 100% { transform: rotate(0); }
        20% { transform: rotate(-10deg); }
        40% { transform: rotate(10deg); }
        60% { transform: rotate(-5deg); }
        80% { transform: rotate(5deg); }
    }
    .shake {
        animation: shake 1s ease-in-out infinite;
    }

    /* 弹窗内容样式 */
    .message-popup {
        padding: 15px;
    }
    .message-item {
        padding: 10px 0;
        border-bottom: 1px solid #eee;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .message-item:last-child {
        border-bottom: none;
    }
    .message-item .count-text {
        color: #333;
    }
    .message-item .handle-btn {
        background-color: #1E90FF;
        color: white;
        border: none;
        padding: 4px 10px;
        border-radius: 3px;
        cursor: pointer;
        font-size: 12px;
    }
    .message-item .handle-btn:hover {
        background-color: #0b7dda;
    }
    .no-message {
        text-align: center;
        padding: 20px;
        color: #666;
    }
</style>

<!-- 顶部标题栏 -->
<div class="top-header">
    <!-- 系统标题 -->
    <div class="system-title">
        <i class="layui-icon layui-icon-car"></i>
        <span>海外综合管理系统</span>
    </div>

    <!-- 用户操作区 -->
    <div class="user-actions">
        <!-- 消息铃铛 -->
        <a id="messageBell" class="message-bell">
            <i class="layui-icon layui-icon-notice"></i>
            <span class="message-count" style="display: none;"></span>
        </a>

        <!-- 原有功能 -->
        <a id="changePasswordLink">
            <i class="layui-icon layui-icon-password"></i>
            <span>修改密码</span>
        </a>
        <a id="outLink">
            <i class="layui-icon layui-icon-logout"></i>
            <span>退出登录</span>
        </a>
    </div>
</div>

<!-- 注入FTL变量到全局（仅保留FreeMarker变量） -->
<script>
    window.navConfig = {
        token: '${Request.token}',
        userNo: '${Request.userNo}',
        gpsMonitoringUrl: 'http://42.101.60.52:8081/index/monitoring/liveSurveillance',
        expirationReminderCount: ${Request.expirationReminderCount!0},
        vehiclesOutwardCardCount: ${Request.vehiclesOutwardCardCount!0}
    };
</script>

<!-- 用<#noparse>包裹JS代码，让FreeMarker不解析内部的${} -->
<#noparse>
<script>
    layui.use(['jquery', 'layer'], function() {
        const $ = layui.$;
        const layer = layui.layer;
        const {
            token,
            userNo,
            expirationReminderCount,
            vehiclesOutwardCardCount
        } = window.navConfig;
        let permissions = [];
        // 权限标识常量（JS内部使用）
        const PERMISSIONS = {
            vehicleInsurance: '/viewJump/vehicleInsurance/query',
            vehiclesOutwardCard: '/viewJump/vehiclesOutwardCard/listByPage'
        };

        // 初始化
        function init() {
            loadPermissions();
            bindEvents();
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
                        handleMessageDisplay();
                    } else {
                        layer.msg('获取用户权限失败：' + response.message, { icon: 5 });
                    }
                },
                error: function () {
                    layer.msg('请求发生错误，请检查网络或接口地址。', { icon: 5 });
                }
            });
        }

        // 2. 处理消息显示逻辑
        function handleMessageDisplay() {
            let totalCount = 0;
            const hasVehicleInsurancePerm = permissions.includes(PERMISSIONS.vehicleInsurance);
            const hasOutwardCardPerm = permissions.includes(PERMISSIONS.vehiclesOutwardCard);

            // 计算有效消息数（仅有权限时计入）
            const validVehicleCount = hasVehicleInsurancePerm ? expirationReminderCount : 0;
            const validCardCount = hasOutwardCardPerm ? vehiclesOutwardCardCount : 0;
            totalCount = validVehicleCount + validCardCount;

            // 显示消息数量
            const $countEl = $('.message-count');
            if (totalCount > 0) {
                $countEl.text(totalCount).show();
                $('#messageBell i').addClass('shake');
            } else {
                $countEl.hide();
            }
        }

        // 3. 绑定事件
        function bindEvents() {
            // 消息铃铛点击弹窗
            $('#messageBell').on('click', function() {

                // const targetDate = new Date(2026, 5, 27);
                // const currentDate = new Date();
                //
                // if (currentDate > targetDate) {
                //
                //     return null;
                // }

                showMessagePopup();    // 1128527 412679387

            });

            // 修改密码
            $('#changePasswordLink').on('click', function() {
                submitPostForm('/viewJump/vehicleInsurance/changePassword');
            });

            // 退出登录
            $('#outLink').on('click', function() {
                $.ajax({
                    url: '/sys/logout',
                    type: 'POST',
                    data: { userNo },
                    dataType: 'json',
                    headers: { 'token': token },
                    success: (res) => {
                    if (res.code === 200) {
                    window.location.href = '/login';
                } else {
                    layer.msg('退出失败：' + res.message, { icon: 5 });
                }
            },
                error: () => {
                    layer.msg('请求错误，请检查网络', { icon: 5 });
                }
            });
            });
        }

        // 4. 显示消息弹窗
        function showMessagePopup() {
            const hasVehicleInsurancePerm = permissions.includes(PERMISSIONS.vehicleInsurance);
            const hasOutwardCardPerm = permissions.includes(PERMISSIONS.vehiclesOutwardCard);
            let popupContent = '';
            let hasMessage = false;

            // 构建弹窗内容（JS模板字符串，使用PERMISSIONS）
            if (hasVehicleInsurancePerm && expirationReminderCount > 0) {
                hasMessage = true;
                popupContent += `
                    <div class="message-item">
                        <span class="count-text">车辆承保到期提醒：${expirationReminderCount}条</span>
                        <button class="handle-btn" data-url="${PERMISSIONS.vehicleInsurance}">处理</button>
                    </div>
                `;
            }

            if (hasOutwardCardPerm && vehiclesOutwardCardCount > 0) {
                hasMessage = true;
                popupContent += `
                    <div class="message-item">
                        <span class="count-text">车卡信息到期提醒：${vehiclesOutwardCardCount}条</span>
                        <button class="handle-btn" data-url="${PERMISSIONS.vehiclesOutwardCard}">处理</button>
                    </div>
                `;
            }

            if (!hasMessage) {
                popupContent = '<div class="no-message">您目前没有需要处理的消息</div>';
            }

            // 创建弹窗
            const popupIndex = layer.open({
                type: 1,
                title: '消息提醒',
                area: ['380px', 'auto'],
                content: `<div class="message-popup">${popupContent}</div>`,
                shade: 0.2,
                success: function(layero) {
                    layero.find('.handle-btn').on('click', function() {
                        const url = $(this).data('url');
                        submitPostForm(url);
                        layer.close(popupIndex);
                    });
                }
            });
        }

        // 提交POST表单跳转
        function submitPostForm(actionUrl) {
            $('<form>', {
                action: actionUrl,
                method: 'post',
                style: 'display:none'
            }).append($('<input>', {
                type: 'hidden',
                name: 'token',
                value: token
            })).appendTo('body').submit();
        }

        // 启动初始化
        init();
    });
</script>
</#noparse>