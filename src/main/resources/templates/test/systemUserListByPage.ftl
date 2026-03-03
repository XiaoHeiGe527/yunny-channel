<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户管理</title>
    <#include "../common/head.ftl">
    <style>
        /* 查询表单样式优化 */
        .query-form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            align-items: center;
            margin-bottom: 20px;
            background-color: #ffffff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        .query-form label {
            margin-right: 5px;
        }

        .query-form .layui-input,
        .query-form .layui-select {
            width: 160px;
        }

        .query-form button {
            margin-left: 5px;
        }

        /* 顶部按钮区域 */
        .toolbar {
            margin-bottom: 15px;
        }

        /* 状态标签样式 */
        .state-tag {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
        .state-valid {
            background-color: #e5f5e5;
            color: #2a9d54;
        }
        .state-invalid {
            background-color: #fdecea;
            color: #d9534f;
        }

        /* 编辑弹窗表单样式 */
        .edit-form .layui-form-item {
            margin-bottom: 15px;
        }
        .edit-form .layui-form-label {
            width: 90px;
        }
        .edit-form .layui-input-block {
            margin-left: 120px;
        }

        /* 主内容区偏移（同时适配侧边导航和顶部标题栏） */
        .layui-card {
            margin: 15px;
            margin-left: 220px; /* 避开侧边导航（200px宽度+20px间距） */
            margin-top: 65px; /* 避开顶部标题栏（50px高度+15px间距） */
        }
    </style>
</head>

<body>
<!-- 顶部导航 -->
<#include "../common/inHeader.ftl">

<!-- 侧边导航 -->
<#include "../common/navigation.ftl">

<!-- 主内容区 -->
<div class="layui-card">
    <div class="layui-card-body">
        <!-- 顶部工具栏 -->
        <div class="toolbar">
            <button id="addButton" class="layui-btn layui-btn-radius layui-btn-normal">
                <i class="layui-icon layui-icon-add-circle"></i> 创建账号
            </button>
        </div>

        <!-- 查询条件表单 -->
        <div class="query-form">
            <label for="loginName">登录账号：</label>
            <input type="text" id="loginName" placeholder="请输入登录账号" class="layui-input">

            <label for="name">账号昵称：</label>
            <input type="text" id="name" placeholder="请输入账号昵称" class="layui-input">

            <label for="state">账号状态：</label>
            <select id="state" class="layui-select">
                <option value="">全部状态</option>
                <option value="1">有效</option>
                <option value="0">无效</option>
            </select>

            <button id="queryButton" class="layui-btn layui-btn-radius layui-btn-normal">查询</button>
            <button id="resetButton" class="layui-btn layui-bg-orange">重置查询</button>
        </div>

        <!-- 数据表格 -->
        <table id="userTable" class="layui-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>用户号</th>
                <th>登录账号</th>
                <th>账号昵称</th>
                <th>账号状态</th>
                <th>创建日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <!-- 分页导航 -->
        <div id="pageNav"></div>
    </div>
</div>

<!-- 编辑弹窗表单（隐藏） -->
<div id="editModal" style="display: none; padding: 20px;">
    <form class="layui-form edit-form" lay-filter="editForm">
        <input type="hidden" name="id" id="editId">
        <div class="layui-form-item">
            <label class="layui-form-label">用户号</label>
            <div class="layui-input-block">
                <input type="text" name="userNo" id="editUserNo" readonly class="layui-input" lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">账号昵称</label>
            <div class="layui-input-block">
                <input type="text" name="name" id="editName" placeholder="请输入账号昵称" class="layui-input" lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">账号状态</label>
            <div class="layui-input-block">
                <select name="state" id="editState" lay-verify="required">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </div>

        <!-- 新增角色选择区域 -->
        <div class="layui-form-item">
            <label class="layui-form-label">所属角色</label>
            <div class="layui-input-block" id="editRoleList">
                <div class="layui-anim layui-anim-loading">加载角色中...</div>
            </div>
        </div>
    </form>
</div>
<!-- 创建用户弹窗 -->
<div id="createModal" style="display: none; padding: 20px;">
    <form class="layui-form create-form" lay-filter="createForm">
        <div class="layui-form-item">
            <label class="layui-form-label">登录账号</label>
            <div class="layui-input-block">
                <input type="text" name="loginName" id="createLoginName"
                       placeholder="请输入登录账号（6-16位）" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">用户姓名</label>
            <div class="layui-input-block">
                <input type="text" name="name" id="createName"
                       placeholder="请输入姓名（2-10位）" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="password" name="password" id="createPassword"
                       placeholder="请输入密码（至少6位）" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色选择</label>
            <div class="layui-input-block" id="roleList">
                <div class="layui-anim layui-anim-loading">加载角色中...</div>
            </div>
        </div>
    </form>
</div>

<!-- 传递token给JS -->
<script>
    window.token = '${Request.token}';
    // 导航配置（如果需要）
    window.navConfig = { token: window.token };
</script>
<script src="/js/myjs/systemUserList.js"></script>
</body>

</html>