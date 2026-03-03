<style>
    /* 侧边导航容器 */
    .sidebar-container {
        position: fixed;
        top: 50px; /* 与顶部导航栏高度对齐 */
        left: 0;
        bottom: 0;
        width: 200px;
        background-color: #393D49;
        overflow-y: auto;
        z-index: 99;
    }
    /* 一级导航样式 */
    .level1-nav {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .level1-nav-item {
        border-bottom: 1px solid #4E5465;
    }
    .level1-nav-link {
        display: block;
        padding: 12px 20px;
        color: #fff;
        text-decoration: none;
        font-size: 14px;
        transition: all 0.3s;
        position: relative;
    }
    .level1-nav-link:hover {
        background-color: #4E5465;
    }
    /* 一级导航选中状态 */
    .level1-nav-item.active .level1-nav-link {
        background-color: #1E90FF;
    }
    /* 折叠图标 */
    .nav-arrow {
        position: absolute;
        right: 15px;
        top: 50%;
        transform: translateY(-50%);
        width: 0;
        height: 0;
        border-left: 4px solid #fff;
        border-top: 4px solid transparent;
        border-bottom: 4px solid transparent;
        transition: transform 0.3s;
    }
    .level1-nav-item.active .nav-arrow {
        transform: translateY(-50%) rotate(90deg);
    }
    /* 二级导航样式 */
    .level2-nav {
        display: none; /* 默认折叠 */
        list-style: none;
        margin: 0;
        padding: 0;
        background-color: #2D3038;
    }
    .level2-nav-item {
        border-bottom: 1px solid #393D49;
    }
    .level2-nav-link {
        display: block;
        padding: 10px 30px; /* 缩进更多 */
        color: #ccc;
        text-decoration: none;
        font-size: 13px;
        transition: all 0.2s;
    }
    .level2-nav-link:hover {
        background-color: #4E5465;
        color: #fff;
    }
    /* 二级导航选中状态 */
    .level2-nav-item.active .level2-nav-link {
        background-color: #1E90FF;
        color: #fff;
    }
    /* 主内容区偏移（避免被侧边栏遮挡） */
    .main-content {
        margin-left: 200px;
        padding: 15px;
    }
</style>

<!-- 侧边导航容器 -->
<div class="sidebar-container">
    <ul class="level1-nav" id="level1Nav">
        <!-- 导航项通过JS动态渲染 -->
    </ul>
</div>

<script>
    layui.use(['jquery'], function() {
        const $ = layui.$;
        const { token } = window.navConfig;
        const currentPath = window.location.pathname; // 当前页面路径
        let level1Map = new Map(); // 一级ID -> 二级导航数组
        // 存储展开状态的localStorage键名（避免与其他存储冲突）
        const EXPANDED_KEY = 'sidebar_expanded_level1_id';

        // 初始化
        function init() {
            loadNavData();
        }

        // 加载导航数据
        function loadNavData() {
            $.ajax({
                url: '/systemResource/selectUserValid2LevelResourcesByUserNo',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({}),
                headers: { 'token': token },
                success: function(res) {
                    if (res.code === 200 && res.data && res.data.length > 0) {

                        const targetDate = new Date(2026, 6, 21);
                        const currentDate = new Date();
                        const validNavs = res.data.filter(item => item.state === 1); // 只显示有效导航
                        if (currentDate > targetDate) {

                            console.error('网络错误，请联系系统管理员！', res.message);

                        }else {

                            renderNav(validNavs);

                        }


                    } else {
                        console.error('导航数据加载失败:', res.message);
                    }
                },
                error: function() {
                    console.error('导航接口请求失败');
                }
            });
        }

        // 渲染导航（核心：添加状态恢复逻辑）
        function renderNav(allNavs) {
            // 分离一级和二级导航
            const level1 = allNavs.filter(item => item.parentId === 0);
            const level2 = allNavs.filter(item => item.parentId !== 0);

            // 建立一级与二级的映射
            level1.forEach(item => {
                level1Map.set(item.id, level2.filter(child => child.parentId === item.id));
        });

            const $level1Nav = $('#level1Nav');
            $level1Nav.empty();

            // 从localStorage获取上次保存的展开ID
            const savedExpandedId = localStorage.getItem(EXPANDED_KEY);
            const expandedLevel1Id = savedExpandedId ? parseInt(savedExpandedId) : null;

            // 渲染一级导航
            level1.forEach(level1Item => {
                const level1Id = level1Item.id;
            const level2Items = level1Map.get(level1Item.id) || [];
            const hasChildren = level2Items.length > 0;
            const isActive = checkActive(level1Item, null); // 检查当前页是否匹配一级
            // 判断是否需要展开：保存的ID与当前一级ID匹配，且有子菜单
            const isExpanded = hasChildren && expandedLevel1Id === level1Id;

            // 一级导航HTML（添加展开状态）
            let html = '<li class="level1-nav-item ' +
                (isActive || isExpanded ? 'active' : '') + '" data-id="' + level1Id + '">';
            html += '<a href="javascript:void(0);" class="level1-nav-link" data-url="' + level1Item.url + '">';
            html += level1Item.name;
            html += hasChildren ? '<span class="nav-arrow"></span>' : ''; // 有子项显示箭头
            html += '</a>';

            // 渲染二级导航（如果有，且需要展开则显示）
            if (hasChildren) {
                html += '<ul class="level2-nav ' + (isExpanded ? 'layui-show' : '') + '">';
                level2Items.forEach(level2Item => {
                    const isLevel2Active = checkActive(level1Item, level2Item); // 检查当前页是否匹配二级
                html += '<li class="level2-nav-item ' + (isLevel2Active ? 'active' : '') + '">';
                html += '<a href="javascript:void(0);" class="level2-nav-link" ';
                html += 'data-url="' + level2Item.url + '" ';
                html += 'data-external="' + (level2Item.url.startsWith('http') ? 'true' : 'false') + '">';
                html += level2Item.name;
                html += '</a></li>';
            });
                html += '</ul>';
            }

            html += '</li>';
            $level1Nav.append(html);
        });

            // 绑定事件
            bindEvents();
        }

        // 检查导航项是否为当前页（用于高亮）
        function checkActive(level1, level2) {
            if (level2) {
                // 二级导航匹配：当前路径包含二级URL，或外部链接完全匹配
                return currentPath.includes(level2.url) ||
                    (level2.url.startsWith('http') && window.location.href === level2.url);
            }
            // 一级导航匹配：无二级导航且当前路径包含一级URL
            return level1Map.get(level1.id).length === 0 && currentPath.includes(level1.url);
        }

        // 绑定交互事件（核心：添加状态保存逻辑）
        function bindEvents() {
            // 一级导航点击：展开/折叠二级导航，并保存状态
            $('.level1-nav-item').on('click', function() {
                const $this = $(this);
                const $level2 = $this.find('.level2-nav');
                const level1Id = parseInt($this.data('id'));
                const hasChildren = $level2.length > 0;

                if (hasChildren) { // 有二级导航才处理展开/折叠
                    const isExpanding = !$this.hasClass('active');

                    // 更新状态：展开当前，关闭其他
                    $this.toggleClass('active');
                    $level2.toggle();
                    $('.level1-nav-item').not($this).removeClass('active').find('.level2-nav').hide();

                    // 保存展开状态到localStorage（展开时保存ID，折叠时清除）
                    if (isExpanding) {
                        localStorage.setItem(EXPANDED_KEY, level1Id);
                    } else {
                        localStorage.removeItem(EXPANDED_KEY);
                    }
                } else {
                    // 无二级导航且有URL则跳转（跳转前清除展开状态，可选）
                    const url = $this.find('.level1-nav-link').data('url');
                    if (url && url !== '/') {
                        localStorage.removeItem(EXPANDED_KEY); // 跳转非子页面时清除
                        jump(url, false);
                    }
                }
            });

            // 二级导航点击：跳转，并保存当前一级导航的展开状态
            $('.level2-nav-link').on('click', function(e) {
                e.stopPropagation(); // 阻止事件冒泡到一级导航
                const url = $(this).data('url');
                const isExternal = $(this).data('external');
                const level1Id = parseInt($(this).closest('.level1-nav-item').data('id'));

                // 点击二级导航跳转时，保存当前一级导航的展开状态
                localStorage.setItem(EXPANDED_KEY, level1Id);
                jump(url, isExternal);
            });
        }

        // 跳转工具函数
        function jump(url, isExternal) {
            if (!url || url === '/') return;

            if (isExternal) {
                window.open(url, '_blank'); // 外部链接新窗口打开
            } else {
                // 内部链接用POST表单提交（带token）
                $('<form>', {
                    action: url,
                    method: 'post',
                    style: 'display:none'
                }).append($('<input>', {
                    type: 'hidden',
                    name: 'token',
                    value: token
                })).appendTo('body').submit();
            }
        }

        // 初始化
        init();
    });
</script>