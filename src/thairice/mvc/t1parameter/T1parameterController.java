package thairice.mvc.t1parameter;

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
 * /jf/thairice/t1parameter
 * /jf/thairice/t1parameter/save
 * /jf/thairice/t1parameter/edit
 * /jf/thairice/t1parameter/update
 * /jf/thairice/t1parameter/view
 * /jf/thairice/t1parameter/delete
 * /thairice/t1parameter/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t1parameter")
public class T1parameterController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T1parameterController.class);

	public static final String pthc = "/jf/thairice/t1parameter/";
	public static final String pthv = "/thairice/t1parameter/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T1parameter.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T1parameterValidator.class)
	public void save() {
		T1parameter t1parameter = getModel(T1parameter.class);
		//other set 
		
		//t1parameter.save();		//guiid
		t1parameter.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T1parameter t1parameter = T1parameter.dao.findById(getPara());	//guuid
		T1parameter t1parameter = T1parameterService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t1parameter", t1parameter);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T1parameterValidator.class)
	public void update() {
		getModel(T1parameter.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T1parameter t1parameter = T1parameter.dao.findById(getPara());	//guuid
		T1parameter t1parameter = T1parameterService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t1parameter", t1parameter);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T1parameterService.service.delete("t1parameter", getPara() == null ? ids : getPara());	//guuid
		T1parameterService.service.deleteById("t1parameter", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
