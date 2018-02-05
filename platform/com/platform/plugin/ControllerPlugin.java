package com.platform.plugin;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;
import com.platform.annotation.Controller;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseController;
import com.platform.tools.ToolClassSearch;

/**
 * 扫描Controller上的注解，绑定Controller和controllerKey
 * @author DHJ  dongcb678@163.com
 */
public class ControllerPlugin implements IPlugin {

    protected final Logger log = Logger.getLogger(getClass());
    
    private Routes routes;

	public ControllerPlugin(Routes routes){
		this.routes = routes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean start() {
		// 查询所有继承BaseController的子类
		List<String> jarsList = (List<String>) PropertiesPlugin
				.getParamMapValue(ConstantInit.config_scan_jar);
		List<Class<?>> controllerClasses = ToolClassSearch
				.search(BaseController.class, jarsList);
		
		// 循环处理自动注册映射
		for (Class controller : controllerClasses) {
			// 获取注解对象
			Controller controllerBind = (Controller) controller
					.getAnnotation(Controller.class);
			if (controllerBind == null) {
//				log.warn(controller.getName() + "继承了BaseController，但是没有注解绑定映射路径，请检查是否已经手动绑定 ！！！");
				continue;
			}

			// 获取映射路径数组
			String[] controllerKeys = controllerBind.controllerKey();
			for (String controllerKey : controllerKeys) {
				controllerKey = controllerKey.trim();
				if(controllerKey.equals("")){
					log.error(controller.getName() + "注解错误，映射路径为空");
					continue;
				}
				// 注册映射
				routes.add(controllerKey, controller);
//				log.debug("Controller注册： controller = " + controller + ", controllerKey = " + controllerKey);
			}
		}
		return true;
	}

	@Override
	public boolean stop() {
		return true;
	}

}
