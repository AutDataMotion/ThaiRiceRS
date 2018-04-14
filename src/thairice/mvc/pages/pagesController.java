package thairice.mvc.pages;

import org.apache.log4j.Logger;

import com.jfinal.aop.Clear;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import thairice.mvc.t10pdt_report.T10pdt_reportController;

public class pagesController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(pagesController.class);

	public static final String pthc = "/jf/thairice/pagesController/";
	public static final String pthv = "/f/";
	@Clear
	public void index() {
		//paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T10pdt_report.sqlId_splitPage_from);
		//renderWithPath(pthv+"list.html");
		renderWithPath(pthv+"index.html");
	}
	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}

}
