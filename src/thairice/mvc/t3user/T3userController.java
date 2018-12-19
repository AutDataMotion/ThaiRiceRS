package thairice.mvc.t3user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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
import com.platform.constant.ConstantWebContext;
import com.platform.interceptor.ParamPkgInterceptor;
import com.platform.mvc.base.BaseController;

import csuduc.platform.util.FileUtils;
import thairice.constant.ConstantInitMy;
import thairice.interceptor.UserLoginInterceptor;
import thairice.mvc.r4message_send.R4message_send;
import thairice.mvc.t10pdt_report.T10pdt_report;
import thairice.mvc.t15_news_cnt.t15_news_cnt;
import thairice.mvc.t16_pdt_sample.t16_pdt_sample;
import thairice.mvc.t2syslog.EnumT2sysLog;
import thairice.mvc.t2syslog.T2syslogService;
import thairice.mvc.t8message.T8message;
import thairice.mvc.t8message.T8messageService;
import thairice.utils.ParamUtils;

/**
 * 前台用户中心
 */

@Before(UserLoginInterceptor.class)
public class T3userController extends BaseController {

    private static Logger LOG = Logger.getLogger(T3userController.class);

    public static final String pthc = "/jf/thairice/t3user/";
    public static final String pthv = "/f/t3user/";

    private static final T3userService srv = Duang.duang(T3userService.class);
    private static final T8messageService message_service = Duang.duang(T8messageService.class);
    private static final AuthCodeService codeService = Duang.duang(AuthCodeService.class);

    /**
     * 列表
     */
    public void index() {
	 T3user user;
	if(getPara("account", "user").equals("admin")) {
	    user = getSessionAttr("admin");
	    T3user t3=T3user.dao.findById(user.getBigInteger("id"));
	    setAttr("name",t3.getPD_TpCd());
	}else {
	    user = getSessionAttr("user");
	    T3user t3=T3user.dao.findById(user.getBigInteger("id"));
	    setAttr("name",t3.getPD_TpCd());
	}
	setAttr("account", getPara("account", "user"));
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
        // 新闻信息展示
		Page page  = t15_news_cnt.dao.paginate(getParaToInt(0, 1), 10, "select t.title, t.content, date_format(t.editTime ,'%Y/%m/%d') as editTime from t15_news_cnt t order by t.rank asc ", "");
		// 产品信息展示
		Page pageT16  = t16_pdt_sample.dao.paginate(getParaToInt(0, 1), 10, "select * from t16_pdt_sample t order by t.id asc ", "");
		// 联系我们展示
		String address = ParamUtils.getParam("10000005", "001");
		String telphone = ParamUtils.getParam("10000005", "002");
		String mobile = ParamUtils.getParam("10000005", "003");
		String email = ParamUtils.getParam("10000005", "004");
		//		log.info("查询报表返回结果" + JSON.toJSONString(page));
		setAttr("blogPage",page);
		setAttr("pageT16",pageT16);
		setAttr("address",address);
		setAttr("telphone",telphone);
		setAttr("mobile",mobile);
		setAttr("email",email);
        renderWithPath(pthv + "login.html");
    }

