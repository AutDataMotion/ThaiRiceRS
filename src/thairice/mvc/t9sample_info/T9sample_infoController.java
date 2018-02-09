package thairice.mvc.t9sample_info;

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
 * /jf/thairice/t9sample_info
 * /jf/thairice/t9sample_info/save
 * /jf/thairice/t9sample_info/edit
 * /jf/thairice/t9sample_info/update
 * /jf/thairice/t9sample_info/view
 * /jf/thairice/t9sample_info/delete
 * /thairice/t9sample_info/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t9sample_info")
public class T9sample_infoController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T9sample_infoController.class);

	public static final String pthc = "/jf/thairice/t9sample_info/";
	public static final String pthv = "/thairice/t9sample_info/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T9sample_info.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T9sample_infoValidator.class)
	public void save() {
		T9sample_info t9sample_info = getModel(T9sample_info.class);
		//other set 
		
		//t9sample_info.save();		//guiid
		t9sample_info.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T9sample_info t9sample_info = T9sample_info.dao.findById(getPara());	//guuid
		T9sample_info t9sample_info = T9sample_infoService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t9sample_info", t9sample_info);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T9sample_infoValidator.class)
	public void update() {
		getModel(T9sample_info.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T9sample_info t9sample_info = T9sample_info.dao.findById(getPara());	//guuid
		T9sample_info t9sample_info = T9sample_infoService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t9sample_info", t9sample_info);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T9sample_infoService.service.delete("t9sample_info", getPara() == null ? ids : getPara());	//guuid
		T9sample_infoService.service.deleteById("t9sample_info", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
