package thairice.mvc.t2syslog;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import org.apache.log4j.Logger;
import com.jfinal.aop.Before;

import thairice.constant.ConstantInitMy;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t2syslog
 * /jf/thairice/t2syslog/save
 * /jf/thairice/t2syslog/edit
 * /jf/thairice/t2syslog/update
 * /jf/thairice/t2syslog/view
 * /jf/thairice/t2syslog/delete
 * /thairice/t2syslog/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t2syslog")
public class T2syslogController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T2syslogController.class);

	public static final String pthc = "/jf/thairice/t2syslog/";
	public static final String pthv = "/thairice/t2syslog/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T2syslog.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T2syslogValidator.class)
	public void save() {
		T2syslog t2syslog = getModel(T2syslog.class);
		//other set 
		
		//t2syslog.save();		//guiid
		t2syslog.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T2syslog t2syslog = T2syslog.dao.findById(getPara());	//guuid
		T2syslog t2syslog = T2syslogService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t2syslog", t2syslog);
		renderWithPath(pthv+"update.html");

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
	public void view() {
		//T2syslog t2syslog = T2syslog.dao.findById(getPara());	//guuid
		T2syslog t2syslog = T2syslogService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t2syslog", t2syslog);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T2syslogService.service.delete("t2syslog", getPara() == null ? ids : getPara());	//guuid
		T2syslogService.service.deleteById("t2syslog", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
