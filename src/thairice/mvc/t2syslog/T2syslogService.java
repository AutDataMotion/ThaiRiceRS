package thairice.mvc.t2syslog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;
import org.apache.lucene.util.CollectionUtil;

import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Record;
import com.platform.mvc.base.BaseService;

import csuduc.platform.util.ReflectionUtils;

public class T2syslogService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(T2syslogService.class);

	public static final T2syslogService service = Enhancer.enhance(T2syslogService.class);

	public T2syslog SelectById(Integer id) {

		T2syslog mdl = T2syslog.dao.findFirst("select * from t2syslog where id=?", id);
		return mdl;
	}

	public List<T2syslog> SelectPage(Integer pageIndex, Integer pageSize) {

		List<T2syslog> list = T2syslog.dao.find("select * from t2syslog order by id desc limit ?,?", pageIndex,
				pageSize);
		return list;
	}

	public List<T2syslog> pageOriginal(ParamT2syslogSearch paramMdl) {
		Map<String, Object> param = ReflectionUtils.convertBean2Map(paramMdl);
		List<T2syslog> list = T2syslog.dao.query("thairice.t2syslog.searchPage", param);
		return list;
	}

	public List<ResT2syslogSearch> SearchPage(ParamT2syslogSearch paramMdl) {

		List<T2syslog> list = pageOriginal(paramMdl);
		if (null == list ) {
			return new ArrayList<>();
		}
		return list.stream().map(e->mdlDb2ResSearchMdl(e)).collect(Collectors.toList());
	}
	public ResT2syslogSearch mdlDb2ResSearchMdl(T2syslog log){
		ResT2syslogSearch resMdl = new ResT2syslogSearch();
		resMdl.setId(log.getId());
		resMdl.setUserid(log.getUserid());
		resMdl.setUserName(log.getUsername());
		resMdl.setType_(EnumT2sysLog.Type.getNameById(log.getType_()));
		resMdl.setAction_(log.getAction_());
		resMdl.setContent(log.getContent());
		resMdl.setAdd_time(log.getAdd_time());
		return resMdl;
	}
}
