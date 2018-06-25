package thairice.mvc.t14my_region;

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
 * /jf/thairice/t14my_region
 * /jf/thairice/t14my_region/save
 * /jf/thairice/t14my_region/edit
 * /jf/thairice/t14my_region/update
 * /jf/thairice/t14my_region/view
 * /jf/thairice/t14my_region/delete
 * /thairice/t14my_region/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t14my_region")
public class t14my_regionController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t14my_regionController.class);

	public static final String pthc = "/jf/thairice/t14my_region/";
	public static final String pthv = "/thairice/t14my_region/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, t14my_region.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(t14my_regionValidator.class)
	public void save() {
		t14my_region t14my_region = getModel(t14my_region.class);
		//other set 
		
		//t14my_region.save();		//guiid
		t14my_region.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//t14my_region t14my_region = t14my_region.dao.findById(getPara());	//guuid
		t14my_region t14my_region = t14my_regionService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t14my_region", t14my_region);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(t14my_regionValidator.class)
	public void update() {
		getModel(t14my_region.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//t14my_region t14my_region = t14my_region.dao.findById(getPara());	//guuid
		t14my_region t14my_region = t14my_regionService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t14my_region", t14my_region);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//t14my_regionService.service.delete("t14my_region", getPara() == null ? ids : getPara());	//guuid
		t14my_regionService.service.deleteById("t14my_region", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
