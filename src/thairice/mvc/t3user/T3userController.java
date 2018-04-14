package thairice.mvc.t3user;

import com.platform.constant.ConstantLogin;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import thairice.mvc.t3user.T3userService;
import thairice.utils.EmailUtils;
import thairice.utils.ParamUtils;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import thairice.constant.ConstantInitMy;
import thairice.entity.ResultEntity;

/**
 * XXX 管理 描述：
 * 
 * /jf/thairice/t3user /jf/thairice/t3user/save /jf/thairice/t3user/edit
 * /jf/thairice/t3user/update /jf/thairice/t3user/view
 * /jf/thairice/t3user/delete /thairice/t3user/add.html
 * 
 */
// @Controller(controllerKey = "/jf/thairice/t3user")
public class T3userController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(T3userController.class);

	public static final String pthc = "/jf/thairice/t3user/";
	public static final String pthv = "/thairice/t3user/";

	private static final thairice.mvc.t3user.T3userService srv = T3userService.service;

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select,
				T3user.sqlId_splitPage_from);
		renderWithPath(pthv + "list.html");
	}

	/**
	 * 找回密码 zhuchaobin, 20180314
	 */
	public void getPassword() {
		setAttr("path", "/ui/thairice/");
		renderWithPath("/thairice/password_getback.html");
	}

	/**
	 * 找回密码 zhuchaobin, 20180314
	 * 
	 * @throws Exception
	 */
	public void getValidateCode() throws Exception {
		setAttr("path", "/ui/thairice/");
		// 处理结果
		ResultEntity res = null;
		try {
			// 获取用户账号
			String account = getPara("account");
			if (StringUtils.isBlank(account)) {
				LOG.debug("用户账号不能为空");
				res = new ResultEntity("0003");
				renderJson(res);
				return;
			}
			// 1、查找用户姓名和用户邮箱
			String sql = "select * from T3user t where t.account = '" + account + "'";
			LOG.debug("根据用户账户查找用户信息：" + sql);
			T3user user = T3user.dao.findFirst(sql);
			if (null == user) {
				res = new ResultEntity("0001");
				LOG.debug(res.getDesc() + account);
				renderJson(res);
				return;
			} else {
				// 获取参数找回密码邮件模板
				String msgTemplate = ParamUtils.getParam("10000002", "001");
				if (StringUtils.isBlank(msgTemplate)) {
					LOG.debug("找回密码邮件模板不存在");
					res = new ResultEntity("0002");
					renderJson(res);
					return;
				} else {
					msgTemplate = msgTemplate.replace("$1", user.getName_());
					String identiCode = EmailUtils.getRadSix();
					msgTemplate = msgTemplate.replace("$2", identiCode);
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MINUTE, 30);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String expiryTime = dateFormat.format(calendar.getTime());
					msgTemplate = msgTemplate.replace("$3", expiryTime);
					// 更新用户信息：验证码，验证码失效
					if (EmailUtils.sendTextMail(user.getEmail(), "Find your password of Thai agricultural remote sensing system!", msgTemplate)) {
						user.setIdentiCode_(identiCode);
						user.setExpiryTime(Timestamp.valueOf(expiryTime));
						user.update();
						LOG.debug("找回密码正常结束，已发送验证码到用户邮箱");
						res = new ResultEntity("0000");
						renderJson(res);
						// renderWithPath("/ui/thairice/password_getback.html");
					} else {
						res = new ResultEntity("0010");
						LOG.error(res.getDesc());
						renderJson(res);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("找回用户密码发生异常" + e);
			res = new ResultEntity("0002", "获取验证码失败");
			renderJson(res);
		}
	}

	/**
	 * 找回密码 zhuchaobin, 20180314
	 * 
	 * @throws Exception
	 */
	public void valiModiPwd() throws Exception {
		setAttr("path", "/ui/thairice/");
		// 处理结果
		ResultEntity res = null;
		// 1、获取用户账号
		String account = getPara("account", "").trim();
		String pwd = getPara("pwd", "").trim();
		String identiCode = getPara("identiCode");
		if (StringUtils.isBlank(account)) {
			LOG.debug("用户账号不能为空");
			res = new ResultEntity("0003");
			renderJson(res);
			return;
		}
		if (StringUtils.isBlank(pwd)) {
			LOG.debug("密码不能为空");
			res = new ResultEntity("0004");
			renderJson(res);
			return;
		}
		if (StringUtils.isBlank(identiCode)) {
			LOG.debug("验证码不能为空");
			res = new ResultEntity("0005");
			renderJson(res);
			return;
		}
		// 2、查询用户信息
		String sql = "select * from T3user t where t.account = '" + account + "'";
		LOG.debug("根据用户账户查找用户信息：" + sql);
		T3user user = T3user.dao.findFirst(sql);
		if (null == user) {
			LOG.debug("账户号" + account + "不存在");
			res = new ResultEntity("0001");
			renderJson(res);
			return;
		}
		// 3、验证验证码
		if (null != user) {
			if (identiCode.equals(user.getIdentiCode())) {
				LOG.debug("修改密码，验证码正确");
				// 4、验证新旧密码是否一样
				if (pwd.equals(user.getPwd())) {
					LOG.error("新旧密码不能一样，请重新输入新密码");
					res = new ResultEntity("0009");
					renderJson(res);
					return;
				}
				// 5、验证验证码有效期
				Calendar nowCal = Calendar.getInstance();
				// 验证码未失效
				if (nowCal.getTime().before((Timestamp) user.getExpiryTime())) {
					// 6、失效验证码及有效期
					user.setIdentiCode_("");
					user.setExpiryTime(null);
					// 7、修改密码
					// 密码加密
					// pass = HashKit.md5(pass);
					user.setPwd(pwd);
					user.update();
					LOG.debug("修改密码成功，请用新密码登录");
					res = new ResultEntity("0008");
					renderJson(res);
					return;
				} else {
					LOG.error("验证码已过有效期，请重新获取");
					res = new ResultEntity("0007");
					renderJson(res);
					return;
				}
			} else {
				LOG.error("修改密码，验证码错误");
				res = new ResultEntity("0006");
				renderJson(res);
				return;
			}
		}
	}

	/**
	 * 增加
	 */
	@Before(T3userValidator.class)
	public void save() {
		T3user t3user = getModel(T3user.class);
		// other set

		// t3user.save(); //guiid
		t3user.saveGenIntId(); // serial int id
		renderWithPath(pthv + "add.html");
	}

	/**
	 * 准备更新
	 */
	public void edit() {
		// T3user t3user = T3user.dao.findById(getPara()); //guuid
		T3user t3user = T3userService.service.SelectById(getParaToInt()); // serial int id
		setAttr("t3user", t3user);
		renderWithPath(pthv + "update.html");

	}

	/**
	 * 更新
	 */
	@Before(T3userValidator.class)
	public void update() {
		getModel(T3user.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		// T3user t3user = T3user.dao.findById(getPara()); //guuid
		T3user t3user = T3userService.service.SelectById(getParaToInt()); // serial int id
		setAttr("t3user", t3user);
		renderWithPath(pthv + "view.html");
	}

	/**
	 * 删除
	 */
	public void delete() {
		// T3userService.service.delete("t3user", getPara() == null ? ids : getPara());
		// //guuid
		T3userService.service.deleteById("t3user", getPara() == null ? ids : getPara()); // serial int id
		redirect(pthc);
	}

	public void setViewPath() {
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}

	public void login() {

		renderWithPath(pthv + "login.html");
	}

	public void doLogin() {

		String account = getPara("account", "");
		String pass = getPara("password", "");

		pass = HashKit.md5(pass);
		String sql = "select * from t3user where account=? limit 1";

		Record record = Db.use(ConstantInitMy.db_dataSource_main).findFirst(sql, account);

		// if(record==null) {
		//
		// }
		//
		// if(!record.getStr(T3user.column_pwd).equals(pass)) {
		//
		// }
		renderJson(record);
		// renderWithPath(pthv+"main.html");
	}

	public void reg() {
		T3user t3user = new T3user();
		t3user.setName_("22222");
		t3user.setEmail("fdsfsdfsdfs");
		setAttr("t3user", t3user);

		List<String> testList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			testList.add(i + "");
		}
		setAttr("testList", testList);
		renderWithPath(pthv + "reg.html");
	}

	/**
	 * 注册操作
	 */
	@Before(RegValidator.class)
	public void doReg() {
		try {
			T3user t3user = getModel(T3user.class);
			// other set
			String pass = t3user.getStr(T3user.column_pwd);
			if (StringUtils.isBlank(pass)) {
				LOG.error("密码为空.");
				keepPara("returnUrl");
				// renderWithPath(pthv+"reg.html");
			} else {
				pass = HashKit.md5(pass);
				t3user.set(T3user.column_pwd, pass);
				boolean rlt = t3user.use(ConstantInitMy.db_dataSource_main).saveGenIntId(); // serial int id
				if (rlt) {
					LOG.debug("用户注册成功，用户名：" + t3user.getName_());
					renderWithPath(pthv + "login.html");
				} else {
					LOG.error("用户注册失败，用户名：" + t3user.getName_());
					keepPara("returnUrl");
					// renderWithPath(pthv+"reg.html");
				}
			}
		} catch (Exception e) {
			LOG.error("用户注册发生异常：" + e);
			// renderWithPath(pthv+"reg.html");
		}
	}

	/**
	 * 显示还没激活页面
	 */
	public void notActivated() {
		render("not_activated.html");
	}

	/**
	 * 用户名是否已被注册
	 */
	public boolean isUserNameExists(String account) {
		account = account.toLowerCase().trim();
		return Db.queryInt("select id from t3user where account = ? limit 1", account) != null;
	}
}
