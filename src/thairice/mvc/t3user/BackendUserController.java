package thairice.mvc.t3user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Duang;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.platform.constant.ConstantRender;
import com.platform.dto.SplitPage;
import com.platform.mvc.base.BaseController;

import thairice.constant.ConstantInitMy;
import thairice.mvc.r4message_send.R4message_send;
import thairice.mvc.t8message.T8message;

/**
 * 后台用户管理
 */
public class BackendUserController extends BaseController {
	public static final String pthc = "/jf/thairice/admin/user/";
	public static final String pthv = "/adm2018/backend_user/";
	private static final T3userService service = Duang.duang(T3userService.class);
	private static final AuthCodeService codeService = Duang.duang(AuthCodeService.class);
	protected SplitPage splitPage; // 分页封装

	@Clear
	public void login() {
		setAttr("returnUrl", "");
		keepPara("returnUrl");
		renderWithPath(pthv + "backend_login.html");

	}

	/**
	 * 后台登陆
	 */
	@Clear
	@Before(T3userValidator.class)
	public void Login() {
		boolean authCode = authCode();
		if (!authCode) {
			renderJson(new Result(0, "Verification code is not correct"));
			return;
		}
		String account = getPara("account").trim();
		String pass = getPara("pass").trim();
		T3user user = service.login(account);
		if (user == null) {
			renderJson(new Result(0, "Account does not exist"));
			return;
		}
		if (pass.equals(user.getPwd())) {
			setSessionAttr("admin", user);
			renderJson(new Result(1, "Login successful"));
		} else {
			renderJson(new Result(0, "Invalid password. Please try again"));
		}
	}

	// 管理员或操作员找回密码界面
	@Clear
	public void find_pass() {
		renderWithPath(pthv + "password_getback.html");
	}

	// 后台首页界面
	public void main() {
		T3user u = getSessionAttr("admin");
		if (u.getType_().equals("03")) {
		        setAttr("data", "active");
			renderWithPath(pthv + "data.html");
		} else {
		        setAttr("user_management", "active");
			renderWithPath(pthv + "user_management.html");
		}
	}

	// 用户管理界面
	public void user_management() {
	        setAttr("user_management", "active");
		renderWithPath(pthv + "user_management.html");
	}

	// 管理员信息界面
	public void data_selfinfo() {
	        setAttr("profile", "active");
		T3user u = getSessionAttr("admin");
		setAttr("admin", service.SelectById(u.getBigInteger("id")));
		renderWithPath(pthv + "data_selfinfo.html");
	}

	/**
	 * 操作员管理
	 */
	public void operator_list() {
		int pages = getParaToInt("start", 0) == 0 ? 1 : getParaToInt("start") / getParaToInt("pageSize", 3) + 1;
		Page<T3user> page = service.selectByPage(pages, getParaToInt("pageSize", 10), getPara("search"),
				getPara("orderColumn", "id"), getPara("orderDir", "desc"));
		Map record = new HashMap();
		record.put("sEcho", false);
		record.put("aaData", page.getList());
		record.put("recordsFiltered", page.getTotalRow());
		record.put("total", page.getTotalRow());
		renderJson(record);
	}

	/**
	 * 用户管理
	 */
	public void user_list() {
		int pages = getParaToInt("start", 0) == 0 ? 1 : getParaToInt("start") / getParaToInt("pageSize", 3) + 1;
		Page<T3user> page = service.selectUsersByPage(pages, getParaToInt("pageSize", 10), getParaToInt("id", 0),
				getPara("username"), getPara("min"), getPara("max"), "", getPara("audit"),getPara("type"),getPara("orderColumn", "id"),
				getPara("orderDir", "desc"));
		Map record = new HashMap();
		record.put("sEcho", false);
		record.put("aaData", page.getList());
		record.put("recordsFiltered", page.getTotalRow());
		record.put("total", page.getTotalRow());
		renderJson(record);
	}

