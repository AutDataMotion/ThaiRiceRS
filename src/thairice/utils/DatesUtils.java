/**
 * <p>title:FtpUtils.java<／p>
 * <p>Description: <／p>
 * @date:2015年11月5日下午4:34:05
 * @author：ZhongwengHao email:zhongweng.hao@qq.com
 * @version 1.0
 */
package thairice.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.jfinal.config.JFinalConfig;
import com.jfinal.plugin.activerecord.Db;
import csuduc.platform.util.networkCom.FTPClientConfigure;
import csuduc.platform.util.networkCom.FTPClientFactory;
import csuduc.platform.util.networkCom.FTPClientPool;
import thairice.config.MyConfig;
import thairice.constant.ConstantInitMy;
import thairice.entity.FtpInfoEntity;
import thairice.mvc.t6org_data.T6org_data;
import thairice.mvc.t6org_data.T6org_dataService;
import thairice.mvc.t7pdt_data.T7pdt_dataService;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.DecimalFormat;
import java.lang.*;

/**  
 * 创建时间：2018年3月1日  
 * 项目名称：zwplatform   
 * 文件名称：FtpUtils.java  
 * 类说明：  
 *
 * Modification History:   
 * Date        Author         Version      Description   
 * ----------------------------------------------------------------- 
 * 2018年3月1日     zhuchaobin      1.0         1.0 Version 
 */
/**
 * <p>
 * Title: FtpUtils<／p>
 * <p>
 * Description: <／p>
 * 
 * @author zhuchaobin
 * @date 2018年3月1日
 */
public class DatesUtils {
	private static Logger LOG = Logger.getLogger(DatesUtils.class);
	static int index = 0;// 下载文件列表中的第几个元素
	FTPClientFactory factory;
	FTPClientPool pool;
	FTPClient ftpClient;

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */

	public DatesUtils() {

	}

