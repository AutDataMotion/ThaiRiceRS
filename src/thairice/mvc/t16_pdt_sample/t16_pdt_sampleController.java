package thairice.mvc.t16_pdt_sample;

import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseModel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.upload.UploadFile;

import thairice.constant.ConstantInitMy;
import thairice.entity.ResultEntity;
import thairice.mvc.t15_news_cnt.t15_news_cnt;
import thairice.mvc.t2syslog.EnumT2sysLog;
import thairice.mvc.t2syslog.T2syslogService;
import thairice.mvc.t3user.T3user;


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
	private static Logger LOG = Logger.getLogger(t16_pdt_sampleController.class);

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
		// 处理结果
		ResultEntity res = null;
		try {
				Long id = Long.parseLong(getPara("id"));
				String rice_planting_area = getPara("rice_planting_area");
				String rice_drought = getPara("rice_drought");
				String rice_condition = getPara("rice_condition");
				String rice_production = getPara("rice_production");
				String pic_path = getPara("pic_path");
				String pdt_desc = getPara("pdt_desc");
				
				t16_pdt_sample t16 = new t16_pdt_sample();
				t16.setId(id);
				t16.setRice_drought(rice_drought);
				t16.setRice_condition(rice_condition);
				t16.setRice_planting_area(rice_planting_area);
				int beginIndex = pic_path.lastIndexOf("\\");
				t16.setPic_path("upload\\" + pic_path.substring(beginIndex + 1));
				t16.setPdt_desc(pdt_desc);
				t16.setRice_production(rice_production);
				t16.update();

				LOG.debug("更新产品样例成功");
				res = new ResultEntity("0000");
				// 写入日志
		 //       T3user user = getSessionAttr("user");
		 //       T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Update news content", "Successful");
				setAttr("t16_pdt_sample", "active");
				renderJson(res);
				return;

		} catch (Exception e) {
			LOG.error("更新新闻发生异常");
			res = new ResultEntity("0016");
			// 写入日志
	     //   T3user user = getSessionAttr("user");
	     //   T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Update news content", res.getDesc());
			renderJson(res);
			return;
		}
	}
	
	/**
	 * Statistic yield file upload 
	 */
	@Clear
	public void imageUpload() {
		int maxSize = 10 * 1024 * 1024;              //上传文件大小10M
		// 获取文件路径
		// 获取工程路径
		String webContentPath = "";
		try {
			String path = Class.class.getResource("/").toURI().getPath();
			webContentPath = new File(path).getParentFile().getParentFile().getCanonicalPath();
			LOG.info("webContentPath=" + webContentPath);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		UploadFile  upFile = getFile("file",webContentPath + "\\upload", maxSize, "utf-8");
		File file=new File(upFile.getSaveDirectory() + "\\" + upFile.getFileName());  
		LOG.debug( "getFileName=" + upFile.getFileName());
		LOG.debug( "getSaveDirectory =" + upFile.getSaveDirectory());
		LOG.debug( "getOriginalFileName =" + upFile.getOriginalFileName());
		LOG.debug( "getParameterName = " + upFile.getParameterName());
		LOG.debug( "getContentType = " + upFile.getContentType());
		System.out.println(upFile.getSaveDirectory() + "\\" + upFile.getOriginalFileName());
		if(file.exists()){
			// 删除原来文件
			File fileOld=new File(upFile.getSaveDirectory() + "\\" + upFile.getOriginalFileName());
			if(fileOld.exists())
				fileOld.delete();
			file.renameTo(new File(upFile.getSaveDirectory() + "\\" + upFile.getOriginalFileName()));			
		}
        T3user user = getSessionAttr("admin");
        T2syslogService.addLog(EnumT2sysLog.INFO, user.getId(), user.getAccount(), "Product sample image upload", "Successful");
		renderWithPath("/adm2018/news_management.html");
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
