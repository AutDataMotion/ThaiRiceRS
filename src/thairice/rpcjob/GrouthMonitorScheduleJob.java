/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.Growth;
import csuduc.platform.util.ComUtil;
import csuduc.platform.util.timeUtils.GenerTimeStamp;
import csuduc.platform.util.tuple.Tuple2;
import csuduc.platform.util.tuple.TupleUtil;
import thairice.config.ConfMain;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t6org_data.T6org_data;

/**
 * @author zw
 *
 */
public class GrouthMonitorScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(GrouthMonitorScheduleJob.class);

	// 参数配置
	private float threshold1 = 0.39f;
	private float threshold2 = 2.33f;
	private float threshold3 = 0.98f;
	private float threshold4 = 0.34f;
	private int yearBeg = 2004;
	private int yearEnd = 2019;

	private void initParams() {
		List<Record> params = ConfMain.db()
				.find("SELECT value_ FROM t1parameter where parm_type_id = '10000004' order by parm__id ");
		if (CollectionUtils.isEmpty(params) || params.size() < 6) {
			// 采用默认参数
			log.error("initParams is null, use default params");
			return;
		}
		threshold1 = Float.valueOf(params.get(0).getStr("value_").trim());
		threshold2 = Float.valueOf(params.get(1).getStr("value_").trim());
		threshold3 = Float.valueOf(params.get(2).getStr("value_").trim());
		threshold4 = Float.valueOf(params.get(3).getStr("value_").trim());
		yearBeg = Integer.valueOf(params.get(4).getStr("value_").trim());
		yearEnd = Integer.valueOf(params.get(5).getStr("value_").trim());

	}

	public List<T12PreProcessInf> loadDataFromDb() {
		// todo 拼接查询条件, 还未确定, 确定后再做
		String whereStr = sqlStr_ProcessStatus(EnumDataStatus.PDT_TYPE_Growth, EnumDataStatus.PROCESS_UN);
		// sql 查询 为了参数有序，需要进行order by
		return T12PreProcessInf.dao.find(String.format(" select * from %s where   %s and data_type =%s   limit 100 ",
				T12PreProcessInf.tableName, whereStr, EnumDataStatus.PDT_TYPE_Growth.getIdStr()));
	}

	public List<Growth> mdlConvert(List<T12PreProcessInf> inputs) {
		if (CollectionUtils.isEmpty(inputs)) {
			throw new IllegalArgumentException(" mdlConvert inputs is null");
		}
		List<Growth> resList  = inputs.stream().map(preObj -> {
			Growth target = new Growth();
			// 用该批数据的第一行id作为taskID
			target.id = preObj.getId();
			target.fileDate = GenerTimeStamp.pickDateStr(preObj.getData_collect_time());;
			target.pathNdvi = (String)preObj.getFile_path()+(String)preObj.getFile_name();
			target.imageLanduse = "D:\\grouth\\data\\Clip_N2_2017.tif";
			target.shpfilePath = "D:\\Thailand_test\\landuse";
			target.outPath = "D:\\Thailand_test\\grouth";
			target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
			target.threshold1 = threshold1;
			target.threshold2 = threshold2;
			target.threshold3 = threshold3;
			target.threshold4 = threshold4;
			return target;
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
		boolean haveUndoData = false;
		while (haveUndoData) {
			// 每批次读取一次参数配置
			initParams();
			// 从数据库读取数据
			List<T12PreProcessInf> dbUndoDatas = loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				return;
			}
			// 封装为rpc接口数据
			List<Growth> rpcTodoDatas = mdlConvert(dbUndoDatas);

			rpcTodoDatas.forEach(rpcData -> {
				// 调用rpc处理程序
				 EnumStatus rpcRes = growthMonitor(rpcData, null);
				 EnumDataStatus dbDataStatus = EnumDataStatus.PROCESS_SUCCE;
				// 封装rpc结果数据，入库
				 if (EnumStatus.Success != rpcRes) {
					// todo 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
					 dbDataStatus = EnumDataStatus.PROCESS_FAIL;
				} 
				 Record record = new Record().set(T12PreProcessInf.column_id, rpcData.id)
							.set(T12PreProcessInf.column_condition_st, dbDataStatus.getId());
					ConfMain.db().update(T6org_data.tableName, record);
			});
		}
		log.info("job all done");
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
