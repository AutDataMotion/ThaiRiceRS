package thairice.mvc.t15_news_cnt;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;

import com.platform.mvc.base.BaseService;

public class t15_news_cntService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(t15_news_cntService.class);
	
	public static final t15_news_cntService service = Enhancer.enhance(t15_news_cntService.class);
	
	public t15_news_cnt SelectById(Integer id){
		
		t15_news_cnt mdl = t15_news_cnt.dao.findFirst("select * from t15_news_cnt where id=?", id);
		return mdl;
	}
}
