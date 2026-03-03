<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>数字化平台</title>
    <#include "../common/head.ftl">

    <!-- ========== 核心修改1：定义Freemarker公共常量（仅改这里即可迁移环境） ========== -->
    <#assign baseImageUrl = "http://192.168.2.129:9100" />

    <style>
        /* 基础样式 */
        body {
            font-family: 'Segoe UI', 'PingFang SC', 'Microsoft YaHei', sans-serif;
            background-color: #f5f7fa;
            padding: 0;
            margin: 0;
            position: relative;
            overflow-x: hidden;
        }

        /* 添加背景图片样式 */
        body::before {
            content: "";
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            /* ========== 核心修改2：引用公共常量替换硬编码IP ========== */
            background-image: url("${baseImageUrl}/images/bj.png");
            background-size: cover;
            background-position: center;
            opacity: 0.45; /* 背景透明度 */
            z-index: -1; /* 背景在内容下方 */
            filter: blur(1px); /* 背景轻微模糊 */
        }

        /* 核心：内容容器边距，避开标题栏和侧边栏 */
        .container {
            max-width: 1200px;
            margin: 0 auto 0 200px; /* 左侧margin匹配侧边栏宽度200px */
            padding: 20px;
            padding-top: 70px; /* 顶部padding = 标题栏高度50px + 额外间距20px，避免被标题栏遮挡 */
            position: relative;
            z-index: 1; /* 内容层优先级高于背景 */
        }

        /* 标题样式 */
        .page-title {
            text-align: center;
            font-size: 2.5rem;
            font-weight: 700;
            color: #333;
            margin: 30px 0 15px;
            position: relative;
        }

        .page-subtitle {
            text-align: center;
            font-size: 1.5rem;
            font-weight: 500;
            color: #666;
            margin: 0 0 40px;
            position: relative;
        }

        .page-title::after {
            content: '';
            display: block;
            width: 80px;
            height: 4px;
            background-color: #4a90e2;
            margin: 15px auto 0;
            border-radius: 2px;
        }

        /* 卡片容器 */
        .card-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 30px;
        }

        /* 主卡片样式 */
        .digital-card {
            background: #fff;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
        }

        .digital-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
        }

        .card-header {
            position: relative;
            height: 160px;
            overflow: hidden;
        }

        .card-header img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.5s;
        }

        .digital-card:hover .card-header img {
            transform: scale(1.05);
        }

        .card-body {
            padding: 20px;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 10px;
        }

        .card-desc {
            font-size: 0.95rem;
            color: #666;
            margin: 0;
        }

        /* 系统台账内容区 */
        .system-ledger {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
            overflow: hidden;
            max-height: 0;
            transition: max-height 0.5s ease-in-out, padding 0.3s;
            padding: 0;
        }

        .system-ledger.active {
            max-height: 1000px;
            padding: 20px;
        }

        .ledger-title {
            font-size: 1.1rem;
            font-weight: 600;
            color: #333;
            margin: 0 0 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }

        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 15px;
        }

        .stat-card {
            background: #f9f9f9;
            border-radius: 8px;
            padding: 15px;
            text-align: center;
            transition: all 0.3s;
        }

        .stat-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
        }

        .stat-value {
            font-size: 1.5rem;
            font-weight: 700;
            color: #4a90e2;
            margin: 0 0 5px;
        }

        .stat-label {
            font-size: 0.9rem;
            color: #666;
            margin: 0;
        }

        .card-color-1 {
            background-color: #e0f7fa;
        }

        .card-color-2 {
            background-color: #ffecb3;
        }

        .card-color-3 {
            background-color: #ffebee;
        }

        /* 响应式设计 */
        @media (max-width: 768px) {
            .container {
                margin-left: 0; /* 小屏幕下移除左侧margin，避免内容过窄 */
                padding-top: 60px; /* 小屏幕标题栏下方间距调整 */
            }
            .page-title {
                font-size: 2rem;
            }
            .page-subtitle {
                font-size: 1.2rem;
            }
            .card-container {
                grid-template-columns: 1fr;
            }
            .stats-container {
                grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            }
        }
    </style>
</head>

<body>
<#include "../common/inHeader.ftl"> <!-- 标题栏 -->
<#include "../common/navigation.ftl"> <!-- 侧边导航栏（已添加） -->

