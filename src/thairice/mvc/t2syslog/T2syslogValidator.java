package thairice.mvc.t2syslog;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T2syslogValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T2syslogValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T2syslogController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T2syslogController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T2syslog.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T2syslogController.pthc+"save")){
			controller.render(T2syslogController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T2syslogController.pthc+"update")){
			controller.render(T2syslogController.pthv+"xxx.html");
		
		}
	}
	
}
