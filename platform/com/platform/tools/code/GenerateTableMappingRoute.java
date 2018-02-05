/**
 * <p>title:GenerateTableMappingRoute.java<／p>
 * <p>Description: <／p>
 * @date:2016年3月12日下午3:49:38
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package com.platform.tools.code;
/**
 * 创建时间：2016年3月12日 下午3:49:38
 * 项目名称：DUCPlatFromWeb
 * 文件类型：GenerateTableMappingRoute.java
 * 类说明：
 *
 *  
 *修改日志：
 * Date			Author		Version		Description
 *---------------------------------------------------
 *2016年3月12日		Zhongweng	1.0			1.0Version
 */

/**
 * <p>Title: GenerateTableMappingRoute<／p>
 * <p>Description: <／p>
 * @author ZhongwengHao
 * @date 2016年3月12日
 */
public class GenerateTableMappingRoute {

	private String db_source;
	private String tablename;
	private String keyString;//key1,key2
	private String mdlname;
	private String tableDes;
	
	public GenerateTableMappingRoute(){
		
	}
	public GenerateTableMappingRoute(String adbsString){
		db_source = adbsString;
	} 
	public GenerateTableMappingRoute(
			String adbsString
			,String atablename
			,String akeyString
			,String amdlname
			,String atableDes){
		this.db_source = adbsString;
		this.tablename = atablename;
		this.keyString = akeyString;
		this.mdlname = amdlname;
		this.tableDes = atableDes;
	}
	/**
	 * @return the db_source
	 */
	public String getDb_source() {
		return db_source;
	}
	/**
	 * @param db_source the db_source to set
	 */
	public void setDb_source(String db_source) {
		this.db_source = db_source;
	}
	/**
	 * @return the tablename
	 */
	public String getTablename() {
		return tablename;
	}
	/**
	 * @param tablename the tablename to set
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	/**
	 * @return the keyString
	 */
	public String getKeyString() {
		return keyString;
	}
	/**
	 * @param keyString the keyString to set
	 */
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	/**
	 * @return the mdlname
	 */
	public String getMdlname() {
		return mdlname;
	}
	/**
	 * @param mdlname the mdlname to set
	 */
	public void setMdlname(String mdlname) {
		this.mdlname = mdlname;
	}
	/**
	 * @return the tableDes
	 */
	public String getTableDes() {
		return tableDes;
	}
	/**
	 * @param tableDes the tableDes to set
	 */
	public void setTableDes(String tableDes) {
		this.tableDes = tableDes;
	} 
	
}
