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
                        message: 'The user name is  required and can\'t be empty '
                    },
                    regexp: {
                    	 regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/,
                        message: 'The user name is made up of 6 to 12 letters and Numbers'
                    },
                    remote: {//ajax验证。server result:{"valid",true or false}
                        url: '/jf/thairice/t3user/valiUserName',
                        message: 'user name has already exist!',
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
                        message: 'The pwd is  required and can\'t be empty '
                    },
                    regexp: {
                    	regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/,
                        message: 'The password is made up of 6 to 12 letters and Numbers'
                    }
                }
            },
            'confirmPass': {
                message: 'Password is invalid',
                validators: {
                    notEmpty: {
                        message: 'Password is  required and can\'t be empty'
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
                        message: 'Phone is  required and can\'t be empty'
                    },                    
                    regexp: {
                        regexp: /^1\d{10}$/,
                        message: 'The Phone format is not correct'
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
                        url: '/jf/thairice/t3user/valiMailBox',
                        message: 'email has already exist!',
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
                        message: 'must choose a'
                    }
                }
            },
            'PD_TpCd': {
                validators: {
                    notEmpty: {
                        message: 'must choose a'
                    }
                }
            },
            'Prdt_EfDt': {
                validators: {
                    notEmpty: {
                        message: 'can\'t be empty'
                    },
                    date : {
                        format : 'YYYY-MM-DD',
                        message : '日期格式不正确'
                    }
                }
            },
            'PD_ExDat': {
                validators: {
                    notEmpty: {
                        message: 'can\'t be empty'
                    },
                    date : {
                        format : 'YYYY-MM-DD',
                        message : '日期格式不正确'
                    }
                }
            }
        }
    });
});
//bootstrapValidator与datetimepicker混合使用时日期验证不刷新
$("#Prdt_EfDt").blur(function(){
    $('#fm').data('bootstrapValidator').updateStatus('Prdt_EfDt','NOT_VALIDATED',null).validateField('Prdt_EfDt');
});
$("#PD_ExDat").blur(function(){
    $('#fm').data('bootstrapValidator').updateStatus('PD_ExDat','NOT_VALIDATED',null).validateField('PD_ExDat');
});
function submitForm() {
    $("#fm").bootstrapValidator('validate');
    if(!$("#fm").data('bootstrapValidator').isValid()){
        return;
    }
    var psd = hex_md5($('#pwd').val());
    $('#pwd').val(psd);
    showLoading();
    sendAjax("/jf/thairice/t3user/doReg", $("#fm").serialize(), function (res) {
    	cancelLoading();
        showMessage(res.desc);
        if (res.code == 1) {
        	  setTimeout(function(){
        		  window.location.href = "/jf/thairice/t3user/notActivated";
              },500);
        }
    })
}