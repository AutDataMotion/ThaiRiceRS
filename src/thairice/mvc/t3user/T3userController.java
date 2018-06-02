package thairice.mvc.t3user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.platform.constant.ConstantRender;
import com.platform.interceptor.ParamPkgInterceptor;
import com.platform.mvc.base.BaseController;

import thairice.constant.ConstantInitMy;
import thairice.interceptor.UserLoginInterceptor;
import thairice.mvc.r4message_send.R4message_send;
import thairice.mvc.t10pdt_report.T10pdt_report;
import thairice.mvc.t2syslog.EnumT2sysLog;
import thairice.mvc.t2syslog.T2syslogService;
import thairice.mvc.t8message.T8message;
import thairice.mvc.t8message.T8messageService;

/**
 * 前台用户中心
 */

@Before(UserLoginInterceptor.class)
public class T3userController extends BaseController {

	private static Logger LOG = Logger.getLogger(T3userController.class);

	public static final String pthc = "/jf/thairice/t3user/";
	public static final String pthv = "/f/t3user/";

	private static final thairice.mvc.t3user.T3userService srv = Duang.duang(T3userService.class);
	private static final T8messageService message_service = Duang.duang(T8messageService.class);
	private static final AuthCodeService codeService = Duang.duang(AuthCodeService.class);

	/**
	 * 列表
	 */
	public void index() {
	    renderWithPath(pthv + "index.html");
	}

	public void setViewPath() {
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}

	@Clear
	@Before(T3userValidator.class)
	public void login() {
		setAttr("returnUrl", "");
		keepPara("returnUrl");
		renderWithPath(pthv + "login.html");
	}
        //消息个数实时更新
	public void refresh() {
	    T3user user = getSessionAttr("user");
	    Record record=new Record();
	    record.set("count", Duang.duang(T3userService.class).getCount(user.getBigInteger("id")));
	    renderJson(record);
	}
	
	/**
	 * 登陆验证
	 *
	 * @throws Exception
	 */
	@Clear
	@Before(T3userValidator.class)
	public void Login() {
		boolean authCode = authCode();
		String account = null;
		Result res;
		if (authCode) {
			account = getPara("account");
			String password = getPara("password");
			res = T3userService.service.login(this, account, password);
		} else {
			res = new Result(0, "Verification code is error, please re-enter");
		}
	/*	if(res.getCode()==1) {
		    T3user user = getSessionAttr("user");
		    T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), account, "Log in", res.toString());  
		}else {
		    T2syslogService.addLog(EnumT2sysLog.INFO, BigInteger.ONE , account, "Log in", res.toString());  
		}*/
		renderJson(res);
	}

	@Clear
	public void reg() {
		renderWithPath(pthv + "reg.html");
	}
	@Clear
	public void privacy() {
	    renderWithPath(pthv + "privacy.html");
	}
	/**
	 * 注册操作
	 */
	@Clear
	@Before(T3userValidator.class)
	public void doReg() {
		T3user t3user = getModel(T3user.class);
		// 如果邮编为空默认00000
		t3user.setZip_encode(getPara("t3user.zip_encode", "00000"));
		t3user.setCreate_time(new Timestamp(new Date().getTime()));
		t3user.set("Prdt_EfDt", Timestamp.valueOf(getPara("Prdt_EfDt") + " 00:00:00"));
		t3user.set("PD_ExDat", Timestamp.valueOf(getPara("PD_ExDat") + " 00:00:00"));
		String l = "";
		for (String str : getParas("PD_TpCd")) {
			l += str + ",";
		}
		t3user.set("area", getPara("province")+" "+getPara("city")+" "+getPara("area"));
		t3user.set("PD_TpCd", l);
		boolean rlt = t3user.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
		if (rlt) {
			String authCode = codeService.createAuthCode(new BigInteger(t3user.get("id").toString()), 0, 3600);
			if (StrKit.notBlank(authCode)) {
				String url = PropKit.get("activation_url") + authCode;
				String content = "Visit the activation link below to complete account activation：\n\n" + "<a href="
						+ url + ">" + url + "</a>";
				boolean success = Mail.sendEmail("Account activation", content, t3user.getEmail().toString(),
						Mail.MODE_HTML);
				if (success) {
					renderJson(new Result(1,
							"Registration is successful, activation email has been sent, please check and activate the account"));
				} else {
					renderJson(new Result(1, "Registration was successful but the mailbox failed to send"));
				}
			}
		} else {
			renderJson(new Result(0, "Registration failed"));
		}
	}

