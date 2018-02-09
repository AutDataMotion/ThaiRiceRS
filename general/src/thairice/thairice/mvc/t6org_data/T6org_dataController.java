package thairice.mvc.t6org_data;

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
 * /jf/thairice/t6org_data
 * /jf/thairice/t6org_data/save
 * /jf/thairice/t6org_data/edit
 * /jf/thairice/t6org_data/update
 * /jf/thairice/t6org_data/view
 * /jf/thairice/t6org_data/delete
 * /thairice/t6org_data/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t6org_data")
public class T6org_dataController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T6org_dataController.class);

	public static final String pthc = "/jf/thairice/t6org_data/";
	public static final String pthv = "/thairice/t6org_data/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T6org_data.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T6org_dataValidator.class)
	public void save() {
		T6org_data t6org_data = getModel(T6org_data.class);
		//other set 
		
		//t6org_data.save();		//guiid
		t6org_data.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T6org_data t6org_data = T6org_data.dao.findById(getPara());	//guuid
		T6org_data t6org_data = T6org_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t6org_data", t6org_data);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T6org_dataValidator.class)
	public void update() {
		getModel(T6org_data.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T6org_data t6org_data = T6org_data.dao.findById(getPara());	//guuid
		T6org_data t6org_data = T6org_dataService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t6org_data", t6org_data);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T6org_dataService.service.delete("t6org_data", getPara() == null ? ids : getPara());	//guuid
		T6org_dataService.service.deleteById("t6org_data", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
