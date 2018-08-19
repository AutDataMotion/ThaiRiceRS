package thairice.autotask;


import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;

import thairice.config.MyConfig;
import thairice.constant.ConstantInitMy;
import thairice.mvc.t1parameter.T1parameter;
import thairice.mvc.t2syslog.EnumT2sysLog;
import thairice.mvc.t2syslog.T2syslog;
import thairice.mvc.t2syslog.T2syslogService;
import thairice.mvc.t3user.T3user;
import thairice.mvc.t6org_data.T6org_data;
import thairice.utils.DataConstants;
import thairice.utils.FileUtils;
import thairice.utils.FtpUtils;
import thairice.utils.ParamUtils;
/**
 @ClassName: autoFtpDownload
 @Description: 定时自动扫描待下载文件并启动ftp下载
 @author: zhuchaobin 
 @date: 2018年2月26日 
 @version: 1.0 版本初成
  **/ 
public class autoFtpDownload extends Controller implements ITask { 
	private static Logger LOG = Logger.getLogger(autoFtpDownload.class);
	@Override 
	public void run() {
		try {
			//格式化时间 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(new Date());
			System.out.println("定时自动扫描待下载文件并启动ftp下载"+time);
			T2syslogService.addLog(EnumT2sysLog.INFO, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Download remote ftp files", "Automatically scan files to be downloaded and start ftp download...");
			final String localfilePath = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.FILE_STRG_ADR);
			final FtpUtils ftpUtils = new FtpUtils();
			final FTPClient ftpClient = ftpUtils.connectFtp();
			// 查询待下载文件列表:未下载+下载失败
			String sql = "select * from T6org_data t where t.status_ = '" + DataConstants.NOT_DOWNLOAD + "' or t.status_ = '" + DataConstants.DOWNLOAD_FAIL + "'";
			LOG.debug("查询待下载文件列表：" + sql);
			List<T6org_data> rltList = T6org_data.dao.find(sql);
			if(null != rltList) {
				for(int i = 0; i < rltList.size(); i ++) {
					T6org_data org_data = rltList.get(i);
					String ftpDir = (String) (org_data.getDownload_path()) + "//" + org_data.getName_();
					org_data.setDload_start_time(new Timestamp(System.currentTimeMillis()));
					org_data.setStatus_(DataConstants.DOWNLOAD_ING);
					org_data.update();
					// 下载后存放到本地归档目录
					String localFileDir = "";
					if(StringUtils.isNotBlank(org_data.getStorage_path())) {
						localFileDir = localfilePath + org_data.getStorage_path();
					} else {
						localFileDir = localfilePath;
					}
					if(!FileUtils.folderCheckAndMake(localFileDir)) {
						LOG.error("检测并创建下载文件保存路径失败！路径：" + localFileDir);
					}
					if(FtpUtils.downloadFile2(ftpClient, ftpDir, localFileDir)) {
						org_data.setStatus_(DataConstants.DOWNLOAD_SUCCE);
						org_data.setDload_end_time(new Timestamp(System.currentTimeMillis()));
						org_data.setUserid(DataConstants.SYS_USER_ID);
						org_data.setStorage_path(localfilePath);
						org_data.update();
					} else {
						org_data.setStatus_(DataConstants.DOWNLOAD_FAIL);
						org_data.update();
					}								
				}		
			} else {
				LOG.debug("待下载文件列表为空");
			}
		} catch(Exception e) {
			LOG.error("定时自动扫描待下载文件并启动ftp下载发生异常：" + e);
			T2syslogService.addLog(EnumT2sysLog.ERROR_N, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Download remote ftp files", "Automatically scan files to be downloaded and start ftp download abnormal!" + e);
		}
	} 
	
	@Override
	public void stop() 
	{ 
		System.out.println("任务结束了");
	}
} 
