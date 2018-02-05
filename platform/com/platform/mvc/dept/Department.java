package com.platform.mvc.dept;

import org.apache.log4j.Logger;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

/**
 * 部门model
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Table(tableName = "pt_department")
public class Department extends BaseModel<Department> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(Department.class);
	
	public static final Department dao = new Department();

	/**
	 * 字段描述：主键 
	 * 字段类型 ：character varying 
	 */
	public static final String column_ids = "ids";
	
	/**
	 * 字段描述：版本号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_version = "version";
	
	/**
	 * 字段描述：所有子节点ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_allchildnodeids = "allchildnodeids";
	
	/**
	 * 字段描述：部门级别 
	 * 字段类型 ：bigint 
	 */
	public static final String column_departmentlevel = "departmentlevel";
	
	/**
	 * 字段描述：部门类型 
	 * 字段类型 ：character 
	 */
	public static final String column_depttype = "depttype";
	
	/**
	 * 字段描述：描述 
	 * 字段类型 ：character varying 
	 */
	public static final String column_description = "description";
	
	/**
	 * 字段描述：图标 
	 * 字段类型 ：character varying 
	 */
	public static final String column_images = "images";
	
	/**
	 * 字段描述：是否上级节点 
	 * 字段类型 ：character varying 
	 */
	public static final String column_isparent = "isparent";
	
	/**
	 * 字段描述：名称 
	 * 字段类型 ：character varying 
	 */
	public static final String column_names = "names";
	
	/**
	 * 字段描述：排序号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_orderids = "orderids";
	
	/**
	 * 字段描述：url 
	 * 字段类型 ：character varying 
	 */
	public static final String column_url = "url";
	
	/**
	 * 字段描述：上级ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_parentdepartmentids = "parentdepartmentids";
	
	/**
	 * 字段描述：负责人ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_principaluserids = "principaluserids";
	
	/**
	 * sqlId : platform.department.noChecked
	 * 描述：查询根节点
	 */
	public static final String sqlId_rootNode = "platform.department.rootNode";

	/**
	 * sqlId : platform.department.childNode
	 * 描述：根据上级节点id查询子部门信息 
	 */
	public static final String sqlId_childNode = "platform.department.childNode";

	/**
	 * sqlId : platform.department.childCount
	 * 描述：根据上级节点id查询子节点数量
	 */
	public static final String sqlId_childCount = "platform.department.childCount";

	private String ids;
	private Long version;
	private String allchildnodeids;
	private Long departmentlevel;
	private String depttype;
	private String description;
	private String images;
	private String isparent;
	private String names;
	private Long orderids;
	private String url;
	private String parentdepartmentids;
	private String principaluserids;

	public void setIds(String ids){
		set(column_ids, ids);
	}
	public <T> T getIds() {
		return get(column_ids);
	}
	public void setVersion(Long version){
		set(column_version, version);
	}
	public <T> T getVersion() {
		return get(column_version);
	}
	public void setAllchildnodeids(String allchildnodeids){
		set(column_allchildnodeids, allchildnodeids);
	}
	public <T> T getAllchildnodeids() {
		return get(column_allchildnodeids);
	}
	public void setDepartmentlevel(Long departmentlevel){
		set(column_departmentlevel, departmentlevel);
	}
	public <T> T getDepartmentlevel() {
		return get(column_departmentlevel);
	}
	public void setDepttype(String depttype){
		set(column_depttype, depttype);
	}
	public <T> T getDepttype() {
		return get(column_depttype);
	}
	public void setDescription(String description){
		set(column_description, description);
	}
	public <T> T getDescription() {
		return get(column_description);
	}
	public void setImages(String images){
		set(column_images, images);
	}
	public <T> T getImages() {
		return get(column_images);
	}
	public void setIsparent(String isparent){
		set(column_isparent, isparent);
	}
	public <T> T getIsparent() {
		return get(column_isparent);
	}
	public void setNames(String names){
		set(column_names, names);
	}
	public <T> T getNames() {
		return get(column_names);
	}
	public void setOrderids(Long orderids){
		set(column_orderids, orderids);
	}
	public <T> T getOrderids() {
		return get(column_orderids);
	}
	public void setUrl(String url){
		set(column_url, url);
	}
	public <T> T getUrl() {
		return get(column_url);
	}
	public void setParentdepartmentids(String parentdepartmentids){
		set(column_parentdepartmentids, parentdepartmentids);
	}
	public <T> T getParentdepartmentids() {
		return get(column_parentdepartmentids);
	}
	public void setPrincipaluserids(String principaluserids){
		set(column_principaluserids, principaluserids);
	}
	public <T> T getPrincipaluserids() {
		return get(column_principaluserids);
	}
	
}
