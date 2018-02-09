package thairice.mvc.t11zone;

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
 * /jf/thairice/t11zone
 * /jf/thairice/t11zone/save
 * /jf/thairice/t11zone/edit
 * /jf/thairice/t11zone/update
 * /jf/thairice/t11zone/view
 * /jf/thairice/t11zone/delete
 * /thairice/t11zone/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t11zone")
public class T11zoneController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T11zoneController.class);

	public static final String pthc = "/jf/thairice/t11zone/";
	public static final String pthv = "/thairice/t11zone/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T11zone.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T11zoneValidator.class)
	public void save() {
		T11zone t11zone = getModel(T11zone.class);
		//other set 
		
		//t11zone.save();		//guiid
		t11zone.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T11zone t11zone = T11zone.dao.findById(getPara());	//guuid
		T11zone t11zone = T11zoneService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t11zone", t11zone);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T11zoneValidator.class)
	public void update() {
		getModel(T11zone.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T11zone t11zone = T11zone.dao.findById(getPara());	//guuid
		T11zone t11zone = T11zoneService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t11zone", t11zone);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T11zoneService.service.delete("t11zone", getPara() == null ? ids : getPara());	//guuid
		T11zoneService.service.deleteById("t11zone", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
