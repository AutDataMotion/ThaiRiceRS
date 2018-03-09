package thairice.mvc.t3user;

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
 * /jf/thairice/t3user
 * /jf/thairice/t3user/save
 * /jf/thairice/t3user/edit
 * /jf/thairice/t3user/update
 * /jf/thairice/t3user/view
 * /jf/thairice/t3user/delete
 * /thairice/t3user/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t3user")
public class T3userController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T3userController.class);

	public static final String pthc = "/jf/thairice/t3user/";
	public static final String pthv = "/thairice/t3user/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T3user.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T3userValidator.class)
	public void save() {
		T3user t3user = getModel(T3user.class);
		//other set 
		
		//t3user.save();		//guiid
		t3user.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T3user t3user = T3user.dao.findById(getPara());	//guuid
		T3user t3user = T3userService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t3user", t3user);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T3userValidator.class)
	public void update() {
		getModel(T3user.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T3user t3user = T3user.dao.findById(getPara());	//guuid
		T3user t3user = T3userService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t3user", t3user);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T3userService.service.delete("t3user", getPara() == null ? ids : getPara());	//guuid
		T3userService.service.deleteById("t3user", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
