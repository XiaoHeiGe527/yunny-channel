<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>海外集团综合管理平台</title>
    <#include "../common/head.ftl">


    <#-- 网络引入  <link href="
  https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css
  " rel="stylesheet">-->



    <!-- 自定义工具类 -->
    <style type="text/tailwindcss">
        @layer utilities {
            .content-auto {
                content-visibility: auto;
            }
            .text-shadow {
                text-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .bg-glass {
                background: rgba(255, 255, 255, 0.25);
                backdrop-filter: blur(8px);
                -webkit-backdrop-filter: blur(8px);
            }
            .card-shadow {
                box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
            }
        }
    </style>

    <style>
        body {
            overflow-x: hidden;
        }

        .wave {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 100px;
            background: url('data:image/svg+xml;utf8,<svg viewBox="0 0 1440 320" xmlns="http://www.w3.org/2000/svg"><path fill="rgba(255, 255, 255, 0.2)" d="M0,224L48,213.3C96,203,192,181,288,176C384,171,480,181,576,197.3C672,213,768,235,864,240C960,245,1056,235,1152,213.3C1248,192,1344,160,1392,144L1440,128L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
            background-size: 1440px 100px;
            animation: wave 15s linear infinite;
        }

        .wave:nth-child(2) {
            bottom: 5px;
            opacity: 0.5;
            background: url('data:image/svg+xml;utf8,<svg viewBox="0 0 1440 320" xmlns="http://www.w3.org/2000/svg"><path fill="rgba(255, 255, 255, 0.1)" d="M0,224L48,213.3C96,203,192,181,288,176C384,171,480,181,576,197.3C672,213,768,235,864,240C960,245,1056,235,1152,213.3C1248,192,1344,160,1392,144L1440,128L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
            background-size: 1440px 100px;
            animation-delay: -5s;
            animation-duration: 20s;
        }

        @keyframes wave {
            0% {
                background-position-x: 0;
            }
            100% {
                background-position-x: 1440px;
            }
        }

        .login-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .login-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 40px rgba(0, 0, 0, 0.2);
        }

        .form-input:focus {
            border-color: #165DFF;
            box-shadow: 0 0 0 2px rgba(22, 93, 255, 0.2);
        }

        .btn-login {
            transition: all 0.3s ease;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(22, 93, 255, 0.3);
        }

        .btn-login:active {
            transform: translateY(0);
            box-shadow: 0 2px 6px rgba(22, 93, 255, 0.4);
        }

        .logo-animation {
            animation: fadeInUp 0.8s ease forwards;
            opacity: 0;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .card-animation {
            animation: fadeIn 1s ease 0.3s forwards;
            opacity: 0;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* 优化图标样式 */
        .custom-icon-container {
            background-color: white;    /* 设置容器背景为白色 */
            border-radius: 8px;         /* 将容器四角设置为8px的圆角 */
            padding: 0;                 /* 移除容器内部的所有内边距 */
            display: flex;              /* 使用Flexbox布局模型 */
            align-items: center;        /* 在交叉轴（垂直方向）上居中对齐内容 */
            justify-content: center;    /* 在主轴（水平方向）上居中对齐内容 */
            box-shadow: 0 2px 8px rgba(0,0,0,0.1); /* 添加轻微的阴影效果 */
        }

        .custom-icon {
            width: 24px;
            height: 24px;
            object-fit: contain;
        }

        .left-logo-icon {
            width: 80px;
            height: 80px;
            object-fit: contain;
        }
    </style>
</head>
<body class="font-inter bg-login-bg bg-cover bg-center min-h-screen flex flex-col">
<!-- 背景波浪效果 -->
<div class="wave"></div>
<div class="wave"></div>

<!-- 顶部导航 -->
<nav class="bg-glass card-shadow py-4 px-6 md:px-12 flex justify-between items-center fixed top-0 w-full z-10 transition-all duration-300">
    <div class="flex items-center space-x-3">
        <!-- 优化图标容器样式 -->
        <div class="custom-icon-container">
            <img src="/images/haiwai.png" alt="平台图标" class="custom-icon">
        </div>
        <span class="text-dark font-bold text-xl">海外集团综合管理平台</span>
    </div>
    <div class="hidden md:flex items-center space-x-6">
        <a href="#" class="text-dark hover:text-primary transition-colors duration-200">首页</a>
        <a href="#" class="text-dark hover:text-primary transition-colors duration-200">帮助</a>
        <a href="#" class="text-dark hover:text-primary transition-colors duration-200">联系我们</a>
    </div>
</nav>

<!-- 主内容区 -->
<main class="flex-grow flex items-center justify-center px-4 py-20 mt-16">
    <div class="max-w-7xl w-full mx-auto flex flex-col md:flex-row items-center justify-between gap-12">
        <!-- 左侧介绍 -->
        <div class="hidden md:block w-1/2 text-white">
            <div class="logo-animation">
                <div class="flex items-center space-x-4 mb-6">
                    <!-- 优化左侧大图标样式 -->
                    <div class="bg-white/20 rounded-xl p-4 custom-icon-container">
                        <img src="/images/haiwai.png" alt="平台Logo" class="left-logo-icon">
                    </div>
                    <h1 class="text-[clamp(2rem,5vw,3rem)] font-bold text-shadow leading-tight">海外集团<br>综合管理平台</h1>
                </div>

                <p class="text-xl text-white/90 mb-8 max-w-lg">
                    一站式企业管理解决方案，提供高效、安全、便捷的企业运营管理服务，助力企业数字化转型。
                </p>

                <div class="grid grid-cols-2 gap-4 mb-8">
                    <div class="bg-white/10 backdrop-blur-sm rounded-lg p-4">
                        <div class="text-3xl mb-2">
                            <i class="fa fa-shield text-secondary"></i>
                        </div>
                        <h3 class="font-bold text-lg mb-1">安全可靠</h3>
                        <p class="text-white/80 text-sm">多重数据加密，保障企业信息安全</p>
                    </div>
                    <div class="bg-white/10 backdrop-blur-sm rounded-lg p-4">
                        <div class="text-3xl mb-2">
                            <i class="fa fa-cogs text-secondary"></i>
                        </div>
                        <h3 class="font-bold text-lg mb-1">高效管理</h3>
                        <p class="text-white/80 text-sm">简化流程，提升工作效率</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- 右侧登录卡片 -->
        <div class="w-full md:w-2/5 max-w-md card-animation">
            <div class="login-card bg-white rounded-2xl card-shadow overflow-hidden">
                <div class="bg-gradient-to-r from-primary to-secondary p-6 text-center">
                    <h2 class="text-white text-2xl font-bold">用户登录</h2>
                    <p class="text-white/80 mt-1">请输入账号密码登录系统</p>
                </div>

                <div class="p-8">
                    <form id="loginForm">
                        <div class="mb-6">
                            <label for="account" class="block text-sm font-medium text-gray-700 mb-2">账号</label>
                            <div class="relative">
                                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none text-gray-400">
                                    <i class="fa fa-user"></i>
                                </div>
                                <input type="text" id="account" placeholder="请输入账号" class="form-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:outline-none transition-all duration-200">
                            </div>
                        </div>

                        <div class="mb-6">
                            <label for="password" class="block text-sm font-medium text-gray-700 mb-2">密码</label>
                            <div class="relative">
                                <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none text-gray-400">
                                    <i class="fa fa-lock"></i>
                                </div>
                                <input type="password" id="password" placeholder="请输入密码" class="form-input w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:outline-none transition-all duration-200">
                            </div>
                        </div>

                        <button type="button" id="loginButton" class="btn-login w-full bg-primary hover:bg-primary/90 text-white font-medium py-3 px-4 rounded-lg transition-all duration-200 flex items-center justify-center">
                            <i class="fa fa-sign-in mr-2"></i>
                            登录系统
                        </button>

                        <!-- 员工资料填写按钮 -->
                        <!-- <button type="button" id="employeeProfileBtn" class="w-full bg-accent hover:bg-accent/90 text-white font-medium py-3 px-4 rounded-lg transition-all duration-200 mt-4 flex items-center justify-center">
                            <i class="fa fa-user-circle mr-2"></i>
                            员工资料填写
                        </button> -->
                    </form>

                    <div class="mt-6 text-center text-sm text-gray-500">
                        <p>© 2025 海外集团综合管理平台 版权所有</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<!-- 页脚 -->
<footer class="bg-dark text-white/70 py-6 px-6 md:px-12 text-center text-sm">
    <div class="max-w-7xl mx-auto">
        <p>技术支持：海外集团信息技术部 |联系电话：0464-8351947| 邮箱：qthhwhg@163.com</p>
        <p class="mt-2">本系统仅用于企业内部办公，请勿对外泄露任何信息</p>
    </div>
</footer>

<!-- Tailwind 配置 - 移至页面底部 -->
<script>
    tailwind.config = {
        theme: {
            extend: {
                colors: {
                    primary: '#165DFF',
                    secondary: '#36BFFA',
                    accent: '#0FC6C2',
                    dark: '#1D2129',
                    light: '#F2F3F5',
                },
                fontFamily: {
                    inter: ['Inter', 'system-ui', 'sans-serif'],
                },
                backgroundImage: {
                    'login-bg': "url('http://192.168.2.129:9100/images/22.png')",
                }
            },
        }
    }
</script>

<script>
    $(document).ready(function() {
        // 登录按钮点击事件
        $('#loginButton').click(function() {
            var account = $('#account').val();
            var password = $('#password').val();

            // 简单验证
            if (!account || !password) {
                layer.msg('请输入账号和密码', {icon: 2});
                return;
            }

            // 显示加载状态
            var loading = layer.load(1, {
                shade: [0.5, '#000']
            });

            // 构造请求参数
            var data = {
                "account": account,
                "password": password
            };

            // 发送登录请求
            $.ajax({
                url: '/sys/login',
                type: 'POST',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data),
                dataType: 'json',
                headers: {
                    'Accept': 'application/json'
                },
                success: function(response) {
                    layer.close(loading);

                    if (response.code === 200) {
                        // 创建一个隐藏的表单并提交POST请求
                        var form = $('<form>', {
                            action: '/viewJump/homepage',
                            method: 'post'
                        });
                        form.append($('<input>', {
                            type: 'hidden',
                            name: 'token',
                            value: response.data.token
                        }));
                        form.appendTo('body').submit();
                    } else {
                        // 登录失败，弹出提示
                        layer.msg(response.message, {icon: 2});
                    }
                },
                error: function() {
                    layer.close(loading);
                    // 请求发生错误
                    layer.msg('请求发生错误，请检查网络或接口地址', {icon: 2});
                }
            });
        });

        // 员工资料填写按钮点击事件
        $('#employeeProfileBtn').click(function() {
            window.location.href = '/renShi/uploadFkb';
        });

        // 导航栏滚动效果
        $(window).scroll(function() {
            if ($(this).scrollTop() > 10) {
                $('nav').addClass('py-2 bg-white/90');
                $('nav').removeClass('py-4 bg-glass');
            } else {
                $('nav').addClass('py-4 bg-glass');
                $('nav').removeClass('py-2 bg-white/90');
            }
        });

        // 输入框聚焦效果
        $('.form-input').focus(function() {
            $(this).closest('.mb-6').addClass('scale-[1.02]');
        }).blur(function() {
            $(this).closest('.mb-6').removeClass('scale-[1.02]');
        });
    });
</script>
</body>
</html>