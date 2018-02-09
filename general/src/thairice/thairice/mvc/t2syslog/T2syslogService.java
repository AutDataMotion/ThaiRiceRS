package thairice.mvc.t2syslog;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T2syslogService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T2syslogService.class);
	
	public static final T2syslogService service = Enhancer.enhance(T2syslogService.class);
	
	public T2syslog SelectById(Integer id){
		
		T2syslog mdl = T2syslog.dao.findFirst("select * from t2syslog where id=?", id);
		return mdl;
	}
}
