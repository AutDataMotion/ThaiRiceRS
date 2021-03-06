/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.PreProcess;
import csuduc.platform.util.ComUtil;
import csuduc.platform.util.timeUtils.GenerTimeStamp;
import thairice.config.ConfMain;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t6org_data.T6org_data;

/**
 * @author zw
 *
 */
public class PreProcessScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(PreProcessScheduleJob.class);

	public static JobStatusMdl statusMdl = new JobStatusMdl();
	
	public List<List<T6org_data>> loadDataFromDb() {
		// todo 拼接查询条件, 还未确定, 确定后再做
		// 查询下载成功的，已经构成一组的数据统计
		String sqlDownloadSucCnt = "SELECT * FROM vpretodo_collectdate where cnt = 6 and type_ in ('01','02','03') limit 10";
		List<Record> resDownloadSucCnt = ConfMain.db().find(sqlDownloadSucCnt);
		if (CollectionUtils.isEmpty(resDownloadSucCnt)) {
			return Lists.newArrayList();
		}
		// 根据数据统计获取具体数据 sql 查询 为了参数有序，需要进行order by
		List<List<T6org_data>> listGroupOrgDatas = resDownloadSucCnt.stream().map(sucCnt -> {
			return T6org_data.dao.find(
					" select * from  t6org_data where collect_time = ? and type_  = ?  order by row_column limit 6 "
					,sucCnt.get(T6org_data.column_collect_time)
					, sucCnt.get(T6org_data.column_type_));
		}).collect(Collectors.toList());

		return listGroupOrgDatas;
	}

	public PreProcess mdlConvert(List<T6org_data> inputs) {
		if (Objects.isNull(inputs) || inputs.size() < 6) {
			throw new IllegalArgumentException("PreProcess 参数不全");
		}
		PreProcess target = new PreProcess();
		// 用该批数据的第一行id作为taskID
		BigInteger id = inputs.get(0).getId();
		target.id = id.intValue();
		target.type = inputs.get(0).getType_();
		target.h26v06 = fetchFilePathName(inputs.get(0));
		target.h27v06 = fetchFilePathName(inputs.get(1));
		target.h27v07 = fetchFilePathName(inputs.get(2));
		target.h27v08 =fetchFilePathName(inputs.get(3));
		target.h28v07 = fetchFilePathName(inputs.get(4));
		target.h28v08 = fetchFilePathName(inputs.get(5));
		target.outFilePath = "E:\\preprocess\\result\\"; //

		String collectDateStr = inputs.get(0).getCollect_time();
		String type = EnumDataStatus.fetchDataTypeName(target.type);
		target.outFileName = String.format("%s_%s.tif", type, collectDateStr); //

		return target;
	}
	
	private String fetchFilePathName(T6org_data data){
		return (String)data.getStorage_path() + (String)data.getName_();
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
			List<List<T6org_data>> dbUndoDatas = loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				break;
			}
			statusMdl.start(dbUndoDatas.size());
			dbUndoDatas.forEach(data -> {
				// 封装为rpc接口数据
				PreProcess rpcTodoData = mdlConvert(data);
				// 调用rpc处理程序
				EnumStatus rpcRes = preProcessing(rpcTodoData, null);
				// 封装rpc结果数据，入库
				if (EnumStatus.Success == rpcRes) {
					updateToDb(data, rpcTodoData);
					statusMdl.succOne();
				} else {
					// 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
					data.stream().forEach(d -> {
						Record record = new Record().set(T6org_data.column_id, d.getId()).set(T6org_data.column_status_,
								EnumDataStatus.PROCESS_FAIL.getIdStr());
						ConfMain.db().update(T6org_data.tableName, record);
					});
					statusMdl.failedOne();
				}
			});
		}
		statusMdl.stop();
		log.info("<<<<job end");
	}

	public void updateToDb(List<T6org_data> inputs, PreProcess rpcTodoData) {
		if (Objects.isNull(inputs)) {
			return;
		}
		List<String> sourceFilePathList = Lists.newArrayList();
		// 更新原始数据标志位
		inputs.stream().forEach(d -> {
			Record record = new Record().set(T6org_data.column_id, d.getId()).set(T6org_data.column_status_,
					EnumDataStatus.PROCESS_SUCCE.getIdStr());
			ConfMain.db().update(T6org_data.tableName, record);
			sourceFilePathList.add((String) d.getStorage_path() + d.getName_());
		});
		// 存入预处理表
		Timestamp st = GenerTimeStamp.DateKeyToTimestamp(inputs.get(0).getCollect_time());
		Record target = new Record()
				.set(T12PreProcessInf.column_data_type, rpcTodoData.type)
				.set(T12PreProcessInf.column_source_file_list, Joiner.on(splitChar).join(sourceFilePathList))
				.set(T12PreProcessInf.column_file_path, rpcTodoData.outFilePath)
				.set(T12PreProcessInf.column_file_name, rpcTodoData.outFileName)
				.set(T12PreProcessInf.column_data_collect_time, st)
				.set(T12PreProcessInf.column_condition_st, EnumDataStatus.PROCESS_UN.getIdStr())
				.set(T12PreProcessInf.column_drought_st, EnumDataStatus.PROCESS_UN.getIdStr())
				.set(T12PreProcessInf.column_estimate_st, EnumDataStatus.PROCESS_UN.getIdStr())
				.set(T12PreProcessInf.column_generate_time, GenerTimeStamp.dateToTimeStamp(new Date()))
				.set(T12PreProcessInf.column_daynum, GenerTimeStamp.dayNumOfYear(st));
	  ConfMain.db().save(T12PreProcessInf.tableName, target);
		return;
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

	public static String redoDayNumsOfYear(Integer yearBeg, Integer yearEnd){
		if (Objects.isNull(yearBeg) || Objects.isNull(yearEnd)
				|| (yearEnd - yearBeg > 10)) {
			return "not support";
		}
		
		String sql = String.format("SELECT id, data_collect_time  FROM %s where date_format(data_collect_time, '%%Y') between '%d' and '%d'  limit 2000"
				,  T12PreProcessInf.tableName, yearBeg, yearEnd) ;
		List<Record> resDownloadSucCnt = ConfMain.db().find(sql);
		if (CollectionUtils.isEmpty(resDownloadSucCnt)) {
			return "no data";
		}
		resDownloadSucCnt.forEach(r -> {
			Record record = new Record()
					.set(T12PreProcessInf.column_id, r.get(T12PreProcessInf.column_id))
					.set(T12PreProcessInf.column_daynum, GenerTimeStamp.dayNumOfYear(r.get(T12PreProcessInf.column_data_collect_time)));
			ConfMain.db().update(T12PreProcessInf.tableName, record);
		});
		
		return "done";
	}
}
