package thairice.mvc.t5parameter_type;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T5parameter_typeValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T5parameter_typeValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T5parameter_typeController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T5parameter_typeController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T5parameter_type.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T5parameter_typeController.pthc+"save")){
			controller.render(T5parameter_typeController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T5parameter_typeController.pthc+"update")){
			controller.render(T5parameter_typeController.pthv+"xxx.html");
		
		}
	}
	
}
