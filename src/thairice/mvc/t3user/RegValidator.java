package thairice.mvc.t3user;
import org.apache.log4j.Logger;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class RegValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RegValidator.class);
	
	protected void validate(Controller controller) {
		String actionKey = getActionKey();
		String pass = controller.getPara("t3user.pwd");
		String confirmPass = controller.getPara("confirmPass");
		if (actionKey.equals(T3userController.pthc+"doReg")){
			 validateString("t3user.account", 6, 18, "accountMsg", "Please enter the 6 to 18 digit login account!");
			 validateEmail("t3user.email", "emailMsg", "please enter your vaild email");
			 pass = pass.trim();
			 confirmPass = confirmPass.trim();
			 if(!pass.equals(confirmPass)) {
				 addError("passMsg", "The password entered twice is inconsistent");
			 }
			 validateString("t3user.phone", 1, 10, "phoneMsg", "Please enter the correct mobile!");
			 validateEmail("t3user.email","emailMsg", "Please input the correct email!");
			 validateString("t3user.name_", 1, 180, "name_", "Please enter the user name from 1 to 180 digits in length!");
//			 validateString("t3user.company", 1, 240, "companyMsg", "Please enter a company name from 1 to 240 lengths!");
//			 validateString("t3user.industry", 1, 80, "industryMsg", "Please enter industry name from 1 to 80 digits in length!");
		} else if (actionKey.equals(T3userController.pthc+"update")){
			 validateString("t3user.account", 6, 18, "accountMsg", "The account must be between 6 and 18 characters!");
			 validateEmail("t3user.email", "emailMsg", "please enter your vaild email");
			 pass = pass.trim();
			 confirmPass = confirmPass.trim();
			 if(!pass.equals(confirmPass)) {
				 addError("passMsg", "The password entered twice is inconsistent");
			 }
			 validateString("t3user.phone", 1, 10, "phoneMsg", "Please enter the correct Mobile!");
			 validateEmail("t3user.email","emailMsg", "Please input the correct email!");
			 validateString("t3user.name_", 1, 180, "name_", "Please enter the user name from 1 to 180 digits in length!");
			 validateString("t3user.company", 1, 240, "companyMsg", "Please enter a company name from 1 to 240 lengths!");
			 validateString("t3user.industry", 1, 80, "industryMsg", "Please enter industry name from 1 to 80 digits in length!");
			
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(T3user.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals(T3userController.pthc+"doReg")){
			controller.render(T3userController.pthv+"reg.html");
		
		} else if (actionKey.equals(T3userController.pthc+"update")){
			controller.render(T3userController.pthv+"User.html");
		
		}
	}
	
}
