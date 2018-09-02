package thairice.mvc.t7pdt_data;

import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T7pdt_dataService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T7pdt_dataService.class);
	
	public static final T7pdt_dataService service = Enhancer.enhance(T7pdt_dataService.class);
	
	public T7pdt_data SelectById(Integer id){
		
		T7pdt_data mdl = T7pdt_data.dao.findFirst("select * from t7pdt_data where id=?", id);
		return mdl;
	}
	public List<T7pdt_data> SelectByZone_code(String zone_code,String productKind_code,String startTime,String endTime){
		
		List<T7pdt_data> mdl = T7pdt_data.dao.find("select year(collect_time) as Year from t7pdt_data where zone_code=? and type_ = ? and collect_time>=? and collect_time<=?", zone_code,productKind_code,startTime,endTime);
		return mdl;
	}
	public List<T7pdt_data> SelectByZone_code_year(String zone_code,String productKind_code,String startTime,String endTime,String year){
		
		List<T7pdt_data> mdl = T7pdt_data.dao.find("select month(collect_time) as month from t7pdt_data where zone_code=? and type_ = ? and collect_time>=? and collect_time<=? and year(collect_time) = ?", zone_code,productKind_code,startTime,endTime,year);
		return mdl;
	}
	public List<T7pdt_data> SelectByZone_code_year_month(String zone_code,String productKind_code,String startTime,String endTime,String year,String month){
		
		List<T7pdt_data> mdl = T7pdt_data.dao.find("select day(collect_time) as day from t7pdt_data where zone_code=? and type_ = ? and collect_time>=? and collect_time<=? and year(collect_time) = ? and month(collect_time) = ?", zone_code,productKind_code,startTime,endTime,year,month);
		return mdl;
	}
}
