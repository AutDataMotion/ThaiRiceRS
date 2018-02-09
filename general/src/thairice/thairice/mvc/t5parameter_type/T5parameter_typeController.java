package thairice.mvc.t5parameter_type;

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
 * /jf/thairice/t5parameter_type
 * /jf/thairice/t5parameter_type/save
 * /jf/thairice/t5parameter_type/edit
 * /jf/thairice/t5parameter_type/update
 * /jf/thairice/t5parameter_type/view
 * /jf/thairice/t5parameter_type/delete
 * /thairice/t5parameter_type/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t5parameter_type")
public class T5parameter_typeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T5parameter_typeController.class);

	public static final String pthc = "/jf/thairice/t5parameter_type/";
	public static final String pthv = "/thairice/t5parameter_type/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T5parameter_type.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T5parameter_typeValidator.class)
	public void save() {
		T5parameter_type t5parameter_type = getModel(T5parameter_type.class);
		//other set 
		
		//t5parameter_type.save();		//guiid
		t5parameter_type.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T5parameter_type t5parameter_type = T5parameter_type.dao.findById(getPara());	//guuid
		T5parameter_type t5parameter_type = T5parameter_typeService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t5parameter_type", t5parameter_type);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T5parameter_typeValidator.class)
	public void update() {
		getModel(T5parameter_type.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T5parameter_type t5parameter_type = T5parameter_type.dao.findById(getPara());	//guuid
		T5parameter_type t5parameter_type = T5parameter_typeService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t5parameter_type", t5parameter_type);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T5parameter_typeService.service.delete("t5parameter_type", getPara() == null ? ids : getPara());	//guuid
		T5parameter_typeService.service.deleteById("t5parameter_type", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
