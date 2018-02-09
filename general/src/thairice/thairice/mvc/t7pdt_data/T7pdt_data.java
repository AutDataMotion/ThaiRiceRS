package thairice.mvc.t7pdt_data;

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
//@Table(tableName = "t7pdt_data")
public class T7pdt_data extends BaseModel<T7pdt_data> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T7pdt_data.class);
	
	public static final T7pdt_data dao = new T7pdt_data();
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_type_ = "type_";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_source_file_list = "source_file_list";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_sample_path = "sample_path";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_product_path = "product_path";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_collect_time = "collect_time";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_generate_time = "generate_time";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_zone_code = "zone_code";
	
	/**
	 * 字段描述： 
	 * 字段类型：  长度：null
	 */
	public static final String column_fail_code = "fail_code";
	
	
	/**
	 * sqlId : thairice.t7pdt_data.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t7pdt_data.splitPageFrom";

	private BigInteger id;
	private String type_;
	private String source_file_list;
	private String sample_path;
	private String product_path;
	private Timestamp collect_time;
	private Timestamp generate_time;
	private String zone_code;
	private String fail_code;

	public void setId(BigInteger id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setType_(String type_){
		set(column_type_, type_);
	}
	public <T> T getType_() {
		return get(column_type_);
	}
	public void setSource_file_list(String source_file_list){
		set(column_source_file_list, source_file_list);
	}
	public <T> T getSource_file_list() {
		return get(column_source_file_list);
	}
	public void setSample_path(String sample_path){
		set(column_sample_path, sample_path);
	}
	public <T> T getSample_path() {
		return get(column_sample_path);
	}
	public void setProduct_path(String product_path){
		set(column_product_path, product_path);
	}
	public <T> T getProduct_path() {
		return get(column_product_path);
	}
	public void setCollect_time(Timestamp collect_time){
		set(column_collect_time, collect_time);
	}
	public <T> T getCollect_time() {
		return get(column_collect_time);
	}
	public void setGenerate_time(Timestamp generate_time){
		set(column_generate_time, generate_time);
	}
	public <T> T getGenerate_time() {
		return get(column_generate_time);
	}
	public void setZone_code(String zone_code){
		set(column_zone_code, zone_code);
	}
	public <T> T getZone_code() {
		return get(column_zone_code);
	}
	public void setFail_code(String fail_code){
		set(column_fail_code, fail_code);
	}
	public <T> T getFail_code() {
		return get(column_fail_code);
	}
	
}
