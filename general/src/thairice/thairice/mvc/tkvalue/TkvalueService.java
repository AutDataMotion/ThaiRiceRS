package thairice.mvc.tkvalue;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class TkvalueService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TkvalueService.class);
	
	public static final TkvalueService service = Enhancer.enhance(TkvalueService.class);
	
	public Tkvalue SelectById(Integer id){
		
		Tkvalue mdl = Tkvalue.dao.findFirst("select * from tkvalue where id=?", id);
		return mdl;
	}
}
