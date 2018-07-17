// JavaScript Document
$(function(){
	$('.result_btn').click(function(e) {
		$('.block').hide();
		$('.modes').hide();
		$('.samples').hide();
		$('.okay_btn').hide();
    });
	/*$('#mycp').colorpicker();*/
	//日期选择插件
			$('#starttime3').datetimepicker({  
				format: 'YYYY-MM-DD',  
				locale: moment.locale('zh-cn')  
			}); 
			$('#endtime3').datetimepicker({  
				format: 'YYYY-MM-DD',  
				locale: moment.locale('zh-cn')  
			});
			$('#starttime4').datetimepicker({  
				format: 'YYYY-MM-DD',  
				locale: moment.locale('zh-cn')  
			}); 
			$('#endtime4').datetimepicker({  
				format: 'YYYY-MM-DD',  
				locale: moment.locale('zh-cn')  
			});
			function initFileInput(ctrlName, uploadUrl) { 
			 var control = $('#' + ctrlName); 
			 control.fileinput({
			 language: 'zh',
			 uploadUrl: uploadUrl,
			 allowedFileExtensions : ['jpg', 'png','gif'],
			 showUpload: false, 
			 showCaption: false,
			 browseClass: "btn btn-primary",  
			 previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
			 });
			}
			initFileInput("txt_file01", "" ,"primary",false);
			initFileInput("txt_file06", "" ,"primary",false);
			/*initDate();
			initTable();
			radioCheckedEvent();*/
	
});