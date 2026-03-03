// 等待DOM加载完成
$(document).ready(function () {
    layui.use(['laypage', 'layer', 'form'], function () {
        var laypage = layui.laypage;
        var layer = layui.layer;
        var form = layui.form;

        var token = window.token;
        var currentPage = 1;
        var pageSize = 20;
        var editIndex = null;
        var createIndex = null;

        // 初始化查询用户数据
        fetchUserData(currentPage, pageSize);

        /**
         * 获取用户数据
         */
        function fetchUserData(page, size) {
            var params = {
                loginName: $('#loginName').val().trim() || null,
                name: $('#name').val().trim() || null,
                state: $('#state').val() || null,
                currentPage: page,
                pageSize: size
            };

            $.ajax({
                url: '/systemUser/listByPage',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(params),
                dataType: 'json',
                headers: { 'token': token },
                success: function (response) {
                    if (response.code === 200) {
                        var data = response.data;
                        renderUserTable(data.dataList);
                        renderPagination(data.page);
                    } else {
                        layer.msg('查询失败：' + response.message, { icon: 5 });
                    }
                },
                error: function (error) {
                    console.error('请求出错:', error);
                    layer.msg('网络请求失败', { icon: 5 });
                }
            });
        }

        /**
         * 渲染用户表格
         */
        function renderUserTable(dataList) {
            var tbody = $('#userTable tbody');
            tbody.empty();

            if (!dataList || dataList.length === 0) {
                tbody.append('<tr><td colspan="7" style="text-align: center;">暂无数据</td></tr>');
                return;
            }

            $.each(dataList, function (index, item) {
                var stateText = item.state === 1 ? '有效' : '无效';
                var stateClass = item.state === 1 ? 'state-valid' : 'state-invalid';
                var userNo = item.userNo || '';

                var operationHtml = '<div class="layui-btn-group">' +
                    '<button class="layui-btn layui-btn-xs layui-btn-primary edit-btn" ' +
                    'data-id="' + (item.id || '') + '" ' +
                    'data-userno="' + userNo + '" ' +
                    'data-name="' + (item.name || '') + '" ' +
                    'data-state="' + (item.state || 0) + '">编辑</button>' +
                    '</div>';

                var row = '<tr>' +
                    '<td>' + (item.id || '') + '</td>' +
                    '<td class="user-no-cell">' + userNo + '</td>' +
                    '<td>' + (item.loginName || '') + '</td>' +
                    '<td>' + (item.name || '') + '</td>' +
                    '<td><span class="state-tag ' + stateClass + '">' + stateText + '</span></td>' +
                    '<td>' + (item.createTime || '') + '</td>' +
                    '<td>' + operationHtml + '</td>' +
                    '</tr>';

                tbody.append(row);
            });

            $('.edit-btn').click(function () {
                var btnData = $(this).data();
                openEditModal(btnData);
            });
        }

        /**
         * 渲染分页控件
         */
        function renderPagination(pager) {
            laypage.render({
                elem: 'pageNav',
                count: pager.totalCount,
                curr: pager.currentPage,
                limit: pager.pageSize,
                limits: [10, 20, 50],
                layout: ['count', 'prev', 'page', 'next', 'limit'],
                jump: function (obj, first) {
                    if (!first) {
                        currentPage = obj.curr;
                        pageSize = obj.limit;
                        fetchUserData(currentPage, pageSize);
                    }
                }
            });
        }

        /**
         * 打开编辑弹窗
         */
        function openEditModal(data) {
            var userNo = data.userno || '';
            if (!userNo) {
                layer.msg('用户号获取失败，请刷新页面重试', { icon: 5 });
                return;
            }

            // 填充基础数据
            $('#editId').val(data.id || '');
            $('#editUserNo').val(userNo);
            $('#editName').val(data.name || '');
            $('#editState').val(data.state !== undefined ? data.state : 1);
            form.render('select');

            // 加载角色列表并匹配用户角色ID
            loadRolesAndMatch(userNo);

            // 打开弹窗
            editIndex = layer.open({
                type: 1,
                title: '编辑用户',
                content: $('#editModal'),
                area: ['450px', '450px'],
                btn: ['确认修改', '取消'],
                btn1: function (index) {
                    var userNo = $('#editUserNo').val().trim();
                    var name = $('#editName').val().trim();
                    var roleId = $('input[name="editRoleId"]:checked').val();

                    if (!userNo) {
                        layer.tips('用户号不能为空', '#editUserNo', { tips: 1 });
                        return;
                    }
                    if (!name) {
                        layer.tips('账号昵称不能为空', '#editName', { tips: 1 });
                        return;
                    }
                    if (!roleId) {
                        layer.tips('请选择角色', '#editRoleList', { tips: 1 });
                        return;
                    }

                    submitUpdate({
                        userNo: userNo,
                        name: name,
                        state: $('#editState').val(),
                        roleId: roleId
                    }, index);
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }

        /**
         * 加载所有角色并匹配用户角色ID
         */
        function loadRolesAndMatch(userNo) {
            // 1. 先加载所有有效角色
            $.ajax({
                url: '/systemRole/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({ state: '1' }),
                dataType: 'json',
                headers: { 'token': token },
                success: function (roleRes) {
                    console.log('所有角色接口返回:', roleRes);
                    if (roleRes.code !== 200 || !roleRes.data || roleRes.data.length === 0) {
                        $('#editRoleList').html('<div style="color: #ff4d4f;">暂无可用角色</div>');
                        return;
                    }
                    var allRoles = roleRes.data;

                    // 2. 获取用户角色ID（从response.data.roleId提取）
                    $.ajax({
                        url: '/systemUserRole/getByUserNo',
                        type: 'GET',
                        data: { userNo: userNo },
                        dataType: 'json',
                        headers: { 'token': token },
                        success: function (userRoleRes) {
                            console.log('用户角色接口返回:', userRoleRes);
                            // 提取用户角色ID（重点：从roleId字段获取）
                            var userRoleId = userRoleRes.data?.roleId || ''; // 可选链避免data为null时报错
                            userRoleId = String(userRoleId); // 转为字符串，统一类型

                            // 渲染角色并匹配ID
                            renderEditRoles(allRoles, userRoleId);
                        },
                        error: function (xhr) {
                            console.error('用户角色接口请求失败:', xhr);
                            $('#editRoleList').html('<div style="color: #ff4d4f;">获取用户角色失败</div>');
                        }
                    });
                },
                error: function (xhr) {
                    console.error('角色列表接口请求失败:', xhr);
                    $('#editRoleList').html('<div style="color: #ff4d4f;">角色列表加载失败</div>');
                }
            });
        }

        /**
         * 渲染编辑角色（通过roleId匹配选中状态）
         */
        function renderEditRoles(allRoles, userRoleId) {
            var roleHtml = '';
            allRoles.forEach(function (role) {
                var roleIdStr = String(role.id); // 转为字符串，统一类型
                var isChecked = (roleIdStr === userRoleId) ? 'checked' : '';

                console.log('角色ID匹配:', '列表角色ID=' + roleIdStr, '用户角色ID=' + userRoleId, '是否选中=' + isChecked);

                roleHtml += '<div class="layui-radio-box" style="margin-bottom: 10px;">' +
                    '<input type="radio" name="editRoleId" value="' + role.id + '" ' +
                    isChecked + ' lay-skin="primary">' +
                    '<label>' + role.name + ' <span style="color: #666; font-size: 12px;">(' + (role.content || '') + ')</span></label>' +
                    '</div>';
            });

            $('#editRoleList').html(roleHtml);
            form.render('radio'); // 强制刷新渲染
        }

        /**
         * 提交修改
         */
        function submitUpdate(data, index) {
            $.ajax({
                url: '/systemUser/update',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                dataType: 'json',
                headers: { 'token': token },
                success: function (response) {
                    if (response.code === 200) {
                        layer.msg('修改成功', { icon: 1, time: 1000 }, function () {
                            layer.close(index);
                            fetchUserData(currentPage, pageSize);
                        });
                    } else {
                        layer.msg('修改失败：' + response.message, { icon: 5 });
                    }
                },
                error: function (error) {
                    console.error('修改请求出错:', error);
                    layer.msg('网络请求失败', { icon: 5 });
                }
            });
        }

        /**
         * 打开创建用户弹窗
         */
        function openCreateModal() {
            $('#createLoginName').val('');
            $('#createName').val('');
            $('#createPassword').val('');
            loadRoles();

            createIndex = layer.open({
                type: 1,
                title: '创建新用户',
                content: $('#createModal'),
                area: ['450px', '500px'],
                btn: ['确认创建', '取消'],
                btn1: function (index) {
                    if (validateCreateForm()) {
                        var formData = {
                            loginName: $('#createLoginName').val().trim(),
                            name: $('#createName').val().trim(),
                            password: $('#createPassword').val().trim(),
                            roleId: $('input[name="roleId"]:checked').val()
                        };
                        submitCreate(formData, index);
                    }
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }

        /**
         * 加载角色列表（创建用户用）
         */
        function loadRoles() {
            $.ajax({
                url: '/systemRole/listByQuery',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({ state: '1' }),
                dataType: 'json',
                headers: { 'token': token },
                success: function (response) {
                    if (response.code === 200 && response.data && response.data.length > 0) {
                        renderRoles(response.data);
                    } else {
                        $('#roleList').html('<div style="color: #ff4d4f;">暂无可用角色，请联系管理员</div>');
                    }
                },
                error: function (error) {
                    console.error('加载角色失败:', error);
                    $('#roleList').html('<div style="color: #ff4d4f;">角色加载失败，请刷新重试</div>');
                }
            });
        }

        /**
         * 渲染角色单选按钮（创建用户用）
         */
        function renderRoles(roles) {
            var roleHtml = '';
            roles.forEach(function (role, index) {
                roleHtml += '<div class="layui-radio-box" style="margin-bottom: 10px;">' +
                    '<input type="radio" name="roleId" value="' + role.id + '" ' +
                    (index === 0 ? 'checked' : '') + ' lay-skin="primary">' +
                    '<label>' + role.name + ' <span style="color: #666; font-size: 12px;">(' + role.content + ')</span></label>' +
                    '</div>';
            });
            $('#roleList').html(roleHtml);
            form.render('radio');
        }

        /**
         * 验证创建表单
         */
        function validateCreateForm() {
            var loginName = $('#createLoginName').val().trim();
            var name = $('#createName').val().trim();
            var password = $('#createPassword').val().trim();
            var roleId = $('input[name="roleId"]:checked').val();

            if (!loginName) {
                layer.tips('登录账号不能为空', '#createLoginName', { tips: 1 });
                return false;
            }
            if (loginName.length < 6 || loginName.length > 16) {
                layer.tips('登录账号长度必须在6-16位之间', '#createLoginName', { tips: 1 });
                return false;
            }
            if (!name) {
                layer.tips('用户姓名不能为空', '#createName', { tips: 1 });
                return false;
            }
            if (name.length < 2 || name.length > 10) {
                layer.tips('用户姓名长度必须在2-10位之间', '#createName', { tips: 1 });
                return false;
            }
            if (!password) {
                layer.tips('密码不能为空', '#createPassword', { tips: 1 });
                return false;
            }
            if (password.length < 6) {
                layer.tips('密码长度不能少于6位', '#createPassword', { tips: 1 });
                return false;
            }
            if (!roleId) {
                layer.tips('请选择角色', '#roleList', { tips: 1 });
                return false;
            }

            return true;
        }

        /**
         * 提交创建用户请求
         */
        function submitCreate(data, index) {
            $.ajax({
                url: '/systemUser/create',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                dataType: 'json',
                headers: { 'token': token },
                success: function (response) {
                    if (response.code === 200) {
                        layer.msg('用户创建成功', { icon: 1, time: 1000 }, function () {
                            layer.close(index);
                            fetchUserData(currentPage, pageSize);
                        });
                    } else {
                        layer.msg('创建失败：' + response.message, { icon: 5 });
                    }
                },
                error: function (error) {
                    console.error('创建用户请求出错:', error);
                    layer.msg('网络请求失败', { icon: 5 });
                }
            });
        }

        // 事件绑定
        $('#queryButton').click(function () {
            currentPage = 1;
            fetchUserData(currentPage, pageSize);
        });

        $('#resetButton').click(function () {
            $('#loginName').val('');
            $('#name').val('');
            $('#state').val('');
            currentPage = 1;
            fetchUserData(currentPage, pageSize);
        });

        $('#addButton').click(function () {
            openCreateModal();
        });
    });
});