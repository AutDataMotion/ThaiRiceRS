package thairice.mvc.t13region;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T13RegionValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T13RegionValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T13RegionController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T13RegionController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T13Region.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T13RegionController.pthc+"save")){
			controller.render(T13RegionController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T13RegionController.pthc+"update")){
			controller.render(T13RegionController.pthv+"xxx.html");
		
		}
	}
	
}
