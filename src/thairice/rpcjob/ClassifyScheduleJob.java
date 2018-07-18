/**
 * ThaiRiceRS
 * create by zw at 2018年6月14日
 * version: v1.0
 */
package thairice.rpcjob;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Collections2;
import com.jfinal.log.Logger;
import com.jfinal.plugin.cron4j.ITask;

import RPCRice.ClassifyA;
import RPCRice.ClassifyB;
import RPCRice.PreProcess;
import csuduc.platform.util.ComUtil;
import thairice.constant.EnumStatus;
import thairice.mvc.t12preprocessinf.T12PreProcessInf;
import thairice.mvc.t6org_data.T6org_data;

/**
 * @author zw
 *
 */
public class ClassifyScheduleJob extends AbsScheduleJob implements ITask {

	private static Logger log = Logger.getLogger(ClassifyScheduleJob.class);
	
	public List<T12PreProcessInf> loadDataFromDb_A(){
		
		// todo 拼接查询条件, 还未确定, 确定后再做
		String whereStr = " 1=1";
		// sql 查询 为了参数有序，需要进行order by
		return T12PreProcessInf.dao.find( String.format(" select * from %s where %s order by row_column  limit 100 ", T12PreProcessInf.tableName, whereStr) );
	}
	
	public ClassifyA mdlConvertA(List<T12PreProcessInf> inputs ){
		if (CollectionUtils.isEmpty(inputs)) {
			throw new IllegalArgumentException("mdlConvertA 参数不全");
		}
		ClassifyA target = new ClassifyA();
		// 用该批数据的第一行id作为taskID
		target.id = inputs.get(0).getId();
		 //文件的collect time  20140102
		target.fileDate = "";
		 // 读库 Landsat预处理后图像路径
		target.imagePath = "";
		 // imagePath的文件名读取 行政区代码
		target.outCode = "";
		// 写死 行政区范围shp
		target.shpfilePath = "D:\\classify\\shp\\Thailand_SuphanburiProvince.shp";
		// 云飞 读库  t9sample_info 样本shp存放文件夹
		target.lablePath = "D:\\classify\\lable2";
		// 写死 分类结果图像存放文件夹
		target.outprePath = "D:\\Thailand_test\\preshp";
		target.pathGdalwarpS = "C:\\warmerda\\bld\\bin\\gdalwarp.exe";
		return target;
	}
	
public List<T12PreProcessInf> loadDataFromDb_B(){
		
		// 拼接查询条件
		String whereStr = " 1=1";
		// sql 查询 为了参数有序，需要进行order by
		return T12PreProcessInf.dao.find( String.format(" select * from %s where %s order by row_column  limit 100 ", T12PreProcessInf.tableName, whereStr) );
	}
	
	public ClassifyB mdlConvertB(List<T12PreProcessInf> inputs ){
		if (CollectionUtils.isEmpty(inputs)) {
			throw new IllegalArgumentException("mdlConvertB 参数不全");
		}
		ClassifyB target = new ClassifyB();
		// 用该批数据的第一行id作为taskID
		target.id = inputs.get(0).getId();
		 //文件的collect time  20140102
		target.fileDate = "";
		 // 读库 云飞写入 在产品表 行政区代码code
		target.outCode = "";
		 // 读库 云飞写入 在产品表 修正后省级水稻面积   D:\\Thailand_test\\preshp 
		target.modClassShp = "";
		// 写死 行政区范围shp
		target.shpfilePath = "D:\\classify\\shp\\Thailand_SuphanburiProvince.shp";
		// 写死 最终结果保存文件夹
		target.outPath = "D:\\Thailand_test\\classify";
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
			List<T12PreProcessInf> dbUndoDatasA =  loadDataFromDb_A();
			if (ComUtil.isEmptyList(dbUndoDatasA)) {
				log.info("no predata now");
				haveUndoData = false;
				break ;
			}
			// 封装为rpc接口数据
			ClassifyA rpcTodoDataA = mdlConvertA(dbUndoDatasA);
			 
			// 调用rpc处理程序
			 EnumStatus rpcResA = classifyA(rpcTodoDataA, null);
			 
			// 封装rpc结果数据，入库
			 if (EnumStatus.Success != rpcResA) {
				// 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
				 continue;
			} 
			// 修改标志位为成功
			 
			 // =============classifyB
			// 从数据库读取数据
				List<T12PreProcessInf> dbUndoDatasB =  loadDataFromDb_B();
				if (ComUtil.isEmptyList(dbUndoDatasB)) {
					log.info("no dbUndoDatasB now");
					continue ;
				}
				// 封装为rpc接口数据
				ClassifyB rpcTodoDataB = mdlConvertB(dbUndoDatasB);
				 
				// 调用rpc处理程序
				 EnumStatus rpcResB = classifyB(rpcTodoDataB, null);
				 
				// 封装rpc结果数据，入库
				 if (EnumStatus.Success != rpcResB) {
					// 修改标志位为失败，等待下次任务继续执行，当失败超过3次则标志位终生失败
					 continue;
				} 
				// 修改标志位为成功
				 
		}
		log.info("<<<<job end");
	}

	
	
	/* (non-Javadoc)
	 * @see com.jfinal.plugin.cron4j.ITask#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
