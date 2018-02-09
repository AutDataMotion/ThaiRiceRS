package thairice.mvc.t5parameter_type;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T5parameter_typeService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T5parameter_typeService.class);
	
	public static final T5parameter_typeService service = Enhancer.enhance(T5parameter_typeService.class);
	
	public T5parameter_type SelectById(Integer id){
		
		T5parameter_type mdl = T5parameter_type.dao.findFirst("select * from t5parameter_type where id=?", id);
		return mdl;
	}
}
