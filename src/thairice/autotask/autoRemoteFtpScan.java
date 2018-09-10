package thairice.autotask;

import java.math.BigInteger;
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
 * @ClassName: autoRemoteFtpScan
 * @Description: 定时扫描检测远程ftp文件
 * @author: zhuchaobin
 * @date: 2018年2月25日 下午9:23:31
 * @version: 1.0 版本初成
 **/
public class autoRemoteFtpScan extends Controller implements ITask {
	private static Logger LOG = Logger.getLogger(autoRemoteFtpScan.class);

	@Override
	public void run() {
		try {
			// 格式化时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			System.out.println("自动任务定时扫描检测远程ftp文件" + time);
			T2syslogService.addLog(EnumT2sysLog.INFO, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Scan files to be downloaded", "Automaticly detect remote ftp files starting...");
			final FtpUtils ftpUtils = new FtpUtils();
			final FTPClient ftpClient = ftpUtils.connectFtp();
			List<String> remotePathList = ParamUtils.getParamList(ParamUtils.PC_FTP_AUTO_DWLD,
					ParamUtils.PD_SRCFTP_ROOT_CTLG);
			if (null != remotePathList) {
				for (String remotePath : remotePathList) {
					remotePath = FileUtils.generateNewPath(remotePath, "01", null);
					List<T6org_data> T6org_data_list = FtpUtils.detecRemFilDirList(ftpClient, remotePath);
					if (null != T6org_data_list) {
						for (int i = 0; i < T6org_data_list.size(); i++) {
							T6org_data orgDataObj = T6org_data_list.get(i);
							if (null != orgDataObj) {
								// 检查该文件信息本地是否已经存在
								String sql = "select * from T6org_data t where t.download_path = '"
										+ orgDataObj.getDownload_path() + "' and t.name_ = '" + orgDataObj.getName_()
										+ "'";
								LOG.debug("检查远程文件信息本地是否存在：" + sql);
								List<T6org_data> rltList = Db.use(ConstantInitMy.db_dataSource_main).query(sql);
								// 若远程文件信息本地不存在，则记录新文件信息
								if (rltList == null || rltList.size() == 0) {
									orgDataObj.saveGenIntId();
								} else {
									LOG.debug("文件信息本地已经写入，文件：" + orgDataObj.getDownload_path() + orgDataObj.getName_());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			T2syslogService.addLog(EnumT2sysLog.ERROR_N, DataConstants.SYS_USER_ID, DataConstants.SYS_USER_NM, "Scan files to be downloaded", "Automaticly detect remote ftp files abnormal!" + e);
			LOG.error("定时扫描检测远程ftp文件发生异常：" + e);
		}
	}

	@Override
	public void stop() {
		System.out.println("任务结束了");
	}
}
