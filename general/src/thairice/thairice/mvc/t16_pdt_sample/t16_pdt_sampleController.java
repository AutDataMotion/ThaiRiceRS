package thairice.mvc.t16_pdt_sample;

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
 * /jf/thairice/t16_pdt_sample
 * /jf/thairice/t16_pdt_sample/save
 * /jf/thairice/t16_pdt_sample/edit
 * /jf/thairice/t16_pdt_sample/update
 * /jf/thairice/t16_pdt_sample/view
 * /jf/thairice/t16_pdt_sample/delete
 * /thairice/t16_pdt_sample/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t16_pdt_sample")
public class t16_pdt_sampleController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t16_pdt_sampleController.class);

	public static final String pthc = "/jf/thairice/t16_pdt_sample/";
	public static final String pthv = "/thairice/t16_pdt_sample/";

	/**
	 * 列表
	 */
	public void index() {
		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, t16_pdt_sample.sqlId_splitPage_from);
		renderWithPath(pthv+"list.html");
	}
	
	/**
	 * 保存
	 */
	@Before(t16_pdt_sampleValidator.class)
	public void save() {
		t16_pdt_sample t16_pdt_sample = getModel(t16_pdt_sample.class);
		//other set 
		
		//t16_pdt_sample.save();		//guiid
		t16_pdt_sample.saveGenIntId();	//serial int id
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//t16_pdt_sample t16_pdt_sample = t16_pdt_sample.dao.findById(getPara());	//guuid
		t16_pdt_sample t16_pdt_sample = t16_pdt_sampleService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t16_pdt_sample", t16_pdt_sample);
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(t16_pdt_sampleValidator.class)
	public void update() {
		getModel(t16_pdt_sample.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//t16_pdt_sample t16_pdt_sample = t16_pdt_sample.dao.findById(getPara());	//guuid
		t16_pdt_sample t16_pdt_sample = t16_pdt_sampleService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t16_pdt_sample", t16_pdt_sample);
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//t16_pdt_sampleService.service.delete("t16_pdt_sample", getPara() == null ? ids : getPara());	//guuid
		t16_pdt_sampleService.service.deleteById("t16_pdt_sample", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	
}
