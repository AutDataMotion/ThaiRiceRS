package thairice.mvc.t10pdt_report;

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
//@Table(tableName = "t10pdt_report")
public class T10pdt_report extends BaseModel<T10pdt_report> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(T10pdt_report.class);
	
	public static final T10pdt_report dao = new T10pdt_report();
	
	/**
	 * 字段描述：产品报表编号 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：用户ID 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_userid = "userid";
	
	/**
	 * 字段描述：报告创建时间 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_add_time = "add_time";
	
	/**
	 * 字段描述：采集日期 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_collect_time = "collect_time";
	
	/**
	 * 字段描述：查询产品开始日期 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_start_time = "start_time";
	
	/**
	 * 字段描述：查询产品结束日期 
	 * 字段类型：datetime  长度：null
	 */
	public static final String column_end_time = "end_time";
	
	/**
	 * 字段描述：查询行政区域代码 
	 * 字段类型：int  长度：null
	 */
	public static final String column_zone_code = "zone_code";
	
	/**
	 * 字段描述：查询作物种类代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_crop_type = "crop_type";
	
	/**
	 * 字段描述：产品类型代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_pdt_type = "pdt_type";
	
	/**
	 * 字段描述：报告格式代码 
	 * 字段类型：enum  长度：2
	 */
	public static final String column_suffix = "suffix";
	
	/**
	 * 字段描述：下载路径 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_download_path = "download_path";
	
	
	/**
	 * sqlId : thairice.t10pdt_report.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t10pdt_report.splitPageFrom";

	private BigInteger id;
	private BigInteger userid;
	private Timestamp add_time;
	private Timestamp collect_time;
	private Timestamp start_time;
	private Timestamp end_time;
	private Integer zone_code;
	private String crop_type;
	private String pdt_type;
	private String suffix;
	private String download_path;
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
	public void setAdd_time(Timestamp add_time){
		set(column_add_time, add_time);
	}
	public <T> T getAdd_time() {
		return get(column_add_time);
	}
	public void setCollect_time(Timestamp collect_time){
		set(column_collect_time, collect_time);
	}
	public <T> T getCollect_time() {
		return get(column_collect_time);
	}
	public void setStart_time(Timestamp start_time){
		set(column_start_time, start_time);
	}
	public <T> T getStart_time() {
		return get(column_start_time);
	}
	public void setEnd_time(Timestamp end_time){
		set(column_end_time, end_time);
	}
	public <T> T getEnd_time() {
		return get(column_end_time);
	}
	public void setZone_code(Integer zone_code){
		set(column_zone_code, zone_code);
	}
	public <T> T getZone_code() {
		return get(column_zone_code);
	}
	public void setCrop_type(String crop_type){
		set(column_crop_type, crop_type);
	}
	public <T> T getCrop_type() {
		return get(column_crop_type);
	}
	public void setPdt_type(String pdt_type){
		set(column_pdt_type, pdt_type);
	}
	public <T> T getPdt_type() {
		return get(column_pdt_type);
	}
	public void setSuffix(String suffix){
		set(column_suffix, suffix);
	}
	public <T> T getSuffix() {
		return get(column_suffix);
	}
	public void setDownload_path(String download_path){
		set(column_download_path, download_path);
	}
	public <T> T getDownload_path() {
		return get(column_download_path);
	}
	
}
