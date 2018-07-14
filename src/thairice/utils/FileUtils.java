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
import org.apache.log4j.Logger;

import csuduc.platform.util.networkCom.FTPClientConfigure;
import csuduc.platform.util.networkCom.FTPClientFactory;
import csuduc.platform.util.networkCom.FTPClientPool;
import thairice.mvc.t1parameter.T1parameter;
import thairice.mvc.t6org_data.T6org_data;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.DecimalFormat;
import java.lang.*;

/**
 * @ClassName: FileUtils
 * @Description:文件操作元件类
 * @author: zhuchaobin
 * @date: 2018年3月1日 上午11:48:44
 * 
 */

public class FileUtils {
	private static Logger LOG = Logger.getLogger(FileUtils.class);

	public FileUtils() {
	}

	public static boolean folderCheckAndMake(String aDir){
		java.io.File targetFolder = new java.io.File(aDir);
		if (!targetFolder.exists()) {
			try {
				return targetFolder.mkdirs();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 生成当日待下载ftp路径
	 * 
	 * @param pathTemplate
	 * @param pathType
	 *            01:/allData/6/MOD13Q1/YYYY/XXX/ 02:/allData/404/MOD12Q1
	 * @param strDate
	 *            指定处理某天yyyyMMdd的数据
	 * @author zhuchaobin, 2018-02-07
	 * @return String newPath
	 * @throws ParseException
	 */
	public static String generateNewPath(String pathTemplate, String pathType, Date scanDate) {
		try {
			String newPath = "";
			if (StringUtils.isBlank(pathTemplate) || StringUtils.isBlank(pathType)) {
				// 日志,生成当日待下载ftp路径出错：输入参数不能为空！
				return null;
			}
			// 年
			Calendar cal = Calendar.getInstance();
			// 若日期参数为空，则取当前系统日期
			if (null != scanDate) {
				// 如果指定处理某天的数据
				cal.setTime(scanDate);
			}
			// cal.set(2017, 1, 1);
			String year = Integer.toString(cal.get(Calendar.YEAR));
			// 三位日期（1-365）
			DecimalFormat dfDayOfYear = new DecimalFormat("000");
			String dayOfYear = dfDayOfYear.format(cal.get(Calendar.DAY_OF_YEAR));
			if (DataConstants.PATH_TYPE01.equals(pathType)) {
				// 路径类型：如/allData/6/MOD13Q1/YYYY/XXX/
				newPath = pathTemplate.replace("$1", year);
				newPath = newPath.replace("$2", dayOfYear);
				return newPath;
			} else if (DataConstants.PATH_TYPE02.equals(pathType)) {
				// 路径类型：/allData/404/MOD12Q1
				// 暂不实施
			}
			return newPath;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("生成当日待下载ftp路径发生异常!");
			return null;
		}
	}

	/**
	 * 解析ftp远程文件路径
	 * 
	 * @param fileDir
	 *            ftp远程文件路径
	 * @author zhuchaobin, 2018-02-26
	 * @return String T6org_data 解析后的原始文件对象
	 * @throws ParseException
	 */
	public static T6org_data parseOrgDataDir(String fileDir) {
		LOG.debug("解析ftp远程文件路径：" + fileDir);
		try {
			if (StringUtils.isBlank(fileDir)) {
				// ftp远程文件路径为空
				LOG.error("ftp远程文件路径为空");
				return null;
			}
			T6org_data orgDataObj = new T6org_data();
			// 文件名
			String fileName = fileDir.substring(fileDir.lastIndexOf("/") + 1);
			orgDataObj.setName_(fileName);
			// 文件路径
			String ftpPath = fileDir.substring(0, fileDir.lastIndexOf("/"));
			orgDataObj.setDownload_path(ftpPath);
			// 解析源路径，得到归档目录
			// 解析文件名
			String[] filePathAttr = ftpPath.split("//");
			String storage_path = "";
			if(5 <= filePathAttr.length) {
				storage_path += (("//") + filePathAttr[2]);
				Timestamp fileDate = DatesUtils.getDateOfJL(filePathAttr[3], filePathAttr[4]);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String strFileDate = sdf.format(fileDate);//时间存储为字符串
				storage_path += (("//") + strFileDate);
				orgDataObj.setStorage_path(storage_path);
			} else {
				LOG.error("解析数据采集日期并生成归档目录失败，文件名格式不对！");
			}
			// 解析文件名
			String[] fileAttr = fileName.split("\\.");
			// 文件状态代码
			orgDataObj.setStatus_(DataConstants.NOT_DOWNLOAD);
			// 波段数编码
			orgDataObj.setBand_number(DataConstants.MODIS_BANDNUM);
			if (fileAttr.length >= 3) {
				// 数据采集日期
				String sampleStr = fileAttr[1];
				if(8 == sampleStr.length()) {
					orgDataObj.setCollect_time(DatesUtils.getDateOfJL(sampleStr.substring(1, 5), sampleStr.substring(5, 8)));
				} else {
					LOG.error("解析数据采集日期失败，文件名格式不对！");
				}
				// 文件类型代码
				// orgDataObj.setType_(fileAttr[0]);
				if("MOD13Q1".equals(fileAttr[0]))
					orgDataObj.setType_("02");
				else if("MOD13A2".equals(fileAttr[0]))
						orgDataObj.setType_("01");
				else if("MOD11A2".equals(fileAttr[0]))
					orgDataObj.setType_("03");
				else {
					LOG.error("文件类型解析发生错误，文件类型不符合MOD13Q1、MOD13A2、MOD11A2三种之一！");
					orgDataObj.setType_("99");
				}
				// 行列号
				orgDataObj.setRow_column(fileAttr[2]);
			} else {
				LOG.error("解析行列号失败，文件名格式不对！");
			}
			return orgDataObj;
		} catch (Exception e) {
			// 解析ftp远程文件路径发生异常
			LOG.error("解析ftp远程文件路径发生异常:");
			e.printStackTrace();
			return null;
		}
	}

	// 判断文件是否存在
	public static void judeFileExists(File file) {
		if (file.exists()) {
			LOG.debug("file exists");
		} else {
			LOG.debug("file not exists, create it ...");
			try {
				LOG.debug("file not exists, create it ...");
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error("判断文件是否存在.");
			}
		}
	}

	// 判断文件夹是否存在，不存在则创建
	public static void judeDirExists(File file) {
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					LOG.debug("dir exists");
				} else {
					LOG.debug("the same name file exists, can not create dir");
				}
			} else {
				LOG.debug("dir not exists, create it ...");
				file.mkdirs();
			}
		} catch(Exception e) {
			LOG.error("判断文件夹是否存在发生异常：" + e);
		}
	}

