package com.platform.tools.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beetl.core.BeetlKit;

/**
 * 创建时间：2016年3月2日 下午13:49:38
 * 项目名称：GenerateBase
 * 文件类型：GenerateBase.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年3月12日		Zhongweng	1.0			1.0Version
 */

/**
 * <p>Title: GenerateBase<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年3月12日
 */
public abstract class GenerateBase {

	/**
	 * 二维数组说明：
	 * 
	 * 数据源（默认可以是null）、
	 * 表名、
	 * 主键名（默认可以是null）、
	 * 类名（不包含后缀.java）
	 * 该表对应的视图的显示名称
	 */
	public static String[][] tableArr = {
		//{"ConstantInit.db_dataSource_main", "test_blog", "ids", "TestBlog"}
		//{null, "test_blog", null, "TestBlog"}
//		{null, "r10_user_evaluate_img", null, "R10_user_evaluate_img","用户融合评价"},
//		{null, "r1_user_regist_sys", null, "R1_user_regist_sys","用户注册系统"},
//		{null, "r2_user_select_appgoal", null, "R2_user_select_appgoal","用户选择应用类型"},
//		{null, "r3_1appdatafusioneva", null, "R3_1appdatafusioneva","应用决策关系"},
//		{null, "r3_2appdecirule", null, "R3_2appdecirule","决策知识规则"},
//		{null, "r3_3appdecitrain", null, "R3_3appdecitrain","应用决策关系训练"},
//		{null, "r4_wise_rcmd_imgdata", null, "R4_wise_rcmd_imgdata","推荐数据"},
//		{null, "r5_user_search_imgdata", null, "R5_user_search_imgdata","检索数据"},
//		{null, "r6_user_select_imgdata", null, "R6_user_select_imgdata","筛选数据"},
//		{null, "r7_wise_rcmd_algfuse", null, "R7_wise_rcmd_algfuse","推荐融合算法"},
//		{null, "r8_user_upload_imgdata", null, "R8_user_upload_imgdata","用户上传数据"},
//		{null, "r9_user_fusion_img", null, "R9_user_fusion_img","图像融合"},
//		{null, "t10userfuseimgbill", null, "T10userfuseimgbill","用户融合订单"},
//		{null, "t1system", null, "T1system","系统"},
//		{null, "t2user", null, "T2user","用户"},
//		{null, "t3admin", null, "T3admin","管理员"},
//		{null, "t4_appgoal", null, "T4_appgoal","应用目的"},
//		{null, "t5_datatype", null, "T5_datatype","数据产品类型"},
//		{null, "t6_dataproduct_optical", null, "T6_dataproduct_optical","光学数据产品"},
//		{null, "t7_dataproduct_sar", null, "T7_dataproduct_sar","sar数据产品"},
//		{null, "t8_algfusion", null, "T8_algfusion","融合算法"},
//		{null, "t9_algevaluate", null, "T9_algevaluate","评价算法"},
//		{null, "tcms_articles", null, "Tcms_articles","新闻资讯"},
//		{null, "tcms_kvalue", null, "Tcms_kvalue","系统配置"},
//		{null, "tcms_resource", null, "Tcms_resource","资源"},
//		{null, "tcms_tag", null, "Tcms_tag","标签"}
//		{null, "tapiserv", null, "Tapiserv","API接口"}
//		{null, "tplugin", null, "Tplugin","插件"}
//		{null, "tcms_regist_agent", null, "Tcms_regist_agent","中介方入驻"},
//		{null, "tcms_regist_money", null, "Tcms_regist_money","资金方入驻"},
//		{null, "tcms_regist_prj", null, "Tcms_regist_prj","项目方入驻"},
//		{null, "tcms_user_keyinvite", null, "Tcms_user_keyinvite","邀请码生成"}
//		{null, "listblack", null, "Listblack","失信黑榜"},
//		{null, "listinvestor", null, "Listinvestor","每周资方"},
//		{null, "listprj", null, "Listprj","每周项目"}
		{null, "r2_user_look_img", null, "R2_user_look_img","用户查看图片"},
		{null, "r3_user_search_word", null, "R3_user_search_word","用户文字搜索"},
		{null, "r4_user_upload_img", null, "R4_user_upload_img","用户上传图片"},
		{null, "r5_user_search_img", null, "R5_user_search_img","用户图片搜索"},
		{null, "t3targetinfo", null, "T3targetinfo","目标信息"},
		{null, "t4pic", null, "T4pic","目标普通图像"},
		{null, "t4_1piccap", null, "T4_1piccap","目标普通剪切图像"},
		{null, "t5remotecap", null, "T5remotecap","目标遥感影像"},
		{null, "t6remoteimg", null, "T6remoteimg","原始遥感影像"},
		{null, "t7dictionary", null, "T7dictionary","特征库"}
	};
	
