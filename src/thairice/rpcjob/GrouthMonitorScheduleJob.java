/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.util.List;
import java.util.Objects;

import com.jfinal.log.Logger;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.Growth;
import RPCRice.PreProcess;
import csuduc.platform.util.ComUtil;
import thairice.constant.EnumStatus;
import thairice.mvc.t6org_data.T6org_data;

/**
 * @author zw
 *
 */
public class GrouthMonitorScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(GrouthMonitorScheduleJob.class);
	
	public List<T6org_data> loadDataFromDb(){
		
		// todo 拼接查询条件, 还未确定, 确定后再做
		String whereStr = " 1=1";
		// sql 查询 为了参数有序，需要进行order by
		return T6org_data.dao.find( String.format(" select * from %s where %s order by row_column  limit 100 ", T6org_data.tableName, whereStr) );
	}
	
	public Growth mdlConvert(List<T6org_data> inputs ){
		if (Objects.isNull(inputs) || inputs.size() < 6) {
			throw new IllegalArgumentException("PreProcess 参数不全");
		}
		Growth target = new Growth();
		// 用该批数据的第一行id作为taskID
		target.id = inputs.get(0).getId();
		target.fileDate = "20180201";
		target.pathNdvi = "";
		target.imageLanduse = "D:\\grouth\\data\\Clip_N2_2017.tif";
		target.shpfilePath = "D:\\Thailand_test\\landuse";
		target.outPath = "D:\\Thailand_test\\grouth";
		target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
		target.threshold1 = 2001;
		target.threshold2 = 2016;
		target.threshold3 = -0.3f;
		target.threshold4 = -0.1f;
		return target;
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
			List<T6org_data> dbUndoDatas =  loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				return ;
			}
			// 封装为rpc接口数据
			Growth rpcTodoData = mdlConvert(dbUndoDatas);
			 
			// 调用rpc处理程序
			 EnumStatus rpcRes = growthMonitor(rpcTodoData, null);
			 
			// 封装rpc结果数据，入库
			 if (EnumStatus.Success == rpcRes) {
				 
			} else {
				// 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
			}
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
