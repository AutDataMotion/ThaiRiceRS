package thairice.mvc.t2syslog;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import javax.swing.event.ListSelectionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.util.CollectionUtil;

import com.alibaba.druid.sql.visitor.functions.If;
import com.google.common.base.Strings;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.mvc.base.BaseController;
import com.platform.mvc.base.BaseService;

import csuduc.platform.util.ReflectionUtils;
import csuduc.platform.util.StringUtil;
import oracle.net.aso.p;
import thairice.config.ConfMain;
import thairice.mvc.comm.ParamComm;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t3user.T3userController;

public class T2syslogService extends BaseController{

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
	
	public static String getSearchWhere(ParamComm paramMdl, List<Object> listArgs) {
		StringBuilder whereStr = new StringBuilder();
		if (StringUtils.isNotBlank(paramMdl.getName1())) {
			whereStr.append(" and type_  like ? ");
			listArgs.add(StringUtil.getStringLikeLeft(paramMdl.getName1()));
		}
		if (StringUtils.isNotBlank(paramMdl.getName2())) {
			whereStr.append(" and username like ? ");
			listArgs.add(StringUtil.getStringLikeLeft(paramMdl.getName2()));
		}
		if (StringUtils.isNotBlank(paramMdl.getName3())) {
			whereStr.append(" and action_  like ? ");
			listArgs.add(StringUtil.getStringLikeLeft(paramMdl.getName3()));
		}
		if (StringUtils.isNotBlank(paramMdl.getName4())) {
			whereStr.append(" and add_time >= ? ");
			listArgs.add(paramMdl.getName4());
		}
		if (StringUtils.isNotBlank(paramMdl.getName5())) {
			whereStr.append(" and add_time <= ?  ");
			listArgs.add(paramMdl.getName5());
		}
		
		return whereStr.toString();
	}

	public List<T2syslog> pageOriginal(ParamComm paramMdl) {
		
		List<Object> listArgs = new ArrayList<>();
		final String searchStr = getSearchWhere(paramMdl, listArgs);
		Long countTotal = ConfMain.db()
				.queryLong(String.format("select count(1) from t2syslog where 1=1 %s ",  searchStr), listArgs.toArray());
		paramMdl.setTotal(countTotal);
		List<T2syslog> resList = new ArrayList<>();
		if (countTotal > 0) {
			listArgs.add(paramMdl.getPageIndex());
			listArgs.add(paramMdl.getPageSize());
			resList = T2syslog.dao.find(String.format(
					"select * from t2syslog where 1=1 %s order by id desc  limit ?,?", searchStr), listArgs.toArray());
		}
		return resList;
	}

	public List<ResT2syslogSearch> SearchPage(ParamComm paramMdl) {
		return pageOriginal(paramMdl).stream().map(e->mdlDb2ResSearchMdl(e)).collect(Collectors.toList());
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
				.set(T2syslog.column_content, content)
				.set(T2syslog.column_add_time, new Date());
		if (queue.size() > 5000) {
			// 太大只记录日志文件
			addLogToFile(record);
		}else{
			queue.offer(record ); // 将指定元素插入此队列的尾部
		}
	}
	
	/**
	 *  重载记录日志接口，不需要传用户信息，自动识别用户信息
	 *  zhuchaobin
	 *  2018-08-19
	 */
	public static void addLog(EnumT2sysLog type, String action_, String content){
		Record record = new Record().set(T2syslog.column_type_, type.getName())
				.set(T2syslog.column_action_, action_)
				.set(T2syslog.column_content, content)
				.set(T2syslog.column_add_time, new Date());
		Integer userid = 99999999;
		String username = "System";
		T3userController t3 = new T3userController();
		T3user user = t3.getSessionAttr("user");
		if(null != user && (null !=user.getId())) {
			userid = user.getId();
			username = user.getAccount();
		}
		record.set(T2syslog.column_userid, userid);
		record.set(T2syslog.column_username, username);
		if (queue.size() > 5000) {
			// 太大只记录日志文件
			addLogToFile(record);
		}else{
			queue.offer(record ); // 将指定元素插入此队列的尾部
		}
	}
	
	public static void info(BigInteger userid, String userName, String action_, String content){
		addLog(EnumT2sysLog.INFO, userid, userName, action_, content);
	}
	public static void warn(BigInteger userid, String userName, String action_, String content){
		addLog(EnumT2sysLog.WARN, userid, userName, action_, content);
	}
	public static void error(BigInteger userid, String userName, String action_, String content){
		addLog(EnumT2sysLog.ERROR_N, userid, userName, action_, content);
	}
	public static void disaster(BigInteger userid, String userName, String action_, String content){
		addLog(EnumT2sysLog.ERROR_S, userid, userName, action_, content);
	}
	public static void addLogToFile(Record record ){
		String type = record.get(T2syslog.column_type_);
		if (type.equals(EnumT2sysLog.INFO.getName())) {
			log.info(record.toJson());
		} else if(type.equals(EnumT2sysLog.WARN.getName())){
			log.warn(record.toJson());
		} else if (type.equals(EnumT2sysLog.ERROR_S.getName())
				|| type.equals(EnumT2sysLog.ERROR_S.getName())) {
			log.error(record.toJson());
		} else {
			log.debug(record.toJson());
		}
	
	}
	
	public static void startLogThread(){
		try {
			 new Thread(()->{
				 while (threadRun) {
						try {
							// 取队列数据
							// System.out.println("--syslogService run...");
							Record sysLog = queue.poll();
							if(null == sysLog){
								Thread.sleep(2000);
							} else {

								ConfMain.db().save(T2syslog.tableName,sysLog);// 日志入库
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

	@Override
	protected void setViewPath() {
		// TODO Auto-generated method stub
		
	}
	
	
}
