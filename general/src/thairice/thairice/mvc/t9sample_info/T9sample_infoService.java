package thairice.mvc.t9sample_info;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T9sample_infoService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T9sample_infoService.class);
	
	public static final T9sample_infoService service = Enhancer.enhance(T9sample_infoService.class);
	
	public T9sample_info SelectById(Integer id){
		
		T9sample_info mdl = T9sample_info.dao.findFirst("select * from t9sample_info where id=?", id);
		return mdl;
	}
}
