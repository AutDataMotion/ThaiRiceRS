package thairice.mvc.t11zone;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;


import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t11zone")
public class T11zone extends BaseModel<T11zone> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T11zone.class);
	
	public static final T11zone dao = new T11zone();
	
	/**
	 * 字段描述：行政区划id 
	 * 字段类型：int  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：行政区划代码 
	 * 字段类型：int  长度：null
	 */
	public static final String column_code_ = "code_";
	
	/**
	 * 字段描述：产品类型代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述：行政区划名称 
	 * 字段类型：varchar  长度：30
	 */
	public static final String column_name_ = "name_";
	
	
	/**
	 * sqlId : thairice.t11zone.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t11zone.splitPageFrom";

	private Integer id;
	private Integer code_;
	private String type_;
	private String name_;

	public void setId(Integer id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setCode_(Integer code_){
		set(column_code_, code_);
	}
	public <T> T getCode_() {
		return get(column_code_);
	}
	public void setType_(String type_){
		set(column_type_, type_);
	}
	public <T> T getType_() {
		return get(column_type_);
	}
	public void setName_(String name_){
		set(column_name_, name_);
	}
	public <T> T getName_() {
		return get(column_name_);
	}
	
}
