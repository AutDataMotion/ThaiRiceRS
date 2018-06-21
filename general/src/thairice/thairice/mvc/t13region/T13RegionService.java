package thairice.mvc.t13region;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T13RegionService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T13RegionService.class);
	
	public static final T13RegionService service = Enhancer.enhance(T13RegionService.class);
	
	public T13Region SelectById(Integer id){
		
		T13Region mdl = T13Region.dao.findFirst("select * from t13Region where id=?", id);
		return mdl;
	}
}
