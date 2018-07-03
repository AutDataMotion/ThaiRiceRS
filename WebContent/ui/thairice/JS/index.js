$(function () {
    //日期选择插件
    $('#Prdt_EfDt').datetimepicker({
        format: 'YYYY-MM-DD',
        locale: moment.locale('zh-cn')
    });
    $('#PD_ExDat').datetimepicker({
        format: 'YYYY-MM-DD',
        locale: moment.locale('zh-cn')
    });
    $('.btn_selfinfo').click(function () {
        $("#fm").bootstrapValidator('validate');
        if ($("#fm").data('bootstrapValidator').isValid()) {
            $('.form_selfinfo').hide();
            $('.form_serviceinfo').show();
        }
    });
    $('#previous').click(function () {
    	 $('.form_selfinfo').show();
         $('.form_serviceinfo').hide();
    });
    //$('#multiple_select').multiselect();
    $("#fm").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            /*输入框不同状态，显示图片的样式*/
            valid: 'glyphicon glyphicon-ok',
            //invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            /*验证*/
            't3user.account': {
                validators: {
                    notEmpty: {
                        message: 'The account is  required and can\'t be empty '
                    },
                    regexp: {
                    	 regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/,
                        message: 'The account is made up of 6 to 12 letters and Numbers'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: '/jf/thairice/t3user/valiUserName',
                        message: 'The account has already exist',
                        delay: 2000,
                        type: 'POST',
                        //自定义参数
                        data: {
                            userName: function () {
                                return $('input[name="t3user.account"]').val();
                            }
                        }
                    }
                }
            },
            't3user.pwd': {
                validators: {
                    notEmpty: {
                        message: 'The pwd is required and can\'t be empty '
                    },
                    regexp: {
                    	regexp: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$/,
                        message: 'The passwords must contain at least 2 kinds of letters, numbers and symbols and the length is 6-20 characters'
                    }
                }
            },
            'confirmPass': {
                message: 'The password is invalid',
                validators: {
                    notEmpty: {
                        message: 'The password is required and can\'t be empty'
                    },
                    identical: {//相同
                        field: 't3user.pwd',
                        message: ' Two input password must be consistent'
                    }
                }
            },
            't3user.phone': {
                validators: {
                    notEmpty: {
                        message: 'Mob is required and can\'t be empty'
                    },                    
                    regexp: {
                        regexp: /^0\d{9}$/,
                        message: 'The Mob number is invalid'
                    }
                }
            },
            't3user.email': {
                validators: {
                    notEmpty: {
                        message: 'The email address is required and can\'t be empty'
                    },
                    emailAddress: {
                        message: 'The input is not a valid email address'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: '/jf/thairice/t3user/valiMailBox2',
                        message: 'The email address has already exist',
                        delay: 2000,
                        type: 'POST',
                        //自定义参数
                        data: {
                            mailBox: function () {
                                return $('input[name="t3user.email"]').val();
                            }
                        }
                    }
                }
            },
            'PD_TpCd': {
                validators: {
                    notEmpty: {
                        message: 'Please choose one item at least'
                    }
                }
            },
            'PD_TpCd': {
                validators: {
                    notEmpty: {
                        message: 'Please choose one item at least'
                    }
                }
            },
            'Prdt_EfDt': {
                validators: {
                    notEmpty: {
                        message: 'The date can\'t be empty'
                    },
                    date : {
                        format : 'YYYY-MM-DD',
                        message : 'Enters dates in an incorrect format '
                    }
                }
            },
            'PD_ExDat': {
                validators: {
                    notEmpty: {
                        message: 'The date can\'t be empty'
                    },
                    date : {
                        format : 'YYYY-MM-DD',
                        message : 'Enters dates in an incorrect format '
                    }
                }
            }
        }
    });
});
//bootstrapValidator与datetimepicker混合使用时日期验证不刷新
$("#Prdt_EfDt1").blur(function(){
    $('#fm').data('bootstrapValidator').updateStatus('Prdt_EfDt','NOT_VALIDATED',null).validateField('Prdt_EfDt');
});
$("#PD_ExDat1").blur(function(){
    $('#fm').data('bootstrapValidator').updateStatus('PD_ExDat','NOT_VALIDATED',null).validateField('PD_ExDat');
});
function submitForm() {
    $("#fm").bootstrapValidator('validate');
    if(!$("#fm").data('bootstrapValidator').isValid()){
        return;
    }
	  var list=[];
  	  $('.item').each(function(){
  	    list.push({'province':$(this).find('#p').attr("type"),'city':$(this).find('#c').attr("type"),'area':$(this).find('#a').attr("type")})
  	 });
    if(!$("#agree").is(":checked")){
    	showMessage('Please agree with the terms of service first');
    	return;
    }
    if(list.length==0){
    	showMessage('Please select the service area');
    	return;
    }
    var psd = hex_md5($('#pwd').val());
    $('#pwd').val(psd);
    showLoading();
	sendAjax("/jf/thairice/t3user/doReg", $("#fm").serialize()+"&list="+JSON.stringify(list), function (res) {
    	cancelLoading();
        showMessage(res.desc);
        if (res.code == 1) {
        	  setTimeout(function(){
        		  window.location.href = "/jf/thairice/t3user/notActivated";
              },500);
        }
    })
}