	//String prjName = "targrecog";
	public static String  prjName = "targrecog";
	/**
	 * 生成的包和类所在的源码根目录，比如src或者是weiXin
	 */
	public static String srcFolder = prjName;

	/**
	 * 生成的文件存放的包，公共基础包
	 * 描述：比如
	 * 	platform所在的包就是com.platform
	 * 	weixin所在的包就是com.weixin
	 */
	public static String packageBase = prjName+".mvc";
	public static String packageConf = prjName+".config";
	/**
	 * controller基础路径，例如
	 * @Controller(controllerKey = "/jf/platform/authImg") 中的platform
	 * @Controller(controllerKey = "/jf/wx/authImg") 中的wx
	 * 
	 * render基础路径，例如
	 * /platform/user/add.jsp 中的 platform
	 * /weiXin/user/list.jsp 中的 weiXin
	 */
	public static String basePath = prjName;
	
	public static String generalPathSrc = "/general/src/";
	public static String generalPathView = "/general/view/";

	/**
	 * 获取表的所有字段信息
	 * @param tableName
	 * @return
	 */
	public abstract List<TableColumnDto> getColunm(String tableName) ;
	
	/**
	 * 获取所有数据类型
	 * @param tableName
	 * @return
	 */
	public abstract Map<String, String> getJavaType(String tableName);

	/**
	 * 获取所有数据类型
	 * @param tableName
	 * @return
	 */
	public Set<String> getJataTypeList(String tableName){
		Map<String, String> map = getJavaType(tableName);
		Set<String> keys = map.keySet();
		Set<String> typeSet = new HashSet<String>();
		for (String key : keys) {
			String type = map.get(key);
			if(type.equals("byte[]") || type.startsWith("java.lang.")){
				continue;
			}
			typeSet.add(map.get(key));
		}
		return typeSet;
	}
	
	/**
	 * 生成Model
	 * @param className
	 * @param classNameSmall
	 * @param dataSource
	 * @param tableName
	 * @param pkName
	 */
	public void model(String className, String classNameSmall, String dataSource, String tableName, String pkName, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String packages = packageBase + "." + className.toLowerCase();
		paraMap.put("package", packages);
		paraMap.put("className", className);
		paraMap.put("dataSource", dataSource);
		paraMap.put("tableName", tableName);
		paraMap.put("pkName", pkName);
		paraMap.put("namespace", basePath + "." + classNameSmall);

		paraMap.put("colunmList", colunmList);
		paraMap.put("dataTypes", getJataTypeList(tableName));
		
		String filePath = System.getProperty("user.dir") +generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/" + className +".java";
		createFileByTemplete("model.html", paraMap, filePath);
	}

	/**
	 * 生成DTO                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	 * @param base
	 * @param className
	 * @param classNameSmall
	 * @param dataSource
	 * @param tableName
	 */
	public void dto(String className, String classNameSmall, String dataSource, String tableName, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String packages = packageBase + "." + className.toLowerCase();
		paraMap.put("package", packages);
		paraMap.put("className", className);
		paraMap.put("dataSource", dataSource);
		paraMap.put("tableName", tableName);

		paraMap.put("colunmList", colunmList);
		paraMap.put("dataTypes", getJataTypeList(tableName));
		
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/" + className +"Dto.java";
		createFileByTemplete("dto.html", paraMap, filePath);
	}

	/**
	 * 生成.sql.xml
	 * @param classNameSmall
	 * @param tableName
	 */
	public void sql(String classNameSmall, String tableName){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String packages = packageBase + "." + classNameSmall.toLowerCase();
		paraMap.put("namespace", basePath + "." + classNameSmall);
		paraMap.put("tableName", tableName);
		
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/" + classNameSmall + ".sql.xml";
		createFileByTemplete("sql.html", paraMap, filePath);
	}

	/**
	 * 生成Controller
	 * @param className
	 * @param classNameSmall
	 */
	public void controller(String className, String classNameSmall, String tableName){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String packages = packageBase + "." + classNameSmall.toLowerCase();
		paraMap.put("package", packages);
		paraMap.put("className", className);
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("basePath", basePath);
		paraMap.put("tableName", tableName);
		paraMap.put("prjName", prjName);
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/" + className + "Controller.java";
		createFileByTemplete("controller.html", paraMap, filePath);
	}

	/**
	 * 生成validator
	 * @param className
	 * @param classNameSmall
	 */
	public void validator(String className, String classNameSmall){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String packages = packageBase + "." + classNameSmall.toLowerCase();
		paraMap.put("package", packages);
		paraMap.put("className", className);
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("basePath", basePath);

		
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/" + className + "Validator.java";
		createFileByTemplete("validator.html", paraMap, filePath);
	}
	
