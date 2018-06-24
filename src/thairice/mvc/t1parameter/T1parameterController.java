package thairice.mvc.t1parameter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import csuduc.platform.util.ReportUtil;
import csuduc.platform.util.lyf.lyfGis;
import thairice.entity.ResultEntity;
import thairice.interceptor.AdminLoginInterceptor;
import thairice.mvc.t3user.Result;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t7pdt_data.T7pdt_data;
import thairice.mvc.t9sample_info.T9sample_info;
import thairice.utils.ParamUtils;


/**
 * XXX 管理	
 * 描述：
 * 
 * /jf/thairice/t1parameter
 * /jf/thairice/t1parameter/save
 * /jf/thairice/t1parameter/edit
 * /jf/thairice/t1parameter/update
 * /jf/thairice/t1parameter/view
 * /jf/thairice/t1parameter/delete
 * /thairice/t1parameter/add.html
 * 
 */
//@Controller(controllerKey = "/jf/thairice/t1parameter")
@Before(AdminLoginInterceptor.class)
public class T1parameterController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(T1parameterController.class);

	public static final String pthc = "/jf/thairice/t1parameter/";
	public static final String pthv = "/thairice/t1parameter/";
	
	public static final String areaTifPath = "E:/areatif/";
	
	public static final String merge_gpserver_workspace = "C:/arcgisserver/directories/arcgisjobs/merge_gpserver/";
	public static final String erase_gpserver_workspace = "C:/arcgisserver/directories/arcgisjobs/erase_gpserver/";
	public static final String arcgisserver_shp_workspacePath = "E:/arcgisserver_shp_workspace/";
	public static final String thairiceproduct_path = "E:/thairiceproduct/";
	public static final String areaSampleAndTempProductPath = "E:/areaSampleAndTempProduct/";//Sample、TempProduct
	
	public static final String models_workspace = "E:/gpmodels/";
	//将gp服务执行的生成结果result.*（shp、dbf等）copyTo models_workspace里边
	//并将文件以waitingformodify.*（shp、dbf等）命名，做下次使用
	public static final String gpresultFilePreName = "result";
	public static final String models_workspace_tempFilePreName = "waitingformodify";
	//2018-05-11_72.tif--->arearaster.tif 以备改变波段显示
	public static final String models_workspace_tempAreaTifFilePreName = "arearaster";
	/**
	 * 列表
	 */
	public void index() {
		// System.out.println("ssssss");
		// paging(ConstantInitMy.db_dataSource_main, splitPage,
		// BaseModel.sqlId_splitPage_select, T1parameter.sqlId_splitPage_from);
		// renderWithPath(pthv+"list.html");
		//
		Page page = T1parameter.dao.paginate(getParaToInt(0, 1), 10, "select *", "from t1parameter order by id asc");
		setAttr("blogPage", page);
		setAttr("queryAllParm", "active");
		renderWithPath("/adm2018/production_configuration.html");
	}
	
	/**
	 * 列表
	 */
	@Clear
	public void queryAllParm() {
		Page page = T1parameter.dao.paginate(getParaToInt(0, 1), 1000, "select *", "from t1parameter order by id asc");
		setAttr("blogPage", page);
		setAttr("queryAllParm", "active");
		renderWithPath("/adm2018/production_configuration.html");
	}
	
	/**
	 * Statistic yield file upload 
	 */
	@Clear
	public void riceYieldUpload() {
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
		System.out.println(upFile.getSaveDirectory() + "\\statistic_yield_file.csv");
		if(file.exists()){
			// 删除原来文件
			File fileOld=new File(upFile.getSaveDirectory() + "\\statistic_yield_file.csv");
			if(fileOld.exists())
				fileOld.delete();
			file.renameTo(new File(upFile.getSaveDirectory() + "\\statistic_yield_file.csv"));			
		}
		
		//后面的123456.jpg是重命名的文件名
//		getFile("file").getFile().renameTo(new File(webContentPath + "statistic_yield_file.csv"));
		
		renderWithPath("/adm2018/production_configuration.html");
	}
	
	/**
	 * Statistic yield file upload 
	 */
	@Clear
	public void riceYieldUpload2() {
		
		System.out.println("上传后文件名："  + getAttrForStr("url"));
		renderJson(new Result(1, getAttrForStr("url")));
		
/*		
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
		if(file.exists()){
			file.renameTo(new File(upFile.getSaveDirectory() + "\\statistic_yield_file.csv"));
		}*/
		
		//后面的123456.jpg是重命名的文件名
//		getFile("file").getFile().renameTo(new File(webContentPath + "statistic_yield_file.csv"));
		
		/*renderWithPath("/adm2018/production_configuration.html");*/
	}
	


	/**
	 * ftp源路径查询 zhuchaobin, 2018-03-13
	 */
	public void queryParams() {
		String flag = getPara("flag");
		// 处理结果
		ResultEntity res = null;
		JSONObject result = new JSONObject();
		try {
			// 001:ftp下载路径查询
			if ("001".equals(flag)) {

				// 从参数明细查询远程ftp下载路径
				List<String> remotePathList = ParamUtils.getParamList(ParamUtils.PC_FTP_AUTO_DWLD,
						ParamUtils.PD_SRCFTP_ROOT_CTLG);
				result.put("flag", "001");
				if (null != remotePathList) {
					for (int i = 0; i < remotePathList.size(); i++) {
						String remotePath = remotePathList.get(i);
						if(StringUtils.isBlank(remotePath))
							result.put("ftpSrcPath" + i, "");
						else
							result.put("ftpSrcPath" + i, remotePath);
					}
					res = new ResultEntity("0000");
					result.put("code", "0000");
					result.put("desc", res.getDesc());
				} else {
					LOG.error("从参数明细查询远程ftp下载路径结果为空");
					res = new ResultEntity("9999");
					result.put("code", "9999");
					result.put("desc", res.getDesc());
				}
			} else if ("002".equals(flag)) { // 002:干旱监测阈值查询
				result.put("flag", "002");
				String droughtThreshold1 = ParamUtils.getParam("10000003", "001");
				String droughtThreshold2 = ParamUtils.getParam("10000003", "002");
				String droughtThreshold3 = ParamUtils.getParam("10000003", "003");
				String droughtThreshold4 = ParamUtils.getParam("10000003", "004");
				if (null == droughtThreshold1 || null == droughtThreshold2 || null == droughtThreshold3
						|| null == droughtThreshold4) {
					res = new ResultEntity("9999");
					result.put("code", "9999");
					result.put("desc", res.getDesc());
				} else {
					result.put("droughtThreshold1", droughtThreshold1);
					result.put("droughtThreshold2", droughtThreshold2);
					result.put("droughtThreshold3", droughtThreshold3);
					result.put("droughtThreshold4", droughtThreshold4);
					res = new ResultEntity("0000");
					result.put("code", "0000");
					result.put("desc", res.getDesc());
				}
			} else if ("003".equals(flag)) { // 003:长势监测阈值查询
				result.put("flag", "003");
				String conditionThreshold1 = ParamUtils.getParam("10000004", "001");
				String conditionThreshold2 = ParamUtils.getParam("10000004", "002");
				String conditionThreshold3 = ParamUtils.getParam("10000004", "003");
				String conditionThreshold4 = ParamUtils.getParam("10000004", "004");
				String inStartYear = ParamUtils.getParam("10000004", "005");
				String inEndYear = ParamUtils.getParam("10000004", "006");
				if (null == conditionThreshold1 || null == conditionThreshold2 || null == conditionThreshold3
						|| null == conditionThreshold4 || null == inStartYear || null == inEndYear) {
					res = new ResultEntity("9999");
					result.put("code", "9999");
					result.put("desc", res.getDesc());
				} else {
					result.put("conditionThreshold1", conditionThreshold1);
					result.put("conditionThreshold2", conditionThreshold2);
					result.put("conditionThreshold3", conditionThreshold3);
					result.put("conditionThreshold4", conditionThreshold4);
					result.put("inStartYear", inStartYear);
					result.put("inEndYear", inEndYear);
					res = new ResultEntity("0000");
					result.put("code", "0000");
					result.put("desc", res.getDesc());
				}
			}
			renderJson(result.toString());
		} catch (Exception e) {
			res = new ResultEntity("9999");
			result.put("code", "9999");
			result.put("desc", res.getDesc());
			LOG.error("查询参数发生异常:" + e);
			renderJson(result.toString());
		}
		// renderWithPath("/ui/thairice/production_configuration.html");
	}
	
	/**
	 * 更新
	 */
	// @Before(T1parameterValidator.class)
	public void updateParams() {
		// 处理结果
		ResultEntity res = null;
		try {
			String flag = getPara("flag");
			if("001".equals(flag)) {
				String ftpSrcPath0 = getPara("ftpSrcPath0");
				String ftpSrcPath1 = getPara("ftpSrcPath1");
				String ftpSrcPath2 = getPara("ftpSrcPath2");
				String ftpSrcPath3 = getPara("ftpSrcPath3");				
				T1parameter parm = new T1parameter();
				parm.setId(new BigInteger("5"));
				parm.setValue_(ftpSrcPath0);
				parm.update();
				parm.setId(new BigInteger("6"));
				parm.setValue_(ftpSrcPath1);
				parm.update();
				parm.setId(new BigInteger("7"));
				parm.setValue_(ftpSrcPath2);
				parm.update();
				parm.setId(new BigInteger("14"));
				parm.setValue_(ftpSrcPath3);
				parm.update();
				LOG.debug("更新参数成功");
				res = new ResultEntity("0000");
				renderJson(res);
				return;
			} else if("002".equals(flag)) {
				String droughtThreshold4 = getPara("droughtThreshold4");
				String droughtThreshold1 = getPara("droughtThreshold1");
				String droughtThreshold2 = getPara("droughtThreshold2");
				String droughtThreshold3 = getPara("droughtThreshold3");				
				ParamUtils.updateParam("10000003", "001", droughtThreshold1);
				ParamUtils.updateParam("10000003", "002", droughtThreshold2);
				ParamUtils.updateParam("10000003", "003", droughtThreshold3);
				ParamUtils.updateParam("10000003", "004", droughtThreshold4);
				LOG.debug("更新参数成功");
				res = new ResultEntity("0000");
				renderJson(res);
				return;
			} else if("003".equals(flag)) {
				String conditionThreshold4 = getPara("conditionThreshold4");
				String conditionThreshold1 = getPara("conditionThreshold1");
				String conditionThreshold2 = getPara("conditionThreshold2");
				String conditionThreshold3 = getPara("conditionThreshold3");
				String inStartYear = getPara("inStartYear");		
				String inEndYear = getPara("inEndYear");					
				ParamUtils.updateParam("10000004", "001", conditionThreshold1);
				ParamUtils.updateParam("10000004", "002", conditionThreshold2);
				ParamUtils.updateParam("10000004", "003", conditionThreshold3);
				ParamUtils.updateParam("10000004", "004", conditionThreshold4);
				ParamUtils.updateParam("10000004", "005", inStartYear);
				ParamUtils.updateParam("10000004", "006", inEndYear);
				LOG.debug("更新参数成功");
				res = new ResultEntity("0000");
				renderJson(res);
				return;
			} else {
				LOG.error("更新参数发生异常");
				res = new ResultEntity("0011");
				renderJson(res);
				return;
			}
		} catch (Exception e) {
			LOG.error("更新参数发生异常");
			res = new ResultEntity("0011");
			renderJson(res);
			return;
		}
	}
