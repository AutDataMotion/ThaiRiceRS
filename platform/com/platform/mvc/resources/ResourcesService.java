package com.platform.mvc.resources;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.constant.ConstantInit;
import com.platform.mvc.base.BaseService;
import com.platform.plugin.PropertiesPlugin;
import com.platform.tools.ToolDateTime;
import com.platform.tools.ToolOS;

/**
 * 系统资源负载
 * @author DHJ
 */
public class ResourcesService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ResourcesService.class);

	public static final ResourcesService service = Enhancer.enhance(ResourcesService.class);
	
	/**
	 * 最近15天PV
	 * @return
	 */
	public Map<String, Object> pv(){
		Date endDate = ToolDateTime.endDateByDay(ToolDateTime.getDate());
		Date startDate = ToolDateTime.startDateByDay(endDate, -14);
		
		List<Record> list = null;
		String db_type = (String) PropertiesPlugin.getParamMapValue(ConstantInit.db_type_key);
		if(db_type.equals(ConstantInit.db_type_postgresql)){ // pg
			String sql = getSql(Resources.sqlId_pv_pg);
			list = Db.use(ConstantInit.db_dataSource_main).find(sql, ToolDateTime.getSqlTimestamp(startDate), ToolDateTime.getSqlTimestamp(endDate));
		
		}else if(db_type.equals(ConstantInit.db_type_mysql)){ // mysql
			String sql = getSql(Resources.sqlId_pv_mysql);
			list = Db.use(ConstantInit.db_dataSource_main).find(sql, ToolDateTime.getSqlTimestamp(startDate), ToolDateTime.getSqlTimestamp(endDate));
		
		}else if(db_type.equals(ConstantInit.db_type_oracle)){ // oracle
			String sql = getSql(Resources.sqlId_pv_oracle);
			list = Db.use(ConstantInit.db_dataSource_main).find(sql, ToolDateTime.getSqlTimestamp(startDate), ToolDateTime.getSqlTimestamp(endDate));
		}
		
		List<String> adates = new LinkedList<String>();
		List<Long> acounts = new LinkedList<Long>();
		for (Record record : list) {
			adates.add(ToolDateTime.format(record.getStr("adates"), ToolDateTime.pattern_ymd, "MM-dd"));
			acounts.add(record.getNumber("acounts").longValue());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adates", JSON.toJSON(adates).toString());
		map.put("acounts", JSON.toJSON(acounts).toString());
		return map;
	}
	
	/**
	 * 获取系统负载信息
	 * @return
	 */
	public Map<String, Object> getResources(){
		Date endDate = ToolDateTime.endDateByHour(new Date());
		Date startDate = ToolDateTime.startDateByHour(endDate, -24);
		
		String hostName = ToolOS.getOsLocalHostName(); // 获取本机名称

		String sql = getSql(Resources.sqlId_24hour);
		List<Resources> list = Resources.dao.find(sql, hostName, ToolDateTime.getSqlTimestamp(startDate), ToolDateTime.getSqlTimestamp(endDate));
		
		List<String> datesList = new LinkedList<String>();
		List<Integer> cpuList = new LinkedList<Integer>();
		List<Long> phymemoryList = new LinkedList<Long>();
		List<Long> jvmmemoryList = new LinkedList<Long>();
		List<Long> gccountList = new LinkedList<Long>();
		
		Long phymemory = 0l;
		Long jvmmaxmemory = 0l;
		
		for (Resources resources : list) {
			phymemory = resources.getNumber(Resources.column_phymemory).longValue();
			jvmmaxmemory = resources.getNumber(Resources.column_jvmmaxmemory).longValue();
			
			datesList.add(ToolDateTime.format(resources.getDate(Resources.column_createdate), "HH:mm"));
			
			int cpuratio = (int)(resources.getBigDecimal(Resources.column_cpuratio).doubleValue() * 100);
			cpuList.add(Integer.valueOf(cpuratio));
			
			phymemoryList.add(resources.getNumber(Resources.column_phymemory).longValue() - resources.getNumber(Resources.column_phyfreememory).longValue());
			
			jvmmemoryList.add(resources.getNumber(Resources.column_jvmtotalmemory).longValue() - resources.getNumber(Resources.column_jvmfreememory).longValue());
			
			gccountList.add(resources.getNumber(Resources.column_gccount).longValue());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dates", JSON.toJSON(datesList).toString());
		
		map.put("cpu", JSON.toJSON(cpuList).toString());
		
		map.put("phymemorymax", phymemory);
		map.put("phymemoryval", JSON.toJSON(phymemoryList).toString());

		map.put("jvmmemorymax", jvmmaxmemory);
		map.put("jvmmemoryval", JSON.toJSON(jvmmemoryList).toString());
		
		map.put("gccount", JSON.toJSON(gccountList).toString());
		return map;
	}

	/**
	 * 定时清理数据
	 */
	public void timerDataClear(){
		Date date = ToolDateTime.getDate(-365, 0, 0, 0, 0); // 设置时间为365天前
		Timestamp timestamp = ToolDateTime.getSqlTimestamp(date);
		Db.use(ConstantInit.db_dataSource_main).update(" delete from pt_syslog where startdate < ? ", timestamp);
	}
	
}
