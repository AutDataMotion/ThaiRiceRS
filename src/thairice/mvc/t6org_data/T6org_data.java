package thairice.mvc.t6org_data;

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
//@Table(tableName = "t6org_data")
public class T6org_data extends BaseModel<T6org_data> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T6org_data.class);
	
	public static final String tableName = "t6org_data";
	
	public static final T6org_data dao = new T6org_data();
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_userid = "userid";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_name_ = "name_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_size_ = "size_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_download_path = "download_path";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_storage_path = "storage_path";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_row_column = "row_column";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_band_number = "band_number";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_status_ = "status_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_fail_code = "fail_code";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_collect_time = "collect_time";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_dload_start_time = "dload_start_time";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_dload_end_time = "dload_end_time";
	
	
	/**
	 * sqlId : thairice.t6org_data.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t6org_data.splitPageFrom";

	private BigInteger id;
	private BigInteger userid;
	private String name_;
	private Float size_;
	private String download_path;
	private String storage_path;
	private String type_;
	private String row_column;
	private String band_number;
	private String status_;
	private String fail_code;
	private String collect_time;
	private Timestamp dload_start_time;
	private Timestamp dload_end_time;

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
	public void setName_(String name_){
		set(column_name_, name_);
	}
	public <T> T getName_() {
		return get(column_name_);
	}
	public void setSize_(Float size_){
		set(column_size_, size_);
	}
	public <T> T getSize_() {
		return get(column_size_);
	}
	public void setDownload_path(String download_path){
		set(column_download_path, download_path);
	}
	public <T> T getDownload_path() {
		return get(column_download_path);
	}
	public void setStorage_path(String storage_path){
		set(column_storage_path, storage_path);
	}
	public <T> T getStorage_path() {
		return get(column_storage_path);
	}
	public void setType_(String type_){
		set(column_type_, type_);
	}
	public <T> T getType_() {
		return get(column_type_);
	}
	public void setRow_column(String row_column){
		set(column_row_column, row_column);
	}
	public <T> T getRow_column() {
		return get(column_row_column);
	}
	public void setBand_number(String band_number){
		set(column_band_number, band_number);
	}
	public <T> T getBand_number() {
		return get(column_band_number);
	}
	public void setStatus_(String status_){
		set(column_status_, status_);
	}
	public <T> T getStatus_() {
		return get(column_status_);
	}
	public void setFail_code(String fail_code){
		set(column_fail_code, fail_code);
	}
	public <T> T getFail_code() {
		return get(column_fail_code);
	}
	public void setCollect_time(String collect_time){
		set(column_collect_time, collect_time);
	}
	public <T> T getCollect_time() {
		return get(column_collect_time);
	}
	public void setDload_start_time(Timestamp dload_start_time){
		set(column_dload_start_time, dload_start_time);
	}
	public <T> T getDload_start_time() {
		return get(column_dload_start_time);
	}
	public void setDload_end_time(Timestamp dload_end_time){
		set(column_dload_end_time, dload_end_time);
	}
	public <T> T getDload_end_time() {
		return get(column_dload_end_time);
	}
	
}
