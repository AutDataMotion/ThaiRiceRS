package thairice.mvc.t2syslog;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import csuduc.platform.util.JsonUtils;

import java.util.List;

import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

import thairice.constant.ConstantInitMy;
import thairice.interceptor.AdminLoginInterceptor;

/**
 * XXX 管理 描述：
 * 
 * /jf/thairice/t2syslog /jf/thairice/t2syslog/save /jf/thairice/t2syslog/edit
 * /jf/thairice/t2syslog/update /jf/thairice/t2syslog/view
 * /jf/thairice/t2syslog/delete /thairice/t2syslog/add.html
 * 
 */
// @Controller(controllerKey = "/jf/thairice/t2syslog")
@Before(AdminLoginInterceptor.class)
public class T2syslogController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T2syslogController.class);

	public static final String pthc = "/jf/thairice/t2syslog/";
	public static final String pthv = "/adm2018/t2syslog/";
	public static final String pthvm = "log_management.html";

	/**
	 * 列表 首次显示
	 */
	public void index() {
		// 提取前100条
		List<T2syslog> list = T2syslogService.service.SelectPage(0, 10);
		setAttr("list", list);
		renderWithPath(pthv + pthvm);
	}

	// 查询
	public void search() {
		// 获取检索条件
		String strvalue = getPara("v");
		if (null == strvalue || strvalue.isEmpty()) {
			renderText("-1");
			return;
		}
		log.debug(strvalue);
		// 转化为Model
		ParamT2syslogSearch paramMdl = null;
		try {
			paramMdl = JsonUtils.deserialize(strvalue, ParamT2syslogSearch.class);
			if (null == paramMdl) {
				renderText("-1");
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			renderText("-1");// 错误
			return;
		}
		// 查询数据
		List<ResT2syslogSearch> listSysLog = T2syslogService.service.SearchPage(paramMdl);
		renderJson(listSysLog);
	}

	/**
	 * 保存
	 */
	@Before(T2syslogValidator.class)
	public void save() {
		T2syslog t2syslog = getModel(T2syslog.class);
		// other set

		// t2syslog.save(); //guiid
		t2syslog.saveGenIntId(); // serial int id
		renderWithPath(pthv + "add.html");
	}

	/**
	 * 准备更新
	 */
	public void edit() {
		// T2syslog t2syslog = T2syslog.dao.findById(getPara()); //guuid
		T2syslog t2syslog = T2syslogService.service.SelectById(getParaToInt()); // serial
																				// int
																				// id
		setAttr("t2syslog", t2syslog);
		renderWithPath(pthv + "update.html");

	}

	/**
	 * 更新
	 */
	@Before(T2syslogValidator.class)
	public void update() {
		getModel(T2syslog.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	@Clear
	public void view() {
		// T2syslog t2syslog = T2syslog.dao.findById(getPara()); //guuid
		T2syslog t2syslog = T2syslogService.service.SelectById(getParaToInt()); // serial
																				// int
																				// id
		setAttr("t2syslog", t2syslog);
		renderWithPath(pthv + "view.html");
	}

	/**
	 * 删除
	 */
	public void delete() {
		// T2syslogService.service.delete("t2syslog", getPara() == null ? ids :
		// getPara()); //guuid
		T2syslogService.service.deleteById("t2syslog", getPara() == null ? ids : getPara()); // serial
																								// int
																								// id
		redirect(pthc);
	}

	public void setViewPath() {
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}

}
