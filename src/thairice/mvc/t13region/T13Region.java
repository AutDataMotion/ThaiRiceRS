package thairice.mvc.t13region;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;


import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t13region")
public class T13Region extends BaseModel<T13Region> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T13Region.class);
	
	public static final T13Region dao = new T13Region();
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_Id = "Id";
	
	/**
	 * 字段描述： 
	 * 字段类型：varchar  长度：200
	 */
	public static final String column_name = "name";
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_parentId = "parentId";
	
	
	/**
	 * sqlId : thairice.t13Region.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t13Region.splitPageFrom";

	private Integer Id;
	private String name;
	private Integer parentId;

	public void setId(Integer Id){
		set(column_Id, Id);
	}
	public <T> T getId() {
		return get(column_Id);
	}
	public void setName(String name){
		set(column_name, name);
	}
	public <T> T getName() {
		return get(column_name);
	}
	public void setParentId(Integer parentId){
		set(column_parentId, parentId);
	}
	public <T> T getParentId() {
		return get(column_parentId);
	}
	
}
