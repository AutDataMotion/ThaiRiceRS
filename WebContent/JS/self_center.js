/**
 * author:lyf
 */
$(function(){
	$('#reportDownloadBtn').click(function(e) {//生成统计图
		$(document.getElementsByClassName("reportDownloadCheckbox")).each(function(i){
            if(this.checked == true){
                //console.log(this.getAttribute('value'));
            	window.open(this.getAttribute('value'),"_blank");
            }
        })
    });
});