	/**
	 * 导出用户
	 */
	public void export() {
		// 全部导出
		if (getPara("select").equals("all")) {
			List<T3user> rates = service.selectUsers();
			ExportService.service.export("Export user data", "thairice.t3user", getResponse(), getRequest(), rates);
		} else {
			List<T3user> rates = service.selectUsersByChoose(getPara("ids"));
			ExportService.service.export("Export user data", "thairice.t3user", getResponse(), getRequest(), rates);
		}
		renderNull();
	}

	/**
	 * 导出操作员
	 */
	public void export_operator() {
		// 全部导出
		if (getPara("select").equals("all")) {
			List<T3user> rates = service.selectOperators();
			ExportService.service.export("Export operator data", "thairice.t3user", getResponse(), getRequest(), rates);
		} else {
			List<T3user> rates = service.selectOperatorsByChoose(getPara("ids"));
			ExportService.service.export("Export operator data", "thairice.t3user", getResponse(), getRequest(), rates);
		}
		renderNull();
	}

	/**
	 * 退出登录
	 */
	public void exit() {
		removeSessionAttr("admin");
		redirect("/jf/thairice/admin/user/login");
	}

	/**
	 * 新增或修改
	 */
	public void save() {
		T3user user = getModel(T3user.class);
		try {
			if (user.getId() == null) {
				user.setCreate_time(new Timestamp(new Date().getTime()));
				// 初始密码为123456,后期系统操作员自行修改
				user.setPwd(HashKit.md5("123456"));
				user.setType_("02");
				user.setStatus_("02");
				user.setZip_encode("00000");
				user.setPD_TpCd("01,02,03,04,");
				user.set("Prdt_EfDt","2000-01-01 00:00:00");
				user.set("PD_ExDat","2199-01-01 00:00:00");
				user.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
			} else {
				user.update();
			}
			renderJson(new Result(1, "Operation succeeded"));
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new Result(0, "Operation failed"));
		}

	}

	/**
	 * 发送消息
	 */
	public void send_message() {
		T8message t8message = getModel(T8message.class);
		T3user u = getSessionAttr("admin");
		t8message.set("send_userid", u.getId());
		t8message.set("flag", getParaToInt("t8message.flag"));
		t8message.setAdd_time(new Timestamp(new Date().getTime()));
		boolean success = t8message.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
		if (success) {
			// 发给0指定用户,2群发,3审核不通过的消息
			if (getParaToInt("t8message.flag") == 1 || getParaToInt("t8message.flag") == 3) {
				R4message_send send = new R4message_send();
				send.set("message_id", t8message.getId());
				send.set("receive_userid", getPara("receive_userid"));
				send.setStatus_("01");
				send.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
				// 将普通用户状态标识为‘审核不通过’
				if (getParaToInt("t8message.flag") == 3) {
					T3user user = new T3user();
					user.set("id", getPara("receive_userid"));
					user.setStatus_("03");
					user.use(ConstantInitMy.db_dataSource_main).update();
					renderJson(new Result(2, "Sent successful"));
					return;
				}
			} else {
				List<T3user> list = Duang.duang(T3userService.class).selectUsers();
				for (T3user user : list) {
					R4message_send send_all = new R4message_send();
					send_all.set("message_id", t8message.getId());
					send_all.set("receive_userid", user.getId());
					send_all.setStatus_("01");
					send_all.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
				}
			}
			renderJson(new Result(1, "Sent successful"));
		} else {
			renderJson(new Result(0, "Failed to send"));
		}
	}

	/**
	 * 审核或修改用户信息
	 */
	public void audit() {
		T3user user = getModel(T3user.class);
		user.set("Prdt_EfDt", getPara("Prdt_EfDt"));
		user.set("PD_ExDat", getPara("PD_ExDat"));
		String cd[] = getParas("PD_TpCd");
		String l = "";
		for (String str : cd) {
			l += str + ",";
		}
		user.set("PD_TpCd", l);
		user.set("area", getPara("province")+" "+getPara("city")+" "+getPara("area"));
		user.use(ConstantInitMy.db_dataSource_main).update();
		renderJson(new Result(1, "Operation succeeded"));
	}

	
	/**
	 * 用户选择的服务地区
	 */
	public void region() {
	      List<Record>all=new ArrayList<>();
          List<Record>list=Db.use(ConstantInitMy.db_dataSource_main).find("SELECT r.name,r.id as regionId,m.id FROM t13region r LEFT JOIN t14my_region m ON m.regionId = r.id WHERE r.parentId=0 and m.userId=? GROUP BY r.name",getParaToInt("userId"));
          if(!list.isEmpty()){
        	 for (Record record : list) {
        		 List<Record>list_c=Db.use(ConstantInitMy.db_dataSource_main).find("SELECT r.name,r.id as regionId,m.id FROM t13region r LEFT JOIN t14my_region m ON m.regionId = r.id LEFT JOIN t13region tr ON tr.id = r.parentId WHERE r.parentId =? and userId=?",record.getInt("regionId"),getParaToInt("userId"));
        		 List<Record>r2_list=new ArrayList<>();
        		 Record r1=new Record();
        		 r1.set("v", record.getInt("id"));
        		 r1.set("n", record.getStr("name"));
        		 for (Record record2 : list_c) {
        			 Record r2=new Record();
        			 r2.set("v", record2.getInt("id"));
        			 r2.set("n", record2.getStr("name"));
        			 List<Record>list_d=Db.use(ConstantInitMy.db_dataSource_main).find("SELECT r.name,m.id FROM t13region r LEFT JOIN t14my_region m ON m.regionId = r.id LEFT JOIN t13region tr ON tr.id = r.parentId WHERE r.parentId =? and userId=?",record2.getInt("regionId"),getParaToInt("userId"));
        			 List<Record>r3_list=new ArrayList<>();
        			 for (Record record3 : list_d) {
        				 Record r3=new Record();
	        			 r3.set("v", record3.getInt("id"));
	        			 r3.set("n", record3.getStr("name"));
	        			 r3_list.add(r3);
	        			 r2.set("s", r3_list);
					}
        			 r2_list.add(r2);
        			 r1.set("s", r2_list);
				}
        		 all.add(r1);
			}
          }
	      renderJson(all);
	}
	//删除服务地区
	public void del_address(){
		String ids=getPara("ids");
		Db.update(" DELETE FROM thairice.t14my_region WHERE id in (" + ids + ")");
		renderJson(new Result(1, "successfully deleted"));
	}
	//添加服务地区
	public void add_address(){
			Record record=new Record();
			record.set("userId", getParaToInt("userId"));
			record.set("regionId", getParaToInt("province"));
			Db.use(ConstantInitMy.db_dataSource_main).save("t14my_region", record);
			Record record2=new Record();
			record2.set("userId", getParaToInt("userId"));
			record2.set("regionId",getParaToInt("city"));
			Db.use(ConstantInitMy.db_dataSource_main).save("t14my_region", record2);
			Record record3=new Record();
			record3.set("userId", getParaToInt("userId"));
			record3.set("regionId",getParaToInt("area"));
			Db.use(ConstantInitMy.db_dataSource_main).save("t14my_region", record3);
		renderJson(new Result(1, "successfully"));
	}

	
	
	
	
	/**
	 * 删除单个或多个
	 */
	public void delete() {
		int rows = service.deletes(getPara("ids"));
		if (rows > 0) {
			renderJson(new Result(1, "Operation succeeded"));
		} else {
			renderJson(new Result(0, "Operation failed"));
		}
	}

	/**
	 * 修改信息
	 */
	public void edit_info() {
		T3user t3user = getModel(T3user.class);
		boolean rlt = t3user.use(ConstantInitMy.db_dataSource_main).update();
		if (rlt) {
			renderJson(new Result(1, "Modified Successfully"));
		} else {
			renderJson(new Result(0, "Fail to edit"));
		}
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

	@Override
	protected void setViewPath() {
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}

}
