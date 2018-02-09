package thairice.mvc.t11zone;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T11zoneValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T11zoneValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T11zoneController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T11zoneController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T11zone.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T11zoneController.pthc+"save")){
			controller.render(T11zoneController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T11zoneController.pthc+"update")){
			controller.render(T11zoneController.pthv+"xxx.html");
		
		}
	}
	
}