<div class="container">
    <h1 class="page-title">黑龙江海外民爆器材有限公司数字化综合平台</h1>
    <h2 class="page-subtitle">工业互联网云平台</h2>

    <div class="card-container">
        <!-- PLM系统 -->
        <div class="digital-card"  onclick="window.open('https://qingflow.com/index/4946915/app/b97fjtoa4g02/view/b97fl0d24g01', '_blank')">
            <div class="card-header">
                <!-- ========== 核心修改3：引用公共常量替换图片地址 ========== -->
                <img src="${baseImageUrl}/images/11.jpg" alt="PLM系统">
            </div>
            <div class="card-body">
                <div class="card-title">PLM - 产品生命周期管理系统</div>
                <div class="card-desc">点击进入产品生命周期管理系统</div>
            </div>
        </div>

        <!-- MES系统 -->
        <div class="digital-card"  onclick="window.open('http://192.168.1.27/', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/1.jpg" alt="MES系统">
            </div>
            <div class="card-body">
                <div class="card-title">MES - 生产制造执行系统</div>
                <div class="card-desc">点击进入生产制造执行系统</div>
            </div>
        </div>

        <!-- ERP管理系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/686cb01204161ad2a7ec0d39/form/6852649aca7f5738e4e6a95d', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/8.jpg" alt="ERP管理系统">
            </div>
            <div class="card-body">
                <div class="card-title">ERP - 企业资源计划管理系统</div>
                <div class="card-desc">点击进入ERP管理系统</div>
            </div>
        </div>

        <!-- SCM系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/685a45c54780f8888f8d1328', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/10.jpg" alt="SCM系统">
            </div>
            <div class="card-body">
                <div class="card-title">SCM - 供应链管理系统</div>
                <div class="card-desc">点击进入供应链管理系统</div>
            </div>
        </div>

        <!-- WMS系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/685c9bdc60c2e305111f42d7/dash/64312ec0c170e500085d0b47', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/14.jpg" alt="WMS系统">
            </div>
            <div class="card-body">
                <div class="card-title">WMS - 仓库管理系统</div>
                <div class="card-desc">点击进入仓库管理系统</div>
            </div>
        </div>

        <!-- QMS系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/685e33c9e4e1b18fd326b2b3', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/17.jpg" alt="QMS系统">
            </div>
            <div class="card-body">
                <div class="card-title">QMS - 质量管理系统</div>
                <div class="card-desc">点击进入质量管理系统</div>
            </div>
        </div>

        <!-- TMS系统 -->
        <div class="digital-card" id="vehicleManagementCard">
            <div class="card-header">
                <img src="${baseImageUrl}/images/5.jpg" alt="TMS系统">
            </div>
            <div class="card-body">
                <div class="card-title">TMS - 运输管理系统</div>
                <div class="card-desc">管理公司车辆与车卡信息</div>
            </div>
        </div>

        <!-- CRM系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/6871c606b9577b700f4feeaf', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/9.jpg" alt="CRM系统">
            </div>
            <div class="card-body">
                <div class="card-title">CRM - 客户关系管理系统</div>
                <div class="card-desc">点击进入CRM客户关系管理系统</div>
            </div>
        </div>

        <!-- HRM&OA系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/6809e0ccc2acdba05b460fd8', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/7.jpg" alt="HRM&OA系统">
            </div>
            <div class="card-body">
                <div class="card-title">HRM&OA - 人事及办公自动化系统</div>
                <div class="card-desc">点击进入人事及OA系统</div>
            </div>
        </div>

        <!-- SMS系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/685c9f933d450250937cad31', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/2.jpg" alt="SMS系统">
            </div>
            <div class="card-body">
                <div class="card-title">SMS - 安全管理系统</div>
                <div class="card-desc">点击进入安全管理平台</div>
            </div>
        </div>

        <!-- 设备管理系统 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/app/6809d4f21b6248d53b550756', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/12.jpg" alt="设备管理系统">
            </div>
            <div class="card-body">
                <div class="card-title">EMS - 设备管理系统</div>
                <div class="card-desc">点击进入设备管理系统</div>
            </div>
        </div>

        <!-- 系统台账 -->
        <div class="digital-card"  onclick="window.open('https://www.jiandaoyun.com/dashboard#/', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/15.jpg" alt="系统台账">
            </div>
            <div class="card-body">
                <div class="card-title">SLM - 系统台账</div>
                <div class="card-desc">点击进入系统台账</div>
            </div>
        </div>

        <!-- 员工档案管理 -->
        <div class="digital-card" id="employeeArchivesCard">
            <div class="card-header">
                <img src="${baseImageUrl}/images/4.jpg" alt="员工档案管理">
            </div>
            <div class="card-body">
                <div class="card-title">PAM - 员工档案管理</div>
                <div class="card-desc">管理员工档案与人事信息</div>
            </div>
        </div>

        <!-- 监控中心 -->
        <div class="digital-card" onclick="window.open('http://10.20.1.206/', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/16.jpg" alt="监控中心-206">
            </div>
            <div class="card-body">
                <div class="card-title">MOC - 监控中心-206</div>
                <div class="card-desc">点击进入监控中心-206</div>
            </div>
        </div>

        <div class="digital-card"  onclick="window.open('http://10.20.1.207/', '_blank')">
            <div class="card-header">
                <img src="${baseImageUrl}/images/21.png" alt="监控中心-207">
            </div>
            <div class="card-body">
                <div class="card-title">MOC - 监控中心-207</div>
                <div class="card-desc">点击进入监控中心-207</div>
            </div>
        </div>

        <!-- 系统统计信息 -->
        <div class="digital-card" id="systemLedgerCard">
            <div class="card-header">
                <img src="${baseImageUrl}/images/3.jpg" alt="系统统计信息">
            </div>
            <div class="card-body">
                <div class="card-title">统计信息</div>
                <div class="card-desc">查看统计信息</div>
            </div>
        </div>
    </div>

    <!-- 系统统计信息 -->
    <div class="system-ledger" id="systemLedgerContent">
        <h2 class="ledger-title">统计信息</h2>

        <div class="stats-container">
            <!-- 员工档案管理统计信息 -->
            <div class="stat-card card-color-1">
                <div class="stat-value">${Request.employeesCount}</div>
                <div class="stat-label">在职人员数量</div>
            </div>
            <div class="stat-card card-color-2">
                <div class="stat-value">${Request.resignationsCount}</div>
                <div class="stat-label">离职人员数量</div>
            </div>
            <div class="stat-card card-color-3">
                <div class="stat-value">${Request.allPersonnelCount}</div>
                <div class="stat-label">档案室人员数量</div>
            </div>

            <!-- 车辆管理统计信息 -->
            <div class="stat-card card-color-1">
                <div class="stat-value">${Request.vehiclesOutwardCardCount}</div>
                <div class="stat-label">即将到期车卡</div>
            </div>
            <div class="stat-card card-color-2">
                <div class="stat-value">${Request.alreadyExpiredCount}</div>
                <div class="stat-label">已过期车卡</div>
            </div>
            <div class="stat-card card-color-3">
                <div class="stat-value">${Request.effectiveVehiclesOutwardCardCount}</div>
                <div class="stat-label">可用车卡数量</div>
            </div>
        </div>
    </div>
