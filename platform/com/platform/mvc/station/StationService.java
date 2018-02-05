package com.platform.mvc.station;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.platform.constant.ConstantInit;
import com.platform.dto.ZtreeNode;
import com.platform.mvc.base.BaseService;

public class StationService extends BaseService {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(StationService.class);

	public static final StationService service = Enhancer.enhance(StationService.class);
	
	/**
	 * 获取子节点数据
	 * @param cxt
	 * @param parentIds
	 * @return
	 */
	public List<ZtreeNode> childNodeData(String cxt, String parentIds){
		List<Station> list = null;
		if(null != parentIds){
			String sql = getSql(Station.sqlId_child);
			list = Station.dao.find(sql, parentIds);
			
		}else{
			String sql = getSql(Station.sqlId_root);
			list = Station.dao.find(sql);
		}
		
		List<ZtreeNode> nodeList = new ArrayList<ZtreeNode>();
		ZtreeNode node = null;
		
		for (Station station : list) {
			node = new ZtreeNode();
			node.setId(station.getPKValue());
			node.setName(station.getStr(Station.column_names));
			node.setIsParent(true);
			node.setIcon(cxt + "/jsFile/zTree/css/zTreeStyle/img/diy/" + station.getStr(Station.column_images));
			nodeList.add(node);
		}
		
		return nodeList;
	}
	
	/**
	 * 保存
	 * @param pIds
	 * @param names
	 * @param orderIds
	 * @return
	 */
	public String save(String pIds, String names, int orderIds) {
		Station pStation = Station.dao.findById(pIds);
		pStation.set(Station.column_isparent, "true").update();
		
		String images = "";
		if(orderIds < 2 || orderIds > 9){
			orderIds = 2;
			images = "2.png";
		}else{
			images = orderIds + ".png";
		}
		
		Station station = new Station();
		station.set(Station.column_isparent, "false");
		station.set(Station.column_parentstationids, pIds);
		station.set(Station.column_orderids, orderIds);
		station.set(Station.column_names, names);
		station.set(Station.column_images, images);
		station.save();
		
		// 缓存
		Station.dao.cacheAdd(station.getPKValue());
		
		return station.getPKValue();
	}
	
	/**
	 * 更新
	 * @param ids
	 * @param pIds
	 * @param names
	 */
	public void update(String ids, String pIds, String names) {
		Station station = Station.dao.findById(ids);
		if(null != names && !names.isEmpty()){
			//更新模块名称
			station.set(Station.column_names, names).update();
			
		}else if(null != pIds && !pIds.isEmpty()){
			//更新上级模块
			station.set(Station.column_parentstationids, pIds).update();
		}

		// 缓存
		Station.dao.cacheAdd(ids);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public boolean delete(String ids) {
		Station station = Station.dao.findById(ids);
		
		// 是否存在子节点
		if(station.getStr(Station.column_isparent).equals("true")){
			return false; //存在子节点，不能直接删除
		}

		// 修改上级节点的isparent
		Station pStation = Station.dao.findById(station.getStr(Station.column_parentstationids));
		String sql = getSql(Station.sqlId_childCount);
		Record record = Db.use(ConstantInit.db_dataSource_main).findFirst(sql, pStation.getPKValue());
		Long counts = record.getNumber("counts").longValue();
		if(counts == 1){
			pStation.set(Station.column_isparent, "false");
			pStation.update();
		}
	    
		// 缓存
		Station.dao.cacheRemove(ids);
		
		// 删除
	    Station.dao.deleteById(ids);
	    
	    return true;
	}
	
	/**
	 * 设置岗位功能
	 * @param roleIds
	 * @param moduleIds
	 * @param operatorIds
	 */
	public void setOperator(String stationIds, String moduleIds, String operatorIds){
		Station station = Station.dao.findById(stationIds);
		//station.set("moduleids", moduleIds);
		station.set(Station.column_operatorids, operatorIds).update();
		
		// 缓存
		Station.dao.cacheAdd(stationIds);
	}
	
	
}
