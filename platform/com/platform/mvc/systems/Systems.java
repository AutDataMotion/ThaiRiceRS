package com.platform.mvc.systems;

import org.apache.log4j.Logger;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

/**
 * 系统model
 * @author DHJ
 */
@SuppressWarnings("unused")
//@Table(tableName = "pt_systems")
public class Systems extends BaseModel<Systems> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(Systems.class);
	
	public static final Systems dao = new Systems();

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
	 * 字段描述：名称 
	 * 字段类型 ：character varying 
	 */
	public static final String column_names = "names";
	
	/**
	 * 字段描述：编号 
	 * 字段类型 ：character varying 
	 */
	public static final String column_numbers = "numbers";
	
	/**
	 * 字段描述：排序号 
	 * 字段类型 ：bigint 
	 */
	public static final String column_orderids = "orderids";

	/**
	 * sqlId : platform.systems.all
	 * 描述：
	 */
	public static final String sqlId_all = "platform.systems.all";

	/**
	 * sqlId : platform.systems.splitPageSelect
	 * 描述：分页Select
	 */
	public static final String sqlId_splitPage_select = "platform.systems.splitPageSelect";

	/**
	 * sqlId : platform.systems.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "platform.systems.splitPageFrom";

	private String ids;
	private Long version;
	private String description;
	private String names;
	private String numbers;
	private Long orderids;

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
	public void setNames(String names){
		set(column_names, names);
	}
	public <T> T getNames() {
		return get(column_names);
	}
	public void setNumbers(String numbers){
		set(column_numbers, numbers);
	}
	public <T> T getNumbers() {
		return get(column_numbers);
	}
	public void setOrderids(Long orderids){
		set(column_orderids, orderids);
	}
	public <T> T getOrderids() {
		return get(column_orderids);
	}
	
}