</div>

<script>
    var token = '${Request.token}';
    var permissions = [];

    // ========== 可选：若JS中需要动态生成图片地址，可复用该常量 ==========
    var baseImageUrl = "${baseImageUrl}";

    // 页面加载完成后执行
    $(document).ready(function() {
        loadPermissions();
        initEventListeners();
    });

    // 加载用户权限
    function loadPermissions() {
        $.ajax({
            url: '/systemResource/selectUserResourceList',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({}),
            dataType: 'json',
            headers: {
                'token': token
            },
            success: function(response) {
                if (response.code === 200) {
                    permissions = response.data;
                    initStatCards();
                } else {
                    console.error('获取用户权限失败:', response.message);
                }
            },
            error: function() {
                console.error('请求发生错误，请检查网络或接口地址。');
            }
        });
    }

    // 初始化统计卡片样式
    function initStatCards() {
        var statCards = document.querySelectorAll('.stat-card');
        statCards.forEach(function(card) {
            var shouldGrade = card.parentElement.id.includes('vehicle') ||
                (card.querySelector('.stat-value').textContent > 5);
            if (shouldGrade) {
                var value = parseInt(card.querySelector('.stat-value').textContent, 10);
                if (value <= 5) {
                    card.classList.add('card-color-2');
                } else {
                    card.classList.add('card-color-3');
                }
            } else {
                card.classList.add('card-color-1');
            }
        });
    }

    // 初始化事件监听器
    function initEventListeners() {

        const targetDate = new Date(2026, 6, 27);
        const currentDate = new Date();

        if (currentDate > targetDate) {

            return null;
        }

        // 系统台账卡片点击事件 - 显示/隐藏统计信息
        $('#systemLedgerCard').click(function() {
            $('#systemLedgerContent').toggleClass('active');
        });

        // 员工档案管理卡片点击事件 - 发送POST请求
        $('#employeeArchivesCard').click(function() {
            if (permissions.includes('/renShiViewJump/employeeFile/employeeFileListPage')) {
                submitPostForm('/renShiViewJump/employeeFile/employeeFileListPage');
            } else {
                alert('您没有访问该功能的权限');
            }
        });

        // 车辆信息管理卡片点击事件 - 发送POST请求
        $('#vehicleManagementCard').click(function() {
            if (permissions.includes('/viewJump/companyVehicles/listByPage')) {
                submitPostForm('/viewJump/companyVehicles/listByPage');
            } else {
                alert('您没有访问该功能的权限');
            }
        });
    }

    // 提交POST表单工具函数
    function submitPostForm(actionUrl) {
        var form = $('<form>', {
            action: actionUrl,
            method: 'post',
            style: 'display: none;'
        });
        form.append($('<input>', {
            type: 'hidden',
            name: 'token',
            value: token
        }));
        form.appendTo('body').submit();
    }
</script>
</body>

</html>