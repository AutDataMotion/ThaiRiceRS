package thairice.mvc.t6org_data;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T6org_dataValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T6org_dataValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T6org_dataController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T6org_dataController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T6org_data.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T6org_dataController.pthc+"save")){
			controller.render(T6org_dataController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T6org_dataController.pthc+"update")){
			controller.render(T6org_dataController.pthv+"xxx.html");
		
		}
	}
	
}
