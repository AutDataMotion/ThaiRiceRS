package thairice.mvc.t3user;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T3userService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T3userService.class);
	
	public static final T3userService service = Enhancer.enhance(T3userService.class);
	
	public T3user SelectById(Integer id){
		
		T3user mdl = T3user.dao.findFirst("select * from t3user where id=?", id);
		return mdl;
	}
}
