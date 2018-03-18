package thairice.mvc.t3user;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T3userValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T3userValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T3userController.pthc+"save")){
			 validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T3userController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T3user.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T3userController.pthc+"save")){
			controller.render(T3userController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T3userController.pthc+"update")){
			controller.render(T3userController.pthv+"xxx.html");
		
		}
	}
	
}
