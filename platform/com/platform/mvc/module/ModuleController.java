package com.platform.mvc.module;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.platform.dto.ZtreeNode;
import com.platform.mvc.base.BaseController;

/**
 * 模块管理
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/module")
public class ModuleController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ModuleController.class);
	
	private String systemsIds; // 哪个系统下的模块
	private String pIds; // 上级模块ids
	private String names; // 模块名称
	private int orderIds; // 模块排序号
	
	/**
	 * 模块tree首页
	 */
	public void index() {
		render("/platform/module/tree.html");
	}

	/**
	 * 模块tree数据
	 */
	public void treeData()  {
		List<ZtreeNode> nodeList = ModuleService.service.childNodeData(getCxt(), systemsIds, ids);
		renderJson(nodeList);
	}

	/**
	 * 保存模块
	 */
	@Before(ModuleValidator.class)
	public void save() {
		ids = ModuleService.service.save(pIds, names, orderIds);
		renderText(ids);
	}

	/**
	 * 更新模块
	 */
	@Before(ModuleValidator.class)
	public void update() {
		ModuleService.service.update(ids, pIds, names);
		renderText(ids);
	}

	/**
	 * 删除模块
	 */
	public void delete() {
		ModuleService.service.delete(ids);
		renderText(ids);
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


