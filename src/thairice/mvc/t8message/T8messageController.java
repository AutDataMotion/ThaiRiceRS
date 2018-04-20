package thairice.mvc.t8message;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import thairice.constant.ConstantInitMy;
import thairice.interceptor.AdminLoginInterceptor;
import thairice.mvc.t3user.Result;

/**
 * XXX 管理 描述：
 * <p>
 * /jf/thairice/t8message /jf/thairice/t8message/save
 * /jf/thairice/t8message/edit /jf/thairice/t8message/update
 * /jf/thairice/t8message/view /jf/thairice/t8message/delete
 * /thairice/t8message/add.html
 */
@Before(AdminLoginInterceptor.class)
public class T8messageController extends BaseController {

	private static Logger log = Logger.getLogger(T8messageController.class);

	public static final String pthc = "/jf/thairice/t8message/";
	public static final String pthv = "/adm2018/t8message/";
	private static final T8messageService service = Duang.duang(T8messageService.class);

	/**
	 * 列表
	 */
	public void index() {
		renderWithPath(pthv + "data_message.html");
	}

	public void jsonList() {
		int pages = getParaToInt("start", 0) == 0 ? 1 : getParaToInt("start") / getParaToInt("pageSize", 3) + 1;
		Page<T8message> page = service.selectByPage(pages, getParaToInt("pageSize", 3), getPara("type_"),
				getPara("orderColumn", "id"), getPara("orderDir", "desc"));
		Map record = new HashMap();
		record.put("sEcho", false);
		record.put("aaData", page.getList());
		record.put("recordsFiltered", page.getTotalRow());
		record.put("total", page.getTotalRow());
		renderJson(record);
	}

	/**
	 * 删除多个
	 */
	public void delete() {
		int rows = service.deletes(getPara("ids"));
		if (rows > 0) {
			renderJson(new Result(1, "successfully deleted"));
		} else {
			renderJson(new Result(0, "failed to delete"));
		}
	}

	/**
	 * 清空全部消息
	 */
	public void empty_message() {
		int row = Db.use(ConstantInitMy.db_dataSource_main).update("DELETE FROM t8message");
		if (row > 0) {
			Db.use(ConstantInitMy.db_dataSource_main).update("DELETE FROM r4message_send");
			renderJson(new Result(1, "Cleared successfully"));
		} else {
			renderJson(new Result(1, "operation failed"));
		}
	}

	public void setViewPath() {
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}

}
