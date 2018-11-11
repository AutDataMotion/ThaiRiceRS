package thairice.mvc.t16_pdt_sample;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class t16_pdt_sampleValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t16_pdt_sampleValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(t16_pdt_sampleController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(t16_pdt_sampleController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(t16_pdt_sample.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(t16_pdt_sampleController.pthc+"save")){
			controller.render(t16_pdt_sampleController.pthv+"xxx.html");
		
		} else if (actionKey.equals(t16_pdt_sampleController.pthc+"update")){
			controller.render(t16_pdt_sampleController.pthv+"xxx.html");
		
		}
	}
	
}
