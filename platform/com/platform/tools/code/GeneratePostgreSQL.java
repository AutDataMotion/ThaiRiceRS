package com.platform.tools.code;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
import com.platform.config.run.ConfigCore;
import com.platform.constant.ConstantInit;
import com.platform.tools.ToolSqlXml;
import com.platform.tools.ToolString;

/**
 * 创建时间：2016年3月8日 下午14:19:18
 * 项目名称：GeneratePostgreSQL
 * 文件类型：GeneratePostgreSQL.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年3月12日		Zhongweng	1.0			1.0Version
 */

/**
 * <p>Title: GeneratePostgreSQL<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年3月12日
 */
public class GeneratePostgreSQL extends GenerateBase {

	private static Logger log = Logger.getLogger(GeneratePostgreSQL.class);

	/**
	 * 循环生成文件
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		log.info("启动ConfigCore start ......");
		new ConfigCore();
		log.info("启动ConfigCore end ......");
		List<GenerateTableMappingRoute> listTableMap = new LinkedList<GenerateTableMappingRoute>();

		GenerateTableMappingRoute mdlTable = null;
		GenerateBase base = new GeneratePostgreSQL();
		for (int i = 0; i < tableArr.length; i++) {
			mdlTable = new GenerateTableMappingRoute(tableArr[i][0],
					tableArr[i][1], tableArr[i][2], tableArr[i][3],
					tableArr[i][4]);
			
			listTableMap.add(mdlTable);
			// 数据源名称
			String dataSource = tableArr[i][0];
			// 表名
			String tableName = tableArr[i][1];
			// 主键
			String pkName = tableArr[i][2];
			// 类名
			String className = tableArr[i][3];

			// 页面显示名称
			String pageTitle = tableArr[i][4];
			// 类名首字母小写
			String classNameSmall = ToolString.toLowerCaseFirstOne(className);

			List<TableColumnDto> colunmList = base.getColunm(tableName);

			if (null == colunmList) {
				System.out.println(tableName + "表  字段为空或者缺少注释，处理 终止，请检查该表！");
				continue;
			}

			// 1.生成sql文件
//			base.sql(classNameSmall, tableName);

			// 2.生成model
			base.modelcpp(className, classNameSmall, dataSource, tableName,
					pkName, colunmList);

			// 3.生成validator
//			base.validator(className, classNameSmall);

			// 4.生成controller
//			base.controller(className, classNameSmall, tableName);

			// 5.生成service
//			base.service(className, classNameSmall);

			// 6.生成DTO，还没有处理数据库字段类型到java数据类型的对应转换
			// base.dto(className, classNameSmall, dataSource, tableName,
			// colunmList);

			// 7.生成视图文件
//			base.form(classNameSmall, tableName, colunmList);
//			base.list(classNameSmall, tableName, pageTitle, colunmList);
//			base.view(classNameSmall, tableName, pageTitle, colunmList);
//			base.update(classNameSmall, tableName, pageTitle, colunmList);
//			base.add(classNameSmall, tableName, pageTitle, colunmList);
		}

		// 生成config 类文件
//		base.configGen(basePath, listTableMap);
		// 生成constant 类文件
//		base.constantGen(basePath, listTableMap);
		System.exit(0);
	}

	@Override
	public List<TableColumnDto> getColunm(String tableName) {
		//注意：注释，如果表有注释，则第一个注释是表的注释，之后才是字段的注释
		//现在的算法是适应于 表不加注释的情况
		List<TableColumnDto> list = new ArrayList<TableColumnDto>();

		List<Record> listDesc = Db.use(ConstantInit.db_dataSource_main).find(
				ToolSqlXml.getSql("platform.postgresql.getColumnsInfo"),
				tableName);
		int index = 0;

		Map<String, String> columnJavaTypeMap = getJavaType(tableName);

		List<Record> listColumn = Db.use(ConstantInit.db_dataSource_main).find(
				ToolSqlXml.getSql("platform.postgresql.getColumns"), tableName);

		if (listDesc == null){
			System.out.println(tableName+"listDesc == null");
			return null;
		}
		if (listColumn == null){
			System.out.println(tableName+"listColumn == null");
			return null;
		}
		for (Record record : listColumn) {
			System.out.println(record.getStr("column_name"));
		}
		System.out.println("listColumn----------------"+listColumn.size());
		for (Record des : listDesc) {
			System.out.println(des.getStr("description"));
		}
		System.out.println("listDesc----------------"+listDesc.size());
		if (listDesc.size() != listColumn.size()) {
			System.out.println(tableName
					+ " -- 有字段没有注释，getColunm退出，请确保所有字段都有注释！");
			return null;
		}

		for (Record record : listColumn) {
			String column_name = record.getStr("column_name");
			String data_type = record.getStr("data_type");
			String character_maximum_length = String.valueOf(record
					.getInt("character_maximum_length"));

			// 需要跳过的字段
			if ("xxx".equals(column_name) || "yyy".equals(column_name)
					|| "zzz".equals(column_name)) {
				index += 1;
				continue;
			}

			TableColumnDto table = new TableColumnDto();
			table.setTable_name(tableName);
			table.setTable_desc(listDesc.get(0).getStr("description"));

			table.setColumn_name(column_name);
			table.setColumn_name_upperCaseFirstOne(ToolString
					.toUpperCaseFirstOne(column_name));

			System.out.println(data_type);
			table.setColumn_type(data_type);
			table.setClm_type(data_type);
			table.setColumn_length(character_maximum_length);

			// System.out.println("--index:"+index+"--des:"+listDesc.get(index).getStr("description"));
			table.setColumn_desc(listDesc.get(index).getStr("description"));

			table.setColumn_className(columnJavaTypeMap.get(column_name
					.toLowerCase()));

			list.add(table);

			index += 1;
		}

		return list;
	}

	public Map<String, String> getJavaType(String tableName) {
		// 获取字段数
		Map<String, String> columnJavaTypeMap = new HashMap<String, String>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DbKit.getConfig().getConnection();
			stmt = conn.createStatement();
			String sql = "select * from " + tableName + " where 1 != 1 ";
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				// 获取字段名
				String columnName = rsmd.getColumnName(i).toLowerCase();
				String columnClassName = rsmd.getColumnClassName(i);
				if (columnClassName.equals("[B")) {
					columnClassName = "byte[]";
				}
				columnJavaTypeMap.put(columnName, columnClassName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return columnJavaTypeMap;
	}

}
