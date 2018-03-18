package thairice.mvc.t3user;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class RegValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RegValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		String pass = controller.getPara("t3user.pwd");
		String confirmPass = controller.getPara("confirmPass");
		if (actionKey.equals(T3userController.pthc+"doReg")){
			 validateString("t3user.account", 6, 12, "accountMsg", "请输入6到12位的的登录账号!");
			 validateEmail("t3user.email", "emailMsg", "请输入正确的邮箱");
			 pass = pass.trim();
			 confirmPass = confirmPass.trim();
			 if(!pass.equals(confirmPass)) {
				 addError("passMsg", "两次密码输入不一致");
			 }
			 validateString("t3user.phone", 1, 11, "phoneMsg", "请输入正确的电话号码!");
			 validateEmail("t3user.email","emailMsg", "请输入正确的邮箱地址!");
			 validateString("t3user.name_", 1, 180, "name_", "请输入1到180位的用户姓名!");
			 validateString("t3user.company", 1, 240, "companyMsg", "请输入1到240位的公司名称!");
			 validateString("t3user.industry", 1, 80, "industryMsg", "请输入1到80位的行业名称!");
		} else if (actionKey.equals(T3userController.pthc+"update")){
			 validateString("t3user.account", 6, 12, "accountMsg", "请输入6到12位的的登录账号!");
			 validateEmail("t3user.email", "emailMsg", "请输入正确的邮箱");
			 pass = pass.trim();
			 confirmPass = confirmPass.trim();
			 if(!pass.equals(confirmPass)) {
				 addError("passMsg", "两次密码输入不一致");
			 }
			 validateString("t3user.phone", 1, 11, "phoneMsg", "请输入正确的电话号码!");
			 validateEmail("t3user.email","emailMsg", "请输入正确的邮箱地址!");
			 validateString("t3user.name_", 1, 180, "name_", "请输入1到180位的用户姓名!");
			 validateString("t3user.company", 1, 240, "companyMsg", "请输入1到240位的公司名称!");
			 validateString("t3user.industry", 1, 80, "industryMsg", "请输入1到80位的行业名称!");
			
		} else if (actionKey.equals(T3userController.pthc+"valiModiPwd")){
			 validateString("t3user.account", 6, 12, "accountMsg", "请输入6到12位的的登录账号!");
//			 validateEmail("t3user.email", "emailMsg", "请输入正确的邮箱");
//			 pass = pass.trim();
//			 confirmPass = confirmPass.trim();
//			 if(!pass.equals(confirmPass)) {
//				 addError("passMsg", "两次密码输入不一致");
//			 }
//			 validateString("t3user.phone", 1, 11, "phoneMsg", "请输入正确的电话号码!");
//			 validateEmail("t3user.email","emailMsg", "请输入正确的邮箱地址!");
//			 validateString("t3user.name_", 1, 180, "name_", "请输入1到180位的用户姓名!");
//			 validateString("t3user.company", 1, 240, "companyMsg", "请输入1到240位的公司名称!");
//			 validateString("t3user.industry", 1, 80, "industryMsg", "请输入1到80位的行业名称!");
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T3user.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T3userController.pthc+"doReg")){
			controller.render(T3userController.pthv+"reg.html");
		
		} else if (actionKey.equals(T3userController.pthc+"update")){
			controller.render(T3userController.pthv+"User.html");
		
		}
	}
	
}
