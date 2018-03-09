package com.platform.mvc.login;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.platform.constant.ConstantLogin;
import com.platform.constant.ConstantWebContext;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.user.User;
import com.platform.tools.ToolWeb;

import thairice.constant.ConstantInitMy;

/**
 * 登陆处理
 */
//@Controller(controllerKey = "/jf/platform/login")
public class LoginController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(LoginController.class);
	
	/**
	 * 准备登陆
	 */
	public void index() {
		User user = getCUser(); // cookie认证自动登陆处理
		if(null != user){//后台
			redirect("/jf/platform/");
		}else{
			System.out.println("hello");
			Db.update("update pt_dict set zhuangtai = ? where zhuangtai is null", "2");
			String sql = "update T6org_data set download_path = '9' where download_path='5'";
			Db.use(ConstantInitMy.db_dataSource_main).update(sql);
			render("/platform/login/login.html");
		}
	}

	/**
	 * 验证账号是否可用
	 */
	public void valiUserName(){
		String userIds = getPara("userIds");
		String userName = getPara("userName");
		boolean bool = LoginService.service.valiUserName(userIds, userName);
		renderText(String.valueOf(bool));
	}
	
	/**
	 * 验证邮箱是否可用
	 */
	public void valiMailBox(){
		String userInfoIds = getPara("userInfoIds");
		String mailBox = getPara("mailBox");
		boolean bool = LoginService.service.valiMailBox(userInfoIds, mailBox);
		renderText(String.valueOf(bool));
	}

	/**
	 * 验证身份证是否可用
	 */
	public void valiIdcard(){
		String userInfoIds = getPara("userInfoIds");
		String idcard = getPara("idcard");
		boolean bool = LoginService.service.valiIdcard(userInfoIds, idcard);
		renderText(String.valueOf(bool));
	}

	/**
	 * 验证手机号是否可用
	 */
	public void valiMobile(){
		String userInfoIds = getPara("userInfoIds");
		String mobile = getPara("mobile");
		boolean bool = LoginService.service.valiMobile(userInfoIds, mobile);
		renderText(String.valueOf(bool));
	}

	/**
	 * 登陆验证
	 */
	@Before(LoginValidator.class)
	public void vali() {
		boolean authCode = authCode();
		//===========取消验证码阻拦
		//authCode = true;
		if(authCode){
			log.debug("-------!!!!auth:suc1");
			String username = getPara("username");
			String password = getPara("password");
			String remember = getPara("remember");
			boolean autoLogin = false;
			if(null != remember && remember.equals("1")){
				autoLogin = true;
			}
			int result = LoginService.service.login(getRequest(), getResponse(), username, password, autoLogin);
			log.debug("-----!!!!auth:suc2:"+result);
			if(result == ConstantLogin.login_info_3){
				redirect("/jf/platform/index");
				return;
			}
		}
		
		redirect("/jf/platform/login");
	}

	/**
	 * 锁屏验证密码
	 */
	@Before(LoginValidator.class)
	public void pass() {
		User user = getCUser();
		String password = getPara("password");
		
		int result = LoginService.service.pass(getRequest(), getResponse(), user.getStr("username"), password);
		if(result == ConstantLogin.login_info_3){
			redirect("/jf/platform/index");
			return;
		}
		
		redirect("/jf/platform/login");
	}

	/**
	 * 注销
	 */
	public void logout() {
		ToolWeb.addCookie(getResponse(), "", "/", true, ConstantWebContext.cookie_authmark, null, 0);
		redirect("/jf/platform/login");
	}

	 /* (non-Javadoc)
	 * <p>Description: <／p>
	 * @see com.platform.mvc.base.BaseController#setViewPath()
	 */
	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub
		
	}

}
