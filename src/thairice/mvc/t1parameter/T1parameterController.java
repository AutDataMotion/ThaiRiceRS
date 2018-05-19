package thairice.mvc.t1parameter;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.platform.constant.ConstantRender;
import com.platform.mvc.base.BaseController;

import csuduc.platform.util.ReportUtil;
import csuduc.platform.util.lyf.lyfGis;
import thairice.entity.ResultEntity;
import thairice.interceptor.AdminLoginInterceptor;
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
	public static final String models_workspace = "E:/arcgisData/models/";
	//将gp服务执行的生成结果result.*（shp、dbf等）copyTo models_workspace里边
	//并将文件以waitingformodify.*（shp、dbf等）命名，做下次使用
	public static final String gpresultFilePreName = "result";
	public static final String models_workspace_tempFilePreName = "waitingformodify";
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
		renderWithPath("/adm2018/production_configuration.html");
	}
	
	/**
	 * 列表
	 */
	public void queryAllParm() {
		Page page = T1parameter.dao.paginate(getParaToInt(0, 1), 1000, "select *", "from t1parameter order by id asc");
		setAttr("blogPage", page);
		renderWithPath("/adm2018/production_configuration.html");
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
		renderWithPath(pthv+"add.html");
	}
	
	/**
	 * 准备更新
	 */
	public void edit() {
		//T1parameter t1parameter = T1parameter.dao.findById(getPara());	//guuid
		T1parameter t1parameter = T1parameterService.service.SelectById(getParaToInt());		//serial int id
		setAttr("t1parameter", t1parameter);
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
						JSONObject file = new JSONObject();
						file.put("name", tifFile.getName());
						file.put("date", tifFile.getName().split("_")[0]);
						String temp = tifFile.getName().split("_")[1];
						file.put("district", temp.substring(0, temp.lastIndexOf('.')));
						files.add(file);
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
		
		String geoJsonStr = getPara("geoJsonStr");
		String fileName = getPara("fileName");
		String filePath = getPara("filePath");
		Map<String,String> map = lyfGis.geojson2Shape(geoJsonStr, fileName,filePath);
		renderJson(map);
	}
	
}