	// 判断文件是否为泰国境内条带数据
	public static boolean isThairHV(String remoteFileName) {
		String[] fileAttr = remoteFileName.split("\\.");
		if (fileAttr.length >= 3) {
			if( DataConstants.STR_H26V06.equals(fileAttr[2]) ||
				DataConstants.STR_H27V06.equals(fileAttr[2]) ||
				DataConstants.STR_H27V07.equals(fileAttr[2]) ||
				DataConstants.STR_H27V08.equals(fileAttr[2]) ||
				DataConstants.STR_H28V07.equals(fileAttr[2]) ||
				DataConstants.STR_H28V08.equals(fileAttr[2]) ) {
					LOG.debug("为泰国境内条带:" + remoteFileName);
					return true;
			} else {
				LOG.debug("非泰国境内条带:" + remoteFileName);
				return false;
			}
		} else {
			LOG.error("文件名无效：" + remoteFileName);
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		// String str = "MOD13Q1.A2001033.h00v08.006.2015141152020.hdf";
		// String[] fileAttr = str.split("\\.");
		// System.out.println(fileAttr.length);

		File dir = new File("d://ddddd//113//4455");
		String temp="12345678";
		System.out.println(temp.substring(1,5));
		System.out.println(temp.substring(5,8));
		FileUtils.judeDirExists(dir);
	}

}
