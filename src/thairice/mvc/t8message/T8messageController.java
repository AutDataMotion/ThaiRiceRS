package thairice.mvc.t8message;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import thairice.constant.ConstantInitMy;
import thairice.mvc.t3user.Result;

/**
 * XXX 管理 描述：
 * <p>
 * /jf/thairice/t8message /jf/thairice/t8message/save
 * /jf/thairice/t8message/edit /jf/thairice/t8message/update
 * /jf/thairice/t8message/view /jf/thairice/t8message/delete
 * /thairice/t8message/add.html
 */
public class T8messageController extends BaseController {

    private static Logger log = Logger.getLogger(T8messageController.class);

    public static final String pthc = "/jf/thairice/t8message/";
    public static final String pthv = "/adm2018/t8message/";
    private static final T8messageService service = Duang.duang(T8messageService.class);

    /**
     * 列表
     */
    public void index() {
	setAttr("message", "active");
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
	    renderJson(new Result(1, "Operation succeeded"));
	} else {
	    renderJson(new Result(0, "Operation failed"));
	}
    }

    /**
     * 清空全部消息
     */
    public void empty_message() {
	int row = Db.use(ConstantInitMy.db_dataSource_main).update(" UPDATE thairice.t8message SET show_flag=1");
	if (row > 0) {
	    renderJson(new Result(1, "Operation succeeded"));
	} else {
	    renderJson(new Result(1, "Operation failed"));
	}
    }

    public void setViewPath() {
	setAttr(ConstantRender.PATH_CTL_NAME, pthc);
	setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
    }

}
