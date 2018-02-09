package thairice.mvc.tkvalue;

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
 * /jf/thairice/tkvalue
 * /jf/thairice/tkvalue/save
 * /jf/thairice/tkvalue/edit
 * /jf/thairice/tkvalue/update
 * /jf/thairice/tkvalue/view
 * /jf/thairice/tkvalue/delete
 * /thairice/tkvalue/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/tkvalue")
public class TkvalueController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TkvalueController.class);

	public static final String pthc = "/jf/thairice/tkvalue/";
	public static final String pthv = "/thairice/tkvalue/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, Tkvalue.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(TkvalueValidator.class)
	public void save() {
		Tkvalue tkvalue = getModel(Tkvalue.class);
		//other set 
		
		//tkvalue.save();		//guiid
		tkvalue.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//Tkvalue tkvalue = Tkvalue.dao.findById(getPara());	//guuid
		Tkvalue tkvalue = TkvalueService.service.SelectById(getParaToInt());		//serial int id
		setAttr("tkvalue", tkvalue);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(TkvalueValidator.class)
	public void update() {
		getModel(Tkvalue.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//Tkvalue tkvalue = Tkvalue.dao.findById(getPara());	//guuid
		Tkvalue tkvalue = TkvalueService.service.SelectById(getParaToInt());		//serial int id
		setAttr("tkvalue", tkvalue);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//TkvalueService.service.delete("tkvalue", getPara() == null ? ids : getPara());	//guuid
		TkvalueService.service.deleteById("tkvalue", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
