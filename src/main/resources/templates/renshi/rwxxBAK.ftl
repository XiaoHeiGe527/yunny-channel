<!DOCTYPE html>
<html>
<head>
    <title>申请单列表</title>
    <#include "../common/head.ftl">
    <style>
        div label {
            width: 150px !important;
        }

        .layui-input-block input {
            width: 54% !important;
        }

        .color-red {
            color: red;
        }
    </style>

</head>

<body>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>申请包数据</legend>
</fieldset>

<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="150">
            <col width="150">
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>申请单号</th>
            <th>案件编号</th>
            <th>案件名称</th>
            <th>简要案情</th>
            <th>立案日期</th>
            <th>案件状态</th>
            <th>案件状态名称</th>
            <th>申请人单位名称</th>
            <th>申请人警号</th>
            <th>申请人姓名</th>
            <th>申请人手机号</th>
            <th>申请人单电位话</th>
            <th>协查人单位名称</th>
            <th>协查人单位电话</th>
            <th>协查人警号</th>
            <th>协查人姓名</th>
            <th>协查人手机号</th>
            <th>案件备注</th>
            <th>下载</th>
            <th>上传反馈包</th>
        </tr>
        </thead>
        <tbody>
        <#if data?exists>
            <#list data as item>

                <tr>
                    <td>${item.sqdh?string}</td>
                    <td>${item.ajbh!}</td>
                    <td>${item.ajmc!}</td>
                    <td>${item.jyaq!}</td>
                    <td>${item.larq?datetime?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td>${item.ajzt!}</td>
                    <td>${item.ajztmc!}</td>
                    <td>${item.sqrSsbm!}</td>
                    <td>${item.sqrJycode!}</td>
                    <td>${item.sqrJyname!}</td>
                    <td>${item.sqrLongMobile!}</td>
                    <td>${item.sqrOfficePhone!}</td>
                    <td>${item.xcrSsbm!}</td>
                    <td>${item.xcrOfficePhone!}</td>
                    <td>${item.xcrJycode!}</td>
                    <td>${item.xcrJyname!}</td>
                    <td>${item.xcrLongMobile!}</td>
                    <td>${item.bzxx!}</td>
                    <td>
                        <a href="/rwxx/downloadBysqdh?sqdh=${item.sqdh?string}">  <button class="layui-btn">下载反馈包
                        </button>
                        </a>

                    <td>
                        <#if item.rwzt ==1>
                        <a href="/qzj/uploadFkb?sqdh=${item.sqdh?string}">
                        <button class="layui-btn">上传反馈包数据</button>
                        </a>
                            <#elseif  item.rwzt ==2>
                                <button class="layui-btn layui-btn-disabled">申请单数据已反馈</button>
                        <#else>
                        <a href="/qzj/uploadFkb?sqdh=${item.sqdh?string}">
                            <button class="layui-btn">上传反馈包数据</button>
                        </#if>

                    </td>

                </tr>

            </#list>
        </#if>

        </tbody>
    </table>
</div>

<#--
<div id="demo4">
    <table class="layui-hide" id="test">
    </table>
</div>
-->


<#--
<script>

    $(document).ready(function() {
        // 假设表格渲染在ID为'test'的元素中
        $('#test').on('click', 'button[name="downloadName"]', function() {
            alert("按钮被点击了！");
            // 在这里实现你的下载逻辑
        });
    });

    layui.use('table', function () {
        var table = layui.table;
        var laypage = layui.laypage
            , layer = layui.layer;

        table.render({
            elem: '#test'
            , url: '/rwxx/getRwxxlist'
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
                //,curr: 1 //设定初始在第 5 页
                , groups: 10 //只显示 1 个连续页码
                , first: false //不显示首页
                , last: false //不显示尾页

            }
            , cols: [[
                {field: 'sqdh', width: 135, title: '申请单号', sort: true}
                , {field: 'ajbh', width: 135, title: '案件编号', sort: true}
                , {field: 'ajmc', width: 135, title: '案件名称'}
                , {field: 'jyaq', width: 135, title: '简要案情', sort: true}
                , {field: 'larq', width: 135, title: '立案日期', sort: true}
                , {field: 'ajzt', width: 135, title: '案件状态', sort: true}
                , {field: 'ajztmc', width: 135, title: '案件状态名称'}
                , {field: 'sqrSsbm', width: 135, title: '申请人单位名称', sort: true}
                , {field: 'sqrJycode', width: 135, title: '申请人警号', sort: true}
                , {field: 'sqrJyname', width: 135, title: '申请人姓名', sort: true}
                , {field: 'sqrLongMobile', width: 135, title: '申请人手机号', sort: true}
                , {field: 'sqrOfficePhone', width: 135, title: '申请人单电位话', sort: true}
                , {field: 'xcrSsbm', width: 135, title: '协查人单位名称', sort: true}
                , {field: 'xcrOfficePhone', width: 135, title: '协查人单位电话', sort: true}
                , {field: 'xcrJycode', width: 135, title: '协查人警号', sort: true}
                , {field: 'xcrJyname', width: 135, title: '协查人姓名', sort: true}
                , {field: 'xcrLongMobile', width: 135, title: '协查人手机号', sort: true}
                , {field: 'bzxx', width: 80, title: '案件备注'}
                , {
                    title: '操作', // 定义操作列
                    templet: function(data){ // d 是当前行的数据 id="download_' + d.sqdh
                        var sqdh = data.sqdh;
                       // return '<button class="layui-btn layui-btn-normal" lay-event="download" name = "download_' + sqdh +'">下载申请包</button>';
                        return '<button class="layui-btn layui-btn-normal" lay-event="download" name = "downloadName  ">下载申请包</button>';
                    },
                    fixed: 'right', // 固定在右侧
                    width: 150
                }
            ]]
        });


    });
</script>
-->


</body>
</html>