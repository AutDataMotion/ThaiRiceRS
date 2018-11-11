package thairice.mvc.t16_pdt_sample;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;


import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t16_pdt_sample")
public class t16_pdt_sample extends BaseModel<t16_pdt_sample> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(t16_pdt_sample.class);
	
	public static final String  tableName = "t16_pdt_sample";
	
	public static final t16_pdt_sample dao = new t16_pdt_sample();
	
	/**
	 * 字段描述：id 
	 * 字段类型：bigint  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述：rice_planting_area 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_rice_planting_area = "rice_planting_area";
	
	/**
	 * 字段描述：rice_drought 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_rice_drought = "rice_drought";
	
	/**
	 * 字段描述：rice_condition 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_rice_condition = "rice_condition";
	
	/**
	 * 字段描述：rice_production 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_rice_production = "rice_production";
	
	/**
	 * 字段描述：图片路径 
	 * 字段类型：varchar  长度：256
	 */
	public static final String column_pic_path = "pic_path";
	
	/**
	 * 字段描述：产品描述 
	 * 字段类型：varchar  长度：512
	 */
	public static final String column_pdt_desc = "pdt_desc";
	
	
	/**
	 * sqlId : thairice.t16_pdt_sample.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t16_pdt_sample.splitPageFrom";

	private Long id;
	private String rice_planting_area;
	private String rice_drought;
	private String rice_condition;
	private String rice_production;
	private String pic_path;
	private String pdt_desc;

	public void setId(Long id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setRice_planting_area(String rice_planting_area){
		set(column_rice_planting_area, rice_planting_area);
	}
	public <T> T getRice_planting_area() {
		return get(column_rice_planting_area);
	}
	public void setRice_drought(String rice_drought){
		set(column_rice_drought, rice_drought);
	}
	public <T> T getRice_drought() {
		return get(column_rice_drought);
	}
	public void setRice_condition(String rice_condition){
		set(column_rice_condition, rice_condition);
	}
	public <T> T getRice_condition() {
		return get(column_rice_condition);
	}
	public void setRice_production(String rice_production){
		set(column_rice_production, rice_production);
	}
	public <T> T getRice_production() {
		return get(column_rice_production);
	}
	public void setPic_path(String pic_path){
		set(column_pic_path, pic_path);
	}
	public <T> T getPic_path() {
		return get(column_pic_path);
	}
	public void setPdt_desc(String pdt_desc){
		set(column_pdt_desc, pdt_desc);
	}
	public <T> T getPdt_desc() {
		return get(column_pdt_desc);
	}
	
}
