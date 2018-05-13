package thairice.interceptor;

import com.jfinal.aop.Duang;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;

import thairice.mvc.t3user.T3user;
import thairice.mvc.t3user.T3userService;


/**
 * 前台用户登录拦截器
 */
public class UserLoginInterceptor implements Interceptor {
    public void intercept(Invocation inv) {
        if (inv.getController().getSessionAttr("user") != null||inv.getController().getSessionAttr("admin")!=null) {
            if(inv.getController().getSessionAttr("user") != null) {
        	 T3user user = inv.getController().getSessionAttr("user");
                 inv.getController().setAttr("count", Duang.duang(T3userService.class).getCount(user.getBigInteger("id")));
            }
            inv.invoke();
        } else {
            String queryString = inv.getController().getRequest().getQueryString();
            if (StrKit.isBlank(queryString)) {
                inv.getController().redirect("/jf/thairice/t3user/login?returnUrl=" + inv.getActionKey()+"#page4");
            } else {
                inv.getController().redirect("/jf/thairice/t3user/login?returnUrl=" + inv.getActionKey() + "?" + queryString+"#page4");
            }
        }
    }

}
