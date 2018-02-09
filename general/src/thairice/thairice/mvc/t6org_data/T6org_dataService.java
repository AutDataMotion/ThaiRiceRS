package thairice.mvc.t6org_data;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T6org_dataService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T6org_dataService.class);
	
	public static final T6org_dataService service = Enhancer.enhance(T6org_dataService.class);
	
	public T6org_data SelectById(Integer id){
		
		T6org_data mdl = T6org_data.dao.findFirst("select * from t6org_data where id=?", id);
		return mdl;
	}
}
