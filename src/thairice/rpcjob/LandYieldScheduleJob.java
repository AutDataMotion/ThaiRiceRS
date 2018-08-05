/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.Yield;
import csuduc.platform.util.ComUtil;
import csuduc.platform.util.timeUtils.GenerTimeStamp;
import csuduc.platform.util.tuple.Tuple2;
import csuduc.platform.util.tuple.TupleUtil;
import thairice.config.ConfMain;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;

/**
 * @author zw
 *
 */
public class LandYieldScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(LandYieldScheduleJob.class);
	
	public List<T12PreProcessInf> loadDataFromDb(){
		
		String whereStr = sqlStr_ProcessStatus(EnumDataStatus.PDT_TYPE_Yield, EnumDataStatus.PROCESS_UN);
		// sql 查询 为了参数有序，需要进行order by
		return T12PreProcessInf.dao.find( String.format(" select * from %s where   %s and data_type =%s   limit 100 "
				, T12PreProcessInf.tableName
				, whereStr
				, EnumDataStatus.PDT_TYPE_Yield.getIdStr()) );
	}
	
	public List<Yield> mdlConvert(List<T12PreProcessInf> inputs ){
		if (CollectionUtils.isEmpty(inputs)) {
			return Lists.newArrayList();
		}
		
		List<Yield> resList  = inputs.stream().map(preObj -> {
			Yield target = new Yield();
			// 用该批数据的第一行id作为taskID
			target.id = inputs.get(0).getId();
			target.fileDate = GenerTimeStamp.pickDateStr(preObj.getData_collect_time());
			target.pathNdvi = (String)preObj.getFile_path()+(String)preObj.getFile_name();
			target.outCode = "72";
			target.pathStatistics = "D:\\yield\\yield4\\statistic\\Suphanburd.csv";
			target.imageLanduse = "D:\\Thailand_test\\landuse";
			target.outPath = " E:\\\\thairiceproduct\\\\Yield";
			target.shpfilePath = "D:\\\\Thailand_test\\\\shp";
			target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
			return target;
		}).collect(Collectors.toList());

		return resList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean haveUndoData = false;
		while(haveUndoData){
			// 从数据库读取数据
			List<T12PreProcessInf> dbUndoDatas =  loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				return ;
			}
			// 封装为rpc接口数据
			List<Yield> rpcTodoDatas = mdlConvert(dbUndoDatas);
			 
			rpcTodoDatas.forEach(rpcData -> {
				// 调用rpc处理程序
				 EnumStatus rpcRes = landYield(rpcData, null);
				 EnumDataStatus dbDataStatus = EnumDataStatus.PROCESS_SUCCE;
				// 封装rpc结果数据，入库
				 if (EnumStatus.Success != rpcRes) {
					// todo 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
					 dbDataStatus = EnumDataStatus.PROCESS_FAIL;
				} 
				 Record record = new Record().set(T12PreProcessInf.column_id, rpcData.id)
							.set(T12PreProcessInf.column_estimate_st, dbDataStatus.getId());
					ConfMain.db().update(T12PreProcessInf.tableName, record);
			});
		}
		log.info("job all done");
	}

	
	
	/* (non-Javadoc)
	 * @see com.jfinal.plugin.cron4j.ITask#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
