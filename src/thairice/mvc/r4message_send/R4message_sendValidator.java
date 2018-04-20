package thairice.mvc.r4message_send;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class R4message_sendValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(R4message_sendValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(R4message_sendController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(R4message_sendController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(R4message_send.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(R4message_sendController.pthc+"save")){
			controller.render(R4message_sendController.pthv+"xxx.html");
		
		} else if (actionKey.equals(R4message_sendController.pthc+"update")){
			controller.render(R4message_sendController.pthv+"xxx.html");
		
		}
	}
	
}
