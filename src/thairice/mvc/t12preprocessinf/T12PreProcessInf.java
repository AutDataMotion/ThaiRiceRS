package thairice.mvc.t12preprocessinf;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;

import java.sql.Timestamp; 

import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t12_pre_process_inf")
public class T12PreProcessInf extends BaseModel<T12PreProcessInf> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T12PreProcessInf.class);
	
	public static final T12PreProcessInf dao = new T12PreProcessInf();
	
	/**
	 * 字段描述：预处理数据编号 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：预处理数据类型 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_data_type = "data_type";
	
	/**
	 * 字段描述：源文件列表 
	 * 字段类型：varchar  长度：3000
	 */
	public static final String column_source_file_list = "source_file_list";
	
	/**
	 * 字段描述：预处理文件路径 
	 * 字段类型：varchar  长度：80
	 */
	public static final String column_file_path = "file_path";
	
	/**
	 * 字段描述：预处理文件名 
	 * 字段类型：varchar  长度：80
	 */
	public static final String column_file_name = "file_name";
	
	/**
	 * 字段描述：数据采集时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_data_collect_time = "data_collect_time";
	
	/**
	 * 字段描述：干旱监测处理状态 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_drought_st = "drought_st";
	
	/**
	 * 字段描述：长势监测处理状态 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_condition_st = "condition_st";
	
	/**
	 * 字段描述：估产处理状态 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_estimate_st = "estimate_st";
	
	/**
	 * 字段描述：预处理数据生成时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_generate_time = "generate_time";
	
	
	/**
	 * sqlId : thairice.t12PreProcessInf.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t12PreProcessInf.splitPageFrom";

	private Long id;
	private String data_type;
	private String source_file_list;
	private String file_path;
	private String file_name;
	private Timestamp data_collect_time;
	private String drought_st;
	private String condition_st;
	private String estimate_st;
	private Timestamp generate_time;

	public void setId(Long id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setData_type(String data_type){
		set(column_data_type, data_type);
	}
	public <T> T getData_type() {
		return get(column_data_type);
	}
	public void setSource_file_list(String source_file_list){
		set(column_source_file_list, source_file_list);
	}
	public <T> T getSource_file_list() {
		return get(column_source_file_list);
	}
	public void setFile_path(String file_path){
		set(column_file_path, file_path);
	}
	public <T> T getFile_path() {
		return get(column_file_path);
	}
	public void setFile_name(String file_name){
		set(column_file_name, file_name);
	}
	public <T> T getFile_name() {
		return get(column_file_name);
	}
	public void setData_collect_time(Timestamp data_collect_time){
		set(column_data_collect_time, data_collect_time);
	}
	public <T> T getData_collect_time() {
		return get(column_data_collect_time);
	}
	public void setDrought_st(String drought_st){
		set(column_drought_st, drought_st);
	}
	public <T> T getDrought_st() {
		return get(column_drought_st);
	}
	public void setCondition_st(String condition_st){
		set(column_condition_st, condition_st);
	}
	public <T> T getCondition_st() {
		return get(column_condition_st);
	}
	public void setEstimate_st(String estimate_st){
		set(column_estimate_st, estimate_st);
	}
	public <T> T getEstimate_st() {
		return get(column_estimate_st);
	}
	public void setGenerate_time(Timestamp generate_time){
		set(column_generate_time, generate_time);
	}
	public <T> T getGenerate_time() {
		return get(column_generate_time);
	}
	
}
