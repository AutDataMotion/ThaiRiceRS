package thairice.mvc.t1parameter;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T1parameterValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T1parameterValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T1parameterController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T1parameterController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T1parameter.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T1parameterController.pthc+"save")){
			controller.render(T1parameterController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T1parameterController.pthc+"update")){
			controller.render(T1parameterController.pthv+"xxx.html");
		
		}
	}
	
}
