package com.platform.mvc.systems;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

/**
 * 系统管理
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/systems")
public class SystemsController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SystemsController.class);
	
	/**
	 * 系统管理列表
	 */
	public void index() {
		paging(ConstantInit.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, Systems.sqlId_splitPage_from);
		render("/platform/systems/list.html");
	}
	
	/**
	 * 保存系统
	 */
	@Before(SystemsValidator.class)
	public void save() {
		SystemsService.service.save(getModel(Systems.class));
		redirect("/jf/platform/systems");
	}

	/**
	 * 准备更新系统
	 */
	public void edit() {
		setAttr("systems", Systems.dao.findById(getPara()));
		render("/platform/systems/update.html");
	}

	/**
	 * 更新系统
	 */
	@Before(SystemsValidator.class)
	public void update() {
		getModel(Systems.class).update();
		redirect("/jf/platform/systems");
	}

	/**
	 * 删除系统
	 */
	public void delete() {
		SystemsService.service.delete(getPara() == null ? ids : getPara());
		redirect("/jf/platform/systems");
	}

	 /* (non-Javadoc)
	 * <p>Description: <／p>
	 * @see com.platform.mvc.base.BaseController#setViewPath()
	 */
	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub
		
	}

}