	/**
	 * 生成Service
	 * @param className
	 * @param classNameSmall
	 */
	public void service(String className, String classNameSmall){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String packages = packageBase + "." + classNameSmall.toLowerCase();
		paraMap.put("package", packages);
		paraMap.put("className", className);
		paraMap.put("classNameSmall", classNameSmall);
//		paraMap.put("namespace", srcFolder + "." + classNameSmall);
		
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/" + className + "Service.java";
		createFileByTemplete("service.html", paraMap, filePath);
	}
	
	
	/**
	 * 生成form.html
	 * @param classNameSmall
	 * @param tableName
	 */
	public void form(String classNameSmall, String tableName, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("colunmList", colunmList);
		
		String filePath = System.getProperty("user.dir") + generalPathView + basePath + "/" + classNameSmall +"/form.html";
		createFileByTemplete("form.html", paraMap, filePath);
	}

	/**
	 * 生成view.html
	 * @param classNameSmall
	 * @param tableName
	 */
	public void view(String classNameSmall, String tableName,String pageTitle, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("colunmList", colunmList);
		paraMap.put("pageTitle", pageTitle);
		paraMap.put("basePath", basePath);
		
		String filePath = System.getProperty("user.dir") + generalPathView + basePath + "/" + classNameSmall +"/view.html";
		createFileByTemplete("view.html", paraMap, filePath);
	}
	
	public void list(String classNameSmall, String tableName,String pageTitle, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("colunmList", colunmList);
		paraMap.put("pageTitle", pageTitle);
		paraMap.put("basePath", basePath);
		
		String filePath = System.getProperty("user.dir") + generalPathView + basePath + "/" + classNameSmall +"/list.html";
		createFileByTemplete("list.html", paraMap, filePath);
	}
	
	public void update(String classNameSmall, String tableName,String pageTitle, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("colunmList", colunmList);
		paraMap.put("pageTitle", pageTitle);
		paraMap.put("basePath", basePath);
		
		String filePath = System.getProperty("user.dir") + generalPathView + basePath + "/" + classNameSmall +"/update.html";
		createFileByTemplete("update.html", paraMap, filePath);
	}

	public void add(String classNameSmall, String tableName,String pageTitle, List<TableColumnDto> colunmList){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classNameSmall", classNameSmall);
		paraMap.put("colunmList", colunmList);
		paraMap.put("pageTitle", pageTitle);
		paraMap.put("basePath", basePath);
		
		String filePath = System.getProperty("user.dir") + generalPathView + basePath + "/" + classNameSmall +"/add.html";
		createFileByTemplete("add.html", paraMap, filePath);
	}

	public void tableMapping(String basePath,List<GenerateTableMappingRoute> listTableMap){
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("basePath", basePath);
		System.out.println("tableMapping listTableMap size:"+listTableMap.size());
		paraMap.put("listTableMap", listTableMap);
		
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packageConf.replace(".", "/") + "/MappingTable.java";
		createFileByTemplete("MappingTable.html", paraMap, filePath);
	}
	
	public void routePlugin(String basePath,List<GenerateTableMappingRoute> listTableMap){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		System.out.println("routePlugin listTableMap size:"+listTableMap.size());
		paraMap.put("basePath", basePath);
		paraMap.put("listTableMap", listTableMap);
		
		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packageConf.replace(".", "/") + "/RoutePlugins.java";
		createFileByTemplete("RoutePlugins.html", paraMap, filePath);
	}

	/**
	 * 根据具体模板生成文件
	 * @param templateFileName
	 * @param paraMap
	 * @param filePath
	 */
	public void createFileByTemplete(String templateFileName, Map<String, Object> paraMap, String filePath)  {
		try {
			Class<?> classes = Class.forName("com.platform.tools.code.GenerateBase");
//			File file1 = new File("./"+templateFileName);
//			if (!file1.exists()) {
//				System.out.println("---file not exist:"+templateFileName);
//				return;
//			}
			InputStream controllerInputStream = classes.getResourceAsStream(templateFileName);
			int count = 0;
			while (count == 0) {
				count = controllerInputStream.available();
			}
			
			byte[] bytes = new byte[count];
			int readCount = 0; // 已经成功读取的字节的个数
			while (readCount < count) {
				readCount += controllerInputStream.read(bytes, readCount, count - readCount);
			}
			
			String template = new String(bytes);
			
			String javaSrc = BeetlKit.render(template, paraMap);
			
			File file = new File(filePath);
			
			File path = new File(file.getParent());
			if (!path.exists()) {
				path.mkdirs();
			}
			
			BufferedWriter output = new BufferedWriter(new FileWriter(file));   
			output.write(javaSrc);   
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}

