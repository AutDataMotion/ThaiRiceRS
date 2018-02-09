package thairice.mvc.tkvalue;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class TkvalueValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TkvalueValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(TkvalueController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(TkvalueController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Tkvalue.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(TkvalueController.pthc+"save")){
			controller.render(TkvalueController.pthv+"xxx.html");
		
		} else if (actionKey.equals(TkvalueController.pthc+"update")){
			controller.render(TkvalueController.pthv+"xxx.html");
		
		}
	}
	
}
