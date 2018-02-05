package com.platform.mvc.resources;

import org.apache.log4j.Logger;

import com.platform.annotation.Controller;
import com.platform.mvc.base.BaseController;
import com.platform.plugin.ParamInitPlugin;
import com.platform.plugin.SqlXmlPlugin;

/**
 * 资源监控
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Controller(controllerKey = "/jf/platform/resources")
public class ResourcesController extends BaseController {
	
	private static Logger log = Logger.getLogger(ResourcesController.class);

	/**
	 * 首页
	 */
	public void index(){;
		setAttrs(ResourcesService.service.pv());
		setAttrs(ResourcesService.service.getResources());
		render("/platform/resources/index.html");
	}
	
	/**
	 * 刷新参数缓存，重新加载系统初始化参数
	 */
	public void refreshParamCache(){
		new ParamInitPlugin().start();
		redirect("/jf/platform/resources");
	}

	/**
	 * 刷新Sql缓存，重新加载Sql XML
	 */
	public void refreshSqlCache(){
		SqlXmlPlugin.init(false);
		redirect("/jf/platform/resources");
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