	/**
	 * 显示还没激活页面
	 */
	@Clear
	public void notActivated() {
		render(pthv + "not_activated.html");
	}

	/**
	 * 点击链接激活
	 */
	@Clear
	public void activate() {
		Result result = codeService.activate(getPara("authCode"));
		setAttr("activate", result.getDesc());
		render(pthv + "not_activated.html");
	}

	/**
	 * 用户名是否已被注册
	 */
	@Clear
	public boolean isUserNameExists(String account) {
		account = account.toLowerCase().trim();
		return Db.queryInt("select id from t3user where account = ? limit 1", account) != null;
	}

	/**
	 * 验证用户名是否可用
	 */
	@Clear
	public void valiUserName() {
		String userName = getPara("userName");
		boolean bool = T3userService.service.valiUserName(userName);
		renderJson("valid", bool);
	}

	/**
	 * 验证邮箱是否可用(用于邮箱注册)
	 */
	@Clear
	public void valiMailBox() {
		String mailBox = getPara("mailBox");
		boolean bool = T3userService.service.valiMailBox(mailBox);
		renderJson("valid", bool);
	}

	/**
	 * 验证邮箱是否可用(用于密码找回)
	 */
	@Clear
	public void valiMailBoxForFindPass() {
		String mailBox = getPara("mailBox");
		boolean bool = T3userService.service.valiMailBoxForFindPass(mailBox);
		renderJson("valid", bool);
	}

	/**
	 * 个人信息中心
	 */
	public void self_center() {
		T3user user = getSessionAttr("user");
		String[]ad=user.getStr("area").split(" ");
		Page page  = T10pdt_report.dao.paginate(getParaToInt(0, 1), 10, "select *", "from T10pdt_report order by id asc");
		setAttr("blogPage",page );
		setAttr("province", ad[0]);
		T3user info=srv.SelectById(user.getBigInteger("id"));
		if(TimeUtil.isLaterThanNow(info.getPD_ExDat().toString())) {
		    info.setStatus_("04");    
		}
		setAttr("city", ad[1]);
		setAttr("area", ad[2]);
		setAttr("user", info);
		renderWithPath(pthv + "self_center.html");
	}

	/**
	 * 我的消息
	 */
	public void my_message() {
		renderWithPath(pthv + "my_message.html");
	}

	/**
	 * 个人消息列表
	 */
	public void message_list() {
		int pages = getParaToInt("start", 0) == 0 ? 1 : getParaToInt("start") / getParaToInt("pageSize", 3) + 1;
		T3user user = getSessionAttr("user");
		Page<T8message> page = message_service.selectByUserPage(pages, getParaToInt("pageSize", 3), getPara("state"),
				getPara("search"), user.getBigInteger("id"));
		Map record = new HashMap();
		record.put("sEcho", false);
		record.put("aaData", page.getList());
		record.put("recordsFiltered", page.getTotalRow());
		record.put("total", page.getTotalRow());
		renderJson(record);
	}

	/**
	 * 全部设为已读
	 */
	public void all_read() {
		T3user user = getSessionAttr("user");
		int row = Db.use(ConstantInitMy.db_dataSource_main)
				.update("UPDATE r4message_send SET status_='02' WHERE receive_userid=?", user.getBigInteger("id"));
		if (row > 0) {
			renderJson(new Result(1, "Operation succeeded"));
		} else {
			renderJson(new Result(0, "Operation failed"));
		}
	}

