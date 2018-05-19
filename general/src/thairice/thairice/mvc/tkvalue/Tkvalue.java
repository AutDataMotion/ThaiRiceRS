package thairice.mvc.tkvalue;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp; 
import java.math.BigInteger; 

import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "tkvalue")
public class Tkvalue extends BaseModel<Tkvalue> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(Tkvalue.class);
	
	public static final Tkvalue dao = new Tkvalue();
	
	/**
	 * 字段描述： 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：key 
	 * 字段类型：varchar  长度：64
	 */
	public static final String column_key_ = "key_";
	
	/**
	 * 字段描述：名称 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_value_ = "value_";
	
	/**
	 * 字段描述：备注 
	 * 字段类型：varchar  长度：128
	 */
	public static final String column_info = "info";
	
	/**
	 * 字段描述：状态 （0未/ 1已发布,2已结束） 
	 * 字段类型：int  长度：null
	 */
	public static final String column_status_ = "status_";
	
	/**
	 * 字段描述：添加时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_add_time = "add_time";
	
	
	/**
	 * sqlId : thairice.tkvalue.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.tkvalue.splitPageFrom";

	private BigInteger id;
	private String key_;
	private String value_;
	private String info;
	private Integer status_;
	private Timestamp add_time;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setKey_(String key_){
		set(column_key_, key_);
	}
	public <T> T getKey_() {
		return get(column_key_);
	}
	public void setValue_(String value_){
		set(column_value_, value_);
	}
	public <T> T getValue_() {
		return get(column_value_);
	}
	public void setInfo(String info){
		set(column_info, info);
	}
	public <T> T getInfo() {
		return get(column_info);
	}
	public void setStatus_(Integer status_){
		set(column_status_, status_);
	}
	public <T> T getStatus_() {
		return get(column_status_);
	}
	public void setAdd_time(Timestamp add_time){
		set(column_add_time, add_time);
	}
	public <T> T getAdd_time() {
		return get(column_add_time);
	}
	
}
