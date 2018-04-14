
$(document).ready(function() {
	var myTable;
	myTable = $('#dataTable09').DataTable({
		buttons: [
	        'excel', 'pdf'
	    ],
		columns : [ {
			data : "id"
		}, {
			data : "type_"
		}, {
			data : "userid"
		}, {
			data : "action_"
		}, {
			data : "content"
		}, {
			data : "add_time"
		} ],
		"bProcessing" : true, // DataTables载入数据时，是否显示‘进度’提示
		"sProcessing" : "loading...",
		"scrollX" : true
	});

	// 获取查询参数
	var datasrch = {
		userId : '',
		userName : '',
		dateTimeBeg : '',
		dateTimeEnd : '',
		pageIndex : '',
		pageSize : ''
	};
	function search(data, callback, settings) {
		console.log("search");
		datasrch.userId = $('#userId').val();
		datasrch.dateTimeBeg = $('#datetimeBeg').val();
		datasrch.dateTimeEnd = $('#datetimeEnd').val();
		datasrch.pageIndex = 0;
		datasrch.pageSize = 200;
		// 发送查询请求
		$.ajax({
			type : "get",
			url : encodeURI(encodeURI(cxt + "/jf/thairice/t2syslog/search")),
			data : {
				v : JSON.stringify(datasrch)
			},
			dataType : 'json',
			contentType : "application/json",
			success : function(response) {
				console.log(response);
				myTable.clear().draw();
				myTable.rows.add(response).draw();
			}
		});
	};
	$('#btnSearch').click(search);
	$('#btnSearch').click();
});
