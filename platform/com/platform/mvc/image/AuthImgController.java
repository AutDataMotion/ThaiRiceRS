package com.platform.mvc.image;

import org.apache.log4j.Logger;

import com.jfinal.aop.Clear;
import com.jfinal.render.Render;
import com.platform.beetl.render.MyCaptchaRender;
import com.platform.mvc.base.BaseController;

/**
 * 验证码
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/authImg")

public class AuthImgController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(AuthImgController.class);
	
	@Clear
	public void index() {
		Render render = new MyCaptchaRender();
		render(render);
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


