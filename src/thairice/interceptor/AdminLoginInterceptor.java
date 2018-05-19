package thairice.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;


/**
 * 后台操作员和管理员登录拦截器
 */
public class AdminLoginInterceptor implements Interceptor {
    public void intercept(Invocation inv) {
	
	if (inv.getActionKey().contains("/jf/thairice/t3user")) {
	      inv.invoke();
	      return;
	}   
        if (inv.getController().getSessionAttr("admin") != null) {
            inv.invoke();
        } else {
            String queryString = inv.getController().getRequest().getQueryString();
            if (StrKit.isBlank(queryString)) {
                inv.getController().redirect("/jf/thairice/admin/user/login?returnUrl=" + inv.getActionKey());
            } else {
                inv.getController().redirect("/jf/thairice/admin/user/login?returnUrl=" + inv.getActionKey() + "?" + queryString);
            }
        }
    }

}
