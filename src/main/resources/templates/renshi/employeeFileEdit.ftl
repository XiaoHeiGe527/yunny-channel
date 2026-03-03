<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>员工信息编辑</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/css/public.css" media="all">
    <link rel="stylesheet" href="/css/font-awesome.min.css">
    <#include "../common/head.ftl">
    <style>
        .search-result {
            position: absolute;
            z-index: 999;
            background: #fff;
            border: 1px solid #e6e6e6;
            width: 100%;
            max-height: 200px;
            overflow-y: auto;
            display: none;
        }
        .search-item {
            padding: 8px 10px;
            cursor: pointer;
        }
        .search-item:hover {
            background-color: #f2f2f2;
        }
        .position-grid {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 10px;
            padding: 10px;
        }
        .position-item {
            border: 1px solid #e6e6e6;
            padding: 10px;
            text-align: center;
            cursor: pointer;
        }
        .position-item:hover {
            background-color: #f2f2f2;
        }
        .position-item.selected {
            background-color: #5FB878;
            color: white;
        }

        /* 核心优化：解决标签换行问题 */
        .layui-form-item {
            display: flex;
            align-items: center; /* 垂直居中对齐 */
            margin-bottom: 15px;
        }
        .layui-form-label {
            width: 120px; /* 固定标签宽度 */
            flex-shrink: 0; /* 禁止标签收缩 */
            text-align: right;
            padding-right: 15px;
            white-space: nowrap; /* 强制不换行 */
        }
        .layui-input-block {
            flex: 1; /* 输入区域自适应宽度 */
            margin-left: 0; /* 取消默认左外边距 */
        }
        /* 适配小屏幕 */
        @media (max-width: 768px) {
            .layui-form-item {
                flex-direction: column;
                align-items: flex-start;
            }
            .layui-form-label {
                width: auto;
                text-align: left;
                margin-bottom: 5px;
            }
            .layui-input-inline {
                width: 100% !important;
            }
        }

        /* 按钮样式优化 */
        .layui-btn + .layui-btn {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">员工信息编辑</div>
        <div class="layui-card-body">
            <form class="layui-form" id="employeeForm" lay-filter="employeeForm">
                <input type="hidden" name="id" value="${employeeFile.id}">
                <input type="hidden" name="userNo" value="${userNo}">

                <div class="layui-form-item">
                    <label class="layui-form-label">员工编号</label>
                    <div class="layui-input-block">
                        <input type="text" name="employeeCode" required lay-verify="required"
                               placeholder="请输入员工编号" autocomplete="off"
                               class="layui-input" value="${(employeeFile.employeeCode)!}">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">员工姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" required lay-verify="required"
                               placeholder="请输入员工姓名" autocomplete="off"
                               class="layui-input" value="${(employeeFile.name)!}">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">员工部门</label>
                    <div class="layui-input-block">
                        <select name="department" id="departmentSelect" required lay-verify="required">
                            <option value="">请选择部门</option>
                            <#list departments as department>
                                <option value="${department.content}"
                                        <#if employeeFile.department?exists && employeeFile.department == department.content>selected</#if>>
                                    ${department.content}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">员工职级</label>
                    <div class="layui-input-block">
                        <select name="rank" id="rankSelect" required lay-verify="required">
                            <option value="">请选择职级</option>
                            <#list ranks as rank>
                                <option value="${rank.content}"
                                        <#if employeeFile.rank?exists && employeeFile.rank == rank.content>selected</#if>>
                                    ${rank.content}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">员工岗位</label>
                    <div class="layui-input-block">
                        <div class="layui-input-inline">
                            <input type="text" name="profession" id="professionInput" required lay-verify="required"
                                   placeholder="请选择岗位" autocomplete="off" readonly
                                   class="layui-input" value="${(employeeFile.profession)!}">
                        </div>
                        <button type="button" id="selectProfessionBtn" class="layui-btn">
                            <i class="layui-icon">&#xe615;</i> 选择岗位
                        </button>
                    </div>
                </div>

                <!-- 存放位置改为选填（移除required和lay-verify） -->
                <div class="layui-form-item">
                    <label class="layui-form-label">存放位置</label>
                    <div class="layui-input-block">
                        <div class="layui-input-inline">
                            <input type="text" name="positionCode" id="positionCode"
                                   placeholder="请选择存放位置（选填）" autocomplete="off" readonly
                                   class="layui-input" value="${(employeeFile.positionCode)!}">
                        </div>
                        <button type="button" id="selectPositionBtn" class="layui-btn">
                            <i class="layui-icon">&#xe615;</i> 选择位置
                        </button>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
                        <input type="radio" name="state" value="1" title="在职"
                               <#if employeeFile.state?exists && employeeFile.state == 1>checked</#if>>
                        <input type="radio" name="state" value="0" title="离职"
                               <#if employeeFile.state?exists && employeeFile.state == 0>checked</#if>>
                    </div>
                </div>

                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea name="remarks" placeholder="请输入备注信息"
                                  class="layui-textarea">${(employeeFile.remarks)!}</textarea>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formSubmit">提交</button>
                        <button type="button" class="layui-btn layui-btn-primary" onclick="javascript:history.back();">返回</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 岗位选择弹出层 -->
<div id="professionModal" class="layui-layer-wrap" style="display: none;">
    <div class="layui-layer-title">选择岗位</div>
    <div class="layui-layer-content" style="padding: 10px;">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="text" id="professionSearch" placeholder="搜索岗位" class="layui-input">
            </div>
        </div>
        <div id="professionList" class="position-grid"></div>
    </div>
</div>

<!-- 位置选择弹出层 -->
<div id="positionModal" class="layui-layer-wrap" style="display: none;">
    <div class="layui-layer-title">选择档案存放位置</div>
    <div class="layui-layer-content" style="padding: 10px;">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <input type="text" id="positionSearch" placeholder="搜索位置编码" class="layui-input">
            </div>
        </div>
        <div id="positionList" class="position-grid"></div>
    </div>
</div>

<script>
    layui.use(['form', 'layer', 'element', 'jquery'], function(){
        var form = layui.form;
        var layer = layui.layer;
        var element = layui.element;
        var $ = layui.jquery;

        // 获取Token
        var token = '${Request.token}';

        // 设置全局AJAX配置，添加Token到请求头
        $.ajaxSetup({
            headers: {
                'token': token
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // 统一错误处理
                if (jqXHR.status === 401) {
                    layer.msg('认证失败，请重新登录', {icon: 2});
                } else {
                    layer.msg('请求失败: ' + errorThrown, {icon: 2});
                }
            }
        });

        // 初始化部门下拉框
        function initDepartmentSelect() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({category: '部门'}),
                success: function(response) {
                    if (response && response.code === 200 && response.data) {
                        var departmentSelect = $('#departmentSelect');
                        response.data.forEach(function(item) {
                            departmentSelect.append('<option value="' + item.content + '">' + item.content + '</option>');
                        });
                        form.render('select');
                    } else {
                        handleApiError(response);
                    }
                }
            });
        }

        // 初始化职级下拉框
        function initRankSelect() {
            $.ajax({
                url: '/dictionary/listByQuery',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({category: '职级'}),
                success: function(response) {
                    if (response && response.code === 200 && response.data) {
                        var rankSelect = $('#rankSelect');
                        response.data.forEach(function(item) {
                            rankSelect.append('<option value="' + item.content + '">' + item.content + '</option>');
                        });
                        form.render('select');
                    } else {
                        handleApiError(response);
                    }
                }
            });
        }

        // 初始化岗位选择弹出层
        function initProfessionModal() {
            var professionModal = $('#professionModal');
            var professionList = $('#professionList');
            var allProfessions = []; // 存储所有岗位数据

            // 打开岗位选择弹出层
            $('#selectProfessionBtn').on('click', function() {
                if (allProfessions.length === 0) {
                    loadProfessions('');
                } else {
                    renderProfessions(''); // 如果已有数据，直接渲染
                }
                professionModal.show();

                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['600px', '400px'],
                    content: professionModal,
                    shadeClose: true
                });
            });

            // 搜索岗位
            $('#professionSearch').on('input', function() {
                var keyword = $(this).val().trim();
                renderProfessions(keyword);
            });

            // 加载岗位列表
            function loadProfessions(keyword) {
                professionList.html('<div style="text-align: center; padding: 20px;">加载中...</div>');

                $.ajax({
                    url: '/dictionary/listByQuery',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({category: '岗位'}),
                    success: function(response) {
                        if (response && response.code === 200 && response.data) {
                            allProfessions = response.data; // 保存原始数据
                            renderProfessions(keyword);
                        } else {
                            professionList.html('<div style="text-align: center; padding: 20px; color: #999;">获取岗位列表失败</div>');
                            handleApiError(response);
                        }
                    }
                });
            }

            // 渲染岗位列表
            function renderProfessions(keyword) {
                professionList.empty();

                if (allProfessions.length === 0) {
                    professionList.html('<div style="text-align: center; padding: 20px; color: #999;">没有岗位数据</div>');
                    return;
                }

                // 过滤并保持原始顺序
                var filteredProfessions = keyword
                    ? allProfessions.filter(item => item.content.toLowerCase().includes(keyword.toLowerCase()))
            : allProfessions;

                if (filteredProfessions.length > 0) {
                    filteredProfessions.forEach(function(item) {
                        var professionItem = $('<div class="position-item">' + item.content + '</div>');
                        professionItem.on('click', function() {
                            // 选择岗位并关闭弹出层
                            $('#professionInput').val(item.content);
                            layer.closeAll();
                        });
                        professionList.append(professionItem);
                    });
                } else {
                    professionList.html('<div style="text-align: center; padding: 20px; color: #999;">没有找到匹配的岗位</div>');
                }
            }
        }

        // 初始化位置选择弹出层
        function initPositionModal() {
            var positionModal = $('#positionModal');
            var positionList = $('#positionList');
            var allPositions = []; // 存储所有位置数据

            // 打开位置选择弹出层
            $('#selectPositionBtn').on('click', function() {
                if (allPositions.length === 0) {
                    loadPositions('');
                } else {
                    renderPositions(''); // 如果已有数据，直接渲染
                }
                positionModal.show();

                layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 0,
                    area: ['600px', '400px'],
                    content: positionModal,
                    shadeClose: true
                });
            });

            // 搜索位置
            $('#positionSearch').on('input', function() {
                var keyword = $(this).val().trim();
                renderPositions(keyword);
            });

            // 加载位置列表
            function loadPositions(keyword) {
                positionList.html('<div style="text-align: center; padding: 20px;">加载中...</div>');

                $.ajax({
                    url: '/filePosition/listByQuery',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        fileType: 2
                    }),
                    success: function(response) {
                        if (response && response.code === 200 && response.data) {
                            allPositions = response.data; // 保存原始数据
                            renderPositions(keyword);
                        } else {
                            positionList.html('<div style="text-align: center; padding: 20px; color: #999;">获取位置列表失败</div>');
                            handleApiError(response);
                        }
                    }
                });
            }

            // 渲染位置列表
            function renderPositions(keyword) {
                positionList.empty();

                if (allPositions.length === 0) {
                    positionList.html('<div style="text-align: center; padding: 20px; color: #999;">没有位置数据</div>');
                    return;
                }

                // 过滤并保持原始顺序
                var filteredPositions = keyword
                    ? allPositions.filter(item => item.positionCode.toLowerCase().includes(keyword.toLowerCase()))
            : allPositions;

                if (filteredPositions.length > 0) {
                    filteredPositions.forEach(function(item) {
                        var positionItem = $('<div class="position-item">' + item.positionCode + '</div>');
                        positionItem.on('click', function() {
                            // 选择位置并关闭弹出层
                            $('#positionCode').val(item.positionCode);
                            layer.closeAll();
                        });
                        positionList.append(positionItem);
                    });
                } else {
                    positionList.html('<div style="text-align: center; padding: 20px; color: #999;">没有找到匹配的位置</div>');
                }
            }
        }

        // 表单提交监听
        form.on('submit(formSubmit)', function(data) {
            $.ajax({
                url: '/employeeFile/employeeFileEdit',
                type: 'POST',
                contentType: 'application/json', // 指定JSON格式
                data: JSON.stringify(data.field), // 序列化表单数据为JSON
                success: function(response) {
                    if (response.code === 200) {
                        alert('数据提交成功');
                        // 创建一个隐藏的表单并提交 POST 请求
                        var form = $('<form>', {
                            action: '/renShiViewJump/employeeFile/employeeFileListPage',
                            method: 'post'
                        });
                        form.append($('<input>', {
                            type: 'hidden',
                            name: 'token',
                            value: token
                        }));
                        form.appendTo('body').submit();
                    } else {
                        alert('数据提交失败：' + response.message);
                    }
                },
                error: function(jqXHR) {
                    layer.msg('网络请求失败，请重试', {icon: 2});
                    console.error(jqXHR.responseText);
                }
            });
            return false;
        });

        // 统一处理API错误
        function handleApiError(response) {
            if (response && response.message) {
                layer.msg('接口错误: ' + response.message, {icon: 2});
            } else {
                layer.msg('未知错误，请重试', {icon: 2});
            }
        }

        // 初始化
        initDepartmentSelect();
        initRankSelect();
        initProfessionModal();
        initPositionModal();
        form.render();
    });
</script>
</body>
</html>