/**
 * 公共的js组件封装
 */

function showLoading() {
    layer.load(1, {
        shade: [0.1, '#fff'] //0.1透明度的白色背景
    });
}
function cancelLoading() {
	 var index = layer.load(1); // 换了种风格
	    layer.close(index);
}
/**
 * 弹出一个对话框
 * @param title
 */
function showDialog(title) {
    layer.open({
        type: 1,
        title: [
            'prompt'
        ],
        closeBtn: 1,
        area: ['210px', '150px'],
        anim: 'up',
        shadeClose: false,
        btn: ['close'],
        skin: 'layui-layer-lan',
        content: '<div style="margin-top:20px;text-align: center">' + title + '</div>',
    });
}
/**
 * 弹出一个提示信息
 *
 * @param content
 *            提示内容
 */
function showMessage(content) {
    layer.alert(content, {
    	title:'information',
        skin: 'layui-layer-molv',// 样式类名
        closeBtn: 0,
        btn:['confirm']
    });
}
/**
 * ajax post提交
 *
 * @param url
 * @param param
 * @param callback回调函数
 * @return
 */
function sendAjax(url, param, callback) {
    $.ajax({
        type: "get",
        url: url,
        data: param,
        dataType: "json",
        success: callback,
        error: function () {
            alert("Abnormal service")
        }
    });
}
/**
 * 删除单行活多行
 * @param url
 * @param $table
 */
function deleteRows(url, $table, _table) {
    var ids = $("tbody :checkbox:checked", $table).size();
    if (ids > 0) {
        layer.confirm('Sure you want to delete selected' + ids + 'data?', {icon: 3,title:'prompt',btn: ['confirm','cancel']}, function (index) {
            var list = [];
            $("tbody :checkbox:checked", $table).each(function (i) {
                var item = _table.row($(this).closest('tr')).data();
                list.push(item.id);
            });
            sendAjax(url, {ids: list.toString()}, function (res) {
                showMessage(res.desc);
                if (res.code == 1) {
                    _table.draw();
                }
            })
            layer.close(index);
        });
    } else {
        showMessage("Please delete selected lines")
    }
}

var  getIds=function($table, _table) {
    var list = [];
    $("tbody :checkbox:checked", $table).each(function (i) {
        var item = _table.row($(this).closest('tr')).data();
        list.push(item.id);
    });
    return list.toString();
}

var clickItem=function($table, _table){
    var item = _table.row($(this).closest('tr')).data();
    $(this).closest('tr').addClass("active").siblings().removeClass("active");
    return item;
}

/**
 * 获取选中行
 * @param $table
 * @returns {*}
 */
var getRowItem = function ($table, _table) {
    var selected = $("tbody :checkbox:checked", $table).size();
    if (selected < 1 || selected > 1) {
        showMessage("Please select a line to edit")
        return;
    }
    var item = null;
    $("tbody :checkbox:checked", $table).each(function (i) {
        item = _table.row($(this).closest('tr')).data();
    });
    return item;
}
var CONSTANT = {
    DATA_TABLES: {
        DEFAULT_OPTION: { //DataTables初始化选项
            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
            aLengthMenu: [10, 15, 20, 50],
            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
            processing: true,  //隐藏加载提示,自行处理
            serverSide: true,   //启用服务器端分页
            searching: false,    //禁用原生搜索
            destroy: true
        },
        COLUMN: {
            CHECKBOX: { //复选框单元格
                className: "td-checkbox",
                orderable: false,
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            }
        }
    }
};
var table = {
    initTable: function ($wrapper, $table, url, columns) {
        return $table.dataTable($.extend(true, {}, CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param =getQueryCondition(data);
                $.ajax({
                    type: "GET",
                    url: url,
                    cache: false,  //禁用缓存
                    data: param,    //传入已封装的参数
                    dataType: "json",
                    success: function (result) {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.total;//总记录数
                        returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.aaData;
                        callback(returnData);
                    },
                    error: function () {
                        showMessage("Query failed")
                    }
                });
            },
            "columns": columns,
            "drawCallback": function (settings) {
                //清空全选状态
                $(":checkbox[name='cb-check-all']", $wrapper).prop('checked', false);
            }
        })).api();
    },
    //checkbox点击事件
    checkboxClick: function ($table) {
        $table.on("change", ":checkbox", function () {
            if ($(this).is("[name='cb-check-all']")) {
                //全选
                $(":checkbox", $table).prop("checked", $(this).prop("checked"));
            } else {
                //一般复选
                var checkbox = $("tbody :checkbox", $table);
                $(":checkbox[name='cb-check-all']", $table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
            }
        }).on("click", ".td-checkbox", function (event) {
            //点击单元格即点击复选框
            !$(event.target).is(":checkbox") && $(":checkbox", this).trigger("click");
        });
    }
}

