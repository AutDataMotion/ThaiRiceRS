package com.platform.mvc.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oracle.net.aso.s;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.platform.constant.ConstantInit;
import com.platform.constant.ConstantRender;
import com.platform.dto.SplitPage;
import com.platform.plugin.I18NPlugin;
import com.platform.plugin.PropertiesPlugin;
import com.platform.tools.ToolMail;
import com.platform.tools.ToolSqlXml;
import com.platform.tools.ToolString;

/**
 * 公共方法
 * @author DHJ
 */
public class BaseService {

	private static Logger log = Logger.getLogger(BaseService.class);

	public static final BaseService service = Enhancer.enhance(BaseService.class);
	
	/**
	 * 封装预处理参数解析并执行查询
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public <T> List<T> query(String sqlId, Map<String, Object> param){
		LinkedList<Object> paramValue = new LinkedList<Object>();
		String sql = getSqlByBeetl(sqlId, param, paramValue);
		return Db.query(sql, paramValue.toArray());
	}

	/**
	 * 封装预处理参数解析并执行查询
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public List<Record> find(String sqlId, Map<String, Object> param){
		LinkedList<Object> paramValue = new LinkedList<Object>();
		String sql = getSqlByBeetl(sqlId, param, paramValue);
		return Db.find(sql, paramValue.toArray());
	}

	/**
	 * 封装预处理参数解析并执行更新
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public int update(String sqlId, Map<String, Object> param){
		LinkedList<Object> paramValue = new LinkedList<Object>();
		String sql = getSqlByBeetl(sqlId, param, paramValue);
		return Db.update(sql, paramValue.toArray());
	}
	
	/**
	 * 分页
	 * @param dataSource 数据源
	 * @param splitPage
	 * @param selectSqlId
	 * @param fromSqlId
	 */
	@SuppressWarnings("unchecked")
	public void paging(String dataSource, SplitPage splitPage, String selectSqlId, String fromSqlId){
		String selectSql = getSql(selectSqlId);
		Map<String, Object> map = getFromSql(splitPage, fromSqlId);
		String formSql = (String) map.get("formSql");
		LinkedList<Object> paramValue = (LinkedList<Object>) map.get("paramValue");
		System.out.println(selectSql+" "+formSql+(paramValue==null?"":paramValue.toString()));
		// 分页封装
		Page<?> page = Db.use(dataSource).paginate(splitPage.getPageNumber(), splitPage.getPageSize(), selectSql, formSql, paramValue.toArray());
		splitPage.setTotalPage(page.getTotalPage());
		splitPage.setTotalRow(page.getTotalRow());
		splitPage.setList(page.getList());
		splitPage.compute();
	}

	/**
	 * Distinct分页
	 * @param dataSource 数据源
	 * @param splitPage
	 * @param selectSqlId
	 * @param selectCountId
	 * @param fromSqlId
	 */
	@SuppressWarnings("unchecked")
	public void pagingDistinct(String dataSource, SplitPage splitPage, String selectSqlId, String selectCountId, String fromSqlId){
		String selectSql = getSql(selectSqlId);
		String selectCount = getSql(selectCountId);
		Map<String, Object> map = getFromSql(splitPage, fromSqlId);
		String formSql = (String) map.get("formSql");
		LinkedList<Object> paramValue = (LinkedList<Object>) map.get("paramValue");
				
		// 分页封装
		Page<?> page = Db.use(dataSource).paginateDistinct(splitPage.getPageNumber(), splitPage.getPageSize(), selectSql, selectCount, formSql, paramValue.toArray());
		splitPage.setTotalPage(page.getTotalPage());
		splitPage.setTotalRow(page.getTotalRow());
		splitPage.setList(page.getList());
		splitPage.compute();
	}
	
