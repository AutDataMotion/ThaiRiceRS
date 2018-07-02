package thairice.mvc.t14my_region;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class t14my_regionValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t14my_regionValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(t14my_regionController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(t14my_regionController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(t14my_region.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(t14my_regionController.pthc+"save")){
			controller.render(t14my_regionController.pthv+"xxx.html");
		
		} else if (actionKey.equals(t14my_regionController.pthc+"update")){
			controller.render(t14my_regionController.pthv+"xxx.html");
		
		}
	}
	
}