//	/**
//	 * 列表
//	 */
//	public void index() {
//		paging(ConstantInitMy.db_dataSource_main, splitPage, BaseModel.sqlId_splitPage_select, T1parameter.sqlId_splitPage_from);
//		renderWithPath(pthv+"list.html");
//	}
	
	/**
	 * 保存
	 */
	@Before(T1parameterValidator.class)
	public void save() {
		T1parameter t1parameter = getModel(T1parameter.class);
		//other set 
		
		//t1parameter.save();		//guiid
		t1parameter.saveGenIntId();	//serial int id
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T1parameter t1parameter = T1parameter.dao.findById(getPara());	//guuid
		T1parameter t1parameter = T1parameterService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t1parameter", t1parameter);
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"update.html");

	}
	
	/**
	 * 更新
	 */
	@Before(T1parameterValidator.class)
	public void update() {
		getModel(T1parameter.class).update();
		redirect(pthc);
	}

	/**
	 * 查看
	 */
	public void view() {
		//T1parameter t1parameter = T1parameter.dao.findById(getPara());	//guuid
		T1parameter t1parameter = T1parameterService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t1parameter", t1parameter);
		setAttr("queryAllParm", "active");
		renderWithPath(pthv+"view.html");
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		//T1parameterService.service.delete("t1parameter", getPara() == null ? ids : getPara());	//guuid
		T1parameterService.service.deleteById("t1parameter", getPara() == null ? ids : getPara());	//serial int id
		redirect(pthc);
	}
	
	public void setViewPath(){
		setAttr(ConstantRender.PATH_CTL_NAME, pthc);
		setAttr(ConstantRender.PATH_VIEW_NAME, pthv);
	}
	@Clear
	public void getFilesfromServerWorkspace()
	{
		try {
			JSONObject msg = new JSONObject();
			boolean flag = false;
			JSONArray files = new JSONArray();
			
			File tifFileWorkspace = new File(areaTifPath);
			if(tifFileWorkspace.isDirectory())
			{
				File[] tifFiles = tifFileWorkspace.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						if(name.lastIndexOf('.')>0)
						{
							int lastIndex = name.lastIndexOf('.');
							String file_surffix = name.substring(lastIndex+1);
							if(file_surffix.equals("tif"))
							{
								return true;
							}
						}
						return false;
					}
					
				});
				if(tifFiles.length>0)
				{
					flag  = true;
					
					for(File tifFile:tifFiles)
					{
						///2018-05-11_72.tif
						JSONObject file = new JSONObject();
						file.put("name", tifFile.getName());
						file.put("date", tifFile.getName().split("_")[0]);
						String temp = tifFile.getName().split("_")[1];
						file.put("district", temp.substring(0, temp.lastIndexOf('.')));
						files.add(file);
						saveTifFilesInfo2Database(file);
					}
				}
				
			}
			msg.put("flag", flag);
			msg.put("files", files);
			renderJson(msg);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public boolean saveTifFilesInfo2Database(JSONObject file)
	{
		try {
			String fileName = file.getString("name");
			T7pdt_data item = T7pdt_data.dao.findFirst("select * from t7pdt_data where source_file_list=?", fileName);
			if(item!=null)
			{
				return true;//数据库中已有记录
			}
			else {//数据库中没有相应记录
				T7pdt_data t7pdt_data = getModel(T7pdt_data.class);
				t7pdt_data.setType_("01");//面积
				t7pdt_data.setSource_file_list(fileName);//源文件列表
				t7pdt_data.setCollect_time(Timestamp.valueOf(file.getString("date")+" 00:00:00"));//数据采集时间
				t7pdt_data.setZone_code(file.getString("district"));
				boolean result = t7pdt_data.saveGenIntId();
				return result;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	public void copyAreaTifFile2gpWorkspace()
	{
		try {
			JSONObject msg = new JSONObject();
			
			String tifFileName = getPara("tifFileName");//2018-05-11_72.tif
			String tifFileNamePrefix = tifFileName.substring(0,tifFileName.lastIndexOf("."));//2018-05-11_72
			String AreatifFilePath = areaTifPath;
			File[] AreatifFiles = ReportUtil.fileFilter(AreatifFilePath, tifFileNamePrefix, null);
			for(File file:AreatifFiles)
			{
//				System.out.println(path.getName());
				//copy area tif file to gp models for change bands
				//E:\areatif\2018-05-11_72.tif
				//-->
				//E:\gpmodels\arearaster.tif
				String resultFileName = file.getName();
				///example 2018-05-11_72.tif-->.tif
				String resultFileSuffix = resultFileName.substring(resultFileName.lastIndexOf('.'));
				///arearaster+.tif--->arearaster.tif
				String copyTo = models_workspace+models_workspace_tempAreaTifFilePreName+resultFileSuffix;
				File newfile = new File(copyTo);
//				if(newfile.exists())
//				{
//					boolean del_res = newfile.delete();
//					if(!del_res)
//					{
//						System.gc();
//						newfile.delete();
//						LOG.debug(newfile.getAbsolutePath()+"---delete----success");
//					}
//				}
				boolean result = ReportUtil.fileCopy(file,newfile);
				if(result)
				{
					msg.put("flag", result);
					renderJson(msg);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void copyResult2Modelspace()
	{
		String gpEdit = getPara("gpEdit");
		String gpJobID = getPara("gpJobID");
		String gpResultShpfilePath = null;
		boolean flag = false;
		
		JSONObject msg = new JSONObject();
		try {
			if(gpEdit.equals("add"))
			{
				gpResultShpfilePath = merge_gpserver_workspace+gpJobID+"/"+"scratch/";
			}
			else {
				gpResultShpfilePath = erase_gpserver_workspace+gpJobID+"/"+"scratch/";
			}
			File gpResultShpfileDir = new File(gpResultShpfilePath);
			if(gpResultShpfileDir.isDirectory())
			{
				File[] resultFiles = gpResultShpfileDir.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						if(name.lastIndexOf('.')>0)
						{
							int lastIndex = name.lastIndexOf('.');
							String file_preffix = name.substring(0,lastIndex);
							if(file_preffix.equals(gpresultFilePreName))//result.dbf、shp、prj、shx 等 返回所有前缀为result的文件
							{
								return true;
							}
						}
						return false;
					}
					
				});
				if(resultFiles.length>0)
				{
//					flag  = true;
					
					for(File resultFile:resultFiles)
					{
						String resultFileName = resultFile.getName();
						///example result.shp-->.shp
						String resultFileSuffix = resultFileName.substring(resultFileName.lastIndexOf('.'));
						///waitingformodify+.shp--->waitingformodify.shp
						String copyTo = models_workspace+models_workspace_tempFilePreName+resultFileSuffix;
						
						File newfile = new File(copyTo);
						flag = ReportUtil.fileCopy(resultFile,newfile);
					}
				}
				
			}
			else {
				flag = false;
			}
			msg.put("flag", flag);
			renderJson(msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Clear
	public void getAreaGeoJsonFromShpFile()
	{
		String fileName = getPara("shpFileName");
		Map<String,String> map = lyfGis.shape2Geojson(fileName);
		LOG.debug(map.get("message"));
		renderJson(map);
	}
	
	public void generateShpfileByGeoJson()
	{
		Map<String,String> map = new HashMap<String,String>();  
		try {
			String drawSample = getPara("drawSample");
			if(drawSample.equals("true"))//保存样本
			{
				String tifFileName = getPara("tifFileName");
				String geoJsonStr = getPara("geoJsonStr");
				String fileName = getPara("fileName");
				//filePath = E:/areaSampleAndTempProduct/2018-05-11_72/sample/
				String filePath = areaSampleAndTempProductPath+tifFileName.substring(0, tifFileName.lastIndexOf("."))+"/sample/";
				map = lyfGis.geojson2Shape(geoJsonStr, fileName,filePath);
				//如果生成成功 将样本信息保存进数据库
				if(map.get("status").equals("success"))
				{
					T3user u = getSessionAttr("admin");
					BigInteger userID = u.getBigInteger("id");//用户ID
					
					T7pdt_data item = T7pdt_data.dao.findFirst("select * from t7pdt_data where source_file_list=?", tifFileName);
					BigInteger identifier = item.getBigInteger("id");//源文件编号
					
					String name_ = fileName.substring(0, fileName.lastIndexOf("."));//样本名称
					String path_ = filePath+fileName;//样本路径
					String type_content = fileName.substring(0, fileName.lastIndexOf("."));//样本类型内容
					
					Date day = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String datetime_ = sf.format(day);//样本时间
					
					
					T9sample_info t9sample_info = getModel(T9sample_info.class);
					
					t9sample_info.setUserid(userID);
					t9sample_info.setIdentifier(identifier);
					t9sample_info.setName_(name_);
					t9sample_info.setPath_(path_);
					t9sample_info.setType_content(type_content);
					t9sample_info.setDatetime_(Timestamp.valueOf(datetime_));
					
					boolean result = t9sample_info.saveGenIntId();
					if(result)
					{
						renderJson(map);
					}
					else {
						map.put("status", "failure");
						renderJson(map);
					}
				}
				else {
					renderJson(map);
				}
				
			}
			else {//保存add.shp
				
			}
		}catch(Exception e) {
			map.put("status", "failure");  
            map.put("message", e.getMessage());  
			e.printStackTrace();
			renderJson(map);
		}
		
	
	}
	public void generateTempProduct()
	{
		JSONObject msg = new JSONObject();
		try{
			String tifFileName = getPara("tifFileName");
			T7pdt_data item = T7pdt_data.dao.findFirst("select * from t7pdt_data where source_file_list=?", tifFileName);
			BigInteger identifier = item.getBigInteger("id");//源文件编号
			//根据文件编号 获取和此文件编号关联的所有样本信息
			List<T9sample_info> t9sample_infos = T9sample_info.dao.find("select path_,type_content from t9sample_info where identifier=? group by type_content", identifier);
			for(T9sample_info t9sample_info:t9sample_infos)
			{
				String type_content = t9sample_info.getType_content();//样本类型内容
				String path_ = t9sample_info.getPath_();//样本路径
			}
			/*to do sth
			 * 
			*/
			//after done
			
			//filePath = E:/areaSampleAndTempProduct/2018-05-11_72/tempProduct/2018-05-11_72_temp.shp
			//filename = 2018-05-11_72_temp.shp
			String tifFileNamePrefix = tifFileName.substring(0, tifFileName.lastIndexOf("."));//2018-05-11_72
			String tempProductPath = areaSampleAndTempProductPath+tifFileNamePrefix+"/tempProduct/";
			String tempProductFileName = tifFileNamePrefix+"_temp.shp";
			String tempProductFileNamePrefix = tifFileNamePrefix+"_temp";
			
			File[] tempProductFiles = ReportUtil.fileFilter(tempProductPath, tempProductFileNamePrefix, null);
			for(File file:tempProductFiles)
			{
//				System.out.println(path.getName());
				//copy temp product file to arcgis server workspace to show on map
				//E:\areaSampleAndTempProduct\2018-05-11_72\tempProduct\2018-05-11_72_temp.shp
				//-->
				//E:\arcgisserver_shp_workspace\Area\2018-05-11_72_temp.shp
				String copyTo = arcgisserver_shp_workspacePath+"Area/"+file.getName();
				File newfile = new File(copyTo);
				ReportUtil.fileCopy(file,newfile);
				
				//copy temp product file to gpmodels workspace to prepare edit
				//E:\areaSampleAndTempProduct\2018-05-11_72\tempProduct\2018-05-11_72_temp.shp
				//-->
				//E:\gpmodels\waitingformodify.shp
				String resultFileName = file.getName();
				///example result.shp-->.shp
				String resultFileSuffix = resultFileName.substring(resultFileName.lastIndexOf('.'));
				///waitingformodify+.shp--->waitingformodify.shp
				String copyTo1 = models_workspace+models_workspace_tempFilePreName+resultFileSuffix;
				File newfile1 = new File(copyTo1);
				ReportUtil.fileCopy(file,newfile1);
				
			}
			
			msg.put("status", "success");
			msg.put("filename", tempProductFileName);
		}catch(Exception e)
		{
			e.printStackTrace();
			msg.put("status", "failure");
		}
		renderJson(msg);
		
	}
	public void SaveAreaEditedProduct()
	{
		boolean flag = false;
		JSONObject msg =  new JSONObject();
		try {
			//fileinfo = 2018-05-11_72
			String fileinfo = getPara("fileinfo");
			String fileDate = fileinfo.split("_")[0];//2018-05-11
			String fileDistrict = fileinfo.split("_")[1];//72
			String fileSavePath = thairiceproduct_path+"Area/"+fileDate+"/";
			File parentFile = new File(fileSavePath);
			if(!parentFile.exists())
			{
				parentFile.mkdirs();
			}
			
			File[] tempProductFiles = ReportUtil.fileFilter(models_workspace, models_workspace_tempFilePreName, null);
			for(File file:tempProductFiles)
			{
				//E:\gpmodels\waitingformodify.shp
				//-->
				//E:\thairiceproduct\Area\2018-05-05\72.shp
				String tempFileName = file.getName();
				///example waitingformodify.shp-->.shp
				String tempFileNameSuffix = tempFileName.substring(tempFileName.lastIndexOf('.'));
				///waitingformodify+.shp--->waitingformodify.shp
				String copyTo = fileSavePath+fileDistrict+tempFileNameSuffix;
				File newfile = new File(copyTo);
				ReportUtil.fileCopy(file,newfile);
			}
			
			//save result info to database
			String source_file = fileinfo+".tif";//2018-05-05_72.tif
			String product_path = fileSavePath+fileDistrict+".shp";//E:\thairiceproduct\Area\2018-05-05\72.shp
			
			Date day = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String generate_time = sf.format(day);//样本时间
			
			int res = Db.update("update thairice.t7pdt_data set product_path=?,generate_time=? where source_file_list=?",product_path,Timestamp.valueOf(generate_time),source_file);
            if(res>0)
            {
            	flag = true; 
            }
			
			msg.put("flag", flag);
			renderJson(msg);
		}catch(Exception e)
		{
			e.printStackTrace();
			flag = false;
			msg.put("flag", flag);
			renderJson(msg);
		}
		
	}
	
}
