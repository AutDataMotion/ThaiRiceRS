/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

import com.jfinal.log.Logger;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.Drought;
import RPCRice.PreProcess;
import csuduc.platform.util.ComUtil;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t6org_data.T6org_data;

/**
 * @author zw
 *
 */
public class LandDroughtScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(LandDroughtScheduleJob.class);
	
	public List<T12PreProcessInf> loadDataFromDb(){
		
		// todo 拼接查询条件, 还未确定, 确定后再做
		String whereStr = " 1=1";
		// sql 查询 为了参数有序，需要进行order by
		return T12PreProcessInf.dao.find( String.format(" select * from %s where %s order by row_column  limit 100 ", T6org_data.tableName, whereStr) );
	}
	
	public Drought mdlConvert(List<T12PreProcessInf> inputs ){
		if (CollectionUtils.isEmpty(inputs)) {
			throw new IllegalArgumentException("参数不全");
		}
		Drought target = new Drought();
		// 用该批数据的第一行id作为taskID
		target.id = inputs.get(0).getId();
		target.fileDate = "2017209";
		target.imageLst = "D:\\drought\\data\\Thailand_LST_2017209.tif";
		target.imageNdvi = "D:\\drought\\data\\Thailand_NDVI_2017209.tif";
		target.imageLanduse = "D:\\drought\\land\\ThailandAgriculturalLand_1km_N_n.tif";
		target.shpfilelPath = "D:\\Thailand_test\\shp";
		target.outPath = "D:\\Thailand_test\\drought";
		target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
		target.threshold1 = 0.2f;
		target.threshold2 = 0.4f;
		target.threshold3 = 0.6f;
		target.threshold4 = 0.8f;
		

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
			List<T12PreProcessInf> dbUndoDatas =  loadDataFromDb();
			if (ComUtil.isEmptyList(dbUndoDatas)) {
				log.info("no predata now");
				haveUndoData = false;
				break ;
			}
			// 封装为rpc接口数据
			Drought rpcTodoData = mdlConvert(dbUndoDatas);
			 
			// 调用rpc处理程序
			 EnumStatus rpcRes = landDrought(rpcTodoData, null);
			 
			// 封装rpc结果数据，入库
			 if (EnumStatus.Success == rpcRes) {
				 
			} else {
				// 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
			}
		}
		log.info("<<<<job all done");
	}

	
	
	/* (non-Javadoc)
	 * @see com.jfinal.plugin.cron4j.ITask#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
