<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>车辆信息页面</title>
    <#include "../common/head.ftl">
    <link rel="stylesheet" href="/css/mycss/vehicle-information-page.css">
    <style>
        #companyVehiclesListByPage {
            color: #ff9800;
        }

        /* 侧边导航栏基础样式（只保留布局相关，删除背景色等外观设置） */
        .sidebar-container {
            position: fixed !important;
            top: 50px !important; /* 匹配标题栏高度 */
            left: 0 !important;
            width: 200px !important; /* 固定宽度 */
            height: calc(100% - 50px) !important; /* 充满标题栏下方空间 */
            /* 删掉这行：background-color: #fff !important; */
            box-shadow: 2px 0 5px rgba(0,0,0,0.1) !important;
            z-index: 999 !important;
            overflow-y: auto !important;
        }

        /* 主内容容器（保持不变） */
        .main-container {
            margin-top: 50px;
            margin-left: 200px;
            padding: 20px;
            box-sizing: border-box;
            min-height: calc(100vh - 50px);
        }

        body {
            margin: 0;
            padding: 0;
            overflow-x: hidden;
        }

        /* 响应式适配（保持不变） */
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
<#include "../common/navigation.ftl"> <!-- 侧边导航栏（已添加） -->

<!-- 主内容容器：通过边距避开导航栏和标题栏 -->
<div class="main-container">
    <div class="layui-card">
        <div class="layui-card-body">
            <div class="query-form">
                <label for="carOwner">所属单位：</label>
                <input type="text" id="carOwner" placeholder="所属单位">
                <label for="carNumber">车牌号：</label>
                <input type="text" id="carNumber" placeholder="车牌号">
                <label for="carType">车辆类型：</label>
                <select class="carType layui-select" id="carType">
                    <option value="">全部</option>
                    <option value="1">特三</option>
                    <option value="2">丰田</option>
                    <option value="3">希尔</option>
                    <option value="4">五菱</option>
                    <option value="5">春星</option>
                    <option value="6">货</option>
                    <option value="7">长城货</option>
                    <option value="8">长城</option>
                    <option value="9">红宇</option>
                    <option value="10">鸿星达</option>
                    <option value="11">哈弗</option>
                    <option value="12">霸道</option>
                    <option value="13">雷克萨斯</option>
                    <option value="14">雪佛兰</option>
                    <option value="15">奔驰</option>
                    <option value="16">江特</option>
                    <option value="17">威尔法</option>
                    <option value="18">奥迪</option>
                    <option value="19">比亚迪</option>
                    <option value="20">酷路泽</option>
                    <option value="21">普拉多</option>
                </select>
                <label for="activeState">车辆使用状态：</label>
                <select class="activeState layui-select" id="activeState">
                    <option value="">全部</option>
                    <option value="1">可用</option>
                    <option value="2">外出中</option>
                </select>
                <label for="state">车身状态：</label>
                <select class="state layui-select" id="state">
                    <option value="">全部</option>
                    <option value="0">报废车辆</option>
                    <option value="1">正常使用</option>
                    <option value="2">维修中</option>
                    <option value="3">其他</option>
                </select>
                <label for="isManage">是否受车队管理：</label>
                <select class="isManage layui-select" id="isManage">
                    <option value="">全部</option>
                    <option value="0">不受车队管理车辆</option>
                    <option value="1" selected>受车队管理</option>
                </select>
                <div class="layui-inline">
                    <button id="queryButton" class="layui-btn blue">查询</button>
                </div>
            </div>
            <div class="layui-btn-group">
                <!-- 这里可以添加其他按钮 -->
            </div>

            <table id="insuranceTable">
                <thead>
                <tr>
                    <th class="hidden">ID</th>
                    <th>所属单位</th>
                    <th>车牌号</th>
                    <th>车型</th>
                    <th>车辆备注</th>
                    <th>车身状态</th>
                    <th>车辆使用状态</th>
                    <th>发车地址</th> <!-- 新增列 -->
                    <th id="returnColumn">还车操作</th>
                    <th id="openCardColumn">开卡操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>

            <div id="page"></div> <!-- 分页插件容器 -->
        </div>
    </div>
</div>

<script>
    // 定义全局变量来存储 token
    window.token = '${Request.token}';
</script>
<!-- 引入外部 JS 文件 -->
<script>
    window.token = '${Request.token}';
</script>
<script src="/js/myjs/vehicle-information-page.js"></script>
</body>

</html>