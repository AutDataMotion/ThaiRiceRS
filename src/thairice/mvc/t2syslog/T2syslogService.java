package thairice.mvc.t2syslog;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;
import org.apache.lucene.util.CollectionUtil;

import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.mvc.base.BaseService;

import csuduc.platform.util.ReflectionUtils;
import oracle.net.aso.p;
import thairice.config.ConfMain;

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
		resMdl.setType_(EnumT2sysLog.getNameById(log.getType_()));
		resMdl.setAction_(log.getAction_());
		resMdl.setContent(log.getContent());
		resMdl.setAdd_time(log.getAdd_time());
		return resMdl;
	}
	
	private static boolean threadRun = true; // 线程是否运行
	/**
	 * 队列
	 */
	private static Queue<Record> queue = new ConcurrentLinkedQueue<Record>(); //	此队列按照 FIFO（先进先出）原则对元素进行排序
	public static void setThreadRun(boolean athreadRun) {
		threadRun = athreadRun;
	}
	
	public static void addLog(EnumT2sysLog type, BigInteger userid, String userName, String action_, String content){
		Record record = new Record().set(T2syslog.column_type_, type.getName())
				.set(T2syslog.column_userid, userid)
				.set(T2syslog.column_username, userName)
				.set(T2syslog.column_action_, action_)
				.set(T2syslog.column_content, content);
		
		if (queue.size() > 5000) {
			// 太大只记录日志文件
			addLogToFile(record);
		}else{
			queue.offer(record ); // 将指定元素插入此队列的尾部
		}
	}
	public static void addLogToFile(Record record ){
		EnumT2sysLog type = record.get(T2syslog.column_type_);
		switch (type) {
		case ERROR_S:
		case ERROR_N:
			log.error(record.toJson());
			break;
		case WARN:
			log.warn(record.toJson());
			break;
		default:
			log.info(record.toJson());
			break;
		}
	}
	
	public static void startLogThread(){
		try {
			 new Thread(()->{
				 while (threadRun) {
						try {
							// 取队列数据
							//System.out.println("--syslogService run...");
							Record sysLog = queue.poll();
							if(null == sysLog){
								Thread.sleep(2000);
							} else {
								ConfMain.db().save(T2syslog.tableName, sysLog);// 日志入库
								addLogToFile(sysLog);// 日志入文件
							}
						} catch (Exception e) {
							log.error("保存操作日志T2syslogService异常：" + e.getMessage());
							e.printStackTrace();
						}
					}
			}).start();;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("日志T2syslogService线程异常：" + e.getMessage());
		}
	}
	
	
}
