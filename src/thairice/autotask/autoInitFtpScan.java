package thairice.autotask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.cron4j.ITask;
import thairice.constant.ConstantInitMy;
import thairice.mvc.t6org_data.T6org_data;
import thairice.utils.DataConstants;
import thairice.utils.DatesUtils;
import thairice.utils.FileUtils;
import thairice.utils.FtpUtils;
import thairice.utils.ParamUtils;

/**
 * @ClassName: autoInitFtpScan
 * @Description: FTP初始化文件扫描
 * @author: zhuchaobin
 * @date: 2018年2月28日 下午6:23:31
 * @version: 1.0 版本初成
 **/
public class autoInitFtpScan extends Controller implements ITask {
	private static Logger LOG = Logger.getLogger(autoInitFtpScan.class);

	@Override
	public void run() {
		try {
			LOG.debug("自动任务FTP初始化文件扫描开始.");
			// 获取FTP初始化文件扫描参数
			// 初始化开关
			String inlzSwtc = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_SWTC);
			// 初始数据开始日期
			String inlzStDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT);
			// 初始数据结束日期
			String inlzEdDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_EDDT);
			LOG.debug("FTP初始化文件扫描参数:开关=" + inlzSwtc + "扫描开始日期=" + inlzStDt + "扫描结束日期=" + inlzEdDt);
			// 参数校验
			if(StringUtils.isBlank(inlzSwtc)) {
				LOG.error("初始化开关为空，自动任务FTP初始化文件扫描失败");
				return;
			}
			if(StringUtils.isBlank(inlzStDt)) {
				LOG.error("初始化开始日期为空，自动任务FTP初始化文件扫描失败");
				return;
			}
			if(StringUtils.isBlank(inlzEdDt)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				inlzEdDt = sdf.format(new Date());
				LOG.debug("初始化结束日期为空，自动任务FTP初始化文件扫描结束日期取当天日期：" + inlzEdDt);
			}
			// 如果开关为打开则开始逐天扫描
			if (DataConstants.FLAGE_ON.equals(inlzSwtc)) {
				// 初始化完成关闭开关
				if (ParamUtils.updateParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_SWTC,
						DataConstants.FLAGE_OFF)) {
					LOG.info("初始化完成关闭开关关闭成功");
				} else {
					LOG.error("初始化完成关闭开关关闭失败");
				}
				List<Date> dateList = new ArrayList<Date>();
				try {
					dateList = DatesUtils.getBetweenDates(inlzStDt, inlzEdDt);
				} catch (ParseException e) {
					LOG.error("获取初始化时间区间失败:" + e);
					LOG.error("自动任务FTP初始化文件扫描失败.");
					return;
				}
				if (null != dateList) {
					//
					final FtpUtils ftpUtils = new FtpUtils();
					FTPClient ftpClient = ftpUtils.connectFtp();
					for (Date eleDate : dateList) {
//						// 更新初始数据开始日期
//						inlzStDt = ParamUtils.getParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT);						
//						String dateStr = new SimpleDateFormat("yyyyMMdd").format(eleDate);
//						if(dateStr.compareTo(inlzStDt) > 0) {
//							ParamUtils.updateParam(ParamUtils.PC_FTP_AUTO_DWLD, ParamUtils.INLZ_STDT, dateStr);
//						}

						Calendar cal = Calendar.getInstance();
						List<String> remotePathList = ParamUtils.getParamList(ParamUtils.PC_FTP_AUTO_DWLD,
								ParamUtils.PD_SRCFTP_ROOT_CTLG);
						if (null != remotePathList) {
							for (String remotePath : remotePathList) {
								remotePath = FileUtils.generateNewPath(remotePath, "01", eleDate);
								if(null == ftpClient)
									ftpClient = ftpUtils.connectFtp();
								List<T6org_data> T6org_data_list = FtpUtils.detecRemFilDirList(ftpClient, remotePath);
								if (null != T6org_data_list) {
									for (int i = 0; i < T6org_data_list.size(); i++) {
										T6org_data orgDataObj = T6org_data_list.get(i);
										if (null != orgDataObj) {
											// 检查该文件信息本地是否已经存在
//											String sql = "select * from T6org_data t where t.download_path = '"
//													+ orgDataObj.getDownload_path() + "' and t.name_ = '"
//													+ orgDataObj.getName_() + "'";
											String sql = "select * from T6org_data t where t.name_ = '"
													+ orgDataObj.getName_() + "'";
											LOG.debug("检查远程文件信息本地是否存在：" + sql);
											List<T6org_data> rltList = Db.use(ConstantInitMy.db_dataSource_main)
													.query(sql);
											// 若远程文件信息本地不存在，则记录新文件信息
											if (rltList == null || rltList.size() == 0) {
												orgDataObj.saveGenIntId();
											} else {
												LOG.debug("文件信息本地已经写入，文件：" + orgDataObj.getDownload_path()
														+ orgDataObj.getName_());
											}
										}
									}
								} else {
									LOG.debug("远程目录" + remotePath + "下返回文件列表为空");
								}
							}
						}
					}
					ftpClient.disconnect();
				}
			} else {
				LOG.info("FTP初始化文件扫描开关为关闭");
			}
		} catch (Exception e) {
			LOG.debug("FTP初始化文件扫描发生异常:" + e);
		}
	}

	@Override
	public void stop() {
		System.out.println("任务结束了");
	}
}
