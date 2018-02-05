package com.platform.mvc.ueditor;

import java.io.File;

import org.apache.log4j.Logger;

import com.baidu.ueditor.ActionEnter;
import com.jfinal.kit.PathKit;
import com.platform.annotation.Controller;
import com.platform.mvc.base.BaseController;

/**
 * Ueditor编辑器
 */
@SuppressWarnings("unused")
//@Controller(controllerKey = {"/jf/platform/ueditor"})
public class UeditorController extends BaseController {

	private static Logger log = Logger.getLogger(UeditorController.class);
	
	/***
	 * ueditor 
	 * 前台文件放在 WebContent/jsFile/ueditor,其中先config.js，后all.js
	 * 后台配置文件config.json 放在WebContent/jf/platform
	 * */
	public void index() {
		String htmlText = new ActionEnter( getRequest(), PathKit.getWebRootPath() + File.separator ).exec();
		renderHtml(htmlText);
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
