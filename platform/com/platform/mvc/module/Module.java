package com.platform.mvc.module;

import org.apache.log4j.Logger;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

/**
 * 模块model
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Table(tableName = "pt_module")
public class Module extends BaseModel<Module> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(Module.class);
	
	public static final Module dao = new Module();

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
	 * 字段描述：上级模块ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_parentmoduleids = "parentmoduleids";
	
	/**
	 * 字段描述：所属系统ids 
	 * 字段类型 ：character varying 
	 */
	public static final String column_systemsids = "systemsids";

	/**
	 * sqlId : platform.module.rootBySystemIds
	 * 描述：根节点
	 */
	public static final String sqlId_rootBySystemIds = "platform.module.rootBySystemIds";

	/**
	 * sqlId : platform.module.root
	 * 描述：根节点
	 */
	public static final String sqlId_root = "platform.module.root";

	/**
	 * sqlId : platform.module.child
	 * 描述：子节点
	 */
	public static final String sqlId_child = "platform.module.child";

	/**
	 * sqlId : platform.module.childCount
	 * 描述：子节点数量
	 */
	public static final String sqlId_childCount = "platform.module.childCount";

	private String ids;
	private Long version;
	private String description;
	private String images;
	private String isparent;
	private String names;
	private Long orderids;
	private String parentmoduleids;
	private String systemsids;

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
	public void setParentmoduleids(String parentmoduleids){
		set(column_parentmoduleids, parentmoduleids);
	}
	public <T> T getParentmoduleids() {
		return get(column_parentmoduleids);
	}
	public void setSystemsids(String systemsids){
		set(column_systemsids, systemsids);
	}
	public <T> T getSystemsids() {
		return get(column_systemsids);
	}
	
}
