package thairice.mvc.t8message;

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
 * /jf/thairice/t8message
 * /jf/thairice/t8message/save
 * /jf/thairice/t8message/edit
 * /jf/thairice/t8message/update
 * /jf/thairice/t8message/view
 * /jf/thairice/t8message/delete
 * /thairice/t8message/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t8message")
public class T8messageController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T8messageController.class);

	public static final String pthc = "/jf/thairice/t8message/";
	public static final String pthv = "/thairice/t8message/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T8message.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(T8messageValidator.class)
	public void save() {
		T8message t8message = getModel(T8message.class);
		//other set 
		
		//t8message.save();		//guiid
		t8message.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T8message t8message = T8message.dao.findById(getPara());	//guuid
		T8message t8message = T8messageService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t8message", t8message);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T8messageValidator.class)
	public void update() {
		getModel(T8message.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T8message t8message = T8message.dao.findById(getPara());	//guuid
		T8message t8message = T8messageService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t8message", t8message);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T8messageService.service.delete("t8message", getPara() == null ? ids : getPara());	//guuid
		T8messageService.service.deleteById("t8message", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
