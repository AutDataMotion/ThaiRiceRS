package thairice.mvc.t15_news_cnt;

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
 * /jf/thairice/t15_news_cnt
 * /jf/thairice/t15_news_cnt/save
 * /jf/thairice/t15_news_cnt/edit
 * /jf/thairice/t15_news_cnt/update
 * /jf/thairice/t15_news_cnt/view
 * /jf/thairice/t15_news_cnt/delete
 * /thairice/t15_news_cnt/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t15_news_cnt")
public class t15_news_cntController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t15_news_cntController.class);

	public static final String pthc = "/jf/thairice/t15_news_cnt/";
	public static final String pthv = "/thairice/t15_news_cnt/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, t15_news_cnt.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(t15_news_cntValidator.class)
	public void save() {
		t15_news_cnt t15_news_cnt = getModel(t15_news_cnt.class);
		//other set 
		
		//t15_news_cnt.save();		//guiid
		t15_news_cnt.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//t15_news_cnt t15_news_cnt = t15_news_cnt.dao.findById(getPara());	//guuid
		t15_news_cnt t15_news_cnt = t15_news_cntService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t15_news_cnt", t15_news_cnt);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(t15_news_cntValidator.class)
	public void update() {
		getModel(t15_news_cnt.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//t15_news_cnt t15_news_cnt = t15_news_cnt.dao.findById(getPara());	//guuid
		t15_news_cnt t15_news_cnt = t15_news_cntService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t15_news_cnt", t15_news_cnt);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//t15_news_cntService.service.delete("t15_news_cnt", getPara() == null ? ids : getPara());	//guuid
		t15_news_cntService.service.deleteById("t15_news_cnt", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
