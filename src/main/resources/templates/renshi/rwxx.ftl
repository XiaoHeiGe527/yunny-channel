<!DOCTYPE html>
<html>



<head>
    <title>申请单列表</title>
    <#include "../common/head.ftl">
    <@css_version paths=["css/global.css","css/index.css"]/>
    <@js_version paths=["js/index.js"]/>z
</head>


>
<body>
<div class="layuimini-container" id="APP">
    <div class="layuimini-main">

        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" lay-filter="data-search">
                    <input type="hidden" name="pageNum" id="pageNum" value="1"/>
                    <input type="hidden" name="pageSize" id="pageSize" value="10"/>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <select name="type" lay-filter="type">
                                    <option value="">操作类型</option>
                                    <option value="0">操作日志</option>
                                    <option value="1">登录日志</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <select name="status" lay-filter="status">
                                    <option value="">操作状态</option>
                                    <option value="0">失败</option>
                                    <option value="1">成功</option>
                                </select>
                            </div>
                        </div>


                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="sqdh" placeholder="申请单号" autocomplete="off" class="layui-input">
                            </div>
                        </div>



                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" id="strDate" name="strDate" placeholder="开始日期" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text"  id="endDate" name="endDate" placeholder="结束日期" autocomplete="off" class="layui-input">
                            </div>
                        </div>

                        <div class="layui-inline">
                            <button type="button" id="searchBut" class="layui-btn layui-btn-primary"><i class="layui-icon"></i> 搜 索</button>
                        </div>



                    </div>
                </form>
            </div>
        </fieldset>

        <script type="text/html" id="myToolbar">
            <div class="layui-btn-container">
                <name="批量删除" power="log_delete" layEvent="del" class="layui-btn-sm layui-btn-danger data-add-btn"/>
            </div>
        </script>

        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        <div id="tablePageBar"></div>

        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs data-count-info" lay-event="info">详情</a>
        </script>

    </div>
</div>

<script>


    layui.use(['form', 'table','laypage','laydate'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            laypage = layui.laypage,
            laydate = layui.laydate;

        laydate.render({
            elem: '#strDate' //指定元素
        });

        laydate.render({
            elem: '#endDate' //指定元素
        });


        /**
         * 初始加载数据表格
         */
        data_list();

        /**
         * 监听搜索操作
         */
        $('#searchBut').click(function () {
            $("#pageNum").val(1);
            data_list();
        });

        form.on('select(type)', function(data){
            $("#pageNum").val(1);
            data_list();
        });

        form.on('select(status)', function(data){
            $("#pageNum").val(1);
            data_list();
        });


    });


    /**
     * 获取列表数据并渲染
     */
    function data_list(){
        layer.load(2);
        var searchData = layui.form.val("data-search");
        axios.post('/rwxx/getRwxxPage',searchData).then(resp => {
            layui.table.render({
                elem: '#currentTableId',
                data: resp.data.records,  // 数据
                limit: resp.data.pageSize,  //显示的数量
                toolbar: '#myToolbar',
                defaultToolbar: ['filter', 'exports', 'print'],
                cols: [[
                    {type: 'checkbox'},
                    {field: 'sqdh', title: '申请单号', align: 'center'},
                    {field: 'ajbh', title: '案件编号'},
                    {field: 'ajmc', title: '案件名称'},
                    {field: 'jyaq', title: '立案日期'},
                    {field: 'ajzt', title: '案件状态'},
                    {field: 'ajztmc', title: '案件状态名称'},
                    {field: 'rwzt', title: '任务状态', align: 'center', templet: function (d) {
                            if (d.status == 0) {
                                return '<span style="color:#FF5722">失败</span>';
                            }else {
                                return '成功';
                            }
                        }
                    },
                    {field: 'larq', title: '立案日期',},
                    {title: '操作', minWidth: 60, toolbar: '#currentTableBar', fixed: "right", align: "center"}
                ]],
                done: function () {
                    layer.closeAll('loading');
                }
            });

        //执行一个laypage实例
        layui.laypage.render({
            elem: 'tablePageBar',
            count: resp.data.total,
            curr:  $("#pageNum").val(),
            limit: $("#pageSize").val(),
            limits: [10, 20, 30, 40, 50, 100, 200, 500],
            layout: ['prev', 'page', 'next',"limit","count"],
            jump: function(obj, first){
                if(!first){//首次不执行
                    $("#pageNum").val(obj.curr);
                    $("#pageSize").val(obj.limit);
                    data_list();
                }
            }
        });

    });
    }

</script>
<script>

</script>

</body>
</html>