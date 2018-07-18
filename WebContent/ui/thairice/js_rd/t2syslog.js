
$(document).ready(function() {

	// 获取查询参数
	// 获取查询参数
	var datasrch = {
		id : '',
		name1 : '',//
		name2 : '',//
		name3 : '',//
		name4 : '',//
		name5 : '',//
		name6 : '',//
		name7 : '',//
		name8 : '',//
		exportall : '',
		pageIndex : '',
		pageSize : ''
	};
	
	var myTable;
	myTable = $('#dataTable09').DataTable({
		dom : 'Bfrtip',
		select : false,
		serverSide : true,
		scrollY : 400,
		scrollX : true,
		responsive : true,
		"searching": false,
		"ordering": false,
		"bProcessing" : true, // DataTables载入数据时，是否显示‘进度’提示
		"sProcessing" : "loading...",
//		"bFilter" : true, // 过滤功能
		"bPaginate" : true, // 翻页功能
		"bLengthChange" : true, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : true, // 排序功能
		ajax : {
			type : "POST",
			url : encodeURI(encodeURI(cxt + "/jf/thairice/t2syslog/search")),
			data : function(d) {
				d.columns = null;
				d.v = JSON.stringify(datasrch);
			}
		},
		buttons: [
	        'excel', 'pdf'
	    ],
		columns : [ {
			data : "id"
		}, {
			data : "type_"
		}, {
			data : "userName"
		}, {
			data : "action_"
		}, {
			data : "content"
		}, {
			data : "add_time"
		} ]
	});

	$('#btnSearch').click( function() {
	
		datasrch.name1 = $('#Type').val();
		datasrch.name2 = $('#userName').val();
		datasrch.name3 = $('#Action').val();
		datasrch.name4 = $('#datetimeBeg').val();
		datasrch.name5 = $('#datetimeEnd').val();
		// 重新加载table数据
		myTable.ajax.reload();
	});

});
