package com.platform.mvc.systems;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;
import com.platform.mvc.base.BaseService;
import com.platform.mvc.menu.Menu;
import com.platform.mvc.module.Module;

public class SystemsService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SystemsService.class);

	public static final SystemsService service = Enhancer.enhance(SystemsService.class);
	
	/**
	 * 保存
	 * @param systems
	 */
	public void save(Systems systems){
		// 保存系统
		systems.save();
		
		// 初始化模块根节点
		Module module = new Module();
		module.set(Module.column_systemsids, systems.getPKValue());
		module.set(Module.column_isparent, "true");
		module.set(Module.column_images, "3.png");
		module.set(Module.column_orderids, 1);
		module.set(Module.column_names, "根节点");
		module.save();
		
		// 初始化菜单根节点
		Menu menu = new Menu();
		menu.set(Menu.column_systemsids, systems.getPKValue());
		menu.set(Menu.column_isparent, "true");
		menu.set(Menu.column_images, "3.png");
		menu.set(Menu.column_orderids, 1);
		menu.set(Menu.column_names_zhcn, "根节点");
		menu.save();
	}

	/**
	 * 删除
	 * @param ids
	 */
	public void delete(String ids){
		String[] idsArr = splitByComma(ids);
		for (String systemsIds : idsArr) {
			//删除系统
			Systems.dao.deleteById(systemsIds);
			//删除关联模块
			
			//删除关联菜单
			
			//删除关联日志
		}
	}
	
}
