package thairice.mvc.t16_pdt_sample;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class t16_pdt_sampleService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t16_pdt_sampleService.class);
	
	public static final t16_pdt_sampleService service = Enhancer.enhance(t16_pdt_sampleService.class);
	
	public t16_pdt_sample SelectById(Integer id){
		
		t16_pdt_sample mdl = t16_pdt_sample.dao.findFirst("select * from t16_pdt_sample where id=?", id);
		return mdl;
	}
}
