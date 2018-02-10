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
		{null, "t1parameter", null, "T1parameter","参数"},
		{null, "t2syslog", null, "T2syslog","日志"},
		{null, "t3user", null, "T3user","用户"},
		{null, "r4message_send", null, "R4message_send","消息发送"},
		{null, "t5parameter_type", null, "T5parameter_type","参数类型"},
		{null, "t6org_data", null, "T6org_data","原始数据信息"},
		{null, "t7pdt_data", null, "T7pdt_data","产品数据信息"},
		{null, "t8message", null, "T8message","消息"},
		{null, "t9sample_info", null, "T9sample_info","样本数据信息"},
		{null, "t10pdt_report", null, "T10pdt_report","产品报告"},
		{null, "t11zone", null, "T11zone","行政区"},
		{null, "tkvalue", null, "Tkvalue","键值"}
	};
	
	//String prjName = "targrecog";
	protected static final String schema = "thairice";
	public static String  prjName = "thairice";
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
	public static String packageConstant = prjName+".constant";
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
	public void modelcpp(String className, String classNameSmall, String dataSource, String tableName, String pkName, List<TableColumnDto> colunmList){
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
		
		String filePath = System.getProperty("user.dir") +generalPathSrc+srcFolder+"/" + packages.replace(".", "/") + "/../../cpp/" + className +".h";
		createFileByTemplete("modelCPP.html", paraMap, filePath);
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

	public void configGen(String basePath,List<GenerateTableMappingRoute> listTableMap){
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		System.out.println("tableMapping listTableMap size:"+listTableMap.size());
		paraMap.put("basePath", basePath);
		paraMap.put("listTableMap", listTableMap);
		paraMap.put("prjName", prjName);
		String filePathPackage = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packageConf.replace(".", "/");
		
		String filePath = filePathPackage + "/MappingTable.java";
		createFileByTemplete("MappingTable.html", paraMap, filePath);
		
		filePath = filePathPackage + "/RoutePlugins.java";
		createFileByTemplete("RoutePlugins.html", paraMap, filePath);
		
		filePath = filePathPackage + "/ConfMain.java";
		createFileByTemplete("ConfMain.html", paraMap, filePath);
		
		filePath = filePathPackage + "/DBMappingMy.java";
		createFileByTemplete("DBMappingMy.html", paraMap, filePath);
		
	}
	
//	public void routePlugin(String basePath,List<GenerateTableMappingRoute> listTableMap){
//		Map<String, Object> paraMap = new HashMap<String, Object>();
//		System.out.println("routePlugin listTableMap size:"+listTableMap.size());
//		paraMap.put("basePath", basePath);
//		paraMap.put("listTableMap", listTableMap);
//		
//		String filePath = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packageConf.replace(".", "/") + "/RoutePlugins.java";
//		createFileByTemplete("RoutePlugins.html", paraMap, filePath);
//	}

	public void constantGen(String basePath,List<GenerateTableMappingRoute> listTableMap){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("basePath", basePath);
		paraMap.put("prjName", prjName);
		String filePathPackage = System.getProperty("user.dir") + generalPathSrc+srcFolder+"/" + packageConstant.replace(".", "/");
		String filePath =  filePathPackage + "/ConstantInitMy.java";
		createFileByTemplete("ConstantInitMy.html", paraMap, filePath);
		filePath = filePathPackage + "/PropertiesInitMy.java";
		createFileByTemplete("PropertiesInitMy.html", paraMap, filePath);
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

