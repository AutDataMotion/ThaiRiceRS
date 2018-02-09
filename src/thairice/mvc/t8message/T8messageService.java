package thairice.mvc.t8message;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T8messageService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T8messageService.class);
	
	public static final T8messageService service = Enhancer.enhance(T8messageService.class);
	
	public T8message SelectById(Integer id){
		
		T8message mdl = T8message.dao.findFirst("select * from t8message where id=?", id);
		return mdl;
	}
}
