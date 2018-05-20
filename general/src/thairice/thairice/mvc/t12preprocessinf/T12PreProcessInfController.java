package thairice.mvc.t12preprocessinf;

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
 * /jf/thairice/t12PreProcessInf
 * /jf/thairice/t12PreProcessInf/save
 * /jf/thairice/t12PreProcessInf/edit
 * /jf/thairice/t12PreProcessInf/update
 * /jf/thairice/t12PreProcessInf/view
 * /jf/thairice/t12PreProcessInf/delete
 * /thairice/t12PreProcessInf/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t12PreProcessInf")
public class T12PreProcessInfController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T12PreProcessInfController.class);

	public static final String pthc = "/jf/thairice/t12PreProcessInf/";
	public static final String pthv = "/thairice/t12PreProcessInf/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T12PreProcessInf.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T12PreProcessInfValidator.class)
	public void save() {
		T12PreProcessInf t12PreProcessInf = getModel(T12PreProcessInf.class);
		//other set 
		
		//t12PreProcessInf.save();		//guiid
		t12PreProcessInf.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T12PreProcessInf t12PreProcessInf = T12PreProcessInf.dao.findById(getPara());	//guuid
		T12PreProcessInf t12PreProcessInf = T12PreProcessInfService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t12PreProcessInf", t12PreProcessInf);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T12PreProcessInfValidator.class)
	public void update() {
		getModel(T12PreProcessInf.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T12PreProcessInf t12PreProcessInf = T12PreProcessInf.dao.findById(getPara());	//guuid
		T12PreProcessInf t12PreProcessInf = T12PreProcessInfService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t12PreProcessInf", t12PreProcessInf);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T12PreProcessInfService.service.delete("t12_pre_process_inf", getPara() == null ? ids : getPara());	//guuid
		T12PreProcessInfService.service.deleteById("t12_pre_process_inf", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
