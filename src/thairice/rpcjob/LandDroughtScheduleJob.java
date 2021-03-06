/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.Drought;
import csuduc.platform.util.ComUtil;
import csuduc.platform.util.timeUtils.GenerTimeStamp;
import thairice.config.ConfMain;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t2syslog.T2syslogService;

/**
 * @author zw
 *
 */
public class LandDroughtScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(LandDroughtScheduleJob.class);
	public static JobStatusMdl statusMdl = new JobStatusMdl();
	
	// 参数配置
	private float threshold1 = 0.2f;
	private float threshold2 = 0.4f;
	private float threshold3 = 0.6f;
	private float threshold4 = 0.8f;

	public List<T12PreProcessInf> loadDataFromDb() {
		String whereStr = sqlStr_ProcessStatus(EnumDataStatus.PDT_TYPE_Drought, EnumDataStatus.PROCESS_UN);
		// sql 查询 为了参数有序，需要进行order by
		// 查找NDVI_1的干旱数据文件
		return T12PreProcessInf.dao.find(String.format(" select * from %s where %s and data_type =%s order by data_collect_time   limit 10 ",
				T12PreProcessInf.tableName, whereStr, EnumDataStatus.DATA_TYPE_NDVI_1.getIdStr()));
	}

	public List<Drought> mdlConvert(List<T12PreProcessInf> inputs) {
		if (CollectionUtils.isEmpty(inputs)) {
			return Collections.emptyList();
		}

		// 按时间范围查找lst文件
		String dateBeg = GenerTimeStamp.TimestampToStr(inputs.get(0).getData_collect_time());
		String dateEnd = GenerTimeStamp.TimestampToStr(inputs.get(inputs.size() - 1).getData_collect_time());
		String whereStr = String.format("data_collect_time >= '%s' and data_collect_time <= '%s'  ", dateBeg, dateEnd);
		// 根据NDVI_1查找对应的LST温度文件 批量
		List<T12PreProcessInf> dataLSTs = T12PreProcessInf.dao
				.find(String.format(" select * from %s where   %s and data_type =%s and drought_st='11' order by data_collect_time   limit 100 ",
						T12PreProcessInf.tableName, whereStr, EnumDataStatus.DATA_TYPE_LST.getIdStr()));
		if (CollectionUtils.isEmpty(dataLSTs)) {
			T2syslogService.warn(userId, userName, "mdlConvert ", "isEmpty(dataLSTs)");
			return Collections.emptyList();
		}
		T12PreProcessInf lstObj = null;
		T12PreProcessInf ndviObj = null;

		List<Drought> resList  = Lists.newArrayListWithCapacity(dataLSTs.size());
		
		String imageNdviUse = null;
		
		for (int idx_lst = 0; idx_lst < dataLSTs.size(); idx_lst++) {
			// 找对应的ndvi文件，采取向前匹配原则
			lstObj = dataLSTs.get(idx_lst);
			String lstCollectTime = GenerTimeStamp.pickDateStr(lstObj.getData_collect_time());
			for(int idx_ndvi = 0; idx_ndvi < inputs.size(); idx_ndvi++){
				ndviObj = inputs.get(idx_ndvi);
				String ndviCollectTime = GenerTimeStamp.pickDateStr(ndviObj.getData_collect_time());
				if (lstCollectTime.compareToIgnoreCase(ndviCollectTime) > 0) {
					continue;
				} else if (lstCollectTime.compareToIgnoreCase(ndviCollectTime) == 0) {
					break;
				} else  {
					if (idx_ndvi > 0) {
						ndviObj = inputs.get(idx_ndvi -1);
					}
					break; 
				}
			}
			
			imageNdviUse = addFilePathName(ndviObj.getFile_path(), ndviObj.getFile_name());
			
			Drought target = new Drought();
			// 用该数据的id作为taskID
			Long id =  lstObj.getId();
			Long idndviLong = ndviObj.getId();
			target.id = id.intValue();
			target.idndvi = idndviLong.intValue();
			target.fileDate = lstCollectTime;
			target.imageLst = addFilePathName( lstObj.getFile_path() , lstObj.getFile_name());
			target.imageNdvi =imageNdviUse;
			target.imageLanduse = "E:\\Thailand_test\\landuse\\1km";
			target.shpfilelPath = "E:\\\\Thailand_test\\\\shp";
			target.outPath = "E:\\\\thairiceproduct\\\\Drought";
			target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
			target.threshold1 = threshold1;
			target.threshold2 = threshold2;
			target.threshold3 = threshold3;
			target.threshold4 = threshold4;
			
			resList.add(target);

		}

		return resList;
	}

	private void initParams() {
		List<Record> params = ConfMain.db()
				.find("SELECT value_ FROM t1parameter where parm_type_id = '10000003' order by parm__id ");
		if (CollectionUtils.isEmpty(params) || params.size() < 4) {
			// 采用默认参数
			log.error("initParams is null, use default params");
			return;
		}
		threshold1 = Float.valueOf(params.get(0).getStr("value_").trim());
		threshold2 = Float.valueOf(params.get(1).getStr("value_").trim());
		threshold3 = Float.valueOf(params.get(2).getStr("value_").trim());
		threshold4 = Float.valueOf(params.get(3).getStr("value_").trim());

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
			// 每批次读取一次参数配置
			initParams();
			// 从数据库读取数据
			List<T12PreProcessInf> dbUndoDatas = loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				T2syslogService.info(userId, userName, "drought job", "loadDataFromDb no data now");
				haveUndoData = false;
				break;
			}
			// 封装为rpc接口数据
			List<Drought> rpcTodoDatas = mdlConvert(dbUndoDatas);
			if (ComUtil.isEmptyList(rpcTodoDatas)) {
				T2syslogService.info(userId, userName, "drought job", "mdlConvert no data now");
				haveUndoData = false;
				break;
			}

			statusMdl.start(rpcTodoDatas.size());
			rpcTodoDatas.forEach(rpcData -> {
				// 调用rpc处理程序
				EnumStatus rpcRes = landDrought(rpcData, null);
				EnumDataStatus dbDataStatus = EnumDataStatus.PROCESS_SUCCE;
				// 封装rpc结果数据，入库
				if (EnumStatus.Success != rpcRes) {
					// todo 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
					dbDataStatus = EnumDataStatus.PROCESS_FAIL;
					statusMdl.failedOne();
				} else {
					statusMdl.succOne();
				}
				Record recordndvi = new Record().set(T12PreProcessInf.column_id, rpcData.idndvi)
						.set(T12PreProcessInf.column_drought_st, dbDataStatus.getIdStr());
				ConfMain.db().update(T12PreProcessInf.tableName, recordndvi);
				
				Record record = new Record().set(T12PreProcessInf.column_id, rpcData.id)
						.set(T12PreProcessInf.column_drought_st, dbDataStatus.getIdStr());
				ConfMain.db().update(T12PreProcessInf.tableName, record);
			});
		}
		statusMdl.stop();
		log.info(" <<<<job end");
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
