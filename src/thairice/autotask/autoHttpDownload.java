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
 @Description: 定时自动扫描待下载文件并启动wget下载
 @author: zhuchaobin 
 @date: 2018年2月26日 
 @version: 1.0 版本初成
  **/ 
public class autoHttpDownload extends Controller implements ITask { 
	private static Logger LOG = Logger.getLogger(autoFtpDownload.class);
	@Override 
	public void run() {
		FtpUtils.autoWgetdownload();
	} 
	
	@Override
	public void stop() 
	{ 
		System.out.println("任务结束了");
	}
} 
