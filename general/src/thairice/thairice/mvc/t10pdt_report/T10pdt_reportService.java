package thairice.mvc.t10pdt_report;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class T10pdt_reportService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T10pdt_reportService.class);
	
	public static final T10pdt_reportService service = Enhancer.enhance(T10pdt_reportService.class);
	
	public T10pdt_report SelectById(Integer id){
		
		T10pdt_report mdl = T10pdt_report.dao.findFirst("select * from t10pdt_report where id=?", id);
		return mdl;
	}
}
