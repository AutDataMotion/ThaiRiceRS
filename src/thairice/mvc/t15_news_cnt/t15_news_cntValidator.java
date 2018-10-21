package thairice.mvc.t15_news_cnt;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class t15_news_cntValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t15_news_cntValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		if (actionKey.equals(t15_news_cntController.pthc+"save")){
			// validateString("username", 6, 30, "usernameMsg", "请输入登录账号!");
			
		} else if (actionKey.equals(t15_news_cntController.pthc+"update")){
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(t15_news_cnt.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(t15_news_cntController.pthc+"save")){
			controller.render(t15_news_cntController.pthv+"xxx.html");
		
		} else if (actionKey.equals(t15_news_cntController.pthc+"update")){
			controller.render(t15_news_cntController.pthv+"xxx.html");
		
		}
	}
	
}