	/**
	 * 分页获取form sql和预处理参数
	 * @param splitPage
	 * @param fromSqlId
	 * @return
	 */
	private Map<String, Object> getFromSql(SplitPage splitPage, String fromSqlId){
		// 接收返回值对象
		StringBuilder formSqlSb = new StringBuilder();
		LinkedList<Object> paramValue = new LinkedList<Object>();
		
		// 调用生成from sql，并构造paramValue
		String sql = getSqlByBeetl(fromSqlId, splitPage.getQueryParam(), paramValue);
		formSqlSb.append(sql);
		
		// 行级：过滤，暂未做实现
		rowFilter(formSqlSb);
		
		// 排序
		String orderColunm = splitPage.getOrderColunm();
		String orderMode = splitPage.getOrderMode();
		if(null != orderColunm && !orderColunm.isEmpty() && null != orderMode && !orderMode.isEmpty()){
			formSqlSb.append(" order by ").append(orderColunm).append(" ").append(orderMode);
		}
		
		String formSql = formSqlSb.toString();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("formSql", formSql);
		map.put("paramValue", paramValue);
		
		return map;	
	}

	/**
     * 获取SQL，固定SQL
     * @param sqlId
     * @return
     */
	public String getSql(String sqlId){
		return ToolSqlXml.getSql(sqlId);
	}

    /**
     * 获取SQL，动态SQL，使用Beetl解析
     * @param sqlId
     * @param param
     * @return
     */
	public String getSqlByBeetl(String sqlId, Map<String, Object> param){
    	return ToolSqlXml.getSql(sqlId, param, ConstantRender.sql_renderType_beetl);
    }
    
    /**
     * 获取SQL，动态SQL，使用Beetl解析
     * @param sqlId 
     * @param param 查询参数
     * @param list 用于接收预处理的值
     * @return
     */
	public String getSqlByBeetl(String sqlId, Map<String, Object> param, LinkedList<Object> list){
    	return ToolSqlXml.getSql(sqlId, param, ConstantRender.sql_renderType_beetl, list);
    }

	/**
	 * 根据i18n参数查询获取哪个字段的值
	 * @param i18n
	 * @return
	 */
	public String i18n(String i18n){
		return I18NPlugin.i18n(i18n);
	}
	
	/**
	 * 把11,22,33...转成'11','22','33'...
	 * @param ids
	 * @return
	 */
	public String sqlIn(String ids){
		if(null == ids || ids.trim().isEmpty()){
			return null;
		}
		
		String[] idsArr = ids.split(",");
		
		return sqlIn(idsArr);
	}

	/**
	 * 把数组转成'11','22','33'...
	 * @param ids
	 * @return
	 */
	public String sqlIn(String[] idsArr){
		if(idsArr == null || idsArr.length == 0){
			return null;
		}
		
		int length = idsArr.length;
		
		StringBuilder sqlSb = new StringBuilder();
		
		for (int i = 0, size = length -1; i < size; i++) {
			String id = idsArr[i];
			if(!ToolString.regExpVali(id, ToolString.regExp_letter_5)){ // 匹配字符串，由数字、大小写字母、下划线组成
				log.error("字符安全验证失败：" + id);
				throw new RuntimeException("字符安全验证失败：" + id);
			}
			sqlSb.append(" '").append(id).append("', ");
		}
		
		if(length != 0){
			String id = idsArr[length-1];
			if(!ToolString.regExpVali(id, ToolString.regExp_letter_5)){ // 匹配字符串，由数字、大小写字母、下划线组成
				log.error("字符安全验证失败：" + id);
				throw new RuntimeException("字符安全验证失败：" + id);
			}
			sqlSb.append(" '").append(id).append("' ");
		}
		
		return sqlSb.toString();
	}

