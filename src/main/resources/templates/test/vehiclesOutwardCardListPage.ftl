<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>车开卡列表</title>
    <#include "../common/head.ftl"> <!-- 先引入基础样式（含侧边栏样式） -->
    <link rel="stylesheet" href="/css/mycss/vehiclesOutwardCardListPage.css">
    <!-- 在<head>标签内的样式区域添加 -->
    <style>
        /* 主内容区偏移：避开侧边栏（宽度200px）和顶部导航栏（高度50px） */
        .layui-card {
            margin-left: 220px; /* 大于侧边栏宽度200px，留出间隙 */
            margin-top: 65px; /* 大于顶部导航栏高度50px，避免被顶部栏遮挡 */
            margin-right: 15px; /* 右侧留一点间隙，美观 */
        }

        /* 如果表格内容被挤压，可补充表格宽度自适应 */
        #insuranceTable {
            width: 100%;
            min-width: 800px; /* 避免内容过窄时错乱 */
        }
    </style>
</head>
<body>
<#include "../common/inHeader.ftl"> <!-- 引入侧边导航栏 -->
<#include "../common/navigation.ftl"> <!-- 二级导航放在侧边栏之后 -->

<!-- 主内容区（关键：与角色页面保持一致的偏移逻辑） -->
<div class="layui-card">
    <div class="layui-card-header">
        <h2>车开卡管理</h2>
    </div>
    <div class="layui-card-body">
        <!-- 搜索表单 -->
        <form class="layui-form search-form" lay-filter="searchForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">客户名称</label>
                    <div class="layui-input-inline" style="width: 200px;">
                        <input type="text" id="vehicleCustomersName" placeholder="请选择客户" autocomplete="off" class="layui-input">
                        <div id="customerList"></div>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">车型</label>
                    <div class="layui-input-inline">
                        <select class="layui-select" id="carType" lay-search>
                            <option value="">请选择车型</option>
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
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">开卡开始时间</label>
                    <div class="layui-input-inline">
                        <input type="text" id="openDateDateStart" placeholder="YYYY-MM-DD HH:MM:SS" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">开卡结束时间</label>
                    <div class="layui-input-inline">
                        <input type="text" id="openDateDateEnd" placeholder="YYYY-MM-DD HH:MM:SS" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">车牌号</label>
                    <div class="layui-input-inline">
                        <input type="text" id="carNumber" placeholder="车牌号" autocomplete="off" class="layui-input">
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">临期卡</label>
                    <div class="layui-input-inline">
                        <select id="isReminder" class="layui-select">
                            <option value="true">查询临期</option>
                            <option value="false" selected>查询所有</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <label class="layui-form-label">卡状态</label>
                    <div class="layui-input-inline">
                        <select class="layui-select" id="state">
                            <option value="1" selected>可用卡</option>
                            <option value="0">已使用的卡</option>
                            <option value="3">到期未使用</option>
                        </select>
                    </div>
                </div>

                <div class="layui-inline">
                    <button id="queryButton" class="layui-btn layui-btn-normal" lay-submit lay-filter="searchBtn">查询</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>

        <!-- 操作按钮组 -->
        <div class="layui-btn-group">
            <!-- 可添加按钮 -->
        </div>

        <!-- 表格 -->
        <table class="layui-table" lay-size="sm" id="insuranceTable">
            <thead>
            <tr>
                <th class="hidden">ID</th>
                <th class="hidden">cardNo</th>
                <th>开卡车牌号</th>
                <th>开卡车型</th>
                <th>开卡客户</th>
                <th>开卡日期</th>
                <th>提醒用卡日期</th>
                <th>卡到期日期</th>
                <th>卡状态</th>
                <th>车辆使用状态</th>
                <th>备注</th>
                <th id="departColumn">操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <!-- 分页控件 -->
        <div id="pageNav" style="text-align: right;"></div>
    </div>
</div>

<script>window.token = '${Request.token}';</script>
<script src="/js/myjs/vehiclesOutwardCardListPage.js"></script>
</body>
</html>