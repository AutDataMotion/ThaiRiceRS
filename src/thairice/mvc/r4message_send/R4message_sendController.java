package thairice.mvc.r4message_send;

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
 * /jf/thairice/r4message_send
 * /jf/thairice/r4message_send/save
 * /jf/thairice/r4message_send/edit
 * /jf/thairice/r4message_send/update
 * /jf/thairice/r4message_send/view
 * /jf/thairice/r4message_send/delete
 * /thairice/r4message_send/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/r4message_send")
public class R4message_sendController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(R4message_sendController.class);

	public static final String pthc = "/jf/thairice/r4message_send/";
	public static final String pthv = "/thairice/r4message_send/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, R4message_send.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(R4message_sendValidator.class)
	public void save() {
		R4message_send r4message_send = getModel(R4message_send.class);
		//other set 
		
		//r4message_send.save();		//guiid
		r4message_send.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//R4message_send r4message_send = R4message_send.dao.findById(getPara());	//guuid
		R4message_send r4message_send = R4message_sendService.service.SelectById(getParaToInt());		//serial int id
		setAttr("r4message_send", r4message_send);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(R4message_sendValidator.class)
	public void update() {
		getModel(R4message_send.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//R4message_send r4message_send = R4message_send.dao.findById(getPara());	//guuid
		R4message_send r4message_send = R4message_sendService.service.SelectById(getParaToInt());		//serial int id
		setAttr("r4message_send", r4message_send);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//R4message_sendService.service.delete("r4message_send", getPara() == null ? ids : getPara());	//guuid
		R4message_sendService.service.deleteById("r4message_send", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
