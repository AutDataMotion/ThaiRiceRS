package thairice.mvc.r4message_send;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class R4message_sendService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(R4message_sendService.class);
	
	public static final R4message_sendService service = Enhancer.enhance(R4message_sendService.class);
	
	public R4message_send SelectById(Integer id){
		
		R4message_send mdl = R4message_send.dao.findFirst("select * from r4message_send where id=?", id);
		return mdl;
	}
}
