// JavaScript Document
$(function(){
	$('.list-group-item-text').mouseover(function(e) {
        $(this).addClass('active').siblings().removeClass('active');
    });
	$('.list-group-item-text').mouseout(function(e) {
        $(this).removeClass('active');
    });
	$('.list-group-item-text').click(function(e) {
        $('.BbProductMenu').hide();
		$('.BbProductabs').show();
		$('.BbReportcons').show();
		$('.BbReportcons .BbReportLeft').show();
		$('.BbReportcons .BbReportRight').hide();
		$('.BbReportWrap').show();
		$('.BbReportGenerate').hide();
		$('.progress').hide();
    });
	$('.report_btn01').click(function(e) {
        $('.BbReportcons .BbReportRight').fadeIn();
    });
	$('.BbProductabs').click(function(e) {
        $(this).hide().siblings().show();
    });
	$('.generate_cons button.close').click(function(e) {
    	$('.BbReportGenerate').hide();
		$('.BbReportcons').show();
		$('.BbReportcons .BbReportLeft').show();
		$('.BbReportcons .BbReportRight').show();
    });
	$('.modal-footer .btn').click(function(e) {
        $('.modal').hide();
		$('.BbReportGenerate').hide();
		$('.BbReportcons').hide();
		$('.BbReportRight').hide();
		$('.progress').hide();
		$('.BbReportWrap').hide();
    });
	$('.download_btn').click(function(e) {
        $('.BbReportWrap').hide();
    });
	$('.report_downbtn').click(function(e) {
        $('#reportModal').modal();
    });
})