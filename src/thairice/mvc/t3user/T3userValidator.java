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
        //用户登录
        if (actionKey.equals(T3userController.pthc + "doLogin")) {
        	if(StrKit.isBlank(c.getPara("account"))){
        		addError("errorMsg", "Please enter your account!");
        	}
         	if(StrKit.isBlank(c.getPara("password"))){
        		addError("errorMsg", "Please enter your password!");
        	}
        	if(StrKit.isBlank(c.getPara("authCode"))){
        		addError("errorMsg", "Please enter verification code!");
        	}
        }
        //用户登录
        if (actionKey.equals(T3userController.pthc + "doReg")) {
        	if(TimeUtil.dateDiff(c.getPara("Prdt_EfDt",TimeUtil.getNow()), c.getPara("PD_ExDat"), "yyyy-MM-dd", "d")<=0){
        		addError("errorMsg", "The service expiration time cannot be less than the start time!");
        	}
        }
        //找回密码或修改密码
        if (actionKey.equals(T3userController.pthc + "edit_pass")||actionKey.equals(T3userController.pthc + "rest_pass")) {
            validateEmail("email", "errorMsg", "E-mail format is incorrect!");
            validateRequired("code", "errorMsg", "Please enter the authorization code!");
        }
        //找回密码或修改密码
        if (actionKey.equals(T3userController.pthc + "send_code")) {
            validateEmail("email", "errorMsg", "E-mail format is incorrect!");
        }
    }

    protected void handleError(Controller c) {
        c.setAttr("code", 0);
        c.renderJson(new String[]{"code", "errorMsg"});
    }

}
