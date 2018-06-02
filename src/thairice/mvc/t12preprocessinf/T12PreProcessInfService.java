package thairice.mvc.t12preprocessinf;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T12PreProcessInfService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T12PreProcessInfService.class);
	
	public static final T12PreProcessInfService service = Enhancer.enhance(T12PreProcessInfService.class);
	
	public T12PreProcessInf SelectById(Integer id){
		
		T12PreProcessInf mdl = T12PreProcessInf.dao.findFirst("select * from t12PreProcessInf where id=?", id);
		return mdl;
	}
}
