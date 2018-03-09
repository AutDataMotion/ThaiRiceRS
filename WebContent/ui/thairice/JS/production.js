// JavaScript Document
$(function(){
	$('#mycp').colorpicker();
	$('.result_btn').click(function(e) {
		$('.block').hide();
		$('.modes').hide();
		$('.samples').hide();
		$('.okay_btn').hide();
    });
	initFileInput("txt_file01", "" ,"primary",false);
	initFileInput("txt_file02", "" ,"primary",false);
	initFileInput("txt_file03", "" ,"primary",false);
	initFileInput("txt_file04", "" ,"primary",false);
	initFileInput("txt_file05", "" ,"primary",false);
	initDate();
	initTable();
	radioCheckedEvent();
});