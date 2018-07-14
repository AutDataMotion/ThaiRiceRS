package thairice.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.log4j.Logger;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

import thairice.entity.EmailEntity;
import thairice.mvc.t1parameter.T1parameter;

public class ParamUtils {
	private static Logger LOG = Logger.getLogger(ParamUtils.class);
	/**
	 * 参数目录：FTP自动下载
	 */
	public static final String PC_FTP_AUTO_DWLD = "10000001";
	/**
	 * 参数明细：源ftp地址
	 */
	public static final String PD_SRCFTP_ADR = "001";
	/**
	 * 参数明细：源ftp端口
	 */
	public static final String PD_SRCFTP_PT = "002";
	/**
	 * 参数明细：源ftp用户名
	 */
	public static final String PD_SRCFTP_USR_NM = "003";
	/**
	 * 参数明细：源ftp密码
	 */
	public static final String PD_SRCFTP_PSWD = "004";
	/**
	 * 参数明细：源ftp根目录
	 */
	public static final String PD_SRCFTP_ROOT_CTLG = "005";
	/**
	 * 参数明细：文件存放地址
	 */
	public static final String FILE_STRG_ADR = "006";
	/**
	 * 参数明细：初始化FTP下载数据开关
	 */
	public static final String INLZ_SWTC = "007";
	/**
	 * 参数明细：初始化FTP下载数据开始日期
	 */
	public static final String INLZ_STDT = "008";	
	/**
	 * 参数明细：初始化FTP下载数据结束日期
	 */
	public static final String INLZ_EDDT = "009";
	
	/**
	 * 根据参数类型ID和参数明细ID查询参数值
	 * @param parmTypeId
	 * @param parmId
	 * @author zhuchaobin, 2018-02-27
	 * @return String paramValue
	 */
	public static String getParam(String parmTypeId, String parmId) {
		String paramValue = "";
		try{
			if(StringUtils.isBlank(parmTypeId) || StringUtils.isBlank(parmId)){
				LOG.error("参数目录ID或者参数ID为空，查询参数值失败！");
			} else {
				String sql = "select * from t1parameter t where t.parm_type_id = '" + parmTypeId 
						+ "' and t.parm__id = '" + parmId + "'";
				LOG.debug("根据参数类型ID和参数明细ID查询参数值：" + sql);
				List<T1parameter> rltList = T1parameter.dao.find(sql);
				if(null != rltList) {
					T1parameter elem = rltList.get(0);
					paramValue = elem.getValue_();
				}
				else
					LOG.error("根据参数类型ID和参数明细ID查询参数值为NULL,查询条件：parmTypeId=\" + parmTypeId + \"parmId=\" + parmId");
			}
		} catch (Exception e) {
			LOG.error("根据参数类型ID和参数明细ID查询参数值发生异常：" + e);
			return null;
		}
		return paramValue;
	}
	
	/**
	 * 根据参数类型ID和参数明细ID更新参数值
	 * @param parmTypeId
	 * @param parmId
	 * @param paramValue
	 * @author zhuchaobin, 2018-03-1
	 * @return boolean
	 */
	public static boolean updateParam(String parmTypeId, String parmId, String value) {
		try{
			if(StringUtils.isBlank(parmTypeId) || StringUtils.isBlank(parmId)){
				LOG.error("参数目录ID或者参数ID为空，更新参数值失败！");
			} else {
				String sql = "select * from t1parameter t where t.parm_type_id = '" + parmTypeId 
						+ "' and t.parm__id = '" + parmId + "'";
				LOG.debug("根据参数类型ID和参数明细ID查询参数值：" + sql);
				List<T1parameter> rltList = T1parameter.dao.find(sql);
				if(null != rltList) {
					T1parameter elem = rltList.get(0);
					elem.setValue_(value);
					elem.setDatetime_(new Timestamp(System.currentTimeMillis()));
					elem.update();
					return true;
				}
				else
					LOG.error("更新参数值失败,参数不存在,条件：parmTypeId=\" + parmTypeId + \"parmId=\" + parmId");
			}
		} catch (Exception e) {
			LOG.error("根据参数类型ID和参数明细ID更新参数值发生异常：" + e);
			return false;
		}
		return false;
	}
	
	/**
	 * 根据参数类型ID和参数明细ID查询参数值列表
	 * @param parmTypeId
	 * @param parmId
	 * @author zhuchaobin, 2018-02-27
	 * @return String paramValue
	 */
	public static List<String> getParamList(String parmTypeId, String parmId) {
		List<String> paramValueList = new ArrayList<String>();
		try{
			if(StringUtils.isBlank(parmTypeId) || StringUtils.isBlank(parmId)){
				LOG.error("参数目录ID或者参数ID为空，查询参数值失败！");
			} else {
				String sql = "select * from t1parameter t where t.parm_type_id = '" + parmTypeId 
						+ "' and t.parm__id = '" + parmId + "'";
				LOG.debug("根据参数类型ID和参数明细ID查询参数值：" + sql);
				List<T1parameter> rltList = T1parameter.dao.find(sql);
				if(null != rltList) {
					for(T1parameter parmElem : rltList) {
						String parmValue = parmElem.getValue_();
						if(!StringUtils.isBlank(parmValue)){
							paramValueList.add(parmValue);
						} else {
							LOG.debug("根据参数类型ID和参数明细ID查询参数值列表查询到空值，查询条件：parmTypeId=" + parmTypeId + "parmId=" + parmId);
						}
					}
				}
				else
					LOG.error("根据参数类型ID和参数明细ID查询参数值为NULL");
			}
		} catch (Exception e) {
			LOG.error("根据参数类型ID和参数明细ID查询参数值发生异常：" + e);
			return null;
		}
		return paramValueList;
	}
	
	   /**
	    * 初始化发送邮件参数
	    * 
	    * @throws Exception
	    */
	   public static EmailEntity initEmailParm(){
		   EmailEntity entity = new EmailEntity();
		   Prop p =PropKit.use("email.properties");
//		   PropKit.use("email.properties");
		   entity.setHost(p.get("config.email.host"));
		   entity.setPort(Integer.parseInt((p.get("config.email.port"))));
		   entity.setHost(p.get("config.email.userName"));
		   entity.setHost(p.get("config.email.password"));
		   return entity;
	   }
}