    //消息个数实时更新
    public void refresh() {
        T3user user = getSessionAttr("user");
        Record record = new Record();
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
//*****************************************************************	
/*	thairice.utils.FileUtils  obj = new thairice.utils.FileUtils();
	obj.prepareTestDataDir("F:\\MODIS\\LST", "d:\\");
	LOG.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");*/
//*****************************************************************

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
    	if(res.getCode()==1) {
            T3user user = getSessionAttr("user");
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), account, "Login", res.getDesc());
    	 }
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
        t3user.set("area", getPara("province") + " " + getPara("city") + " " + getPara("area"));
        t3user.set("PD_TpCd", l);
        boolean rlt = t3user.use(ConstantInitMy.db_dataSource_main).saveGenIntId();
        if (rlt) {
            //添加地区
            JSONArray array = new JSONArray(getPara("list"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                
                if(object.getInt("province")==1) {
                    Db.use(ConstantInitMy.db_dataSource_main).update("delete  from t14my_region where userId="+t3user.getId());
                    List<Record>province=Db.use(ConstantInitMy.db_dataSource_main).find("select * from t13region where parentId=0");
                    for(Record r:province) {
                	if(!r.get("name").equals("ALL")) {
                	    Record record = new Record();
                            record.set("userId", t3user.getId());
                            record.set("provinceId", r.getInt("Id"));
                            record.set("cityId", 0);
                            record.set("areaId", 0);
                            Db.use(ConstantInitMy.db_dataSource_main).save("t14my_region", record); 
                	}
                    }
                }else {
                    Record record = new Record();
                    record.set("userId", t3user.getId());
                    record.set("provinceId", object.getInt("province"));
                    String city = object.get("city") + "";
                    String area = object.get("area") + "";
                    if (StrKit.isBlank(city)) {
                        record.set("cityId", 0);
                    } else {
                        record.set("cityId", object.getInt("city"));
                    }
                    if (StrKit.isBlank(area)) {
                        record.set("areaId", 0);
                    } else {
                        record.set("areaId", object.getInt("area"));
                    }
                    Db.use(ConstantInitMy.db_dataSource_main).save("t14my_region", record);
                }
            }
            
            //邮箱验证，激活链接
            String authCode = codeService.createAuthCode(new BigInteger(t3user.get("id").toString()), 0, 1800);
            if (StrKit.notBlank(authCode)) {
        	String ip = getAttr(ConstantWebContext.request_cxt);
                String url = ip+"/jf/thairice/t3user/activate?authCode=" + authCode;
                String content = "Hello!\r\n<br>"
                			+ "We sent you this email because you're "
                			+ "<br>signing up for a new Thai ARSM Platform account with your email address,<br>"
                			+t3user.getEmail().toString()+
                			"<br><br>"+"Click the following link to complete registration: <br><br>" 
                			+ "<a href="+ url + ">" + url + "</a>"
                			+"<br><br>This link will expire in 30 minutes. " 
                	                +"<br><br>If this wasn't you, someone entered your email address by mistake so you can disregard this email."
                			+"<br><br><br>Thanks";
                boolean success = Mail.sendEmail("Your Thai ARSM Platform Account activation", content, t3user.getEmail().toString(),
                        Mail.MODE_HTML);
                if (success) {
                    T2syslogService.addLog(EnumT2sysLog.INFO, new BigInteger(t3user.get("id").toString()), t3user.getAccount(), "Register", "Registration is successful");
                    renderJson(new Result(1,"Registration is successful, activation email has been sent, please check and activate the account"));
                } else {
                    T2syslogService.addLog(EnumT2sysLog.INFO, new BigInteger(t3user.get("id").toString()), t3user.getAccount(),"Register", "Registration was successful but the mailbox failed to send");
                    renderJson(new Result(1,"Registration was successful but the mailbox failed to send"));
                }
            }
        } else {
            T2syslogService.addLog(EnumT2sysLog.INFO, BigInteger.ONE, t3user.getAccount(),"Register", "Registration failed");
            renderJson(new Result(0, "Registration failed"));
        }
    }

    //所有的地址列表
    @Clear
    public void region_list() {
        List<Record> all = new ArrayList<>();
        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=0 GROUP BY name");
        if (!list.isEmpty()) {
            for (Record record : list) {
                List<Record> list_c = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", record.getInt("id"));
                List<Record> r2_list = new ArrayList<>();
                Record r1 = new Record();
                r1.set("v", record.getInt("id"));
                r1.set("n", record.getStr("name"));
                for (Record record2 : list_c) {
                    Record r2 = new Record();
                    r2.set("v", record2.getInt("id"));
                    r2.set("n", record2.getStr("name"));
                    List<Record> list_d = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", record2.getInt("id"));
                    List<Record> r3_list = new ArrayList<>();
                    for (Record record3 : list_d) {
                        Record r3 = new Record();
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

    @Clear
    public void province() {
        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=0 GROUP BY name");
        renderJson(list);
    }

    @Clear
    public void cityArea() {
        String id = getPara("id");
        if (StrKit.isBlank(id)) {
            List<Record> list = new ArrayList<>();
            renderJson(list);
            return;
        }
        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=? GROUP BY name", id);
        renderJson(list);
    }

    @Clear
    public void userCityArea() {
        String id = getPara("id");
        String type = getPara("type");
        if (StrKit.isBlank(id)) {
            List<Record> list = new ArrayList<>();
            renderJson(list);
            return;
        }
        T3user user;
        if(getPara("account", "user").equals("user")) {
            user = getSessionAttr("user");
        }else {
            user = getSessionAttr("admin");
        }
        BigInteger userId = user.getBigInteger("id");
        if (type.equals("p")) {
            List<Record> list1 = Db.use(ConstantInitMy.db_dataSource_main).find("select * from t14my_region where userId=?", userId);
            String p = "";
            if (list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    Record record = list1.get(i);
                    p = p + "'" + record.getInt("provinceId") + "',";
                }
                List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where Id in (" + p.substring(0, p.length() - 1) + ") GROUP BY name");
                renderJson(list);
                return;
            } else {
                List<Record> list = new ArrayList<>();
                renderJson(list);
                return;
            }
        }
        if (type.equals("c")) {
            List<Record> list1 = Db.use(ConstantInitMy.db_dataSource_main).find("select * from t14my_region where userId=?", userId);
            String c = "";
            for (int i = 0; i < list1.size(); i++) {
                Record record = list1.get(i);
                if (record.getInt("cityId") == 0) {
                    int provinceId = record.getInt("provinceId");
                    List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", provinceId);
                    for (int j = 0; j < list.size(); j++) {
                        Record record1 = list.get(j);
                        c = c + "'" + record1.getInt("id") + "',";
                    }
                } else {
                    c = c + "'" + record.getInt("cityId") + "',";
                }
            }
            List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where Id in (" + c.substring(0, c.length() - 1) + ") and parentId=" + id + " GROUP BY name");
            renderJson(list);
            return;
        }
        if (type.equals("a")) {
            List<Record> list1 = Db.use(ConstantInitMy.db_dataSource_main).find("select * from t14my_region where userId=?", userId);
            String a = "";
            for (int i = 0; i < list1.size(); i++) {
                Record record = list1.get(i);
                if (record.getInt("areaId") == 0) {
                    if (record.getInt("cityId") == 0) {
                        int provinceId = record.getInt("provinceId");
                        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", provinceId);
                        for (int j = 0; j < list.size(); j++) {
                            Record record3 = list.get(j);
                            int cityId = record3.getInt("id");
                            List<Record> list2 = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", cityId);
                            for (int k = 0; k < list2.size(); k++) {
                                Record record1 = list2.get(k);
                                a = a + "'" + record1.getInt("id") + "',";
                            }
                        }
                    } else {
                        int cityId = record.getInt("cityId");
                        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", cityId);
                        for (int j = 0; j < list.size(); j++) {
                            Record record1 = list.get(j);
                            a = a + "'" + record1.getInt("id") + "',";
                        }
                    }
                } else {
                    a = a + "'" + record.getInt("areaId") + "',";
                }
            }
            List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where Id in (" + a.substring(0, a.length() - 1) + ") and parentId=" + id + " GROUP BY name");
            renderJson(list);
            return;
        }
        List<Record> list = new ArrayList<>();
        renderJson(list);
        return;
    }


    @Clear
    public void adminUserCityArea() {
        String id = getPara("id");
        String type = getPara("type");
        if (StrKit.isBlank(id)) {
            List<Record> list = new ArrayList<>();
            renderJson(list);
            return;
        }
        int userId = getParaToInt("userId");
        if (type.equals("p")) {
            List<Record> list1 = Db.use(ConstantInitMy.db_dataSource_main).find("select * from t14my_region where userId=?", userId);
            String p = "";
            if (list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    Record record = list1.get(i);
                    p = p + "'" + record.getInt("provinceId") + "',";
                }
                List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where Id in (" + p.substring(0, p.length() - 1) + ") GROUP BY name");
                renderJson(list);
                return;
            } else {
                List<Record> list = new ArrayList<>();
                renderJson(list);
                return;
            }
        }
        if (type.equals("c")) {
            List<Record> list1 = Db.use(ConstantInitMy.db_dataSource_main).find("select * from t14my_region where userId=?", userId);
            String c = "";
            for (int i = 0; i < list1.size(); i++) {
                Record record = list1.get(i);
                if (record.getInt("cityId") == 0) {
                    int provinceId = record.getInt("provinceId");
                    List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", provinceId);
                    for (int j = 0; j < list.size(); j++) {
                        Record record1 = list.get(j);
                        c = c + "'" + record1.getInt("id") + "',";
                    }
                } else {
                    c = c + "'" + record.getInt("cityId") + "',";
                }
            }
            List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where Id in (" + c.substring(0, c.length() - 1) + ") and parentId=" + id + " GROUP BY name");
            renderJson(list);
            return;
        }
        if (type.equals("a")) {
            List<Record> list1 = Db.use(ConstantInitMy.db_dataSource_main).find("select * from t14my_region where userId=?", userId);
            String a = "";
            for (int i = 0; i < list1.size(); i++) {
                Record record = list1.get(i);
                if (record.getInt("areaId") == 0) {
                    if (record.getInt("cityId") == 0) {
                        int provinceId = record.getInt("provinceId");
                        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", provinceId);
                        for (int j = 0; j < list.size(); j++) {
                            Record record3 = list.get(j);
                            int cityId = record3.getInt("id");
                            List<Record> list2 = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", cityId);
                            for (int k = 0; k < list2.size(); k++) {
                                Record record1 = list2.get(k);
                                a = a + "'" + record1.getInt("id") + "',";
                            }
                        }
                    } else {
                        int cityId = record.getInt("cityId");
                        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where parentId=?", cityId);
                        for (int j = 0; j < list.size(); j++) {
                            Record record1 = list.get(j);
                            a = a + "'" + record1.getInt("id") + "',";
                        }
                    }
                } else {
                    a = a + "'" + record.getInt("areaId") + "',";
                }
            }
            List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("select id,name from t13region where Id in (" + a.substring(0, a.length() - 1) + ") and parentId=" + id + " GROUP BY name");
            renderJson(list);
            return;
        }
        List<Record> list = new ArrayList<>();
        renderJson(list);
        return;
    }

    /**
     * 用户选择的地区
     */
    public void region() {
        List<Record> all = new ArrayList<>();
        List<Record> list = Db.use(ConstantInitMy.db_dataSource_main).find("SELECT r.name,r.id as regionId,m.id FROM t13region r LEFT JOIN t14my_region m ON m.regionId = r.id WHERE r.parentId=0 and m.userId=? GROUP BY r.name", getParaToInt("userId"));
        if (!list.isEmpty()) {
            for (Record record : list) {
                List<Record> list_c = Db.use(ConstantInitMy.db_dataSource_main).find("SELECT r.name,r.id as regionId,m.id FROM t13region r LEFT JOIN t14my_region m ON m.regionId = r.id LEFT JOIN t13region tr ON tr.id = r.parentId WHERE r.parentId =? and userId=?", record.getInt("regionId"), getParaToInt("userId"));
                List<Record> r2_list = new ArrayList<>();
                Record r1 = new Record();
                r1.set("v", record.getInt("id"));
                r1.set("n", record.getStr("name"));
                for (Record record2 : list_c) {
                    Record r2 = new Record();
                    r2.set("v", record2.getInt("id"));
                    r2.set("n", record2.getStr("name"));
                    List<Record> list_d = Db.use(ConstantInitMy.db_dataSource_main).find("SELECT r.name,m.id FROM t13region r LEFT JOIN t14my_region m ON m.regionId = r.id LEFT JOIN t13region tr ON tr.id = r.parentId WHERE r.parentId =? and userId=?", record2.getInt("regionId"), getParaToInt("userId"));
                    List<Record> r3_list = new ArrayList<>();
                    for (Record record3 : list_d) {
                        Record r3 = new Record();
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
     * 验证邮箱是否可用(用于邮箱注册，不检查过期用户和审核未通过用户)
     */
    @Clear
    public void valiMailBox2() {
        String mailBox = getPara("mailBox");
        boolean bool = T3userService.service.valiMailBox2(mailBox);
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
        String[] ad = user.getStr("area").split(" ");
//        Page page = T10pdt_report.dao.paginate(getParaToInt(0, 1), 10, "select *", "from T10pdt_report order by id asc");
        Page page  = T10pdt_report.dao.paginate(getParaToInt(0, 1), 10, "select t.id, date_format(t.add_time ,'%Y-%m-%d %H:%i:%S') as add_time, date_format(t.collect_time ,'%Y-%m-%d') as collect_time, t.zone_code,(case pdt_type \r\n" + 
				"when '01' then 'Area monitoring' \r\n" + 
				"when '02' then 'Growth monitoring'\r\n" + 
				"when '03' then 'Estimated production'\r\n" + 
				"when '04' then 'Drought monitoring'\r\n" + 
				"else ''\r\n" + 
				"end) as pdt_type,"
				+ "(case suffix \r\n" + 
						"when '01' then 'WORD' \r\n" + 
						"when '02' then 'PDF'\r\n" + 
						"else ''\r\n" + 
						"end) as suffix", "from T10pdt_report t where userid = "+String.valueOf(user.getBigInteger("id"))+" order by t.add_time desc");
        setAttr("blogPage", page);
        setAttr("province", ad[0]);
        T3user info = srv.SelectById(user.getBigInteger("id"));
        if (TimeUtil.isLaterThanNow(info.getPD_ExDat().toString())) {
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
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Empty_message", "Operation succeeded");
            renderJson(new Result(1, "Operation succeeded"));
        } else {
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Empty_message", "Operation failed");
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
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Delete_message", "Operation succeeded");
            renderJson(new Result(1, "Operation succeeded"));
        } else {
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Delete_message", "Operation failed");
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
        T3user user = getSessionAttr("user");
        T3user t3user = getModel(T3user.class);
        boolean rlt = t3user.use(ConstantInitMy.db_dataSource_main).update();
        if (rlt) {
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Modify personal information", "Operation succeeded");
            renderJson(new Result(1, "Operation succeeded"));
        } else {
            T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Modify personal information", "Operation failed");
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
        String authCode = codeService.createAuthCode(t3user.getBigInteger("id"), 0, 1800);
        boolean success = Mail.sendEmail(getParaToInt("type", 0) == 0 ? "Your Thai ARSM Platform Verification Code" : "change Password",
                "Hi,\nAre you trying to reset/recover password?\nIf so, use this code to finish reseting/recovering password.\n\n" + authCode +
                        "\n\nThis code will expire in 30 minutes. \r\n" +
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
        T3user user = getSessionAttr("user");
        T2syslogService.addLog(EnumT2sysLog.INFO, BigInteger.ONE, "User", "Reset_password", result.getDesc());
        renderJson(result);
    }

    /**
     * 修改密码
     */
    @Before(T3userValidator.class)
    public void edit_pass() {
        Result result = codeService.reset_pass(getPara("code"), HashKit.md5(getPara("pwd")));
        T3user user = getSessionAttr("user");
        T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Edit_password", result.getDesc());
        renderJson(result);
    }

    /**
     * 退出登录
     */
    public void exit() {
        T3user user = getSessionAttr("user");
        T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Logout", "Logout succeeded");
        removeSessionAttr("user");
        redirect("/jf/thairice/t3user/login#page4");
    }


}
