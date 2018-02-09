package thairice.mvc.t10pdt_report;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class T10pdt_reportValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T10pdt_reportValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(T10pdt_reportController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(T10pdt_reportController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T10pdt_report.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T10pdt_reportController.pthc+"save")){
			controller.render(T10pdt_reportController.pthv+"xxx.html");
		
		} else if (actionKey.equals(T10pdt_reportController.pthc+"update")){
			controller.render(T10pdt_reportController.pthv+"xxx.html");
		
		}
	}
	
}
