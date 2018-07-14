package thairice.mvc.t14my_region;

import com.platform.annotation.Table;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseModel;


import org.apache.log4j.Logger;

/**
 * @description：
 * @author ZW
 */
@SuppressWarnings("unused")
//@Table(tableName = "t14my_region")
public class t14my_region extends BaseModel<t14my_region> {

	private static final long serialVersionUID = 6761767368352810428L;

	private static Logger log = Logger.getLogger(t14my_region.class);
	
	public static final t14my_region dao = new t14my_region();
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_id = "id";
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_userId = "userId";
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_provinceId = "provinceId";
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_cityId = "cityId";
	
	/**
	 * 字段描述： 
	 * 字段类型：int  长度：null
	 */
	public static final String column_areaId = "areaId";
	
	
	/**
	 * sqlId : thairice.t14my_region.splitPageFrom
	 * 描述：分页from
	 */
	public static final String sqlId_splitPage_from = "thairice.t14my_region.splitPageFrom";

	private Integer id;
	private Integer userId;
	private Integer provinceId;
	private Integer cityId;
	private Integer areaId;

	public void setId(Integer id){
		set(column_id, id);
	}
	public <T> T getId() {
		return get(column_id);
	}
	public void setUserId(Integer userId){
		set(column_userId, userId);
	}
	public <T> T getUserId() {
		return get(column_userId);
	}
	public void setProvinceId(Integer provinceId){
		set(column_provinceId, provinceId);
	}
	public <T> T getProvinceId() {
		return get(column_provinceId);
	}
	public void setCityId(Integer cityId){
		set(column_cityId, cityId);
	}
	public <T> T getCityId() {
		return get(column_cityId);
	}
	public void setAreaId(Integer areaId){
		set(column_areaId, areaId);
	}
	public <T> T getAreaId() {
		return get(column_areaId);
	}
	
}
