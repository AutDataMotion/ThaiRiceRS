package thairice.mvc.t3user;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

/**
 * 用户登录和注册的校验
 */
public class T3userValidator extends Validator {
    {this.setShortCircuit(true);}
    protected void validate(Controller c) {
        String actionKey = getActionKey();
        //用户上传头像
        if (actionKey.equals(T3userController.pthc + "upload_head")||actionKey.equals(BackendUserController.pthc + "upload_head")) {
            String Path ="head";
            UploadFile uf = null;
            try {
                uf = c.getFile("head", Path, 1024 * 1024);
                if(uf==null){
                    addError("errorMsg", "Please upload pictures");
                }
                c.setAttr("url", "/upload/" + Path +"/"+uf.getFileName());
            } catch (Exception e) {
                String msg = e.getMessage();
                if (msg != null) {
                    if (msg.indexOf("exceeds") != -1) {
                        addError("errorMsg", "Size exceeds 1M limit");
                    }
                    if (uf != null) {
                        uf.getFile().delete();
                    }
                    addError("errorMsg", msg);
                }
            }

        }
        //前台用户登录
        if (actionKey.equals(T3userController.pthc + "Login")) {
        	if(StrKit.isBlank(c.getPara("account"))){
        		addError("desc", "Please enter your account!");
        	}
         	if(StrKit.isBlank(c.getPara("password"))){
        		addError("desc", "Please enter your password!");
        	}
        	if(StrKit.isBlank(c.getPara("authCode"))){
        		addError("desc", "Please enter verification code!");
        	}
        }
        //后台用户登录
        if (actionKey.equals(BackendUserController.pthc + "Login")) {
    	if(StrKit.isBlank(c.getPara("account"))){
    		addError("desc", "Please enter your account!");
    	}
     	if(StrKit.isBlank(c.getPara("pass"))){
    		addError("desc", "Please enter your password!");
    	}
    	if(StrKit.isBlank(c.getPara("authCode"))){
    		addError("desc", "Please enter verification code");
    	}
    }
        //用户注册
        if (actionKey.equals(T3userController.pthc + "doReg")) {
        	if(TimeUtil.dateDiff(c.getPara("Prdt_EfDt",TimeUtil.getNow()), c.getPara("PD_ExDat"), "yyyy-MM-dd", "d")<=0){
        		addError("desc", "The expiration time of the product should be greater than the start time");
        	}
        }
        //找回密码或修改密码
        if (actionKey.equals(T3userController.pthc + "edit_pass")||actionKey.equals(T3userController.pthc + "rest_pass")) {
            validateEmail("email", "desc", "Please input the vaild email");
            validateRequired("code", "desc", "Please enter verification code");
        }
        //找回密码或修改密码
        if (actionKey.equals(T3userController.pthc + "send_code")) {
            validateEmail("email", "desc", "Please input the vaild email");
        }
    }

    protected void handleError(Controller c) {
        c.setAttr("code", 0);
        c.renderJson(new String[]{"code", "desc"});
    }

}

