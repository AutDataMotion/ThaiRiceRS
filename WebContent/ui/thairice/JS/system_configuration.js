// JavaScript Document
$(function(){
	function initFileInput(ctrlName, uploadUrl) { 
		 var control = $('#' + ctrlName); 
		 control.fileinput({
		 language: 'zh',
		 uploadUrl: uploadUrl,
		 allowedFileExtensions : ['jpg', 'png','gif'],
		 showUpload: false, 
		 showCaption: false,
		 /*browseClass: "btn btn-primary", */
		 previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
		 });
		}
		initFileInput("txt_file07", "" ,"primary",false); 
})