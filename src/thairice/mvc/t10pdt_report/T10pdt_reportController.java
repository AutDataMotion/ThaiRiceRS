package thairice.mvc.t10pdt_report;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

import thairice.constant.ConstantInitMy;
import thairice.entity.ResultEntity;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t10pdt_report
 * /jf/thairice/t10pdt_report/save
 * /jf/thairice/t10pdt_report/edit
 * /jf/thairice/t10pdt_report/update
 * /jf/thairice/t10pdt_report/view
 * /jf/thairice/t10pdt_report/delete
 * /thairice/t10pdt_report/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t10pdt_report")
public class T10pdt_reportController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(T10pdt_reportController.class);

	public static final String pthc = "/jf/thairice/t10pdt_report/";
	public static final String pthv = "/thairice/t10pdt_report/";

	/**
	 * 列表
	 * modified by zhuchaobin, 2018-03-10
	 */
	public void index() {
//		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T10pdt_report.sqlId_splitPage_from);
		setAttr("path", "/ui/thairice/");
		// 处理结果
		ResultEntity res = null;
		// 1、获取用户账号
		String account = getPara("account", "").trim();
		account = "333";
		if (StringUtils.isBlank(account)) {
			LOG.debug("用户账号不能为空");
			res = new ResultEntity("0003");
			renderJson(res);
			return;
		}
		Page page  = T10pdt_report.dao.paginate(getParaToInt(0, 1), 10, "select *", "from T10pdt_report t where t.userid = '" + account + "' order by id asc");
		setAttr("blogPage",page );
//		renderWithPath("/ui/thairice/test.html");
		renderWithPath("/thairice/self_center.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T10pdt_reportValidator.class)
	public void save() {
		T10pdt_report t10pdt_report = getModel(T10pdt_report.class);
		//other set 		
		//t10pdt_report.save();		//guiid
		t10pdt_report.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T10pdt_report t10pdt_report = T10pdt_report.dao.findById(getPara());	//guuid
		T10pdt_report t10pdt_report = T10pdt_reportService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t10pdt_report", t10pdt_report);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T10pdt_reportValidator.class)
	public void update() {
		getModel(T10pdt_report.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T10pdt_report t10pdt_report = T10pdt_report.dao.findById(getPara());	//guuid
		T10pdt_report t10pdt_report = T10pdt_reportService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t10pdt_report", t10pdt_report);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 * modified by zhuchaobin, 2018-03-10
	 */
	public void delete() {
		T10pdt_report.dao.deleteById(getPara() == null ? ids : getPara());
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
