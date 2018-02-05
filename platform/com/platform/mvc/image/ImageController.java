package com.platform.mvc.image;

import org.apache.log4j.Logger;

import com.platform.mvc.base.BaseController;

/**
 * 图片裁剪
 * @author DHJ
 */
//@Controller(controllerKey = "/jf/platform/image")
public class ImageController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ImageController.class);

	public int type;
	public String imageName;
	public String imagePath;
	
	public int x1;	// 
	public int y1;	// 
	public int x2;	// 
	public int y2;	// 
	public int w;	// 
	public int h;	// 
	
	public void index() {
		String result = ImageService.service.cut(type, imageName, imagePath, x1, y1, w, h);
		renderText(result);
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


