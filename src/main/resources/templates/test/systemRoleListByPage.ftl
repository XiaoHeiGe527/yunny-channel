<!DOCTYPE html>
<html>
<head>
    <#include "../common/head.ftl">
    <title>角色管理</title>
    <style>
        .permission-level1 {
            margin: 8px 0;
            padding-left: 10px;
        }
        .permission-level2-container {
            margin-left: 20px;
            padding-left: 10px;
            border-left: 1px dashed #ccc;
        }
        .permission-level2 {
            margin: 6px 0;
        }
        .permission-level3-container {
            margin-left: 20px;
            padding-left: 10px;
            border-left: 1px dashed #eee;
        }
        .permission-level3 {
            margin: 4px 0;
        }

        .layui-card { margin: 15px; }
        .search-form { padding: 15px; background-color: #f5f5f5; border-radius: 4px; }
        .layui-btn-group { margin-bottom: 10px; }
        .state-valid { color: #009688; }
        .state-invalid { color: #ff4500; }
        .type-admin { color: #1E90FF; }
        .type-normal { color: #333; }
        .layui-form-label { min-width: 90px; white-space: nowrap; }
        .modal-form { padding: 20px; }
        .modal-form .layui-form-item { margin-bottom: 15px; }
        .modal-form .layui-form-label { width: 90px; }
        .modal-form .layui-input-block { margin-left: 120px; }

        /* 三级权限样式 */
        #permissionContainer {
            max-height: 600px;
            overflow-y: auto;
            border: 1px solid #eee;
            border-radius: 4px;
            padding: 20px;
        }
        .level1-permission {
            margin: 20px 0;
            padding: 10px;
            background-color: #f5f5f5;
            border-radius: 4px;
        }
        .level1-permission .layui-form-checkbox {
            font-weight: 600;
            font-size: 15px;
        }
        .level2-container {
            margin-left: 30px;
            margin-top: 10px;
        }
        .level2-permission {
            margin: 15px 0;
            padding: 8px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }
        .level2-permission .layui-form-checkbox {
            font-weight: 500;
            font-size: 14px;
        }
        .level3-container {
            margin-left: 60px;
            margin-top: 8px;
        }
        .level3-permission {
            margin: 8px 0;
        }
        .layui-form-checkbox[lay-skin="primary"] i.indeterminate {
            background-color: #ccc;
            color: #fff;
        }
        #permissionContainer::-webkit-scrollbar { width: 8px; }
        #permissionContainer::-webkit-scrollbar-thumb { background-color: #ccc; border-radius: 4px; }
        .layui-btn-disabled { cursor: not-allowed !important; opacity: 0.6; }

        /* 布局偏移 */
        .layui-card {
            margin: 15px;
            margin-left: 220px;
            margin-top: 65px;
        }
    </style>
</head>
<body>
<#include "../common/inHeader.ftl">
<#include "../common/navigation.ftl">

<div class="layui-card">
    <div class="layui-card-header">
        <h2>角色管理</h2>
    </div>
    <div class="layui-card-body">
        <!-- 搜索表单 -->
        <form class="layui-form search-form" lay-filter="searchForm">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-inline" style="width: 200px;">
                        <input type="text" name="name" placeholder="请输入角色名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">角色状态</label>
                    <div class="layui-input-inline">
                        <select name="state">
                            <option value="">全部</option>
                            <option value="1">有效</option>
                            <option value="0">无效</option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="searchBtn">查询</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>

        <!-- 操作按钮 -->
        <div class="layui-btn-group">
            <button class="layui-btn layui-btn-primary" id="addRoleBtn">
                <i class="layui-icon layui-icon-add-1"></i> 新增角色
            </button>
        </div>

        <!-- 角色表格 -->
        <table class="layui-table" lay-size="sm">
            <thead>
            <tr>
                <th style="width: 60px;">ID</th>
                <th>角色名称</th>
                <th>角色说明</th>
                <th>角色类型</th>
                <th>角色状态</th>
                <th>创建时间</th>
                <th style="width: 160px;">操作</th>
            </tr>
            </thead>
            <tbody id="roleTableBody"></tbody>
        </table>

        <!-- 分页 -->
        <div id="pagination" style="text-align: right;"></div>
    </div>
</div>

<!-- 新增角色弹窗 -->
<div id="addRoleModal" style="display: none;" class="modal-form">
    <form class="layui-form" lay-filter="addRoleForm">
        <div class="layui-form-item">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-block">
                <input type="text" name="name" placeholder="请输入角色名称" required
                       lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色说明</label>
            <div class="layui-input-block">
                <textarea name="content" placeholder="请输入角色说明（可选）"
                          rows="4" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色状态</label>
            <div class="layui-input-block">
                <select name="state" lay-verify="required">
                    <option value="1" selected>有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item" style="text-align: right;">
            <button class="layui-btn" lay-submit lay-filter="doAddRole">确认新增</button>
            <button type="button" class="layui-btn layui-btn-primary" onclick="closeAddModal()">取消</button>
        </div>
    </form>
</div>

<!-- 编辑角色弹窗 -->
<div id="editRoleModal" style="display: none;" class="modal-form">
    <form class="layui-form" lay-filter="editRoleForm">
        <input type="hidden" name="id">
        <input type="hidden" name="type">
        <div class="layui-form-item">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-block">
                <input type="text" id="editRoleName" name="name" placeholder="请输入角色名称" required
                       lay-verify="required" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色说明</label>
            <div class="layui-input-block">
                <textarea id="editRoleContent" name="content" placeholder="请输入角色说明（可选）"
                          rows="4" class="layui-textarea"></textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色状态</label>
            <div class="layui-input-block">
                <select id="editRoleState" name="state" lay-verify="required">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item" style="text-align: right;">
            <button class="layui-btn" lay-submit lay-filter="doEditRole">确认修改</button>
            <button type="button" class="layui-btn layui-btn-primary" onclick="closeEditModal()">取消</button>
        </div>
    </form>
</div>

<!-- 权限分配弹窗 -->
<div id="permissionModal" style="display: none; padding: 15px;">
    <div class="layui-form" lay-filter="permissionForm">
        <input type="hidden" id="roleId">
        <input type="hidden" id="roleType">

        <div class="layui-btn-group" style="margin-bottom: 15px;" id="permissionOperateGroup">
            <button class="layui-btn layui-btn-primary" id="selectAllBtn">
                <i class="layui-icon layui-icon-check-square"></i> 全选
            </button>
            <button class="layui-btn layui-btn-primary" id="unselectAllBtn">
                <i class="layui-icon layui-icon-square"></i> 取消全选
            </button>
            <button class="layui-btn layui-btn-normal" id="visitorModeBtn">
                <i class="layui-icon layui-icon-user"></i> 游客模式
            </button>
        </div>

        <div id="permissionContainer"></div>

        <div class="layui-form-item" style="margin-top: 15px; text-align: right;">
            <button class="layui-btn" lay-submit lay-filter="submitPermission">确认绑定</button>
            <button type="button" class="layui-btn layui-btn-primary" id="cancelPermissionBtn" onclick="closePermissionModal()">取消</button>
        </div>
    </div>
</div>

<!-- 全局变量定义 -->
<script>
    window.token = '${Request.token!""}';
</script>
<!-- 引入外部JS -->
<script src="/js/myjs/systemRoleListByPage.js"></script>
</body>
</html>