	/**
	 * 把11,22,33...转成map，分两部分，sql和值
	 * @param ids
	 * @return
	 * 描述：
	 * 返回map包含两部分数据
	 * 一是 name=? or name=? or name=? or ...
	 * 二是 list = ['11','22','33'...]
	 */
	public Map<String, Object> sqlOr(String column, String ids){
		if(null == ids || ids.trim().isEmpty()){
			return null;
		}
		
		String[] idsArr = ids.split(",");
		int length = idsArr.length;
		
		StringBuilder sql = new StringBuilder();
		List<Object> value = new ArrayList<Object>(length);
		
		for (int i = 0, size = length -1; i < size; i++) {
			String id = idsArr[i];
			if(!ToolString.regExpVali(id, ToolString.regExp_letter_5)){ // 匹配字符串，由数字、大小写字母、下划线组成
				log.error("字符安全验证失败：" + id);
				throw new RuntimeException("字符安全验证失败：" + id);
			}
			
			sql.append(column).append(" = ? or ");
			value.add(id);
		}
		
		if(length != 0){
			String id = idsArr[length-1];
			if(!ToolString.regExpVali(id, ToolString.regExp_letter_5)){ // 匹配字符串，由数字、大小写字母、下划线组成
				log.error("字符安全验证失败：" + id);
				throw new RuntimeException("字符安全验证失败：" + id);
			}
			
			sql.append(column).append(" = ? ");
			value.add(id);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql", sql);
		map.put("value", value);
		
		return map;
	}

	/**
	 * 把11,22,33...转成数组['11','22','33'...]
	 * @param ids
	 * @return
	 * 描述：把字符串分割成数组返回，并验证分割后的数据
	 */
	public String[] splitByComma(String ids){
		if(null == ids || ids.trim().isEmpty()){
			return null;
		}
		
		String[] idsArr = ids.split(",");
		
		for (String id : idsArr) {
			if(!ToolString.regExpVali(id, ToolString.regExp_letter_5)){ // 匹配字符串，由数字、大小写字母、下划线组成
				log.error("字符安全验证失败：" + id);
				throw new RuntimeException("字符安全验证失败：" + id);
			}
		}
		
		return idsArr;
	}
	
	/**
	 * 通用删除
	 * @param table
	 * @param ids 逗号分隔的列值
	 */
	@Before(Tx.class)
	public void delete(String table, String ids){
		String sqlIn = sqlIn(ids);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("table", table);
		param.put("sqlIn", sqlIn);
		
		String sql = getSqlByBeetl(BaseModel.sqlId_deleteIn, param);
		
		Db.use(ConstantInit.db_dataSource_main).update(sql);
	}
	
	/**
	 * 通用删除
	 * @param table
	 * @param id 逗号分隔的列值
	 */
	@Before(Tx.class)
	public void deleteById(String table, String id){
		//String sqlIn = sqlIn(id);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("table", table);
		param.put("sqlIn", id);
		
		String sql = getSqlByBeetl(BaseModel.sqlId_deleteInId, param);
		
		Db.use(ConstantInit.db_dataSource_main).update(sql);
	}

	/**
	 * 行级：过滤
	 * @param formSqlSb
	 */
	public void rowFilter(StringBuilder formSqlSb){
		
	}

	/**
	 * 发送邮件对象
	 * @param sendType 发送邮件的类型：text 、html
	 * @param to	接收者
	 * @param subject 邮件标题
	 * @param content 邮件的文本内容
	 * @param attachFileNames 附件
	 */
	public void sendTextMail(String sendType, List<String> to, String subject, String content, String[] attachFileNames){
		String host = (String) PropertiesPlugin.getParamMapValue(ConstantInit.config_mail_host);		// 发送邮件的服务器的IP
		String port = (String) PropertiesPlugin.getParamMapValue(ConstantInit.config_mail_port);	// 发送邮件的服务器的端口

		boolean validate = true;	// 是否需要身份验证
		
		String from = (String) PropertiesPlugin.getParamMapValue(ConstantInit.config_mail_from);		// 邮件发送者的地址
		String userName = (String) PropertiesPlugin.getParamMapValue(ConstantInit.config_mail_userName);	// 登陆邮件发送服务器的用户名
		String password = (String)PropertiesPlugin.getParamMapValue(ConstantInit.config_mail_password);	// 登陆邮件发送服务器的密码
		
		if(sendType != null && sendType.equals(ToolMail.sendType_text)){
			ToolMail.sendTextMail(host, port, validate, userName, password, from, to, subject, content, attachFileNames);
			
		} else if(sendType != null && sendType.equals(ToolMail.sendType_html)){
			ToolMail.sendHtmlMail(host, port, validate, userName, password, from, to, subject, content, attachFileNames);
			
		} else {
			log.error("发送邮件参数sendType错误");
		}
	}
	
}
