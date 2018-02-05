package com.platform.mvc.role;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class RoleValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RoleValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals("/role/save")){
			
		} else if (actionKey.equals("/role/update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Role.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/role/save")){
			
		} else if (actionKey.equals("/role/update")){
			
		}
	}
}