	/**
	 * 获取两个日期之间的所有日期（yyyy-MM-dd）
	 * 
	 * @Description TODO
	 * @param begin
	 * @param end
	 * @return
	 * @author XuJD
	 * @throws ParseException
	 * @date 2017-4-1
	 */
	public static List<Date> getBetweenDates(String begin, String end) throws ParseException {
		try {
			List<Date> result = new ArrayList<Date>();
			Calendar tempStart = Calendar.getInstance();
			// 如果指定处理某天的数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date dateBegin = sdf.parse(begin);
			tempStart.setTime(dateBegin);
			Calendar tempEnd = Calendar.getInstance();
			// tempStart.add(Calendar.DAY_OF_YEAR, 1);
			Date dateEnd = sdf.parse(end);
			tempEnd.setTime(dateEnd);
			while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
				if(DataConstants.DAYS_OF_JL_03.contains(getJLFromDay(tempStart))) {
					LOG.debug("添加有数据的日期：" + getJLFromDay(tempStart));
					result.add(tempStart.getTime());
				} else {
			//		LOG.debug("跳过没有数据的日期：" + tempStart);
				}
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOG.error("获取两个日期之间的所有日期列表发生异常：");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Description 根据年份，累积天计算当前自然日期(废弃不用)
	 * @param 
	 * @param 
	 * @return
	 * @author zhuchaobin
	 * @throws 
	 * @date 2018-03-03
	 */
	public static Calendar getDateOfJL2(String year, Calendar calStar, Calendar calEnd, int dayOfYear) {
		try {
		LOG.debug("根据年份，累积天计算当前自然日期,参数：year=" + year + "calStar=" + calStar 
				+ "calEnd=" + calEnd + "dayOfYear=" + dayOfYear);
		
        // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");        
		if(null == calStar) {
			// 默认一年中第一天开始
	        String str = year + "0101";
	        // 将字符串的日期转为Date类型
	        Date date = sdf.parse(str);
	        calStar = Calendar.getInstance();
	        calStar.setTime(date);
		}
		if(null == calEnd) {
			// 默认一年中最后一天结束
	        String str = year + "1231";
	        // 将字符串的日期转为Date类型
	        Date date = sdf.parse(str);
	        calEnd = Calendar.getInstance();
	        calEnd.setTime(date);
		}
		// 三位日期（1-365）
		int dayOfYearStart = calStar.get(Calendar.DAY_OF_YEAR);
		if(dayOfYear == dayOfYearStart)
			return calStar;
		else {
			// 计算中间的一天的日期
			 long time1=calStar.getTimeInMillis(); 
			 long time2=calEnd.getTimeInMillis(); 
			 long between_days=(time2-time1)/(1000*3600*24);
			 Calendar calMid = calStar;
			 calMid.add(Calendar.DATE, (int) between_days/2);
			 if(null == calMid) {
				 LOG.error("根据年份，累积天计算当前自然日期发生错误，中间日期为NULL.");
				 return null;
			 }
			 int dayOfYearMid = calStar.get(calMid.DAY_OF_YEAR);
			 if(dayOfYear == dayOfYearMid) {
				 return calMid;
			 }
			 else if(dayOfYear < dayOfYearMid){
				 return getDateOfJL2(year, calStar, calMid, dayOfYear);
			 } else {
				 return getDateOfJL2(year, calMid, calEnd, dayOfYear);
			 }
		}
		} catch (Exception e) {
			LOG.error("根据年份，累积天计算当前自然日期发生异常:" + e);
			return null;
		}
	}
	
	/**
	 * 
	 * @Description 根据年月日计算累计天
	 * @param day yyyymmdd格式
	 * @param 
	 * @return
	 * @author zhuchaobin
	 * @throws 
	 * @date 2018-03-03
	 */
	public static String getJLFromDay(String day){
		String jllDay = "";
        // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date;
		try {
			date = sdf.parse(day);
	        Calendar calStar = Calendar.getInstance();
	        calStar.setTime(date);
			// 三位日期（1-365）
	        jllDay = String.format("%06d", calStar.get(Calendar.DAY_OF_YEAR));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOG.error("根据年月日计算累计天发生异常：");
			e.printStackTrace();
		}

		return jllDay;
	}
	
	/**
	 * 
	 * @Description 根据年月日计算累计天:重载
	 * @param day Calendar格式
	 * @param 
	 * @return
	 * @author zhuchaobin
	 * @throws 
	 * @date 2018-03-03
	 */
	public static String getJLFromDay(Calendar day){
		String jllDay = "";
        // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
		try {
			// 三位日期（1-365）
			jllDay = String.format("%03d", day.get(Calendar.DAY_OF_YEAR));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("根据年月日计算累计天发生异常：");
			e.printStackTrace();
		}

		return jllDay;
	}
	
	/**
	 * 
	 * @Description 根据年份，累积天计算当前自然日期(废弃不用)
	 * @param 
	 * @param 
	 * @return
	 * @author zhuchaobin
	 * @throws 
	 * @date 2018-03-03
	 */
	public static String getDateOfJL(String year, String dayOfYear){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(dayOfYear));
		java.util.Date rltDate =cal.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置你想要的格式
		String dateStr = df.format(cal.getTime());
		return dateStr;		
	}
	
	public static void main(String[] args) {
		// String str = "MOD13Q1.A2001033.h00v08.006.2015141152020.hdf";
		// String[] fileAttr = str.split("\\.");
		// System.out.println(fileAttr.length);
/*		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2001);
		cal.set(Calendar.DAY_OF_YEAR, 344);
		// 若日期参数为空，则取当前系统日期
//		cal = getDateOfJL("2018", null, null, 1);
		// cal.set(2017, 1, 1);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置你想要的格式
//		String dateStr = df.format(cal.getTime());
//	       Calendar calendat = Calendar.getInstance();
//
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        String dateStr = sdf.format(cal.getTime());


//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

		Timestamp ts = Timestamp.valueOf(dateStr);
		System.out.println(ts);*/
		
		String test = String.format("%06d",12);//
		System.out.println(test);
		// cal.set(2017, 1, 1);

	}
}
