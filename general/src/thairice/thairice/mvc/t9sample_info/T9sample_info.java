package thairice.mvc.t9sample_info;

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
//@Table(tableName = "t9sample_info")
public class T9sample_info extends BaseModel<T9sample_info> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T9sample_info.class);
	
	public static final T9sample_info dao = new T9sample_info();
	
	/**
	 * 字段描述：样本编号 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_userid = "userid";
	
	/**
	 * 字段描述：源文件编号 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_identifier = "identifier";
	
	/**
	 * 字段描述：样本名称 
	 * 字段类型：varchar  长度：128
	 */
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述：样本数量 
	 * 字段类型：varchar  长度：30
	 */
	public static final String column_number_ = "number_";
	
	/**
	 * 字段描述：样本路径 
	 * 字段类型：varchar  长度：30
	 */
	public static final String column_path_ = "path_";
	
	/**
	 * 字段描述：样本类型内容 
	 * 字段类型：varchar  长度：30
	 */
	public static final String column_type_content = "type_content";
	
	/**
	 * 字段描述：样本时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_datetime_ = "datetime_";
	
	
	/**
	 * sqlId : thairice.t9sample_info.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t9sample_info.splitPageFrom";

	private BigInteger id;
	private BigInteger userid;
	private BigInteger identifier;
	private String name_;
	private String number_;
	private String path_;
	private String type_content;
	private Timestamp datetime_;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setUserid(BigInteger userid){
		set(column_userid, userid);
	}
	public <T> T getUserid() {
		return get(column_userid);
	}
	public void setIdentifier(BigInteger identifier){
		set(column_identifier, identifier);
	}
	public <T> T getIdentifier() {
		return get(column_identifier);
	}
	public void setName_(String name_){
		set(column_name_, name_);
	}
	public <T> T getName_() {
		return get(column_name_);
	}
	public void setNumber_(String number_){
		set(column_number_, number_);
	}
	public <T> T getNumber_() {
		return get(column_number_);
	}
	public void setPath_(String path_){
		set(column_path_, path_);
	}
	public <T> T getPath_() {
		return get(column_path_);
	}
	public void setType_content(String type_content){
		set(column_type_content, type_content);
	}
	public <T> T getType_content() {
		return get(column_type_content);
	}
	public void setDatetime_(Timestamp datetime_){
		set(column_datetime_, datetime_);
	}
	public <T> T getDatetime_() {
		return get(column_datetime_);
	}
	
}
