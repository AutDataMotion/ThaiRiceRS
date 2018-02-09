package thairice.mvc.t7pdt_data;

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
 * /jf/thairice/t7pdt_data
 * /jf/thairice/t7pdt_data/save
 * /jf/thairice/t7pdt_data/edit
 * /jf/thairice/t7pdt_data/update
 * /jf/thairice/t7pdt_data/view
 * /jf/thairice/t7pdt_data/delete
 * /thairice/t7pdt_data/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t7pdt_data")
public class T7pdt_dataController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T7pdt_dataController.class);

	public static final String pthc = "/jf/thairice/t7pdt_data/";
	public static final String pthv = "/thairice/t7pdt_data/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T7pdt_data.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T7pdt_dataValidator.class)
	public void save() {
		T7pdt_data t7pdt_data = getModel(T7pdt_data.class);
		//other set 
		
		//t7pdt_data.save();		//guiid
		t7pdt_data.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T7pdt_data t7pdt_data = T7pdt_data.dao.findById(getPara());	//guuid
		T7pdt_data t7pdt_data = T7pdt_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t7pdt_data", t7pdt_data);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T7pdt_dataValidator.class)
	public void update() {
		getModel(T7pdt_data.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T7pdt_data t7pdt_data = T7pdt_data.dao.findById(getPara());	//guuid
		T7pdt_data t7pdt_data = T7pdt_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t7pdt_data", t7pdt_data);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T7pdt_dataService.service.delete("t7pdt_data", getPara() == null ? ids : getPara());	//guuid
		T7pdt_dataService.service.deleteById("t7pdt_data", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
