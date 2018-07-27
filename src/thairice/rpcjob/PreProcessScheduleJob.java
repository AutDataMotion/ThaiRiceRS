/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.cron4j.ITask;
import com.mysql.fabric.xmlrpc.base.Data;
import com.sun.org.apache.bcel.internal.generic.NEW;

import RPCRice.PreProcess;
import csuduc.platform.util.ComUtil;
import csuduc.platform.util.timeUtils.GenerTimeStamp;
import thairice.config.ConfMain;
import thairice.config.MyConfig;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t6org_data.T6org_data;

/**
 * @author zw
 *
 */
public class PreProcessScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(PreProcessScheduleJob.class);
	
	public List<List<T6org_data>> loadDataFromDb(){
		// todo 拼接查询条件, 还未确定, 确定后再做
		// 查询下载成功的，已经构成一组的数据统计
		String sqlDownloadSucCnt = "SELECT * FROM vpretodo_collectdate where cnt = 6 and type in ('03') limit 10";
		List<Record> resDownloadSucCnt = ConfMain.db().find(sqlDownloadSucCnt);
		if (CollectionUtils.isEmpty(resDownloadSucCnt)) {
			log.warn("没有下载好的待预处理数据");
			return Lists.newArrayList();
		}
		// 根据数据统计获取具体数据  sql 查询 为了参数有序，需要进行order by
		List<List<T6org_data>> listGroupOrgDatas = resDownloadSucCnt.stream().map(sucCnt -> {
			return T6org_data.dao.find(" select * from  t6org_data where collect_time = ? and   type in ('03') order by row_column limit 6 ",  sucCnt.get(T6org_data.column_collect_time));
		}).collect(Collectors.toList());
		
		return listGroupOrgDatas;
	}
	
	public PreProcess mdlConvert(List<T6org_data> inputs ){
		if (Objects.isNull(inputs) || inputs.size() < 6) {
			throw new IllegalArgumentException("PreProcess 参数不全");
		}
		PreProcess target = new PreProcess();
		// 用该批数据的第一行id作为taskID
		target.id = inputs.get(0).getId();
		target.type = inputs.get(0).getType_();
		target.h26v06 = inputs.get(0).getName_();
		target.h27v06 = inputs.get(1).getName_();
		target.h27v07 = inputs.get(2).getName_();
		target.h27v08 = inputs.get(3).getName_();
		target.h28v07 = inputs.get(4).getName_();
		target.h28v08 = inputs.get(5).getName_();
		target.shpfile = "";// 获取样本
		target.outFile = "D:\\Preprocess\\result"; // 
		
		return target;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean haveUndoData = false;
		log.info(">>>>job begin");
		while(haveUndoData){
			// 从数据库读取数据
			List<List<T6org_data>> dbUndoDatas =  loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				break ;
			}
			
			dbUndoDatas.forEach(data -> {
				// 封装为rpc接口数据
				 PreProcess rpcTodoData = mdlConvert(data);
				// 调用rpc处理程序
				 EnumStatus rpcRes = preProcessing(rpcTodoData, null);
				// 封装rpc结果数据，入库
				 if (EnumStatus.Success == rpcRes) {
					 updateToDb(data);
				} else {
					// 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
					data.stream().forEach(d -> {
						Record record = new Record().set(T6org_data.column_id, d.getId())
								.set(T6org_data.column_status_, EnumDataStatus.PROCESS_FAIL.getId());
						ConfMain.db().update(T6org_data.tableName, record);
					});
				}
			});
		}
		log.info("<<<<job end");
	}

	public void updateToDb(List<T6org_data> inputs ){
		if (Objects.isNull(inputs)) {
			return;
		}
		List<String> sourceFilePathList = Lists.newArrayList();
		// 更新原始数据标志位
		inputs.stream().forEach(d -> {
			Record record = new Record().set(T6org_data.column_id, d.getId())
					.set(T6org_data.column_status_, EnumDataStatus.PROCESS_SUCCE.getId());
			ConfMain.db().update(T6org_data.tableName, record);
			sourceFilePathList.add((String)d.getStorage_path()+d.getName_());
		});
		// 存入预处理表
		T12PreProcessInf target = new T12PreProcessInf();
		// 用该批数据的第一行id作为taskID
		target.setData_type(EnumDataStatus.DATA_TYPE_LST.getIdStr());
		target.setSource_file_list(Joiner.on(splitChar).join(sourceFilePathList)); 
		target.setFile_path(""); 
		target.setFile_name("");
		target.setData_collect_time(inputs.get(0).getCollect_time());
		target.setDrought_st(EnumDataStatus.PROCESS_UN.getIdStr());
		target.setDrought_st(EnumDataStatus.PROCESS_UN.getIdStr());
		target.setDrought_st(EnumDataStatus.PROCESS_UN.getIdStr());
		target.setGenerate_time(GenerTimeStamp.dateToTimeStamp(new Date()));
		target.save();
		return ;
	}
	
	/* (non-Javadoc)
	 * @see com.jfinal.plugin.cron4j.ITask#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