	/**
	 * 清空全部消息
	 */
	public void empty_message() {
		T3user user = getSessionAttr("user");
		int row = Db.use(ConstantInitMy.db_dataSource_main).update("DELETE FROM r4message_send WHERE receive_userid=?",
				user.getBigInteger("id"));
		if (row > 0) {
		    Db.use(ConstantInitMy.db_dataSource_main).update("DELETE FROM t8message WHERE send_userid=?",
				user.getBigInteger("id"));
			renderJson(new Result(1, "Operation succeeded"));
		} else {
			renderJson(new Result(1, "Operation failed"));
		}
	}

	/**
	 * 删除多条消息
	 */
	public void delete_message() {
		T3user user = getSessionAttr("user");
		int row = Db.use(ConstantInitMy.db_dataSource_main).update(
				"DELETE FROM r4message_send WHERE id IN(" + getPara("ids") + ") AND receive_userid=?",
				user.getBigInteger("id"));
		if (row > 0) {
			renderJson(new Result(1, "Operation succeeded"));
		} else {
			renderJson(new Result(0, "Operation failed"));
		}
	}

	/**
	 * 阅读消息
	 */
	public void read_message() {
		R4message_send send = getModel(R4message_send.class);
		if (send.getStatus_().equals("01")) {
			send.set("status_", "02");
			send.update();
		}
		renderJson(new Result(1, "Failed to delete"));
	}

	/**
	 * 修改用户信息
	 */
	public void edit_info() {
		T3user t3user = getModel(T3user.class);
		boolean rlt = t3user.use(ConstantInitMy.db_dataSource_main).update();
		if (rlt) {
			renderJson(new Result(1, "Operation succeeded"));
		} else {
			renderJson(new Result(0, "Operation failed"));
		}
	}

	/**
	 * 用户上传头像
	 */
	@Before(T3userValidator.class)
	public void upload_head() {
		T3user user = new T3user();
		user.set("id", new BigInteger(getPara("id")));
		user.set("heading", getAttrForStr("url"));
		user.update();
		renderJson(new Result(1, getAttrForStr("url")));
	}

	/**
	 * 显示忘记密码页面
	 */
	@Clear
	public void forget_pass() {
		render(pthv + "password_getback.html");
	}

	/**
	 * 发送邮箱验证码
	 */
	@Clear
	public void send_code() {
		T3user t3user = srv.getByEmail(getPara("email"));
		if (t3user == null) {
			renderJson(new Result(0, "The Email does not exist"));
			return;
		}
		// 创建授权码
		String authCode = codeService.createAuthCode(t3user.getBigInteger("id"), 0, 3600);
		boolean success = Mail.sendEmail(getParaToInt("type", 0) == 0 ? "Thailand agricultural remote sensing monitoring platform password assistance" : "change Password",
				"Hi,\n\nAre you trying to sign in?\nIf so, use this code to finish signing in.\n" + authCode+
				 "\n\nDidn't sign in recently?\r\n" + 
				 "\r\n" + 
				 "If this wasn't you, someone entered your email address by mistake so you can disregard this email.\n\n\nThanks.", getPara("email"), Mail.MODE_TEXT);
		if (success) {
			renderJson(new Result(1, "For your security, we need to verify your identity. We've sent a code to the email.Please enter it below."));
		} else {
			renderJson(new Result(0, "The email may not be sent out successfully, please contact the administrator"));
		}
	}

	/**
	 * 重置密码
	 */
	@Clear
	@Before(T3userValidator.class)
	public void rest_pass() {
		Result result = codeService.reset_pass(getPara("code"), getPara("pwd"));
		renderJson(result);
	}

	/**
	 * 修改密码
	 */
	@Before(T3userValidator.class)
	public void edit_pass() {
		Result result = codeService.reset_pass(getPara("code"), HashKit.md5(getPara("pwd")));
		renderJson(result);
	}

	/**
	 * 退出登录
	 */
	public void exit() {
		removeSessionAttr("user");
		redirect("/jf/thairice/t3user/login#page4");
	}

}
