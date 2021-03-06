/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.Yield;
import csuduc.platform.util.ComUtil;
import csuduc.platform.util.FileUtils;
import csuduc.platform.util.timeUtils.GenerTimeStamp;
import csuduc.platform.util.tuple.Tuple2;
import csuduc.platform.util.tuple.TupleUtil;
import thairice.config.ConfMain;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t2syslog.T2syslogService;

/**
 * @author zw
 *
 */
public class LandYieldScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(LandYieldScheduleJob.class);
	public static JobStatusMdl statusMdl = new JobStatusMdl();
	
	private static String jobName = LandYieldScheduleJob.class.getSimpleName();

	private static String csvFilePath = "E:\\Thailand_data\\statistic\\";
	public List<T12PreProcessInf> loadDataFromDb() {

		String whereStr = sqlStr_ProcessStatus(EnumDataStatus.PDT_TYPE_Yield, EnumDataStatus.PROCESS_UN);
		// sql 查询 为了参数有序，需要进行order by
		return T12PreProcessInf.dao.find(String.format(" select * from %s where %s and data_type =%s order by data_collect_time limit 1 ",
				T12PreProcessInf.tableName, whereStr, EnumDataStatus.DATA_TYPE_NDVI_02.getIdStr()));
	}

	public List<Tuple2<Yield, Map<String, String>>> mdlConvert(List<T12PreProcessInf> inputs) {
		if (CollectionUtils.isEmpty(inputs)) {
			return Lists.newArrayList();
		}
		int yearBeg = 2000;
		// 前一年
		Timestamp lastDate = inputs.get(0).getData_collect_time();
		Calendar c = Calendar.getInstance();
		c.setTime(lastDate);
		c.add(Calendar.YEAR, -1);
		int yearEnd = c.get(Calendar.YEAR);
		List<Tuple2<Yield, Map<String, String>>> resList = inputs.stream().map(preObj -> {
			Yield target = new Yield();
			// 用该批数据的第一行id作为taskID
			Long id = inputs.get(0).getId();
			target.id = id.intValue();
			target.fileDate = GenerTimeStamp.pickDateStr(preObj.getData_collect_time());
			target.pathNdvi = addFilePathName(preObj.getFile_path(), preObj.getFile_name());
			target.imageLanduse = "E:\\Thailand_test\\landuse";
			target.outPath = "E:\\\\thairiceproduct\\\\Yield";
			target.shpfilePath = "E:\\\\Thailand_test\\\\shp";
			target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
			// 所有年的该月该日的历史数据 放入map
			Map<String, String> map = Maps.newHashMap();
			String whereStr = sqlStr_ProcessStatus(EnumDataStatus.PDT_TYPE_Yield, EnumDataStatus.PROCESS_SUCCE);
			List<T12PreProcessInf> listArg = T12PreProcessInf.dao.find(String.format(
					" select * from %s where  %s and data_type =%s "
					+ " and date_format(data_collect_time, '%%Y') between '%d' and '%d' "
					+ "and daynum  = %d limit 200 ",
					T12PreProcessInf.tableName, whereStr, EnumDataStatus.DATA_TYPE_NDVI_02.getIdStr()
					,yearBeg, yearEnd
					,preObj.getDaynum()
					));
			if (CollectionUtils.isEmpty(listArg)) {
				T2syslogService.error(userId, userName, jobName, "Objects.isNull(listArg)");
				return null;
			} else {
				listArg.forEach(
						e -> {
							System.out.println((String)e.getFile_name());
							map.put(String.valueOf((Long)e.getId()), addFilePathName(e.getFile_path(), e.getFile_name()));
						});
			}
			return TupleUtil.tuple(target, map);
		}).collect(Collectors.toList());

		return resList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean haveUndoData = true;
		log.info(">>>>job begin");
		while (haveUndoData) {
			// 从数据库读取数据
			List<T12PreProcessInf> dbUndoDatas = loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				break;
			}
			// 封装为rpc接口数据
			List<Tuple2<Yield, Map<String, String>>> rpcTodoDatas = mdlConvert(dbUndoDatas);
			
			if (CollectionUtils.isEmpty(rpcTodoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				break;
			}
			Tuple2 tuple2  = rpcTodoDatas.get(0);
			
			if (Objects.isNull(tuple2)) {
				log.info("no predata now");
				haveUndoData = false;
				break;
			}
			// 存在全国省code 的csv才会调用，故便利csv文件来获取code
			List<String> csvFileNames = FileUtils.getFileNames(csvFilePath);
			if (CollectionUtils.isEmpty(csvFileNames) || CollectionUtils.isEmpty(rpcTodoDatas)) {
				T2syslogService.info(userId, userName, jobName, "no csvFiles or no rpcData");
				break;
			}
			statusMdl.start(dbUndoDatas.size());
			rpcTodoDatas.forEach(rpcData -> {
				csvFileNames.forEach(csvFileName ->{
					String[] tokens = csvFileName.split("\\.");
					rpcData.first.outCode = tokens[0];
					rpcData.first.pathStatistics = csvFilePath + csvFileName;
					// 调用rpc处理程序
					EnumStatus rpcRes = landYield(rpcData.first, rpcData.second);
					EnumDataStatus dbDataStatus = EnumDataStatus.PROCESS_SUCCE;
					// 封装rpc结果数据，入库
					if (EnumStatus.Success != rpcRes) {
						// todo 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
						dbDataStatus = EnumDataStatus.PROCESS_FAIL;
						statusMdl.failedOne();
					}else {
						statusMdl.succOne();
					}
					Record record = new Record().set(T12PreProcessInf.column_id, rpcData.first.id)
							.set(T12PreProcessInf.column_estimate_st, dbDataStatus.getIdStr());
					ConfMain.db().update(T12PreProcessInf.tableName, record);
				});
				
			});
		}
		statusMdl.stop();
		log.info("<<<<job end");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jfinal.plugin.cron4j.ITask#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
