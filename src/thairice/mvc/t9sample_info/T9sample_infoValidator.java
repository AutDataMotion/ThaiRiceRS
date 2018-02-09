package thairice.mvc.t9sample_info;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T9sample_infoValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T9sample_infoValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T9sample_infoController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T9sample_infoController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T9sample_info.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T9sample_infoController.pthc+"save")){
			controller.render(T9sample_infoController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T9sample_infoController.pthc+"update")){
			controller.render(T9sample_infoController.pthv+"xxx.html");
		
		}
	}
	
}
