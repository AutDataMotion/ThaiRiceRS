package thairice.mvc.t1parameter;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T1parameterService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T1parameterService.class);
	
	public static final T1parameterService service = Enhancer.enhance(T1parameterService.class);
	
	public T1parameter SelectById(Integer id){
		
		T1parameter mdl = T1parameter.dao.findFirst("select * from t1parameter where id=?", id);
		return mdl;
	}
}
