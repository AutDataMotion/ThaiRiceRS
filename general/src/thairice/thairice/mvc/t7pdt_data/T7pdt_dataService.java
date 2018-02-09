package thairice.mvc.t7pdt_data;

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
}
