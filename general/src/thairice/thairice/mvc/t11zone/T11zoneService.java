package thairice.mvc.t11zone;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T11zoneService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T11zoneService.class);
	
	public static final T11zoneService service = Enhancer.enhance(T11zoneService.class);
	
	public T11zone SelectById(Integer id){
		
		T11zone mdl = T11zone.dao.findFirst("select * from t11zone where id=?", id);
		return mdl;
	}
}
