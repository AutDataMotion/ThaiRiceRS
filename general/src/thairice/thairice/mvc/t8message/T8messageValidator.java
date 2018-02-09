package thairice.mvc.t8message;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T8messageValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T8messageValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T8messageController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T8messageController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T8message.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T8messageController.pthc+"save")){
			controller.render(T8messageController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T8messageController.pthc+"update")){
			controller.render(T8messageController.pthv+"xxx.html");
		
		}
	}
	
}
