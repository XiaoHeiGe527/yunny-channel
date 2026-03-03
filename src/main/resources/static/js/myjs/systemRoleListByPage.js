layui.use(['form', 'laypage', 'layer'], function() {
    var form = layui.form;
    var laypage = layui.laypage;
    var layer = layui.layer;
    var $ = layui.$;

    // 角色管理参数
    var currentPage = 1;
    var pageSize = 20;
    var totalCount = 0;
    var addModalIndex = null;
    var editModalIndex = null;
    var currentEditRole = null;

    // 权限管理核心参数
    var FIXED_PERMISSION_IDS = [1, 8]; // 固定权限（不可取消）
    var permissionModalIndex = null;
    var allPermissions = []; // 所有权限数据
    var isAdminRole = false; // 是否为管理员角色

    // 页面初始化
    $(function() {
        loadRoleList();
        bindSearchEvent();
        bindAddRoleEvent();
    });

    /**
     * 角色列表相关功能
     */
    function bindSearchEvent() {
        form.on('submit(searchBtn)', function(data) {
            currentPage = 1;
            loadRoleList(data.field);
            return false;
        });
    }

    function bindAddRoleEvent() {
        $('#addRoleBtn').click(function() {
            openAddModal();
        });
    }

    function loadRoleList(params = {}) {
        var requestParams = {
            name: params.name || null,
            state: params.state !== undefined ? params.state : null,
            currentPage: currentPage,
            pageSize: pageSize
        };

        $.ajax({
            url: '/systemRole/listByPage',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(requestParams),
            dataType: 'json',
            headers: { 'token': window.token },
            success: function(res) {
                if (res.code === 200) {
                    var data = res.data;
                    totalCount = data.page.totalCount;
                    renderTable(data.dataList);
                    renderPagination(data.page);
                } else {
                    layer.msg('获取角色列表失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('请求失败，请检查网络', { icon: 5 });
            }
        });
    }

    function renderTable(dataList) {
        var $tbody = $('#roleTableBody');
        $tbody.empty();

        if (dataList.length === 0) {
            $tbody.append(`<tr><td colspan="7" style="text-align: center;">暂无角色数据</td></tr>`);
            return;
        }

        dataList.forEach(role => {
            var typeText = role.type === 0 ? '<span class="type-admin">管理员</span>' : '<span class="type-normal">普通</span>';
        var stateText = role.state === 1 ? '<span class="state-valid">有效</span>' : '<span class="state-invalid">无效</span>';
        var isAdmin = role.type === 0;

        var editBtn = isAdmin ?
            '<button class="layui-btn layui-btn-xs layui-btn-disabled" disabled><i class="layui-icon layui-icon-edit"></i> 编辑</button>' :
            `<button class="layui-btn layui-btn-xs" onclick="editRole(${role.id})"><i class="layui-icon layui-icon-edit"></i> 编辑</button>`;

        var deleteBtn = isAdmin ?
            '<button class="layui-btn layui-btn-xs layui-btn-danger layui-btn-disabled" disabled><i class="layui-icon layui-icon-delete"></i> 删除</button>' :
            `<button class="layui-btn layui-btn-xs layui-btn-danger" onclick="deleteRole(${role.id}, '${role.name}')"><i class="layui-icon layui-icon-delete"></i> 删除</button>`;

        $tbody.append(`
                <tr>
                    <td>${role.id}</td>
                    <td>${role.name || '-'}</td>
                    <td>${role.content || '-'}</td>
                    <td>${typeText}</td>
                    <td>${stateText}</td>
                    <td>${role.createTime || '-'}</td>
                    <td>
                        ${editBtn}
                        ${deleteBtn}
                        <button class="layui-btn layui-btn-xs layui-btn-primary" onclick="assignPermission(${role.id}, ${role.type})">
                            <i class="layui-icon layui-icon-key"></i> 权限分配
                        </button>
                    </td>
                </tr>
            `);
    });
    }

    function renderPagination(pageInfo) {
        laypage.render({
            elem: 'pagination',
            count: totalCount,
            limit: pageSize,
            curr: currentPage,
            layout: ['prev', 'page', 'next', 'count', 'limit'],
            limits: [10, 20, 30, 50],
            jump: function(obj, first) {
                if (!first) {
                    currentPage = obj.curr;
                    pageSize = obj.limit;
                    loadRoleList(form.val('searchForm'));
                }
            }
        });
    }

    /**
     * 新增角色功能
     */
    function openAddModal() {
        form.val('addRoleForm', { name: '', content: '', state: 1 });
        addModalIndex = layer.open({
            type: 1,
            title: '新增角色',
            content: $('#addRoleModal'),
            area: ['500px', '400px'],
            btn: []
        });
    }
    window.closeAddModal = function() {
        layer.close(addModalIndex);
    };
    form.on('submit(doAddRole)', function(data) {
        submitAdd(data.field, addModalIndex);
        return false;
    });
    function submitAdd(formData, index) {
        $.ajax({
            url: '/systemRole/create',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(formData),
            headers: { 'token': window.token },
            success: function(res) {
                if (res.code === 200) {
                    layer.msg('新增成功', { icon: 1 });
                    layer.close(index);
                    loadRoleList(form.val('searchForm'));
                } else {
                    layer.msg('新增失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('新增请求失败', { icon: 5 });
            }
        });
    }

    /**
     * 编辑角色功能
     */
    window.editRole = function(id) {
        $.ajax({
            url: `/systemRole/getById?id=${id}`,
            type: 'GET',
            headers: { 'token': window.token },
            success: function(res) {
                if (res.code === 200 && res.data) {
                    currentEditRole = res.data;
                    var role = res.data;
                    form.val('editRoleForm', {
                        id: role.id,
                        name: role.name,
                        content: role.content || '',
                        state: role.state,
                        type: role.type
                    });
                    if (role.type === 0) {
                        $('#editRoleName,#editRoleContent').attr('disabled', true).addClass('layui-disabled');
                        $('#editRoleState').attr('disabled', true);
                    } else {
                        $('#editRoleName,#editRoleContent').removeAttr('disabled').removeClass('layui-disabled');
                        $('#editRoleState').removeAttr('disabled');
                    }
                    form.render();
                    editModalIndex = layer.open({
                        type: 1,
                        title: '编辑角色',
                        content: $('#editRoleModal'),
                        area: ['500px', '400px'],
                        btn: []
                    });
                } else {
                    layer.msg('获取角色详情失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('获取角色详情失败', { icon: 5 });
            }
        });
    };
    window.closeEditModal = function() {
        layer.close(editModalIndex);
    };
    form.on('submit(doEditRole)', function(data) {
        if (currentEditRole && currentEditRole.type === 0) {
            layer.msg('管理员角色不可编辑', { icon: 5 });
            return false;
        }
        submitEdit(data.field, editModalIndex);
        return false;
    });
    function submitEdit(formData, index) {
        $.ajax({
            url: '/systemRole/update',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(formData),
            headers: { 'token': window.token },
            success: function(res) {
                if (res.code === 200) {
                    layer.msg('修改成功', { icon: 1 });
                    layer.close(index);
                    loadRoleList(form.val('searchForm'));
                } else {
                    layer.msg('修改失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('修改请求失败', { icon: 5 });
            }
        });
    }

    /**
     * 删除角色功能（完善版）
     */
    /**
     * 删除角色功能（适配@RequestBody接收，raw JSON格式）
     */
    window.deleteRole = function(id, name) {
        // 二次确认弹窗，明确操作风险
        layer.confirm(`确定要删除角色【${name}】吗？删除后不可恢复！`, {
            icon: 3,
            title: '删除确认',
            btn: ['确认删除', '取消']
        }, function(confirmIndex) {
            // 构造符合SystemRoleDTO的JSON参数（字段名必须为id，与DTO属性匹配）
            const requestData = {
                id: id // 对应DTO中的id字段
            };

            $.ajax({
                url: '/systemRole/deleteById',
                type: 'POST',
                contentType: 'application/json; charset=utf-8', // 明确指定为JSON格式（raw JSON的核心）
                data: JSON.stringify(requestData), // 序列化为JSON字符串（raw JSON的要求）
                headers: { 'token': window.token },
                success: function(res) {
                    // 处理后台返回结果（外层BaseResult + 内层业务结果）
                    if (res.code === 200) {
                        const businessResult = res.data;
                        if (businessResult.code === 200) {
                            layer.msg('删除成功', { icon: 1 });
                            // 刷新角色列表
                            loadRoleList(form.val('searchForm'));
                        } else {
                            // 显示业务错误信息（如角色绑定用户）
                            layer.msg(`删除失败：${businessResult.message}`, { icon: 5 });
                        }
                    } else {
                        // 外层接口异常
                        layer.msg(`接口请求失败：${res.message || '未知错误'}`, { icon: 5 });
                    }
                },
                error: function(xhr) {
                    // 网络或服务器异常
                    layer.msg(`请求失败：${xhr.statusText || '网络错误'}`, { icon: 5 });
                }
            });

            layer.close(confirmIndex); // 关闭确认弹窗
        });
    };

    /**
     * 权限分配核心功能
     */
    window.assignPermission = function(roleId, roleType) {
        $('#roleId').val(roleId);
        isAdminRole = roleType === 0;
        allPermissions = [];

        // 管理员角色不可操作权限按钮
        if (isAdminRole) {
            $('#permissionOperateGroup button').addClass('layui-btn-disabled').attr('disabled', true);
        } else {
            $('#permissionOperateGroup button').removeClass('layui-btn-disabled').removeAttr('disabled');
        }

        loadAllPermissions(roleId);
        permissionModalIndex = layer.open({
            type: 1,
            title: isAdminRole ? '管理员角色权限' : '角色权限绑定',
            content: $('#permissionModal'),
            area: ['900px', '800px'],
            btn: []
        });
    };

    window.closePermissionModal = function() {
        if (!isAdminRole) layer.close(permissionModalIndex);
    };

    /**
     * 加载所有权限数据并渲染
     */
    function loadAllPermissions(roleId) {
        $.ajax({
            url: '/systemResource/listByQuery',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ state: 1 }),
            dataType: 'json',
            headers: { 'token': window.token },
            success: function(res) {
                if (res.code === 200 && res.data && res.data.length > 0) {
                    allPermissions = res.data;
                    if (isAdminRole) {
                        // 管理员默认全选所有权限
                        renderPermissionList(allPermissions.map(p => p.id));
                    } else {
                        // 普通角色加载已有权限
                        loadRolePermissions(roleId);
                    }
                } else {
                    $('#permissionContainer').html('<div style="padding: 20px; text-align: center; color: #999;">暂无可用权限</div>');
                }
            },
            error: function() {
                layer.msg('加载权限列表失败', { icon: 5 });
            }
        });
    }

    /**
     * 加载角色已有权限并渲染
     */
    function loadRolePermissions(roleId) {
        $.ajax({
            url: '/systemResource/listByRoleId',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ roleId: roleId }),
            dataType: 'json',
            headers: { 'token': window.token },
            success: function(res) {
                let checkedIds = [];
                if (res.code === 200 && res.data) {
                    checkedIds = res.data.map(p => p.id);
                }
                // 强制保留固定权限
                FIXED_PERMISSION_IDS.forEach(id => {
                    if (!checkedIds.includes(id)) checkedIds.push(id);
            });
                renderPermissionList(checkedIds);
            },
            error: function() {
                layer.msg('加载角色权限失败', { icon: 5 });
                renderPermissionList(FIXED_PERMISSION_IDS); // 至少保留固定权限
            }
        });
    }

    /**
     * 渲染权限列表（层级结构）
     * @param {Array} checkedIds - 需要勾选的权限ID集合
     */
    function renderPermissionList(checkedIds) {
        const $container = $('#permissionContainer');
        $container.empty();

        if (allPermissions.length === 0) {
            $container.html('<div style="padding: 20px; text-align: center; color: #999;">暂无权限数据</div>');
            return;
        }

        // 筛选一级权限（parentId=0）
        const level1Permissions = allPermissions.filter(perm => perm.parentId === 0);

        // 构建二级权限映射（key: 一级权限ID）
        const level2Map = new Map();
        level1Permissions.forEach(level1 => {
            level2Map.set(level1.id, allPermissions.filter(perm => perm.parentId === level1.id));
    });

        // 构建三级权限映射（key: 二级权限ID）
        const level3Map = new Map();
        Array.from(level2Map.values()).forEach(level2List => {
            level2List.forEach(level2 => {
                level3Map.set(level2.id, allPermissions.filter(perm => perm.parentId === level2.id));
    });
    });

        // 渲染一级权限
        level1Permissions.forEach(level1 => {
            const level1Id = level1.id;
        const isChecked = checkedIds.includes(level1Id);
        const isDisabled = isAdminRole || FIXED_PERMISSION_IDS.includes(level1Id);
        const level2List = level2Map.get(level1Id) || [];

        let level1Html = `
                <div class="permission-level1" style="margin: 10px 0; padding: 10px; border: 1px solid #eee; border-radius: 4px;">
                    <input type="checkbox" 
                           class="permission-checkbox" 
                           name="permission" 
                           lay-skin="primary" 
                           value="${level1Id}" 
                           ${isChecked ? 'checked' : ''} 
                           ${isDisabled ? 'disabled' : ''}
                           title="${level1.name}">
            `;

        // 渲染二级权限
        if (level2List.length > 0) {
            level1Html += `<div class="permission-level2-container" style="margin-left: 30px; margin-top: 10px;">`;

            level2List.forEach(level2 => {
                const level2Id = level2.id;
            const isL2Checked = checkedIds.includes(level2Id);
            const isL2Disabled = isAdminRole || FIXED_PERMISSION_IDS.includes(level2Id);
            const level3List = level3Map.get(level2Id) || [];

            level1Html += `
                        <div class="permission-level2" style="margin: 8px 0;">
                            <input type="checkbox" 
                                   class="permission-checkbox" 
                                   name="permission" 
                                   lay-skin="primary" 
                                   value="${level2Id}" 
                                   ${isL2Checked ? 'checked' : ''} 
                                   ${isL2Disabled ? 'disabled' : ''}
                                   title="${level2.name}">
                    `;

            // 渲染三级权限
            if (level3List.length > 0) {
                level1Html += `<div class="permission-level3-container" style="margin-left: 30px; margin-top: 5px;">`;

                level3List.forEach(level3 => {
                    const level3Id = level3.id;
                const isL3Checked = checkedIds.includes(level3Id);
                const isL3Disabled = isAdminRole || FIXED_PERMISSION_IDS.includes(level3Id);

                level1Html += `
                                <div class="permission-level3" style="margin: 5px 0;">
                                    <input type="checkbox" 
                                           class="permission-checkbox" 
                                           name="permission" 
                                           lay-skin="primary" 
                                           value="${level3Id}" 
                                           ${isL3Checked ? 'checked' : ''} 
                                           ${isL3Disabled ? 'disabled' : ''}
                                           title="${level3.name}">
                                </div>
                            `;
            });

                level1Html += `</div>`; // 闭合三级容器
            }

            level1Html += `</div>`; // 闭合二级权限
        });

            level1Html += `</div>`; // 闭合二级容器
        }

        level1Html += `</div>`; // 闭合一级权限

        $container.append(level1Html);
    });

        // 刷新LayUI样式
        form.render('checkbox');
    }

    /**
     * 全选/取消全选/游客模式（直接操作DOM）
     */
    $('#selectAllBtn').click(function() {
        if (isAdminRole) return;
        // 全选所有非禁用权限
        $('#permissionContainer input.permission-checkbox:not(:disabled)').prop('checked', true);
        form.render('checkbox');
    });

    $('#unselectAllBtn').click(function() {
        if (isAdminRole) return;
        // 取消全选（保留固定权限）
        $('#permissionContainer input.permission-checkbox:not(:disabled)').each(function() {
            const $cb = $(this);
            if (!FIXED_PERMISSION_IDS.includes(parseInt($cb.val()))) {
                $cb.prop('checked', false);
            }
        });
        form.render('checkbox');
    });

    $('#visitorModeBtn').click(function() {
        if (isAdminRole) return;
        const visitorPermissionIds = [8,18,34,37,38,39,40,35,17,53,56,62,63,64,65,47,52,57,66,67,1];
        // 只勾选游客权限和固定权限
        $('#permissionContainer input.permission-checkbox:not(:disabled)').each(function() {
            const $cb = $(this);
            const id = parseInt($cb.val());
            $cb.prop('checked', visitorPermissionIds.includes(id) || FIXED_PERMISSION_IDS.includes(id));
        });
        form.render('checkbox');
        layer.msg('已切换为游客模式权限', { icon: 1 });
    });

    /**
     * 提交权限（从DOM读取实际勾选状态）
     */
    form.on('submit(submitPermission)', function() {
        const roleId = $('#roleId').val();
        // 读取所有勾选的权限ID
        const checkedIds = [];
        $('#permissionContainer input.permission-checkbox:checked').each(function() {
            checkedIds.push(parseInt($(this).val()));
        });

        // 强制保留固定权限（防止意外取消）
        FIXED_PERMISSION_IDS.forEach(id => {
            if (!checkedIds.includes(id)) checkedIds.push(id);
    });

        // 去重处理
        const uniqueIds = [...new Set(checkedIds)];

        if (uniqueIds.length === 0) {
            layer.msg('请至少选择一个权限', { icon: 3 });
            return false;
        }

        $.ajax({
            url: '/systemResource/assignPermissions',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                roleId: roleId,
                resourceId: uniqueIds
            }),
            headers: { 'token': window.token },
            success: function(res) {
                if (res.code === 200) {
                    layer.msg('权限绑定成功', { icon: 1 });
                    layer.close(permissionModalIndex);
                } else {
                    layer.msg('绑定失败：' + res.message, { icon: 5 });
                }
            },
            error: function() {
                layer.msg('绑定请求失败', { icon: 5 });
            }
        });

        return false;
    });
});