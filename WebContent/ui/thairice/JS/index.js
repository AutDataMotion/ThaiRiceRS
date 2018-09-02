$(function () {
    //日期选择插件
    $('#Prdt_EfDt1').datetimepicker({
        format: 'YYYY-MM-DD',
        locale: moment.locale('zh-cn')
    });
    $('#PD_ExDat1').datetimepicker({
        format: 'YYYY-MM-DD',
        locale: moment.locale('zh-cn')
    })
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
                        message: 'The password is required and can\'t be empty'
                    },
                    regexp: {
                    	regexp: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$/,
                        message: 'It contains at least 2 kinds of letters, numbers and symbols and the length is 6-20 characters'
                    },
                    identical: {//相同
                        field: 'confirmPass',
                        message: 'Two input password must be consistent'
                    },
                    different: {
                        field: 't3user.account',
                        message: 'The password cannot be the same as account'
                    }
                }
            },
            'confirmPass': {
                message: 'The password is invalid',
                validators: {
                    notEmpty: {
                        message: 'The confirm password is required and can\'t be empty'
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
                        message: 'The mobile is required and can\'t be empty'
                    },                    
                    regexp: {
                        regexp: /^0\d{9}$/,
                        message: 'The mobile format is invalid'
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
/*                    regexp: {
                        regexp: /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$ 
                        message: 'The email format is invalid'
                    },*/
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
            't3user.name_': {
                validators: {
                    notEmpty: {
                        message: 'The nickname is required and can\'t be empty'
                    },
    	            stringLength: {
    	                min: 1,
    	                max: 32,
    	                message: 'The nickname cannot exceed 32 characters'
    	            }
                }
            },
            'PD_TpCd': {
                validators: {
                    notEmpty: {
                        message: 'Please choose at least one product'
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
    })
})/*.on('error.validator.bv', function (e, data) {//这个方法是让错误信息只显示最新的一个（有时会出现多个错误信息同时显示用这个方法解决）
	    data.element
	    .data('bv.messages')
	    // Hide all the messages
	    .find('.help-block[data-bv-for="' + data.field + '"]').hide()
	    // Show only message associated with current validator
	    .filter('[data-bv-validator="' + data.validator + '"]').show();
	})*/
  .on('error.field.bv', function (e, data) {//‘用户确认密码’ 没输入的时候，‘用户新密码’不提示‘用户新密码与确认密码不一致’
		// $(e.target)  --> The field element
		// data.bv      --> The BootstrapValidator instance
		// data.field   --> The field name
		// data.element --> The field element
		if (data.field == 't3user.pwd') {
		  var len1 = data.element.val().length;
		  var len2 = $('#confirmPass').val().length;
		  var k = data.element.val().indexOf(" ");
		  if (len1 > 5 && len2 < 6 && k < 0) {
		    var $parent = data.element.parents('.form-group');
		    $parent.removeClass('has-error');
		    $parent.find('.form-control-feedback[data-bv-icon-for="' + data.field + '"]').hide();
		    data.element.siblings('[data-bv-validator="identical"]').hide();
		  }
		}
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
              },4000);
        }
    })
}