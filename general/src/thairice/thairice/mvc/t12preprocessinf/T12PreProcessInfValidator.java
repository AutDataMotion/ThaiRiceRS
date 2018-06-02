package thairice.mvc.t12preprocessinf;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T12PreProcessInfValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T12PreProcessInfValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T12PreProcessInfController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T12PreProcessInfController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T12PreProcessInf.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T12PreProcessInfController.pthc+"save")){
			controller.render(T12PreProcessInfController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T12PreProcessInfController.pthc+"update")){
			controller.render(T12PreProcessInfController.pthv+"xxx.html");
		
		}
	}
	
}
